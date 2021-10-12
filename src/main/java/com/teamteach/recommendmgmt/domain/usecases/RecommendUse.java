package com.teamteach.recommendmgmt.domain.usecases;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import com.teamteach.recommendmgmt.domain.command.RecommendCommand;
import com.teamteach.recommendmgmt.domain.command.RecommendationCommand;
import com.teamteach.recommendmgmt.domain.command.SuggestionCommand;
import com.teamteach.recommendmgmt.domain.models.Category;
import com.teamteach.recommendmgmt.domain.models.Recommendation;
import com.teamteach.recommendmgmt.domain.models.SequenceGeneratorService;
import com.teamteach.recommendmgmt.domain.models.Suggestion;
import com.teamteach.recommendmgmt.domain.ports.in.IRecommendMgmt;
import com.teamteach.recommendmgmt.domain.ports.out.IRecommendRepository;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;
import com.teamteach.recommendmgmt.domain.responses.ObjectResponseDto;
import com.teamteach.recommendmgmt.domain.responses.RecommendResponse;
import com.teamteach.recommendmgmt.domain.responses.RecommendationDashboardResponse;
import com.teamteach.recommendmgmt.domain.responses.RecommendationResponse;
import com.teamteach.recommendmgmt.infra.persistence.dal.RecommendDAL;

public class RecommendUse implements IRecommendMgmt{

	@Autowired
    	private IRecommendRepository recommendRepository;

	@Autowired
		private JournalService journalService;

	@Autowired
		private RecommendDAL recommendDAL;

	@Autowired
        private SequenceGeneratorService sequenceGeneratorService;

    @Override
		public ObjectResponseDto findRecommendation(RecommendCommand recommendCommand, String accessToken) {
			List<Recommendation> recommendations = null;
			int newIndex = -1;
			String recommendationId = recommendCommand.getRecommendationId();
			if (recommendationId != null && !recommendationId.equals("0")) {
				recommendations = recommendRepository.getRecommendations(recommendationId);
				if (recommendCommand.getSuggestionIndex() != null) {
					newIndex = Integer.parseInt(recommendCommand.getSuggestionIndex());
				} else {
					newIndex = 1;
				}
			} else {
				recommendations = recommendRepository.getRecommend(recommendCommand.getText().split(" "));
			}
			if (recommendations != null && !recommendations.isEmpty()) {
				Recommendation recommendation = recommendations.get(0);
				recommendationId = recommendation.getId();
				List<Suggestion> suggestions = recommendation.getSuggestions();
				if (suggestions == null) {
					suggestions = new ArrayList<>();
				}
				if (newIndex == -1) {
					if (suggestions.size() > 0) {
						newIndex = journalService.getLastSuggestionIndex(recommendationId, accessToken);
						newIndex = (newIndex+1) % suggestions.size();	
					} else {
						newIndex = 1;
					}
				}
				String suggestionIndex = String.valueOf(newIndex);
				String suggestion = "";
				String url = "";
				if (suggestions.size() > 0) {
					System.out.println("newIndex: "+suggestionIndex);
					url = suggestions.get(newIndex-1).getUrl();
					suggestion = suggestions.get(newIndex-1).getSuggestion();
				}
				String categoryId = recommendation.getCategoryId();
				return ObjectResponseDto.builder()
										.success(true)
										.message("Recommendation found successfully")
										.object(new RecommendResponse(suggestion, url, categoryId, recommendationId, suggestionIndex))
										.build();
			} else {
				return ObjectResponseDto.builder()
										.success(false)
										.message("Recommendation not found")
										.object(new RecommendResponse("", "", "0", "", "0"))
										.build();
			}
		}

	@Override
		public ObjectListResponseDto<Category> findCategories() {        
			return recommendRepository.getCategories();
		}

	@Override
		public ObjectListResponseDto<RecommendationDashboardResponse> getAllRecommendations() {
			List<Recommendation> recommendations = recommendDAL.getAllRecommendations();
			RecommendationDashboardResponse recommendationDashboardResponse;
			int serialNo = 1;
			List<Suggestion> suggestions;
			List<String> urls;
			String catTitle = null;
			Map<String, String> categoryMap = new HashMap<String, String>();
			
			for (Category category : recommendDAL.getAllCategories()) {
				categoryMap.put(category.getCategoryId(), category.getTitle());
			}
			List<RecommendationDashboardResponse> recommendationDashboardResponses = new ArrayList<>();
			for(Recommendation recommendation : recommendations){
				suggestions = recommendation.getSuggestions() != null ? recommendation.getSuggestions() : null;
				urls = new ArrayList<>();
				if(suggestions == null){
					urls = null;
				} else{
					for(Suggestion suggestion : suggestions){
						urls.add(suggestion.getUrl());
					}
				}

				catTitle = categoryMap.get(recommendation.getCategoryId());
				recommendationDashboardResponse = RecommendationDashboardResponse.builder()
															.id(serialNo++)
															.recommendationId(recommendation.getId())
															.categoryId(recommendation.getCategoryId())
															.categoryTitle(catTitle != null ? catTitle:"")
															.suggestions(recommendation.getSuggestions() != null ? recommendation.getSuggestions() : null)
															.keyword(recommendation.getWord())
															.synonyms(recommendation.getSynonyms())
															.urls(urls)
															.build();
				recommendationDashboardResponses.add(recommendationDashboardResponse);
			}
			return new ObjectListResponseDto<RecommendationDashboardResponse>(
                    true, 
                    "All recommendations retrieved successfully!", 
                    recommendationDashboardResponses);
		}

	@Override
		public ObjectResponseDto getRecommendation(String recommendationId) {
			Recommendation recommendation = recommendDAL.getRecommendation(recommendationId);
			Category category = recommendDAL.getCategory(recommendation.getCategoryId());
			String titleCategory = category != null ? category.getTitle() : "Category Does not Exist";
			List<Suggestion> suggestions = recommendation.getSuggestions() != null ? recommendation.getSuggestions() : null;
			List<Suggestion> revSuggestionsList = new ArrayList<>();
			Suggestion suggestion = null;
			for(int i = suggestions.size(); i > 0; i--) {
				suggestion = suggestions.get(i-1);
				if (suggestion.getUserType() == null) suggestion.setUserType("Parent");
				suggestion.setSuggestionIndex(i);
				revSuggestionsList.add(suggestion);
			}

			
			RecommendationResponse recommendationResponse = RecommendationResponse.builder()
																	.recommendationId(recommendation.getId())
																	.categoryId(recommendation.getCategoryId())
																	.category(titleCategory)
																	.keyword(recommendation.getWord())
																	.synonyms(recommendation.getSynonyms())
																	.suggestions(revSuggestionsList)
																	.build();
			return ObjectResponseDto.builder()
                    .success(true)
                    .message("Recommendation retrieved!")
                    .object(recommendationResponse)
                    .build();
		}

	@Override
		public ObjectResponseDto storeRecommendation(RecommendationCommand recommendationCommand, String accessToken){
			String keyword = recommendationCommand.getKeyword();
			boolean exists = recommendDAL.ifRecommendationExists(keyword);
			if(exists && recommendationCommand.getRecommendationId()==null){
				return ObjectResponseDto.builder()
								.success(false)
								.message("Recommendation for this keyword already exists!")
								.object(null)
								.build();
			}
			Recommendation recommendation = Recommendation.builder()
													.id(sequenceGeneratorService.generateSequence(Recommendation.SEQUENCE_NAME))
													.categoryId(recommendationCommand.getCategoryId())
													.word(recommendationCommand.getKeyword())
													.synonyms(recommendationCommand.getSynonyms())
													.suggestions(null)
													.build();
			if(recommendationCommand.getRecommendationId() != null){
				Recommendation recommendationCheck = recommendDAL.getRecommendation(recommendationCommand.getRecommendationId());
				if(recommendationCheck == null){
					return ObjectResponseDto.builder()
								.success(false)
								.message("Recommendation does not exist!")
								.object(null)
								.build();
				} else {
					recommendation.setId(recommendationCommand.getRecommendationId());
					recommendation.setSuggestions(recommendationCheck.getSuggestions());
				}
			}
			recommendDAL.saveRecommendation(recommendation);
			return ObjectResponseDto.builder()
						.success(true)
						.message("Recommendation Saved!")
						.object(recommendation)
						.build();
		}

	@Override
		public ObjectResponseDto storeSuggestion(SuggestionCommand suggestionCommand, String accessToken){
			Recommendation recommendation = recommendDAL.getRecommendation(suggestionCommand.getRecommendationId());
			if(recommendation == null){
				return ObjectResponseDto.builder()
						.success(false)
						.message("Recommendation with ID does not exist!")
						.object(null)
						.build();
			}
			Suggestion suggestion = Suggestion.builder()
											.suggestion(suggestionCommand.getMessage())
											.url(suggestionCommand.getUrl())
											.userType(suggestionCommand.getUserType())
											.build();
			int suggestionIndex = 1;
			List<Suggestion> suggestions = recommendation.getSuggestions();
			if(suggestions == null){
				suggestions = new ArrayList<>();
				suggestion.setSuggestionIndex(suggestionIndex);
				suggestions.add(suggestion);
			} else if(suggestionCommand.getSuggestionIndex() != 0){
				suggestionIndex = suggestionCommand.getSuggestionIndex();
				suggestion.setSuggestionIndex(suggestionIndex);
				suggestions.set(suggestionIndex-1, suggestion);
			}	else{
				suggestionIndex = suggestions.size()+1;
				suggestion.setSuggestionIndex(suggestionIndex);
				suggestions.add(suggestion);
			}
			recommendation.setSuggestions(suggestions);
			recommendDAL.saveRecommendation(recommendation);
			return ObjectResponseDto.builder()
						.success(true)
						.message("Suggestion Saved!")
						.object(recommendation)
						.build();
		}
}
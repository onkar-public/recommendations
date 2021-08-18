package com.teamteach.recommendmgmt.domain.usecases;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import com.teamteach.recommendmgmt.domain.command.RecommendCommand;
import com.teamteach.recommendmgmt.domain.models.Category;
import com.teamteach.recommendmgmt.domain.models.Recommendation;
import com.teamteach.recommendmgmt.domain.models.Suggestion;
import com.teamteach.recommendmgmt.domain.ports.in.IRecommendMgmt;
import com.teamteach.recommendmgmt.domain.ports.out.IRecommendRepository;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;
import com.teamteach.recommendmgmt.domain.responses.ObjectResponseDto;
import com.teamteach.recommendmgmt.domain.responses.RecommendResponse;
import com.teamteach.recommendmgmt.domain.responses.RecommendationDashboardResponse;

public class RecommendUse implements IRecommendMgmt{

	@Autowired
    private IRecommendRepository recommendRepository;

	@Autowired
	private JournalService journalService;

    @Override
		public ObjectResponseDto findRecommendation(RecommendCommand recommendCommand, String accessToken) {
			List<Recommendation> recommendations = null;
			int newIndex = -1;
			String recommendationId = recommendCommand.getRecommendationId();
			if (recommendationId != null && !recommendationId.equals("0")) {
				recommendations = recommendRepository.getRecommendations(recommendationId);
				System.out.println(recommendations);
				if (recommendCommand.getSuggestionIndex() != null) {
					newIndex = Integer.parseInt(recommendCommand.getSuggestionIndex());
				} else {
					newIndex = 0;
				}
			} else {
				recommendations = recommendRepository.getRecommend(recommendCommand.getText().split(" "));
			}
			if (recommendations != null && !recommendations.isEmpty()) {
				Recommendation recommendation = recommendations.get(0);
				recommendationId = recommendation.getId();
				List<Suggestion> suggestions = recommendation.getSuggestions();
				if (newIndex == -1) {
					if (suggestions.size() > 0) {
						newIndex = journalService.getLastSuggestionIndex(recommendationId, accessToken);
						newIndex = (newIndex+1) % suggestions.size();	
					} else {
						newIndex = 0;
					}
				}
				String suggestionIndex = String.valueOf(newIndex);
				String suggestion = "";
				String url = "";
				if (suggestions.size() > 0) {
					url = suggestions.get(newIndex).getUrl();
					suggestion = suggestions.get(newIndex).getSuggestion();
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
			return new ObjectListResponseDto<RecommendationDashboardResponse>(
                    true, 
                    "All recommendations retrieved successfully!", 
                    null);
		}

	@Override
		public ObjectResponseDto getRecommendation(String recommendationId) {
			return ObjectResponseDto.builder()
                    .success(true)
                    .message("Recommedation retrieved!")
                    .object(null)
                    .build();
		}
}
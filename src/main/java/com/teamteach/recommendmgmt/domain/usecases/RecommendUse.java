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

public class RecommendUse implements IRecommendMgmt{

	@Autowired
    private IRecommendRepository recommendRepository;

    @Override
	public ObjectResponseDto findRecommendation(RecommendCommand recommendCommand) {
		List<Recommendation> recommendations = null;
		int oldIndex = 0;
		int newIndex = -1;
		if (recommendCommand.getRecommendationId() != null) {
			recommendations = recommendRepository.getRecommendations(recommendCommand.getRecommendationId());
			if (recommendCommand.getSuggestionIndex() != null) {
				newIndex = Integer.parseInt(recommendCommand.getSuggestionIndex());
			} else {
				newIndex = 0;
			}
		} else {
			recommendations = recommendRepository.getRecommend(recommendCommand.getText().split(" "));
			if (recommendCommand.getSuggestionIndex() != null) {
				oldIndex = Integer.parseInt(recommendCommand.getSuggestionIndex());
			}
		}
		if (recommendations != null && !recommendations.isEmpty()) {
			Recommendation recommendation = recommendations.get(0);
			String recommendationId = recommendation.getId();
			List<Suggestion> suggestions = recommendation.getSuggestions();
			if (newIndex == -1) newIndex = (oldIndex+1) % suggestions.size();
			String suggestionIndex = String.valueOf(newIndex);
			String suggestion = suggestions.get(newIndex).getSuggestion();
			String url = suggestions.get(newIndex).getUrl();
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
}
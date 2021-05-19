package com.teamteach.recommendmgmt.domain.usecases;

import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.ports.in.*;
import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.ports.out.*;
import com.teamteach.recommendmgmt.domain.responses.*;

import java.util.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecommendUse implements IRecommendMgmt{

    final IRecommendRepository recommendRepository;

    @Override
	public ObjectResponseDto findRecommendation(RecommendCommand recommendCommand) {
		List<Recommendation> recommendations = recommendRepository.getRecommend(recommendCommand.getText().split(" "));
		if (recommendations != null && !recommendations.isEmpty()) {
			String recommendationId = recommendations.get(0).getId();
			List<Suggestion> suggestions = recommendations.get(0).getSuggestions();
			int oldIndex = 0;
			if (recommendCommand.getSuggestionIndex() != null) {
				oldIndex = Integer.parseInt(recommendCommand.getSuggestionIndex());
			}
			int newIndex = (oldIndex+1) % suggestions.size();
			String suggestionIndex = String.valueOf(newIndex);
			String suggestion = suggestions.get(newIndex).getSuggestion();
			String url = suggestions.get(newIndex).getUrl();
			String categoryId = recommendations.get(0).getCategoryId();
			return ObjectResponseDto.builder()
									.success(true)
									.message("Recommendation found successfully")
									.object(new RecommendResponse(suggestion, url, categoryId, recommendationId, suggestionIndex))
									.build();
		} else {
			return ObjectResponseDto.builder()
									.success(false)
									.message("Recommendation not found")
									.build();
		}
	}

	@Override
    public ObjectListResponseDto<Category> findCategories() {        
        return recommendRepository.getCategories();
    }
}
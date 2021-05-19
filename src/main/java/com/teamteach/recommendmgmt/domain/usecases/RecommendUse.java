package com.teamteach.recommendmgmt.domain.usecases;

import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.ports.in.*;
import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.ports.out.*;
import com.teamteach.recommendmgmt.domain.responses.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecommendUse implements IRecommendMgmt{

    final IRecommendRepository recommendRepository;

	@Autowired
    private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private MongoTemplate mongoTemplate;

    @Override
	public ObjectResponseDto findRecommendation(RecommendCommand recommendCommand) {
		List<Recommendation> recommendations = recommendRepository.getRecommend(recommendCommand.getText().split(" "));
		if (recommendations != null) {
			List<Suggestion> suggestions = recommendations.get(0).getSuggestions();
			int newIndex = (recommendCommand.getTheIndex()+1) % suggestions.size();
			String suggestion = suggestions.get(newIndex).getSuggestion();
			String url = suggestions.get(newIndex).getUrl();
			String categoryId = recommendations.get(0).getCategoryId();
			return ObjectResponseDto.builder()
									.success(true)
									.message("Recommendation found successfully")
									.object(new RecommendResponse(suggestion, url, categoryId, newIndex))
									.build();
		} else {
			return ObjectResponseDto.builder()
									.success(false)
									.message("Recommendation not found")
									.build();
		}
	}

	@Override
    public ObjectListResponseDto findCategories() {        
        return recommendRepository.getCategories();
    }
}
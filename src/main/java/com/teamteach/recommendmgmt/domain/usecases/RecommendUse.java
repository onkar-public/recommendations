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
		String recommendation = "Problems with sleeping can affect the behaviour and attitude of a child. Click here for some ideas you might try.";
		if (recommendations != null) {
			//recommendation = recommendations.get(0).
		}
		RecommendResponse response = new RecommendResponse(recommendation);
		if(recommendation != null){
			return ObjectResponseDto.builder()
									.success(true)
									.message("Recommendation found successfully")
									.object(response)
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
package com.teamteach.recommendmgmt.infra.persistence.dal;

import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.ports.out.*;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RecommendDAL  implements IRecommendRepository {
    final MongoTemplate mongoTemplate;

    @Override
    public List<Recommendation> getRecommend(String[] words) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);

        Query query = TextQuery.queryText(criteria);
        return mongoTemplate.find(query, Recommendation.class);
    }

    @Override
    public ObjectListResponseDto<Category> getCategories() {
        Query query = new Query();
        List<Category> categories = mongoTemplate.find(query, Category.class);
        return new ObjectListResponseDto<>(
                true,
                "Categories retrieved successfully!",
				categories); 
    }
}
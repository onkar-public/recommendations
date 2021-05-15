package com.teamteach.recommendmgmt.infra.persistence.dal;

import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.ports.out.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Component
@RequiredArgsConstructor
public class RecommendDAL  implements IRecommendRepository {
    final MongoTemplate mongoTemplate;

    @Override
    public Recommendation getRecommend(String text) {
        Query query = new Query();
        return mongoTemplate.findOne(query, Recommendation.class);
    }
}
package com.teamteach.recommendmgmt.infra.persistence.dal;

import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.ports.out.*;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RecommendDAL  implements IRecommendRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Recommendation> getRecommend(String[] words) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);

        Query query = TextQuery.queryText(criteria);
        return mongoTemplate.find(query, Recommendation.class);
    }

    @Override
    public void saveRecommendation(Recommendation recommendation){
        mongoTemplate.save(recommendation);
    }

    @Override
    public boolean ifRecommendationExists(String keyword){
        Query query = new Query(Criteria.where("word").is(keyword));
        Recommendation recommendation = mongoTemplate.findOne(query, Recommendation.class);
        if(recommendation != null) return true;
        else return false;
    }

    @Override
    public List<Recommendation> getRecommendations(String recommendationId) {
        Query query = new Query(Criteria.where("_id").is(recommendationId));
        return mongoTemplate.find(query, Recommendation.class);
    }

    @Override
    public Recommendation getRecommendation(String recommendationId) {
        Query query = new Query(Criteria.where("_id").is(recommendationId));
        return mongoTemplate.findOne(query, Recommendation.class);
    }

    public Category getCategoryById(String categoryId) {
        Query query = new Query(Criteria.where("_id").is(categoryId));
        return mongoTemplate.findOne(query, Category.class);
    }

    @Override
    public List<Category> getAllCategories(){
        Query query = new Query();
        return mongoTemplate.find(query, Category.class);
    }

    @Override
    public List<Recommendation> getAllRecommendations(){
        Query query = new Query();
        return mongoTemplate.find(query, Recommendation.class);
    }
    
    @Override
    public Category getCategory(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Category category = mongoTemplate.findOne(query, Category.class);
        return category;
    }

    @Override
    public ObjectListResponseDto<Category> getCategories() {
        Query query = new Query();
        List<Category> categories = mongoTemplate.find(query, Category.class);
        return new ObjectListResponseDto<Category>(
                true,
                "Categories retrieved successfully!",
				categories); 
    }
}
package com.teamteach.recommendmgmt.domain.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import com.teamteach.recommendmgmt.domain.models.Suggestion;

import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor

public class RecommendationResponse {
    private String recommendationId;
    private String categoryId;
    private String category;
    private String keyword;
    private String[] synonyms;
    private List<Suggestion> suggestions;
}

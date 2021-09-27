package com.teamteach.recommendmgmt.domain.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import lombok.AllArgsConstructor;
import com.teamteach.recommendmgmt.domain.models.Suggestion;

@Data
@Builder
@AllArgsConstructor

public class RecommendationDashboardResponse {
    private int id;
    private String recommendationId;
    private String categoryId;
    private String categoryTitle;
    private String keyword;
    private String[] synonyms;
    private List<String> urls;
    private List<Suggestion> suggestions;
}

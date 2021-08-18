package com.teamteach.recommendmgmt.domain.responses;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor

public class RecommendationDashboardResponse {
    private int serialNo;
    private String keyword;
    private String[] synonyms;
    private String[] urls;
}
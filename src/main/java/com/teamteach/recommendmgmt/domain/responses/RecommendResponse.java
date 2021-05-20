package com.teamteach.recommendmgmt.domain.responses;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendResponse {
    private String recommendation;
    private String url;
    private String categoryId;
    private String recommendationId;
    private String suggestionIndex;
}
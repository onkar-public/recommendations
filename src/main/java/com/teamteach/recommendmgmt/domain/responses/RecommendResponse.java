package com.teamteach.recommendmgmt.domain.responses;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.*;

import com.teamteach.recommendmgmt.domain.models.*;

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
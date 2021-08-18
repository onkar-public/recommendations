package com.teamteach.recommendmgmt.domain.ports.in;

import com.teamteach.recommendmgmt.domain.command.RecommendCommand;
import com.teamteach.recommendmgmt.domain.models.Category;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;
import com.teamteach.recommendmgmt.domain.responses.ObjectResponseDto;
import com.teamteach.recommendmgmt.domain.responses.RecommendationDashboardResponse;

public interface IRecommendMgmt{
    ObjectResponseDto findRecommendation(RecommendCommand recommendCommand, String token);
    ObjectListResponseDto<Category> findCategories();
    ObjectListResponseDto<RecommendationDashboardResponse> getAllRecommendations();
    ObjectResponseDto getRecommendation(String id);
}
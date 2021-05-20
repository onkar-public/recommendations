package com.teamteach.recommendmgmt.domain.ports.in;

import com.teamteach.recommendmgmt.domain.command.RecommendCommand;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;
import com.teamteach.recommendmgmt.domain.responses.ObjectResponseDto;

public interface IRecommendMgmt{
    
    ObjectResponseDto findRecommendation(RecommendCommand recommendCommand);
    ObjectListResponseDto findCategories();

}
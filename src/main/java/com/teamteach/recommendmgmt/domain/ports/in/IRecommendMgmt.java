package com.teamteach.recommendmgmt.domain.ports.in;

import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.usecases.*;
import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.responses.*;

public interface IRecommendMgmt{
    
    ObjectResponseDto findRecommendation(RecommendCommand recommendCommand);
    ObjectListResponseDto findCategories();

}
package com.teamteach.recommendmgmt.domain.ports.out;

import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.responses.*;

public interface IRecommendRepository {
   Recommendation getRecommend(String text);
   ObjectListResponseDto getCategories();
}

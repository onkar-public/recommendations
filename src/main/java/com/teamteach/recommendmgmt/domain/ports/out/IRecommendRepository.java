package com.teamteach.recommendmgmt.domain.ports.out;

import com.teamteach.recommendmgmt.domain.models.*;

public interface IRecommendRepository {
   Recommendation getRecommend(String text);
}

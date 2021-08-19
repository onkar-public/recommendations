package com.teamteach.recommendmgmt.domain.ports.out;

import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.responses.*;
import java.util.*;

public interface IRecommendRepository {
   List<Recommendation> getRecommend(String[] words);
   List<Recommendation> getRecommendations(String recommendationId);
   ObjectListResponseDto<Category> getCategories();
   List<Recommendation> getAllRecommendations();
   Recommendation getRecommendation(String recommendationId);
}

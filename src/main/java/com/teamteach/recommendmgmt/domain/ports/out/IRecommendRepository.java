package com.teamteach.recommendmgmt.domain.ports.out;

import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.responses.*;
import java.util.*;

public interface IRecommendRepository {
   List<Recommendation> getRecommend(String[] words);
   ObjectListResponseDto getCategories();
}

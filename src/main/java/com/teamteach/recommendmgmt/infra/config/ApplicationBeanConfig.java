package com.teamteach.recommendmgmt.infra.config;

import com.teamteach.recommendmgmt.domain.ports.in.*;
import com.teamteach.recommendmgmt.domain.ports.out.*;
import com.teamteach.recommendmgmt.domain.usecases.*;
import com.teamteach.recommendmgmt.infra.persistence.dal.RecommendDAL;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class ApplicationBeanConfig {

    final IRecommendRepository recommendRepository;

    @Bean("recommendMgmtSvc")
    IRecommendMgmt recommendMgmt() {
        return new RecommendUse(recommendRepository);
    }
}

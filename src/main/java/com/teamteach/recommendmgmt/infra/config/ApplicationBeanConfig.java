package com.teamteach.recommendmgmt.infra.config;

import lombok.RequiredArgsConstructor;

import com.teamteach.recommendmgmt.domain.ports.in.IRecommendMgmt;
import com.teamteach.recommendmgmt.domain.usecases.RecommendUse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class ApplicationBeanConfig {
    @Bean("recommendMgmtSvc")
    IRecommendMgmt recommendMgmt() {
        return new RecommendUse();
    }
}

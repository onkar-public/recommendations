package com.teamteach.recommendmgmt.infra.config;

import com.teamteach.recommendmgmt.domain.ports.in.*;
import com.teamteach.recommendmgmt.domain.ports.out.*;
import com.teamteach.recommendmgmt.domain.usecases.*;
import com.teamteach.recommendmgmt.infra.persistence.dal.RecommendDAL;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

import com.teamteach.commons.connectors.rabbit.core.IMessagingPort;

@Configuration
@RequiredArgsConstructor
public class ApplicationBeanConfig {

    final IRecommendRepository recommendRepository;
    final MongoTemplate mongoTemplate;
    final IMessagingPort messagingPort;
    final RestTemplate restTemplate;

    @Bean("recommendMgmtSvc")
    IRecommendMgmt recommendMgmt() {
        return new RecommendUse(recommendRepository);
        //return null;
    }
}

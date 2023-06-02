package com.qtone.util.config.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.analysis")
public class AnalysisMongoConfig extends AbstractMongoConfig {

    @Primary
    @Override
    @Bean(name = "analysisMongoTemplate")
    public MongoTemplate getMongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }
}

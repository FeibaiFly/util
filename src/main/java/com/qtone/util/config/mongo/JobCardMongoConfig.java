package com.qtone.util.config.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.jobcard")
public class JobCardMongoConfig extends AbstractMongoConfig {

    @Override
    @Bean(name = "jobCardMongoTemplate")
    public MongoTemplate getMongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }
}

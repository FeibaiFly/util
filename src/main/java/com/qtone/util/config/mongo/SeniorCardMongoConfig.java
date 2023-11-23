package com.qtone.util.config.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.seniorcard")
public class SeniorCardMongoConfig extends AbstractMongoConfig {

    @Override
    @Bean(name = "seniorCardMongoTemplate")
    public MongoTemplate getMongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }
}

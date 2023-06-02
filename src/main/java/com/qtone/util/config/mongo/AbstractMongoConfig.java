package com.qtone.util.config.mongo;


import com.mongodb.MongoClientURI;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Data
@Configuration
public abstract class AbstractMongoConfig {
    String uri;

    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClientURI(uri));
    }

    abstract public MongoTemplate getMongoTemplate() throws Exception;

}

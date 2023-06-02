package com.qtone.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(
        //去除掉默认的数据库配置
        exclude = {
                DataSourceAutoConfiguration.class, MongoAutoConfiguration.class
        })
public class UtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilApplication.class, args);
    }

}

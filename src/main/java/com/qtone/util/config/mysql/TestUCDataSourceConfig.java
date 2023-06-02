package com.qtone.util.config.mysql;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.qtone.util.dao.test", sqlSessionFactoryRef = "TestUCSqlSessionFactory")
public class TestUCDataSourceConfig {
    @Bean(name = "TestUCDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test.uc")
    public DataSource getDateSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean(name = "TestUCSqlSessionFactory")
    public SqlSessionFactory TestUCSqlSessionFactory(@Qualifier("TestUCDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/test/*.xml"));
        return bean.getObject();
    }
    @Bean("TestUCSqlSessionTemplate")
    public SqlSessionTemplate TestUCSqlSessionTemplate(
            @Qualifier("TestUCSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}

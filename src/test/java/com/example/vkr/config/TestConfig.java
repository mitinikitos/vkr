package com.example.vkr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@TestPropertySource("classpath:application-test.properties")
@Profile("test")
public class TestConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "testdb.datasourse")
    @Profile("test")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Primary
//    @Bean
//    @ConfigurationProperties(prefix = "testdb.datasourse")
//    @Profile("test")
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "testdb.datasourse")
//    @Profile("test")
//    public HikariDataSource testDataSource(DataSourceProperties properties) {
//        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
//                .build();
//    }

//    @Primary
//    @Bean(name = "entityManagerFactory")
//    @Profile("test")
//    public EntityManagerFactory entityManagerFactory() {
//        return testBarEntityManagerFactory().getObject();
//    }
//
//    @Primary
//    @Bean
//    @Profile("test")
//    public EntityManager entityManager() {
//        return entityManagerFactory().createEntityManager();
//    }
//
////    @Primary
//    @Bean
//    @Profile("test")
//    public LocalContainerEntityManagerFactoryBean testBarEntityManagerFactory() {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        DataSourceProperties properties = dataSourceProperties();
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.example.vkr.ship.model", "com.example.vkr.auth.model");
//        factory.setDataSource(testDataSource(properties));
////        factory.afterPropertiesSet();
//
//        return factory;
//    }
//
//    @Bean
//    @Profile("test")
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory());
//        return txManager;
//    }
}
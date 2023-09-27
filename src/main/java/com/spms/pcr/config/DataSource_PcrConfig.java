package com.spms.pcr.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableAutoConfiguration
@EnableJpaRepositories(
    basePackages = "com.spms.pcr.DataSource_PCR", 
    entityManagerFactoryRef = "PcrEntityManager", 
    transactionManagerRef = "PcrTransactionManager"
)
public class DataSource_PcrConfig {
    @Autowired
    private Environment env;

    public DataSource_PcrConfig() {
        super();
    }

    @Bean(name = "PcrEntityManager")
    public LocalContainerEntityManagerFactoryBean PcrEntityManager() {
        final LocalContainerEntityManagerFactoryBean EntityManager = new LocalContainerEntityManagerFactoryBean();
        EntityManager.setDataSource(PcrDataSource());
        EntityManager.setPackagesToScan("com.spms.pcr.DataSource_PCR.model");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        EntityManager.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("pcr.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
        EntityManager.setJpaPropertyMap(properties);

        return EntityManager;
    }

    @Bean
    public DataSource PcrDataSource() {
 
        DriverManagerDataSource dataSource
          = new DriverManagerDataSource();
        dataSource.setDriverClassName(
          env.getProperty("pcr.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("pcr.datasource.url"));
        dataSource.setUsername(env.getProperty("pcr.datasource.username"));
        dataSource.setPassword(env.getProperty("pcr.datasource.password"));
 
        return dataSource;
    }

    @Bean(name = "PcrTransactionManager")
    public PlatformTransactionManager PcrTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(PcrEntityManager().getObject());
        return transactionManager;
    }
}

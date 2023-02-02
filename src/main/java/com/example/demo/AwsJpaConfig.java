package com.example.demo;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.Objects;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class AwsJpaConfig {
    ObjectMapper mapper = new ObjectMapper();

    private final Environment environment;

    public AwsJpaConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")
    public DataSourceProperties dataSourceProperties() throws IOException {

        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName("org.postgresql.Driver");

            dataSourceProperties.setPassword("root");
            dataSourceProperties.setUsername("root");
            dataSourceProperties.setUrl("jdbc:postgresql://localhost:5432/appdb?currentSchema=appdb");

        return dataSourceProperties;

    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return (HikariDataSource) properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}

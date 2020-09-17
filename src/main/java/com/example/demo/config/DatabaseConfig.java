package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@PropertySource({"classpath:/application.properties"})
@ComponentScan({
    "com.example.demo.repository",
    "com.example.demo.service"
})
@Configuration
public class DatabaseConfig implements EnvironmentAware {

    private Environment environment;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name", String.class));
        dataSource.setJdbcUrl(environment.getProperty("spring.datasource.url", String.class));
        dataSource.setUsername(environment.getProperty("spring.datasource.username", String.class));
        dataSource.setPassword(environment.getProperty("spring.datasource.password", String.class));
        dataSource.setAutoCommit(environment.getProperty("spring.datasource.auto-commit", Boolean.class, false));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
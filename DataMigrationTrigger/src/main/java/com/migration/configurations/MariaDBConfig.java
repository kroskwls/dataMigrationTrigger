package com.migration.configurations;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {"com.migration.mariadb"},
	entityManagerFactoryRef = "mariaEntityManagerFactory",
	transactionManagerRef = "mariaTransactionManager"
)
public class MariaDBConfig {
	@Primary
    @Bean(name="mariaProperties")
    @ConfigurationProperties("spring.maria.datasource")
    public DataSourceProperties pgDataSourceProperties() {
    	return new DataSourceProperties();
    }

	@Primary
    @Bean(name="mariaDatasource")
    @ConfigurationProperties("spring.maria.datasource")
    public DataSource pgDatasource(@Qualifier("mariaProperties") DataSourceProperties properties) {
    	return properties.initializeDataSourceBuilder().build();
    }

	@Primary
    @Bean(name="mariaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
    	EntityManagerFactoryBuilder builder,
    	@Qualifier("mariaDatasource") DataSource dataSource
	) {
    	return builder.dataSource(dataSource).packages("com.migration.mariadb.**").persistenceUnit("mariadb-datasource").build();
    }

	@Primary
    @Bean(name = "mariaTransactionManager")
    @ConfigurationProperties("spring.maria.datasource")
    public PlatformTransactionManager transactionManager(
    	@Qualifier("mariaEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

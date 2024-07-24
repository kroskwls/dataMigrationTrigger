package com.migration.configurations;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = {"com.migration.h2"},
	entityManagerFactoryRef = "h2EntityManagerFactory",
	transactionManagerRef = "h2TransactionManager"
)
public class H2Config {
    @Bean(name="h2Properties")
    @ConfigurationProperties("spring.h2.datasource")
    public DataSourceProperties pgDataSourceProperties() {
    	return new DataSourceProperties();
    }

    @Bean(name="h2Datasource")
    @ConfigurationProperties("spring.h2.datasource")
    public DataSource pgDatasource(@Qualifier("h2Properties") DataSourceProperties properties) {
    	return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name="h2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
    	EntityManagerFactoryBuilder builder,
    	@Qualifier("h2Datasource") DataSource dataSource
	) {
        Map<String, Object> properties = new HashMap<>();
        // properties.put("hibernate.hbm2ddl.auto", "create");
    	return builder.dataSource(dataSource).packages("com.migration.h2").persistenceUnit("h2-datasource").properties(properties).build();
    }

    @Bean(name = "h2TransactionManager")
    @ConfigurationProperties("spring.h2.datasource")
    public PlatformTransactionManager transactionManager(
    	@Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("h2Datasource") DataSource datasource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("sql/schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("sql/data.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}

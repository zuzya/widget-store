package me.zuzyan.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import me.zuzyan.core.properties.DataBaseProperties;

/**
 * Configuration for using SQL database
 *
 * @author Denis Zaripov
 * @created 21.01.2021 г.
 */
@Configuration
@ConditionalOnProperty(name = "storage.type", havingValue = "postgres")
@EnableConfigurationProperties({ DataBaseProperties.class })
@ComponentScan({ "me.zuzyan.core.store.db" })
@EnableJpaRepositories({ "me.zuzyan.core.store.db.repository" })
@EnableTransactionManagement
@Slf4j
public class RelationalDatabaseConfiguration {

    @Autowired
    private DataBaseProperties dbProperties;

    @Bean
    public DataSource dataSource() {

        log.info("=========== db.url {} ===========", dbProperties.getUrl());

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setMinimumIdle(1);
        hikariDataSource.setIdleTimeout(dbProperties.getIdleTimeout());
        hikariDataSource.setMaxLifetime(dbProperties.getMaxLifetime());
        hikariDataSource.setMaximumPoolSize(dbProperties.getMaximumPoolSize());
        hikariDataSource.setJdbcUrl(dbProperties.getUrl());
        hikariDataSource.setPassword(dbProperties.getPassword());
        hikariDataSource.setUsername(dbProperties.getUsername());
        hikariDataSource.setDriverClassName(dbProperties.getDriver());
        hikariDataSource.setConnectionTimeout(dbProperties.getConnectionTimeout());
        hikariDataSource.setConnectionTestQuery("SELECT 1");

        return hikariDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getPackagesToScan());
        factory.setDataSource(dataSource);
        factory.setJpaProperties(getJpaProperties());
        return factory;
    }

    @Bean
    public PlatformTransactionManager
            transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    private Properties getJpaProperties() {

        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", dbProperties.getDialect());
        properties.setProperty("hibernate.hbm2ddl.auto", dbProperties.getHbm2ddl());
        properties.setProperty("hibernate.connection.driver_class", dbProperties.getDriver());
        properties.setProperty("hibernate.show_sql", dbProperties.getShowSql());
        properties.setProperty("hibernate.connection.verifyServerCertificate", "false");
        properties.setProperty("hibernate.connection.useSSL", "false");
        properties.setProperty("hibernate.connection.isolation", "2");
        properties.setProperty("hibernate.default_schema", dbProperties.getSchema());

        // Disabling contextual LOB creation as createClob()
        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");

        return properties;
    }

    protected String getPackagesToScan() {

        return "me.zuzyan.core.store.entity";
    }
}

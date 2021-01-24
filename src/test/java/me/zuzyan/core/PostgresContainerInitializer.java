package me.zuzyan.core;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Init postgres container with testcontainers
 *
 * @author Denis Zaripov
 * @created 22.01.2021 г.
 */
@Testcontainers
public class PostgresContainerInitializer {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:latest").asCompatibleSubstituteFor("postgres"));

    @DynamicPropertySource
    static void iniPpostgreProperties(DynamicPropertyRegistry registry) {

        postgreSQLContainer.start();

        registry.add("db.url", postgreSQLContainer::getJdbcUrl);
        registry.add("db.username", postgreSQLContainer::getUsername);
        registry.add("db.password", postgreSQLContainer::getPassword);
    }
}

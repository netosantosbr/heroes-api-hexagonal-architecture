package br.com.gubee.configuration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class PostgresSQLContainerConfig {
    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:15.1");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", container::getJdbcUrl);
        registry.add("jdbc.username", container::getUsername);
        registry.add("jdbc.password", container::getPassword);
    }
}

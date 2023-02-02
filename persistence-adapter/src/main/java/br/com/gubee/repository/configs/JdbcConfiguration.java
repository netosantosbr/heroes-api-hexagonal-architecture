package br.com.gubee.repository.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
public class JdbcConfiguration {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.schema}")
    private String schema;

    @Bean
    @Profile("!test")
    public DataSource dataSource() {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(getMaxPoolSize());
        dataSource.setConnectionTimeout(TimeUnit.SECONDS.toMillis(5L));
        dataSource.setSchema(schema);
        return dataSource;
    }

    @Bean
    @Profile("test")
    public DataSource dataSourceForTest() {

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(getMaxPoolSize());
        dataSource.setConnectionTimeout(TimeUnit.SECONDS.toMillis(150L));
        dataSource.setSchema(schema);
        return dataSource;
    }

    @Bean
    @Profile("test")
    public PlatformTransactionManager platformTransactionManagerForTest() {
        return new DataSourceTransactionManager(dataSourceForTest());
    }

    @Bean
    @Profile("test")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplateForTest() {
        return new NamedParameterJdbcTemplate(dataSourceForTest());
    }

    @Bean
    @Profile("!test")
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    @Profile("!test")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    /**
     * Identifies how many connections can be opened based on Postgres recommended formula.
     *
     * @return pool size capacity
     * @see <a href="https://wiki.postgresql.org/wiki/Number_Of_Database_Connections#How_to_Find_the_Optimal_Database_Connection_Pool_Size" />
     */
    private int getMaxPoolSize() {
        return (Runtime.getRuntime().availableProcessors() * 2) + 1;
    }
}

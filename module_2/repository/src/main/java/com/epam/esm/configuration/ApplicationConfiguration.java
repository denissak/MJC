package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@PropertySource({"classpath:hikaricp.properties", "classpath:${env}.properties"})
@EnableTransactionManagement
public class ApplicationConfiguration {
/*    private Environment environment;*/

/*    @Autowired
    public ApplicationConfiguration(Environment environment) {
        this.environment = environment;
    }*/

    public static final String HIKARI_CONFIG_FILE_PATH = "/hikaricp.properties";


    @Bean
    public NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        /*HikariConfig config = new HikariConfig();
        config.setDriverClassName(environment.getProperty("driver.classname"));
        config.setJdbcUrl(environment.getProperty("jdbcUrl"));
        config.setUsername(environment.getProperty("dataSource.user"));
        config.setPassword(environment.getProperty("dataSource.password"));
        config.setMaximumPoolSize(Integer.parseInt(environment.getProperty("dataSource.maximumPoolSize")));*/
        HikariConfig config = new HikariConfig(HIKARI_CONFIG_FILE_PATH);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

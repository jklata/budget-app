package pl.jklata.budgetapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
@SuppressWarnings("unused")
public class DBConfiguration {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Profile("dev")
    @Bean
    public String devDatabaseConnection(){
        System.out.println("DB connection for DEV - H2 database");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB Connection for DEV - H2";
    }

    @Profile("prod")
    @Bean
    public String prodDatabaseConnection(){
        System.out.println("DB connection for PROD - mySQL on Docker container database");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB Connection for PROD - mySQL on Docker container database";
    }


}

package guru.springframework.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//JAVA CONFIGURATION
@Configuration
@EnableAutoConfiguration
@ComponentScan("guru.springframework")

public class JpaIntegrationConfig {
}

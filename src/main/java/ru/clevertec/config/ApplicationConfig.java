package ru.clevertec.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(
        basePackages = "ru.clevertec",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = EnableWebMvc.class
        ))
public class ApplicationConfig {

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}

package com.skopik.microsoft.translate;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@PropertySource("classpath:microsoft-api.properties")
@ContextConfiguration(value = "CoreConfiguration.java")
public class CoreConfiguration {

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}

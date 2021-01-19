package me.zuzyan.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json-mapper configuration
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
@Configuration
public class JsonMapperConfiguration {

    @Primary
    @Bean("jsonMapper")
    public ObjectMapper objectMapper() {

        return new ConfigureObjectMapper();
    }
}

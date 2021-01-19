package me.zuzyan.core.config;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static me.zuzyan.core.constants.Common.DATE_FORMAT;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * Custom implementation of {@link ObjectMapper}
 *
 * @author Denis Zaripov
 * @created 19.01.2021 г.
 */
public class ConfigureObjectMapper extends ObjectMapper {

    public ConfigureObjectMapper() {

        super();
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        setDateFormat(dateFormat);
        configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        registerModule(new ParameterNamesModule());
        registerModule(new Jdk8Module());
        registerModule(new JavaTimeModule());
        enable(SerializationFeature.INDENT_OUTPUT);
    }
}

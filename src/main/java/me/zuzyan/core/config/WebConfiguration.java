package me.zuzyan.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Descrition
 *
 * @author Denis Zaripov
 * @created 20.01.2021 г.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private MessageSource messageSource;

    @Bean
    @Primary
    public LocalValidatorFactoryBean customValidator() {

        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);

        return validatorFactoryBean;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {

        return new MethodValidationPostProcessor();
    }

}

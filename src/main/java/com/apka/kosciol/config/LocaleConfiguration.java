package com.apka.kosciol.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfiguration {

    @Value("messages")
    private String baseName;

  //  @Value("pl")
  //  private String defaultLocale;

    @Bean(name = "messages")
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
/*
    @Bean
    public LocaleResolver localeResolver(){
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale(defaultLocale));
        return acceptHeaderLocaleResolver;
    }
*/}

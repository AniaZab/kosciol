package com.apka.kosciol.config;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;
    public Translator(@Qualifier("messages") ResourceBundleMessageSource messageSource){
        this.messageSource=messageSource;
    }
    public static String [] toLocale(String[] code){
        Locale locale = LocaleContextHolder.getLocale();
        String[] codeResult = new String[code.length];
        for(int i = 0; i<codeResult.length; i++){
            messageSource.getMessage(code[i], null, locale);
        }
        return codeResult;
    }
}

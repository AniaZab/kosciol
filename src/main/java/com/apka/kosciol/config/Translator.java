package com.apka.kosciol.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class Translator {
    private final LocaleConfiguration localeConfiguration;
    private static ResourceBundleMessageSource messageSource;

    public Translator(@Qualifier("messages") ResourceBundleMessageSource messageSource,
                      LocaleConfiguration loc) {
        this.messageSource = messageSource;
        this.localeConfiguration = loc;
    }

    public String[] toLocale(String[] code) {
       Locale locale=Locale.getDefault();
        //System.out.println("mza " + locale);
        String[] codeResult = new String[code.length];
        for (int i = 0; i < codeResult.length; i++) {
            codeResult[i] = messageSource.getMessage(code[i], null, locale);
        }
        return codeResult;
    }
}

package com.apka.kosciol.service;

import com.apka.kosciol.config.LocaleConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslatorService {
    private final LocaleConfiguration localeConfiguration;
    private static ResourceBundleMessageSource messageSource;

    public TranslatorService(@Qualifier("messages") ResourceBundleMessageSource messageSource,
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

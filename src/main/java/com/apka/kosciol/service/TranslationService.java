package com.apka.kosciol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.apka.kosciol.util.TranslationCode.names;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final ResourceBundleMessageSource messages;

    public String[] getTranslation() {
        return toLocale(names);
    }

    public String[] toLocale(String[] code) {
        Locale locale = Locale.getDefault();
        String[] codeResult = new String[code.length];
        for (int i = 0; i < codeResult.length; i++) {
            codeResult[i] = messages.getMessage(code[i], null, locale);
        }
        return codeResult;
    }
}

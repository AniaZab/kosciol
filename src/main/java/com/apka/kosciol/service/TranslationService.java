package com.apka.kosciol.service;

import com.apka.kosciol.config.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.apka.kosciol.util.TranslationCode.names;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final Translator translator;

    public String[] getTranslation() {
        return translator.toLocale(names);
    }
}

package com.apka.kosciol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.apka.kosciol.util.TranslationCode.names;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslatorService translatorService;

    public String[] getTranslation() {
        return translatorService.toLocale(names);
    }
}

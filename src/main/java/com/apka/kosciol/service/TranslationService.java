package com.apka.kosciol.service;

import org.springframework.stereotype.Service;

import static com.apka.kosciol.config.Translator.toLocale;
import static com.apka.kosciol.util.TranslationCode.names;
@Service
public class TranslationService {
    public String [] getTranslation(){
        return toLocale(names);
    }
}

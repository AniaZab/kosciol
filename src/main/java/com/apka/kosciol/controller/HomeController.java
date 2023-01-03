package com.apka.kosciol.controller;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.service.EventService;
import com.apka.kosciol.service.TranslationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
@RequestMapping(value = "/main")
public class HomeController {

    private EventService eventService;
    private TranslationService translationService;

    public HomeController(EventService eventService, TranslationService translationService) {
        this.eventService = eventService;
        this.translationService = translationService;
    }

    @GetMapping()
    public String showMain(Model model) {
        setModelAttributes(model);
        return "main";
    }

    @RequestMapping("/language")
    public String setLanguage(Model model, @RequestParam("lang") String lang) {
        Locale.setDefault(new Locale(lang));
        setModelAttributes(model);
        return "main";
    }

    private void setModelAttributes(Model model) {
        String[] translation = translationService.getTranslation();
        for (int i = 0; i < translation.length; i++) {
            model.addAttribute(names[i], translation[i]);
        }
        model.addAttribute("event", new EventDto());
        model.addAttribute("user", new UserDto());
        model.addAttribute("eventsListToDisplay", eventService.getAllPublishedEvents());
    }

}

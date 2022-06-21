package com.apka.kosciol.controller;

import com.apka.kosciol.model.Event;
import com.apka.kosciol.service.TranslationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
//@RestController
@RequestMapping(value="/main")
public class HomeController {

    private TranslationService translationService;
    private List<Event> allEvents = new ArrayList<Event>();

    public HomeController(TranslationService translationService) {
        this.translationService = translationService;
        Event event = new Event();
        event.setTitle("uwielbionkoTytuł");
        event.setCategory("Uwielbienie");
        event.setDescription("Fajnie");
        event.setExtraInfo("Super");
        event.setStart_date("04/06/2030");
        event.setStart_time("12:30");
        event.setFinish_date("07/04/2030");
        event.setFinish_time("12:30");
        event.setFor_who("Młodzież");
        allEvents.add(event);
    }

    @GetMapping()
    public String getHelloWorld(Model model) {
        setModelAttributes(model);
        return "main";
    }

    @RequestMapping("/language")
    public String setLanguage(Model model, @RequestParam("lang") String lang) {
        Locale.setDefault(new Locale(lang));
        setModelAttributes(model);
        return "main";
    }

    @PostMapping("/addEvent")
    public String addEvent(Model model, @Valid Event event, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            allEvents.add(event);
        }
        setModelAttributes(model);
        return "main";
    }

    private void setModelAttributes(Model model) {
        String[] translation = translationService.getTranslation();
        for (int i = 0; i < translation.length; i++) {
            model.addAttribute(names[i], translation[i]);
        }
        model.addAttribute("event", new Event());
        model.addAttribute("eventsListToDisplay", allEvents);
    }
}

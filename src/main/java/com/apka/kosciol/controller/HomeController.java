package com.apka.kosciol.controller;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.Event;
import com.apka.kosciol.entity.MeetingCategory;
import com.apka.kosciol.entity.RecipientCategory;
import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.EventAlreadyExistException;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
@RequestMapping(value="/main")
public class HomeController {

    private TranslationService translationService;
    private List<EventDto> allEvents = new ArrayList<EventDto>();

    public HomeController(TranslationService translationService) {
        this.translationService = translationService;
        EventDto event = new EventDto();
        event.setTitle("uwielbionkoTytuł");
        event.setMeetingCategory(MeetingCategory.UWIELBIENIE);
        event.setDescription("Fajnie");
        /*event.setStartDate(LocalDate.of(2023, Month.JANUARY, 1));
        event.setStartTime(LocalTime.of(12, 0));
        event.setFinishDate(LocalDate.of(2023, Month.JANUARY, 14));
        event.setFinishTime(LocalTime.of(12, 0));*/
        event.setRecipientCategory(RecipientCategory.WSZYSCY);
        allEvents.add(event);
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

    @PostMapping("/addEvent")
    public String addEvent(Model model, @Valid EventDto event, Errors errors, BindingResult bindingResult) {
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
        model.addAttribute("event", new EventDto());
        model.addAttribute("user", new UserDto());
        model.addAttribute("eventsListToDisplay", allEvents);
    }

/*
    @GetMapping("/add")
    public String add(Model model) {
        setModelAttributes(model);
        model.addAttribute("user", new userDto());
        System.out.println("userAddGet");
        model.addAttribute("hrefLink", "/user/add");
        return "adduser"; //"userList";
    }
    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("user") @Valid userDto userDto, Errors errors, BindingResult bindingResult) {
        System.out.println("addPost1");
        setModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            try {
                userService.addNewuser(userDto);
                System.out.println("addPost");
            } catch (userAlreadyExistException eaeEx) {
                model.addAttribute("info", eaeEx.getMessage());
                model.addAttribute("hrefLink", "add");
                System.out.println("ErrorAddPost");
                return "errorAdded";
            }
            model.addAttribute("info", "Congratulations, your user has been successfully created.");
            model.addAttribute("hrefLink", "list");
            System.out.println("userAddPost");
            return "sucessfullyAdded";
        }
        else{
            String[] fields = { "title", "startDate", "startTime", "finishDate"};
            String fullEr = "";
            for (String field : fields) {
                if (errors.hasFieldErrors(field)) {
                    String er = field + "Error"+ Objects.requireNonNull(errors.getFieldError(field)).getDefaultMessage();
                    System.out.println(er);
                    fullEr+=er;
                }
            }

            model.addAttribute("info", fullEr);
            model.addAttribute("hrefLink", "/user/add");
            return "errorAdded";
        }
    }*/



}

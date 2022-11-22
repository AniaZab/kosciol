package com.apka.kosciol.controller;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.Event;
import com.apka.kosciol.entity.MeetingCategory;
import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.EventAlreadyExistException;
import com.apka.kosciol.exceptions.UserAlreadyExistException;
import com.apka.kosciol.service.EventService;
import com.apka.kosciol.service.TranslationService;
import com.apka.kosciol.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
@RequestMapping(value="/event")
public class EventController {

    private TranslationService translationService;
    private EventService eventService;

    public EventController(TranslationService translationService, EventService eventService) {
        this.translationService = translationService;
        this.eventService = eventService;
    }

    @GetMapping("/listOfAll")
    public String listOfAll(Model model) {
        setModelAttributes(model);
        List<EventDto> eventDtoList = eventService.returnAllEvents();
        model.addAttribute("eventsListToDisplay", eventDtoList);
        System.out.println("eventListGet");
        return "eventsPage";
        // https://wisdmlabs.com/blog/responsive-tables-using-css-div-tag/
        // fajna tabelka
        // https://stackoverflow.com/questions/69379932/can-i-attach-a-button-on-top-of-a-element-or-a-column-of-html-table
        // https://stackfame.com/editable-html-table-using-javascript-jquery
        // https://www.jqueryscript.net/table/jQuery-Plugin-For-Editable-Table-Rows-Table-Edits.html
    }
    @GetMapping("/add")
    public String add(Model model) {
        setModelAttributes(model);
        model.addAttribute("event", new EventDto());
        //model.addAttribute("meetingCategoriesListToDisplay", MeetingCategory.values());
        System.out.println("eventAddGet");
        return "addEvent"; //"eventList";
    }
    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("event") @Valid EventDto eventDto, Errors errors, BindingResult bindingResult) {
        setModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            try {
                eventService.addNewEvent(eventDto);
                System.out.println("addPost");
            } catch (EventAlreadyExistException uaeEx) {
                return uaeEx.getMessage(); //do popr. pozniej
            }
        }
        System.out.println("eventAddPost");
        return "addEvent"; //"eventList";
    }
    @GetMapping("/edit")
    public String edit(Model model) {
        setModelAttributes(model);
        return "eventList";
    }

    /*@PostMapping("/list")
    public String eventList(Model model, @Valid User user, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            allUsers.add(user);
        }
        System.out.println("loginPost");
        setModelAttributes(model);
        return "login";
    }*/
    private void setModelAttributes(Model model) {
        String[] translation = translationService.getTranslation();
        for (int i = 0; i < translation.length; i++) {
            model.addAttribute(names[i], translation[i]);
        }
    }
}

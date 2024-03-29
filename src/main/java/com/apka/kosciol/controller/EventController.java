package com.apka.kosciol.controller;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.exceptions.DoesNotExistException;
import com.apka.kosciol.service.EventService;
import com.apka.kosciol.service.TranslationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

    @RequestMapping("/language")
    public String setLanguage(Model model, @RequestParam("lang") String lang) {
        Locale.setDefault(new Locale(lang));
        setModelAttributes(model);
        return "main";
    }

    @GetMapping("/list")
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
        System.out.println("eventAddGet");
        model.addAttribute("hrefLink", "/event/add");
        model.addAttribute("pageEvent_add_or_edit", "add");
        return "add_edit_Event"; //"eventList";
    }
    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("event") @Valid EventDto eventDto, Errors errors, BindingResult bindingResult) {
        System.out.println("addPost1");
        setModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            try {
                eventService.addNewEvent(eventDto);
                System.out.println("addPost");
            } catch (AlreadyExistException eaeEx) {
                model.addAttribute("info", eaeEx.getMessage());
                model.addAttribute("hrefLink", "add");
                System.out.println("ErrorAddPost");
                return "error";
            }
            model.addAttribute("info", "Congratulations, your event has been successfully created.");
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("eventAddPost");
            return "success";
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
            model.addAttribute("hrefLink", "/event/add");
            return "error";
        }
    }
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) throws DoesNotExistException {
        setModelAttributes(model);
        try{
            model.addAttribute("event", eventService.findEventDtoById(id));
            model.addAttribute("hrefLink", "/event/edit/{"+id+"}");
            model.addAttribute("pageEvent_add_or_edit", "edit");
        }
        catch(DoesNotExistException dnee){
            model.addAttribute("info", dnee.getMessage());
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("ErrorEditGet");
            return "error";
        }
        return "add_edit_Event";
    }
    @PostMapping("/edit/{id}")
    public String edit(Model model, @ModelAttribute("event") @Valid EventDto eventDto, Errors errors, BindingResult bindingResult) {
            System.out.println("editPost1");
            setModelAttributes(model);
            if (!bindingResult.hasErrors()) {
                try {
                    eventService.edit(eventDto);
                    System.out.println("editPost");
                } catch (Exception eaeEx) {
                    System.out.println("Something went wrong edit");
                    model.addAttribute("info", eaeEx.getMessage() +" Something went wrong edit");
                    model.addAttribute("hrefLink", "edit/{"+eventDto.getId()+"}");
                    System.out.println("ErrorAddPost");
                    return "error";
                }
                model.addAttribute("info", "Congratulations, your event has been successfully edited.");
                model.addAttribute("hrefLink", "/user/startPage");
                System.out.println("eventEditPost");
                return "success";
            }
            else{
                System.out.println("Wrong bindingResult");
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
                model.addAttribute("hrefLink", "/event/edit/{"+eventDto.getId()+"}");
                return "error";
            }
        //return "add_edit_Event";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) throws DoesNotExistException {
        setModelAttributes(model);
        try{
            eventService.delete(id);
            model.addAttribute("info", "Congratulations, your event has been successfully deleted.");
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("eventDelete");
            return "success";
        }
        catch(DoesNotExistException dnee){
            model.addAttribute("info", dnee.getMessage());
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("ErrorDelete");
            return "error";
        }
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

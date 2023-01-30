package com.apka.kosciol.controller;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.PasswordDto;
import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.Status;
import com.apka.kosciol.exceptions.*;
import com.apka.kosciol.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
@Slf4j
public class UserController {

    private TranslationService translationService;
    private UserService userService;
    private EventService eventService;
    private RecipientService recipientService;
    private PublishService publishService;

    public UserController(PublishService publishService, EventService eventService, TranslationService translationService, UserService userService, RecipientService recipientService) {
        this.userService = userService;
        this.translationService = translationService;
        this.eventService = eventService;
        this.recipientService = recipientService;
        this.publishService = publishService;
    }

    @GetMapping("/user/publish/{id}")
    public String publish(Model model, @PathVariable Integer id) {
        setModelAttributes(model);
        try {
            EventDto eventToSend = eventService.findEventDtoById(id);
            eventService.checkIfEventCanBePublished(eventToSend);
            UserDto sender = userService.getLoggedInUser();
            List<RecipientDto> recipientList = recipientService.getRecipientsOfTheMeetingCategory(eventToSend.getMeetingCategory());
            publishService.publish(eventToSend, recipientList, sender);
            eventToSend.setStatus(Status.PUBLISHED);
            eventService.edit(eventToSend);
        } catch (DoesNotExistException | MessagingException | MissingDataException | PastDateException dnee) {
            model.addAttribute("info", dnee.getMessage());
            model.addAttribute("hrefLink", "/user/startPage");
            return "error";
        }
        model.addAttribute("hrefLink", "/user/startPage");
        model.addAttribute("info", "Congratulations, your event has been successfully published.");
        return "success";
    }

    @PostMapping("/user/edit")
    public String edit(Model model, @ModelAttribute("loggedUser") @Valid UserDto userDto, Errors errors, BindingResult bindingResult) {
        System.out.println("editPost1");
        setModelAttributes(model);
        model.addAttribute("hrefLink", "/user/startPage");
        if (!bindingResult.hasErrors()) {
            try {
                System.out.println("editPost" + userDto.getId());
                userService.edit(userDto);
            } catch (Exception eaeEx) {
                System.out.println("Something went wrong edit");
                model.addAttribute("info", eaeEx.getMessage());
                return "error";
            }
            model.addAttribute("info", "Congratulations, your data has been successfully edited.");
            System.out.println("userEditPost");
            return "success";
        } else {
            System.out.println("Wrong bindingResult");
            String[] fields = {"title", "startDate", "startTime", "finishDate"};
            String fullEr = "";
            for (String field : fields) {
                if (errors.hasFieldErrors(field)) {
                    String er = field + "Error" + Objects.requireNonNull(errors.getFieldError(field)).getDefaultMessage();
                    System.out.println(er);
                    fullEr += er;
                }
            }
            model.addAttribute("info", fullEr);
            model.addAttribute("hrefLink", "/user/edit");
            return "error";
        }
    }

    //metoda do usuniecia, byla na wszelki wypadek
    @GetMapping("/changePassword2")
    public String changePassword2(Model model) throws DoesNotExistException, WrongPasswordException {
        userService.changePassword2(new PasswordDto());
        return "error";

    }

    @PostMapping("/user/changePassword")
    public String changePassword(Model model, @ModelAttribute("passwords") @Valid PasswordDto passwords,
                                 Errors errors, BindingResult bindingResult) {
        model.addAttribute("hrefLink", "/user/startPage");
        setModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            try {
                userService.changePassword(passwords);
            } catch (Exception eaeEx) {
                model.addAttribute("info", eaeEx.getMessage());
                return "error";
            }
            model.addAttribute("info", "Congratulations, your password has been successfully edited.");
            return "success";
        } else {
            System.out.println("Wrong bindingResult");
            String[] fields = {"title", "startDate", "startTime", "finishDate"};
            String fullEr = "";
            for (String field : fields) {
                if (errors.hasFieldErrors(field)) {
                    String er = field + "Error" + Objects.requireNonNull(errors.getFieldError(field)).getDefaultMessage();
                    System.out.println(er);
                    fullEr += er;
                }
            }
            model.addAttribute("info", fullEr);
            return "error";
        }
    }

    @RequestMapping("/user/startPage/language")
    public String setLanguage(Model model, @RequestParam("lang") String lang) {
        Locale.setDefault(new Locale(lang));
        try {
            setStartPageModel(model);
        } catch (DoesNotExistException eaeEx) {
            model.addAttribute("info", eaeEx.getMessage());
            return "error";
        }
        return "usersPage";
    }

    @GetMapping("/user/startPage")
    public String startPage(Model model) {//, String whatPageToShow
        try {
            setStartPageModel(model);
        } catch (DoesNotExistException eaeEx) {
            model.addAttribute("info", eaeEx.getMessage());
            return "error";
        }
        return "usersPage";
    }

    @PostMapping("/user/register")
    public String register(Model model, @ModelAttribute("user") @Valid UserDto userDto, Errors errors, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            try {
                System.out.println("registerPost user");
                userService.registerNewUserAccount(userDto);
                System.out.println("registerPost2 user");
            } catch (AlreadyExistException uaeEx) {
                model.addAttribute("info", uaeEx.getMessage());
                model.addAttribute("hrefLink", "/user/startPage");
                model.addAttribute("whatPageToShow", "PageAddUser");
                return "error";
            }
        } else {
            System.out.println(bindingResult.hasErrors());
            String[] fields = {"login", "role", "password", "id_user"};

            for (String field : fields) {
                if (errors.hasFieldErrors(field)) {
                    System.out.println((field + "Error" + Objects.requireNonNull(errors.getFieldError(field)).getDefaultMessage()));
                }
            }
        }
        System.out.println("registerPost");
        model.addAttribute("info", "Congratulations, your account has been successfully created.");
        model.addAttribute("hrefLink", "/user/startPage");
        setModelAttributes(model);
        return "success"; //"register";
    }

    @GetMapping("/user/success")
    public String success(Model model) {
        model.addAttribute("info", "Congratulations, your account has been successfully created.");
        model.addAttribute("hrefLink", "login");

        return "success";
    }

    @GetMapping("/user/error")
    public String error(Model model) {
        model.addAttribute("info", "Error, your account has not been successfully created.");
        model.addAttribute("hrefLink", "register");

        return "error";
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        System.out.println("loginGet");
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @Valid UserDto user, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
            } catch (Exception e) {
                model.addAttribute("info", e.getMessage());
                model.addAttribute("hrefLink", "/main");
                return "error";
            }
        }
        System.out.println("loginPost");
        setModelAttributes(model);
        return "usersPage";
    }

    @PostMapping("/user/reset")
    public String reset(Model model, @Valid UserDto user, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
        }
        System.out.println("reset");
        setModelAttributes(model);
        return "main";
    }

    private void setModelAttributes(Model model) {
        String[] translation = translationService.getTranslation();
        for (int i = 0; i < translation.length; i++) {
            model.addAttribute(names[i], translation[i]);
        }
    }

    private void setStartPageModel(Model model) throws DoesNotExistException {
        setModelAttributes(model);
        model.addAttribute("user", new UserDto());
        model.addAttribute("passwords", new PasswordDto());
        model.addAttribute("loggedUser", userService.getLoggedInUser());

        List<RecipientDto> recipientDtoList = recipientService.returnAllRecipients();
        model.addAttribute("whatPageToShow", "PageEvents");
        model.addAttribute("recipientsListToDisplay", recipientDtoList);
        List<EventDto> eventDtoList = eventService.returnAllEvents();
        model.addAttribute("eventsListToDisplay", eventDtoList);
    }
}

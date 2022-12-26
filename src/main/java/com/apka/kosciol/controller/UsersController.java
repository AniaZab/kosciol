package com.apka.kosciol.controller;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.PasswordDto;
import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.Recipient;
import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.exceptions.DoesNotExistException;
import com.apka.kosciol.exceptions.WrongPasswordException;
import com.apka.kosciol.service.EventService;
import com.apka.kosciol.service.RecipientService;
import com.apka.kosciol.service.TranslationService;
import com.apka.kosciol.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
//@RequestMapping(value="/user")
@Slf4j
public class UsersController {

    private TranslationService translationService;
    private UserService userService;
    private EventService eventService;
    private RecipientService recipientService;

    public UsersController(EventService eventService, TranslationService translationService, UserService userService, RecipientService recipientService) {
        this.userService = userService;
        this.translationService = translationService;
        this.eventService = eventService;
        this.recipientService = recipientService;
    }

    @GetMapping("/user/publish/{id}")
    public String publish(Model model, @PathVariable Integer id) {
        setModelAttributes(model);
        try{
            EventDto eventToSend = eventService.findEventDtoById(id);
            List<RecipientDto> recipientList = recipientService.getRecipientsOfTheMeetingCategory(eventToSend.getMeetingCategory());
            UserDto sender = userService.getLoggedInUser();
        }
        catch(DoesNotExistException dnee){
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
                                 Errors errors, BindingResult bindingResult)
    {
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
            return "error";
        }
    }

    @RequestMapping("/user/startPage/language")
    public String setLanguage(Model model, @RequestParam("lang") String lang) {
        Locale.setDefault(new Locale(lang));
        setModelAttributes(model);
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("loggedUser", user);
        model.addAttribute("whatPageToShow", "PageChangeUserPassword");
        List<EventDto> eventDtoList = eventService.returnAllEvents();
        model.addAttribute("eventsListToDisplay", eventDtoList);
        List<RecipientDto> recipientDtoList = recipientService.returnAllRecipients();
        model.addAttribute("recipientsListToDisplay", recipientDtoList);

        return "usersPage";
    }

    @GetMapping("/user/startPage")
    public String startPage(Model model, String whatPageToShow) throws DoesNotExistException {//, String whatPageToShow
        setModelAttributes(model);
        model.addAttribute("user", new UserDto());
        model.addAttribute("passwords", new PasswordDto());
        model.addAttribute("loggedUser", userService.getLoggedInUser());
        if(Objects.isNull(whatPageToShow))
        {
            model.addAttribute("whatPageToShow", "PageChangeUserPassword");
        }
        else{
            model.addAttribute("whatPageToShow", whatPageToShow);
        }

        List<RecipientDto> recipientDtoList = recipientService.returnAllRecipients();
        model.addAttribute("recipientsListToDisplay", recipientDtoList);
        List<EventDto> eventDtoList = eventService.returnAllEvents();
        model.addAttribute("eventsListToDisplay", eventDtoList);

        return "usersPage";
    }

    //nie bedzie potrzebny
    /*@GetMapping("/user/register") //do poprawienia potem
    public String showRegistrationForm( Model model) { //WebRequest request,
        UserDto userDto = new UserDto();
        //wywolywanie uslug serwisowych
        //lapac exception w froncie
        model.addAttribute("user", userDto);
        setModelAttributes(model);
        System.out.println("registerGet");
        return "register";
    }*/

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
        }
        else{
            System.out.println(bindingResult.hasErrors());
            String[] fields = { "login", "role", "password", "id_user"};

            for (String field : fields) {
                if (errors.hasFieldErrors(field)) {
                    System.out.println((field + "Error"+ Objects.requireNonNull(errors.getFieldError(field)).getDefaultMessage()));
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
            try{
            }
            catch(Exception e){
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

/*    @PostMapping("/user/updatePassword")
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public GenericResponse changeUserPassword(Locale locale,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldpassword") String oldPassword) {
        User user = allUsers.findUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (!allUsers.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        allUsers.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }*/

    private void setModelAttributes(Model model) {
        String[] translation = translationService.getTranslation();
        for (int i = 0; i < translation.length; i++) {
            model.addAttribute(names[i], translation[i]);
        }
    }
}

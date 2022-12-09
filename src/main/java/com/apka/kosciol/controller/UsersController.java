package com.apka.kosciol.controller;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.service.EventService;
import com.apka.kosciol.service.TranslationService;
import com.apka.kosciol.service.UserService;
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
@RequestMapping(value="/user")
public class UsersController {

    private TranslationService translationService;
    private UserService userService;
    private EventService eventService;

    public UsersController(EventService eventService, TranslationService translationService, UserService userService) {
        this.userService = userService;
        this.translationService = translationService;
        this.eventService = eventService;
    }

    @PostMapping("/edit/{id}")
    public String edit(Model model, @ModelAttribute("loggedUser") @Valid UserDto userDto, Errors errors, BindingResult bindingResult) {
        System.out.println("editPost1");
        setModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            try {
                userService.edit(userDto);
                System.out.println("editPost");
            } catch (Exception eaeEx) {
                System.out.println("Something went wrong edit");
                model.addAttribute("info", eaeEx.getMessage() +" Something went wrong edit");
                model.addAttribute("hrefLink", "edit/{"+userDto.getId()+"}");
                System.out.println("ErrorAddPost");
                return "errorAdded";
            }
            model.addAttribute("info", "Congratulations, your data has been successfully edited.");
            model.addAttribute("hrefLink", "/user/startPage"); ////user/list
            System.out.println("userEditPost");
            return "sucessfullyAdded";
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
            model.addAttribute("hrefLink", "/user/edit/{"+userDto.getId()+"}");
            return "errorAdded";
        }
        //return "adduser";
    }

    @RequestMapping("/startPage/language")
    public String setLanguage(Model model, @RequestParam("lang") String lang) {
        Locale.setDefault(new Locale(lang));
        setModelAttributes(model);
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("loggedUser", user);
        model.addAttribute("whatPageToShow", "PageChangeUserPassword");
        List<EventDto> eventDtoList = eventService.returnAllEvents();
        model.addAttribute("eventsListToDisplay", eventDtoList);

        return "usersPage";
    }

    @GetMapping("/startPage")
    public String startPage(Model model, String whatPageToShow) {//, String whatPageToShow
        setModelAttributes(model);
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("loggedUser", user);
        if(Objects.isNull(whatPageToShow))
        {
            model.addAttribute("whatPageToShow", "PageChangeUserPassword");
        }
        else{
            model.addAttribute("whatPageToShow", whatPageToShow);
        }

        List<EventDto> eventDtoList = eventService.returnAllEvents();
        model.addAttribute("eventsListToDisplay", eventDtoList);

        return "usersPage";
    }

    //nie bedzie potrzebny
    @GetMapping("/register") //do poprawienia potem
    public String showRegistrationForm( Model model) { //WebRequest request,
        UserDto userDto = new UserDto();
        //wywolywanie uslug serwisowych
        //lapac exception w froncie
        model.addAttribute("user", userDto);
        setModelAttributes(model);
        System.out.println("registerGet");
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("user") @Valid UserDto userDto, Errors errors, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            try {
                System.out.println("registerPost user");
                userService.registerNewUserAccount(userDto);
                System.out.println("registerPost2 user");
            } catch (AlreadyExistException uaeEx) {
                model.addAttribute("info", uaeEx.getMessage());
                model.addAttribute("hrefLink", "startPage");
                model.addAttribute("whatPageToShow", "PageAddUser");
                return "errorAdded";
            }
            //allUsers.add(user);
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
        /*try {
            User userToRegister = new User();
            userToRegister.setLogin(user.getLogin());
            userToRegister.setPassword(user.getPassword());
            userService.registerNewUserAccount(userToRegister);
            System.out.println("registerPost user");
        } catch (UserAlreadyExistException uaeEx) {
            return uaeEx.getMessage(); //do popr. pozniej
        }*/
        System.out.println("registerPost");
        model.addAttribute("info", "Congratulations, your account has been successfully created.");
        model.addAttribute("hrefLink", "startPage");
        setModelAttributes(model);
        return "sucessfullyAdded"; //"register";
    }
    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("info", "Congratulations, your account has been successfully created.");
        model.addAttribute("hrefLink", "login");

        return "sucessfullyAdded";
    }
    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("info", "Error, your account has not been successfully created.");
        model.addAttribute("hrefLink", "register");

        return "errorAdded";
    }

    @GetMapping("/login")
    public String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        System.out.println("loginGet");
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @Valid User user, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try{
            }
            catch(Exception e){
                model.addAttribute("info", e.getMessage());
                model.addAttribute("hrefLink", "/main");
                return "errorAdded";
            }
        }
        System.out.println("loginPost");
        setModelAttributes(model);
        return "startPage";
    }

    @PostMapping("/reset")
    public String reset(Model model, @Valid User user, Errors errors, BindingResult bindingResult) {
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

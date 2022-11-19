package com.apka.kosciol.controller;

import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.Role;
import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.UserAlreadyExistException;
import com.apka.kosciol.service.TranslationService;
import com.apka.kosciol.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
@RequestMapping(value="/user")
public class UsersController {

    private TranslationService translationService;
    private UserService userService;

    public UsersController(TranslationService translationService, UserService userService) {
        this.userService = userService;
        this.translationService = translationService;
    }

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
            } catch (UserAlreadyExistException uaeEx) {
                return uaeEx.getMessage(); //do popr. pozniej
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
        setModelAttributes(model);
        return "register";
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
        }
        System.out.println("loginPost");
        setModelAttributes(model);
        return "login";
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

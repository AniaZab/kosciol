package com.apka.kosciol.controller;

import com.apka.kosciol.entity.User;
import com.apka.kosciol.service.TranslationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
@RequestMapping(value="/user")
public class UsersController {

    private TranslationService translationService;
    private List<User> allUsers = new ArrayList<User>();

    public UsersController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm( Model model) { //WebRequest request,
        User user = new User();
        model.addAttribute("user", user);
        return "usersPage";
    }
    @PostMapping("/register")
    public String register(Model model, @Valid User user, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            allUsers.add(user);
        }
        System.out.println("register");
        setModelAttributes(model);
        return "usersPage";
    }

    @PostMapping("/login")
    public String login(Model model, @Valid User user, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            allUsers.add(user);
        }
        System.out.println("login");
        setModelAttributes(model);
        return "main";
    }

    @PostMapping("/reset")
    public String reset(Model model, @Valid User user, Errors errors, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            allUsers.add(user);
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

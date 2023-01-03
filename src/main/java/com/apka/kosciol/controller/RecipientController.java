package com.apka.kosciol.controller;

import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.exceptions.DoesNotExistException;
import com.apka.kosciol.service.RecipientService;
import com.apka.kosciol.service.TranslationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
@RequestMapping(value = "/recipient")
public class RecipientController {
    private TranslationService translationService;
    private RecipientService recipientService;

    public RecipientController(TranslationService translationService, RecipientService recipientService) {
        this.translationService = translationService;
        this.recipientService = recipientService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        setModelAttributes(model);
        model.addAttribute("recipient", new RecipientDto());
        model.addAttribute("pageRecipient_add_or_edit", "add");
        System.out.println("recipientAddGet");
        model.addAttribute("hrefLink", "/recipient/add");
        return "add_edit_Recipient";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute("recipient") @Valid RecipientDto recipientDto, Errors errors, BindingResult bindingResult) {
        System.out.println("addPost1");
        setModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            try {
                recipientService.addNewRecipient(recipientDto);
                System.out.println("addPost");
            } catch (AlreadyExistException eaeEx) {
                model.addAttribute("info", eaeEx.getMessage());
                model.addAttribute("hrefLink", "/recipient/add");
                System.out.println("ErrorAddPost");
                return "error";
            }
            model.addAttribute("info", "Congratulations, your recipient has been successfully created.");
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("recipientAddPost");
            return "success";
        } else {
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
            model.addAttribute("hrefLink", "/recipient/add");
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) throws DoesNotExistException {
        setModelAttributes(model);
        try {
            model.addAttribute("recipient", recipientService.findRecipientDtoById(id));
            model.addAttribute("hrefLink", "/recipient/edit/{" + id + "}");
            model.addAttribute("pageRecipient_add_or_edit", "edit");
        } catch (DoesNotExistException dnee) {
            model.addAttribute("info", dnee.getMessage());
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("ErrorEditGet");
            return "error";
        }
        return "add_edit_Recipient";
    }

    @PostMapping("/edit/{id}")
    public String edit(Model model, @ModelAttribute("recipient") @Valid RecipientDto recipientDto, Errors errors, BindingResult bindingResult) {
        System.out.println("editPost1");
        setModelAttributes(model);
        if (!bindingResult.hasErrors()) {
            try {
                recipientService.edit(recipientDto);
                System.out.println("editPost");
            } catch (Exception eaeEx) {
                System.out.println("Something went wrong edit");
                model.addAttribute("info", eaeEx.getMessage() + " Something went wrong edit");
                model.addAttribute("hrefLink", "edit/{" + recipientDto.getId() + "}");
                System.out.println("ErrorAddPost");
                return "error";
            }
            model.addAttribute("info", "Congratulations, your recipient has been successfully edited.");
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("eventEditPost");
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
            model.addAttribute("hrefLink", "/recipient/edit/{" + recipientDto.getId() + "}");
            return "error";
        }
        //return "addEvent";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) throws DoesNotExistException {
        setModelAttributes(model);
        try {
            recipientService.delete(id);
            model.addAttribute("info", "Congratulations, your recipient has been successfully deleted.");
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("eventDelete");
            return "success";
        } catch (DoesNotExistException dnee) {
            model.addAttribute("info", dnee.getMessage());
            model.addAttribute("hrefLink", "/user/startPage");
            System.out.println("ErrorDelete");
            return "error";
        }
    }

    private void setModelAttributes(Model model) {
        String[] translation = translationService.getTranslation();
        for (int i = 0; i < translation.length; i++) {
            model.addAttribute(names[i], translation[i]);
        }
    }
}

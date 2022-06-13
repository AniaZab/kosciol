package com.apka.kosciol.controller;

import com.apka.kosciol.service.TranslationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

import static com.apka.kosciol.util.TranslationCode.names;

@Controller
//@RestController
@RequestMapping("/")
public class HomeController {

    private TranslationService translationService;

    public HomeController(TranslationService translationService) {
        this.translationService = translationService;
    }

    /*@GetMapping("/translation")
    public ResponseEntity<String> getTranslation(){
        String[] translation = translationService.getTranslation();
        return ResponseEntity.ok(translation);
    }*/
    @GetMapping("/main")
    public String getHelloWorld(Model model) {
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_pl");
        //resourceBundle.getString("");
        setModelAttributesd(model);
        return "main";
    }

    @RequestMapping("/main/en")
    public String SetUkLanguage(Model model) {
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_pl");
        Locale.setDefault(Locale.UK);
        setModelAttributesd(model);
        return "main";
    }

    @RequestMapping("/main/pl")
    public String SetPlLanguage(Model model) {
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_pl");
        Locale.setDefault(new Locale("pl"));
        setModelAttributesd(model);
        return "main";
    }
    @RequestMapping("/main/mza")
    public String setLan(Model model, @RequestParam("lang") String lang) {
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_pl");
        System.out.println("lan lan");
        Locale.setDefault(new Locale(lang));
        setModelAttributesd(model);
        return "main";
    }

    private void setModelAttributesd(Model model) {
        String[] translation = translationService.getTranslation();
       // System.out.println("mza " + List.of(translation));
        for (int i = 0; i < translation.length; i++) {
            model.addAttribute(names[i], translation[i]);
        }
    }
}

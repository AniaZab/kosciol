package com.apka.kosciol.controller;

import com.apka.kosciol.service.TranslationService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.apka.kosciol.util.TranslationCode.names;

import java.util.ResourceBundle;

@Controller
//@RestController
@RequestMapping("/")
public class HomeController {

    private TranslationService translationService;
    public HomeController(TranslationService translationService){
        this.translationService=translationService;
    }

    /*@GetMapping("/translation")
    public ResponseEntity<String> getTranslation(){
        String[] translation = translationService.getTranslation();
        return ResponseEntity.ok(translation);
    }*/
    @GetMapping("/main")
    public String getHelloWorld(Model model){
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_pl");
        //resourceBundle.getString("");
        String[] translation = translationService.getTranslation();
        for(int i = 0; i<translation.length; i++){
            model.addAttribute(names[i], translation[i]);
        }
        return "main";
    }
    public void SetLanguage(String lang, String nameOfFile){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_pl");

    }
}

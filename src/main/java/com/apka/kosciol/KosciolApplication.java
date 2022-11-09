package com.apka.kosciol;

import com.apka.kosciol.service.Helper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class KosciolApplication { //ogloszenia - annoucment - nazwa projektu, to powinno byc nazwa zamiast church
//usunac _table w nazwie
    //recipientCategory zamiast forwho - albo enum i dla category; lepiej nie usuwac
    // role - enum - wartosci pol z wielkich liter,
    public static void main(String[] args) {
        SpringApplication.run(KosciolApplication.class, args);

        System.out.println(KosciolApplication.class.getName());
        System.out.println(KosciolApplication.class.getClass());
        //ArrayList<Character> password = Helper.generatePassword(8);
        //password.forEach(System.out::print);
    }
}
// bez premium member, moze nawet bez member (user) - user i admin
/// user: login i role not null; deleted/active zamiast status, czy zalogowany to cookies sesyjne w user
/// start/finish_time - moga byc nullami by byl stan posredni
/// event: status published, draft - do uzupelnienia opisu, tytul nie null
// admin podaje login wpisuje haslo, i przekazuje userowi, przy 1. logowaniu zmienione haslo
/// user - czy byla zmiana 1. hasla
// reset hasla - wyslac kod wygenerowany losowo
// blokowanie logowania na 10 minut po kilku razach zlego hasla
// jednorazowe czy systematyczne ogloszenie - dodatkowe
/// [status] [varchar](50), /* czy juz opublikowane? */



//na pageEvent - bedzie lista jak w abapie z wszystkimi eventami, beda opcje:
// wyswietl: gotowe do publikowania/odbyte/do zmiany/ opublikowane
// dodanie eventu
// edycja własnych eventow po poprzednim zaznaczeniu z listy


//metoda PUT
//https://jtuto.com/fixed-thymeleaf-thmethoddelete-put-leads-to-request-method-post-not-supported/
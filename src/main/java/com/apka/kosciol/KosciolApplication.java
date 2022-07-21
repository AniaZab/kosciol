package com.apka.kosciol;

import com.apka.kosciol.service.Helper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class KosciolApplication {

    public static void main(String[] args) {
        SpringApplication.run(KosciolApplication.class, args);
        //ArrayList<Character> password = Helper.generatePassword(8);
        //password.forEach(System.out::print);
    }

}

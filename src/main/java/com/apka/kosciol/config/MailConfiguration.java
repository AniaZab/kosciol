package com.apka.kosciol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {
   /* public static Properties getConfiguration(){
        Properties prop = System.getProperties();
        //props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        //props.setProperty("mail.smtp.socketFactory.fallback", "false");
        //prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.auth", true); // włączenie autoryzacji
        prop.put("mail.smtp.starttls.enable", "true"); //ukrywamy protokół
        prop.put("mail.smtp.host", "smtp.gmail.com"); // adres hosta
        prop.put("mail.smtp.port", "587"); //587 - javax.mail.MessagingException: Could not convert socket to TLS;
        //prop.put("mail.debug", "true");
        return prop;
    }*/
    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSender mailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) mailSender).setHost("smtp.gmail.com");
        ((JavaMailSenderImpl) mailSender).setPort(587);

        ((JavaMailSenderImpl) mailSender).setUsername("aniawiller@gmail.com");
        ((JavaMailSenderImpl) mailSender).setPassword("nycqgvykvabrkvnz");

        Properties props = ((JavaMailSenderImpl) mailSender).getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}

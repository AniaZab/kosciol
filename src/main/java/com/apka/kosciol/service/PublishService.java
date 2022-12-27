package com.apka.kosciol.service;

import com.apka.kosciol.config.MailConfiguration;
import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service("emailService")
@RequiredArgsConstructor
public class PublishService {

    @Autowired
    private JavaMailSender mailSender;
    /*
    private EventDto eventToSend;
    private List<RecipientDto> recipientDtoList;
    private UserDto sender;

    public PublishService(EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender) {
        this.eventToSend = eventToSend;
        this.recipientDtoList = recipientDtoList;
        this.sender = sender;
    }*/

    //EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender
    public void publish(EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender){
        if(eventToSend.getEmailPublish()){
            publishEmail(eventToSend, recipientDtoList, sender);
        }
        if(eventToSend.getFacebookPublish()){
            publishFacebook();
        }
        if(eventToSend.getMessengerPublish()){
            publishMessenger();
        }
    }
    private void publishEmail(EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender.getEmail());
        message.setTo(sender.getEmail());
        message.setText("cos");
        message.setSubject("subject");
        mailSender.send(message);
        System.out.println("Sent message successfully....");
    }

    /*private void publishEmail2(){
        String from = sender.getEmail();
        Authenticator auth = new Email_Autherticator();

        //prop.put("mail.smtp.host", "smtp.mailtrap.io");
        //prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

       // prop.setProperty("mail.smtp.host", host);

        Session session = Session.getInstance(MailConfiguration.getConfiguration(), auth);
        *//*Session session = Session.getDefaultInstance(prop,
                new Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("username", "password");
                    }});*//*
        System.out.println("auth");
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (RecipientDto recipientDto:
                    recipientDtoList) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientDto.getEmail()));
            }
            //message.setHeader();
            message.setSubject(eventToSend.getTitle());

            // Now set the actual message
            message.setText(eventToSend.getDescription(), "text/html; charset=utf-8");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        //prepareEmail();
    }*/

    private void publishFacebook(){}

    private void publishMessenger(){}

    private void prepareEmail(){

    }
}

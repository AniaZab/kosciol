package com.apka.kosciol.service;

import com.apka.kosciol.config.MailConfiguration;
import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
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
    /*private void publishEmail(EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender.getEmail());
        message.setTo(sender.getEmail());
        message.setText("cos");
        message.setSubject("subject");
        mailSender.send(message);
        // link do strony: https://howtodoinjava.com/spring-core/send-email-with-spring-javamailsenderimpl-example/
        // link do yt: https://www.youtube.com/watch?v=ugIUObNHZdo&t=213s
        System.out.println("Sent message successfully....");
    }*/
    private void publishEmail(EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender){
        String from = sender.getEmail();
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setFrom(new InternetAddress(from));
                for (RecipientDto recipientDto:
                        recipientDtoList) {
                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientDto.getEmail()));
                }
                mimeMessage.setSubject(eventToSend.getMeetingCategory().getDisplayValue() + ": " + eventToSend.getTitle());
                mimeMessage.setText(eventToSend.getDescription());


                /*MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);

                FileSystemResource res = new FileSystemResource(new File("C:\\Users\\aniaz\\Documents\\studia\\semestr6\\progr_w_int\\kosciol\\src\\main\\resources\\static\\main\\bible.jpg"));
                helper.addInline("identifier1234", res);*/
            }
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
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

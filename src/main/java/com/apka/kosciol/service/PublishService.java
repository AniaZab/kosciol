package com.apka.kosciol.service;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.dto.UserDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// z czego korzystałam
//https://github.com/Java-Techie-jt/spring-boot-email-freemarker/blob/master/src/main/java/com/javatechie/email/api/service/EmailService.java

@Service("emailService")
@RequiredArgsConstructor
public class PublishService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    Configuration config;

    public void publish(EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender) throws MessagingException {
        if (eventToSend.getEmailPublish()) {
            publishEmail(eventToSend, recipientDtoList, sender);
        }
        if (eventToSend.getFacebookPublish()) {
            publishFacebook();
        }
        if (eventToSend.getMessengerPublish()) {
            publishMessenger();
        }
    }

    private void publishEmail(EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender) throws MessagingException {
        String from = sender.getEmail();
        MimeMessage message = mailSender.createMimeMessage();
        for (RecipientDto recipientDto :
                recipientDtoList) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientDto.getEmail()));
        }
        try {
            MimeMessageHelper mimeMessage = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mimeMessage.setFrom(new InternetAddress(from));

            mimeMessage.setSubject(eventToSend.getMeetingCategory().getDisplayValue() + ": " + eventToSend.getTitle());
            Map<String, Object> model = new HashMap<>();
            model.put("Name", sender.getLogin());
            model.put("location", "Bangalore,India");
            mimeMessage.setText(geContentFromTemplate(model), true);
            mailSender.send(message);
            System.out.println("Sent message successfully....");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String geContentFromTemplate(Map<String, Object> model) {
        try {
            Template t = config.getTemplate("mail-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cos nie tak";
    }


    private void publishFacebook() {
    }

    private void publishMessenger() {
    }

    private void prepareEmail() {

    }
}

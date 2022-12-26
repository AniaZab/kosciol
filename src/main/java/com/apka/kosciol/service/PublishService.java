package com.apka.kosciol.service;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublishService {
    EventDto eventToSend; List<RecipientDto> recipientDtoList; UserDto sender;
    //EventDto eventToSend, List<RecipientDto> recipientDtoList, UserDto sender
    public void publish(){
        if(eventToSend.getEmailPublish()){
            publishEmail();
        }
        if(eventToSend.getFacebookPublish()){
            publishFacebook();
        }
        if(eventToSend.getMessengerPublish()){
            publishMessenger();
        }
    }

    private void prepareEmail(){}

    private void publishEmail(){}

    private void publishFacebook(){}

    private void publishMessenger(){}
}

package com.apka.kosciol.service;
import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.dto.UserDto;
import com.apka.kosciol.entity.Event;
import com.apka.kosciol.entity.Role;
import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.EventAlreadyExistException;
import com.apka.kosciol.exceptions.UserAlreadyExistException;
import com.apka.kosciol.repository.IEvent;
import com.apka.kosciol.repository.IUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService extends AbstractChangeService{

    private IEvent eventRepository;

    public EventService(IEvent eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    @Override
    public long count() {
        return eventRepository.count();
    }

    @Override
    public void delete(Object changeEntity) {
        if(changeEntity instanceof Event && changeEntity != null){
            eventRepository.delete((Event) changeEntity);
        }
        else{
            System.err.println("Your event doesn't have data.");
        }
    }

    @Override
    public void save(Object changeEntity) {
        if(changeEntity instanceof Event && changeEntity != null){
            eventRepository.save((Event) changeEntity);
        }
        else{
            System.err.println("Your event doesn't have data.");
        }
    }

    @Override
    public Optional<Object> findById(int id) {
        return Optional.of(eventRepository.findById(id));
    }

    public EventDto findEventById(int id) {
        Event event = eventRepository.getOne(id);
        return setAllFieldsOfEventDto(event);
    }

    public List<EventDto> returnAllEvents(){
        List<EventDto> listEventDto = new ArrayList<>();
        return listEventDto;
    }


    @Override
    public void update(int id, Object changeEntity) {
        EventDto eventDto;
        if(changeEntity instanceof EventDto && changeEntity != null){
            eventDto = ((EventDto) changeEntity);
        }
        else{
            System.err.println("Your event doesn't have data.");
            return;
        }
        Event event = eventRepository.getOne(id);
        event=setAllFieldsOfEvent(eventDto, event, false);

        eventRepository.save(event);
    }
    public void addNewEvent(EventDto eventDto) throws EventAlreadyExistException {
        if (titleExists(eventDto.getTitle())) {
            throw new EventAlreadyExistException("There is an event with that title: "
                    + eventDto.getTitle());
        }
        Event event = new Event();
        event = setAllFieldsOfEvent(eventDto, event, true);
        eventRepository.save(event);
    }
    private boolean titleExists(String title) {
        return eventRepository.existsEventByTitle(title); //metoda exist, w repo ja napisac
    }
    private Event setAllFieldsOfEvent(EventDto eventDto, Event event, boolean ifNew){
        //te co musza byc:
        event.setTitle(eventDto.getTitle());
        event.setEmailPublish(eventDto.getEmailPublish());
        event.setFacebookPublish(eventDto.getFacebookPublish());
        event.setMessengerPublish(eventDto.getMessengerPublish());
        //te co sa opcjonalne:
        if(ifNew){
            event.setVersion(1);
        }
        else{
            event.setVersion(event.getVersion()+1);
        }
        event.setMeetingCategory(eventDto.getMeetingCategory());
        event.setRecipientCategory(eventDto.getRecipientCategory());
        event.setStartDate(eventDto.getStartDate());
        event.setStartTime(eventDto.getStartTime());
        event.setFinishDate(eventDto.getFinishDate());
        event.setFinishTime(eventDto.getFinishTime());
        event.setDescription(eventDto.getDescription());

        event.setStatus(eventDto.getStatus());
        return event;
    }
    private EventDto setAllFieldsOfEventDto(Event event){
        //te co musza byc:
        EventDto eventDto = new EventDto();
        eventDto.setTitle(event.getTitle());
        eventDto.setEmailPublish(event.getEmailPublish());
        eventDto.setFacebookPublish(event.getFacebookPublish());
        eventDto.setMessengerPublish(event.getMessengerPublish());
        //te co sa opcjonalne:
        eventDto.setMeetingCategory(event.getMeetingCategory());
        eventDto.setRecipientCategory(event.getRecipientCategory());
        eventDto.setStartDate(event.getStartDate());
        eventDto.setStartTime(event.getStartTime());
        eventDto.setFinishDate(event.getFinishDate());
        eventDto.setFinishTime(event.getFinishTime());
        eventDto.setDescription(event.getDescription());

        eventDto.setStatus(eventDto.getStatus());
        return eventDto;
    }
}

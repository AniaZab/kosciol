package com.apka.kosciol.service;

import com.apka.kosciol.dto.EventDto;
import com.apka.kosciol.entity.Event;
import com.apka.kosciol.entity.Status;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.exceptions.DoesNotExistException;
import com.apka.kosciol.repository.IEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService  { //extends AbstractChangeService

    private IEvent eventRepository;

    public EventService(IEvent eventRepository) {
        this.eventRepository = eventRepository;
    }

    //@Override
    public long count() {
        return eventRepository.count();
    }

    //@Override
    public void delete(Integer id) throws DoesNotExistException {
        if(idExists(id)){
            Event event = eventRepository.getOne(id);
            eventRepository.delete(event);
        }
        else{
            System.out.println("Delete error.");
            throw new DoesNotExistException("That event with id = "+ id +" doesn't exist.");
        }
    }

    //@Override
    public void save(Object changeEntity) {
        if (changeEntity instanceof Event && changeEntity != null) {
            eventRepository.save((Event) changeEntity);
        } else {
            System.err.println("Your event doesn't have data.");
        }
    }

    //@Override
    public Optional<Object> findById(Integer id) {
        return Optional.of(eventRepository.findById(id));
    }

    public EventDto findEventDtoById(Integer id) throws DoesNotExistException {
        if(idExists(id)){
            Event event = eventRepository.getOne(id);
            return setAllFieldsOfEventDto(event);
        }
        else{
            throw new DoesNotExistException("That event with id = "+ id.intValue()+" doesn't exist.");
        }
    }

    public List<EventDto> returnAllEvents() {
        List<EventDto> eventDtoList = new ArrayList<>();
        List<Event> eventList = findAll();
        for (Event event : eventList) {
            eventDtoList.add(setAllFieldsOfEventDto(event));
        }
        return eventDtoList;
    }

    //@Override
    public void edit(EventDto eventDto) {
        try{
            Event event = eventRepository.getOne(eventDto.getId());
            event = setAllFieldsOfEvent(eventDto, event, false);

            eventRepository.save(event);
        }
        catch (Exception e){
            int i = 0; // cos poszlo nie tak
        }
    }

    public void addNewEvent(EventDto eventDto) throws AlreadyExistException {
        if (titleExists(eventDto.getTitle())) {
            throw new AlreadyExistException("An event with that title: '"
                    + eventDto.getTitle() + "' already exists. Please enter diffrent title.");
        }
        Event event = setAllFieldsOfEvent(eventDto, new Event(), true);
        eventRepository.save(event);
    }

    private boolean titleExists(String title) {
        return eventRepository.existsEventByTitle(title); //metoda exist, w repo ja napisac
    }

    private boolean idExists(Integer id) {
        return eventRepository.existsById(id); //metoda exist, w repo ja napisac
    }

    private List<Event> findAll() {
        return eventRepository.findAll();
    }

    private Event setAllFieldsOfEvent(EventDto eventDto, Event event, boolean isNew) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        //te co musza byc:
        if(Objects.isNull(eventDto.getEmailPublish())){
            eventDto.setEmailPublish(false);
        }
        if(Objects.isNull(eventDto.getFacebookPublish())){
            eventDto.setFacebookPublish(false);
        }
        if(Objects.isNull(eventDto.getMessengerPublish())){
            eventDto.setMessengerPublish(false);
        }
        event.setTitle(eventDto.getTitle());
        event.setEmailPublish(eventDto.getEmailPublish());
        event.setFacebookPublish(eventDto.getFacebookPublish());
        event.setMessengerPublish(eventDto.getMessengerPublish());
        //te co sa opcjonalne:
        if (isNew) {
            event.setVersion(1);
            event.setStatus(Status.TOUPDATE);
        } else {
            //todo status
            event.setVersion(event.getVersion() + 1);
            event.setStatus(eventDto.getStatus());
        }
        event.setMeetingCategory(eventDto.getMeetingCategory());
        event.setRecipientCategory(eventDto.getRecipientCategory());
        try{
            event.setStartDate(LocalDate.parse(eventDto.getStartDate(), formatter));
            event.setFinishDate(LocalDate.parse(eventDto.getFinishDate(), formatter));
        }
        catch(Exception e){}
        event.setStartTime(eventDto.getStartTime());
        event.setFinishTime(eventDto.getFinishTime());
        event.setDescription(eventDto.getDescription());
        event.setPlace(eventDto.getPlace());

        return event;
    }

    private EventDto setAllFieldsOfEventDto(Event event) {
        //te co musza byc:
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setEmailPublish(event.getEmailPublish());
        eventDto.setFacebookPublish(event.getFacebookPublish());
        eventDto.setMessengerPublish(event.getMessengerPublish());
        //te co sa opcjonalne:
        eventDto.setMeetingCategory(event.getMeetingCategory());
        eventDto.setRecipientCategory(event.getRecipientCategory());
        if(!Objects.isNull(event.getStartDate()))
            eventDto.setStartDate(event.getStartDate().toString());
        if(!Objects.isNull(event.getFinishDate()))
            eventDto.setFinishDate(event.getFinishDate().toString());
        eventDto.setStartTime(event.getStartTime());;
        eventDto.setFinishTime(event.getFinishTime());
        eventDto.setDescription(event.getDescription());
        eventDto.setPlace(event.getPlace());

        eventDto.setStatus(event.getStatus());
        return eventDto;
    }
}

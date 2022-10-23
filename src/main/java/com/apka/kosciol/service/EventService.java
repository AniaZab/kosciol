package com.apka.kosciol.service;
import com.apka.kosciol.entity.Event;
import com.apka.kosciol.repository.IEvent;
import com.apka.kosciol.repository.IUser;

import java.util.Optional;

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

    @Override
    public void update(int id, Object changeEntity) {
        Event newEvent;
        if(changeEntity instanceof Event && changeEntity != null){
            newEvent = ((Event) changeEntity);
        }
        else{
            System.err.println("Your event doesn't have data.");
            return;
        }
        Event eventTableEntity = eventRepository.getOne(id);

        eventTableEntity.setIdUser(newEvent.getIdUser());
        eventTableEntity.setTitle(newEvent.getTitle());
        eventTableEntity.setFinishDate(newEvent.getFinishDate());
        eventTableEntity.setFinishTime(newEvent.getFinishTime());
        eventTableEntity.setStartDate(newEvent.getStartDate());
        eventTableEntity.setStartTime(newEvent.getStartTime());
        eventTableEntity.setDescription(newEvent.getDescription());

        eventRepository.save(eventTableEntity);
    }
}

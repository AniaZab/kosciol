package com.apka.kosciol.service;

import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.entity.Recipient;
import com.apka.kosciol.entity.Status;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.exceptions.DoesNotExistException;
import com.apka.kosciol.repository.IRecipient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecipientService {
    private IRecipient recipientRepository;

    public RecipientService(IRecipient recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    //@Override
    public long count() {
        return recipientRepository.count();
    }

    //@Override
    public void delete(Integer id) throws DoesNotExistException {
        if(idExists(id)){
            Recipient recipient = recipientRepository.getOne(id);
            recipientRepository.delete(recipient);
        }
        else{
            System.out.println("Delete error.");
            throw new DoesNotExistException("That recipient with id = "+ id +" doesn't exist.");
        }
    }

    //@Override
    public void save(Object changeEntity) {
        if (changeEntity instanceof Recipient && changeEntity != null) {
            recipientRepository.save((Recipient) changeEntity);
        } else {
            System.err.println("Your recipient doesn't have data.");
        }
    }

    //@Override
    public Optional<Object> findById(Integer id) {
        return Optional.of(recipientRepository.findById(id));
    }

    public RecipientDto findRecipientDtoById(Integer id) throws DoesNotExistException {
        if(idExists(id)){
            Recipient recipient = recipientRepository.getOne(id);
            return setAllFieldsOfEventDto(recipient);
        }
        else{
            throw new DoesNotExistException("That recipient with id = "+ id.intValue()+" doesn't exist.");
        }
    }

    public List<RecipientDto> returnAllRecipients() {
        List<RecipientDto> eventDtoList = new ArrayList<>();
        List<Recipient> eventList = findAll();
        for (Recipient recipient : eventList) {
            eventDtoList.add(setAllFieldsOfEventDto(recipient));
        }
        return eventDtoList;
    }

    //@Override
    public void edit(RecipientDto recipientDto) {
        try{
            Recipient recipient = recipientRepository.getOne(recipientDto.getId());
            recipient = setAllFieldsOfEvent(recipientDto, recipient, false);

            recipientRepository.save(recipient);
        }
        catch (Exception e){
            int i = 0; // cos poszlo nie tak
        }
    }

    public void addNewRecipient(RecipientDto recipientDto) throws AlreadyExistException {
        if (emailExists(recipientDto.getEmail())) {
            throw new AlreadyExistException("An recipient with that email: '"
                    + recipientDto.getEmail() + "' already exists. Please enter diffrent email.");
        }
        Recipient recipient = setAllFieldsOfEvent(recipientDto, new Recipient(), true);
        recipientRepository.save(recipient);
    }

    private boolean idExists(Integer id) {
        return recipientRepository.existsById(id); //metoda exist, w repo ja napisac
    }

    private List<Recipient> findAll() {
        return recipientRepository.findAll();
    }

    private boolean emailExists(String email) {
        return recipientRepository.existsRecipientByEmail(email);
    }

    private Recipient setAllFieldsOfEvent(RecipientDto recipientDto, Recipient recipient, boolean isNew)
    {
        //TODO set user_user
        recipient.setEmail(recipientDto.getEmail());
        recipient.setFirstName(recipientDto.getFirstName());
        recipient.setLastName(recipientDto.getLastName());
        if (isNew) {
            recipient.setActive(true);
        }
        return recipient;
    }

    private RecipientDto setAllFieldsOfEventDto(Recipient recipient) {
        //TODO set user_user
        RecipientDto recipientDto = new RecipientDto();
        recipientDto.setEmail(recipient.getEmail());
        recipientDto.setFirstName(recipient.getFirstName());
        recipientDto.setLastName(recipient.getLastName());
        recipientDto.setId(recipient.getId());
        recipientDto.setActive(recipient.getActive());
        return recipientDto;
    }
}

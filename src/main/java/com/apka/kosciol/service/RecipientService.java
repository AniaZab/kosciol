package com.apka.kosciol.service;

import com.apka.kosciol.dto.RecipientDto;
import com.apka.kosciol.entity.MeetingCategory;
import com.apka.kosciol.entity.Recipient;
import com.apka.kosciol.entity.Subscription;
import com.apka.kosciol.exceptions.AlreadyExistException;
import com.apka.kosciol.exceptions.DoesNotExistException;
import com.apka.kosciol.repository.IRecipient;
import com.apka.kosciol.repository.ISubscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipientService {
    private final IRecipient recipientRepository;
    private final ISubscription subscriptionRepository;


    //@Override
    public long count() {
        return recipientRepository.count();
    }

    //@Override
    public void delete(Integer id) throws DoesNotExistException {
        if (idExists(id)) {
            Recipient recipient = recipientRepository.getOne(id);
            recipientRepository.delete(recipient);
        } else {
            System.out.println("Delete error.");
            throw new DoesNotExistException("That recipient with id = " + id + " doesn't exist.");
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
        if (idExists(id)) {
            Recipient recipient = recipientRepository.getOne(id);
            return setAllFieldsOfEventDto(recipient);
        } else {
            throw new DoesNotExistException("That recipient with id = " + id.intValue() + " doesn't exist.");
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
    @Transactional
    public void edit(RecipientDto recipientDto) {
        try {
            Recipient recipient = recipientRepository.getOne(recipientDto.getId());
            recipient = setAllFieldsOfEvent(recipientDto, recipient, false);
            log.debug("saving recipient with subsc {}", recipient.getSubscriptionList());
            log.debug("del substr amount {}", subscriptionRepository.deleteAllByRecipient(recipient));
        } catch (Exception e) {
            log.error("cos jest grubo nie tak!!! ",e.getMessage());
            e.printStackTrace();
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

    public List<Recipient> getRecipientsOfTheMeetingCategory(MeetingCategory meetingCategory){
        List<Subscription> subscriptionList = subscriptionRepository.findAllByMeetingCategory(meetingCategory);
        List<Recipient> recipientList = new ArrayList<>();
        for (Subscription subscription: subscriptionList) {
            recipientList.add(subscription.getRecipient());
        }
        return recipientList;
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

    private Recipient setAllFieldsOfEvent(RecipientDto recipientDto, Recipient recipient, boolean isNew) {
        log.debug("setting recipient from {}", recipientDto);
        //TODO set user_user
        recipient.setEmail(recipientDto.getEmail());
        recipient.setFirstName(recipientDto.getFirstName());
        recipient.setLastName(recipientDto.getLastName());
        recipient.setSubscriptionList(buildSubscriptions(recipientDto.getMeetingCategoryList()));
        if (isNew) {
            recipient.setActive(true);
        }
        return recipient;
    }

    private List<Subscription> buildSubscriptions(List<MeetingCategory> meetingCategoryList) {
        if (Objects.nonNull(meetingCategoryList)) {
            return meetingCategoryList.stream()
                    .map(this::buildSubscription)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Subscription buildSubscription(MeetingCategory meetingCategory) {
        return Subscription.builder()
                .meetingCategory(meetingCategory)
                .build();
    }

    private RecipientDto setAllFieldsOfEventDto(Recipient recipient) {
        //TODO set user_user
        RecipientDto recipientDto = new RecipientDto();
        recipientDto.setEmail(recipient.getEmail());
        recipientDto.setFirstName(recipient.getFirstName());
        recipientDto.setLastName(recipient.getLastName());
        recipientDto.setId(recipient.getId());
        recipientDto.setActive(recipient.getActive());
        recipientDto.setMeetingCategoryList(buildMeetingCategoryList(recipient.getSubscriptionList()));
        return recipientDto;
    }

    private List<MeetingCategory> buildMeetingCategoryList(List<Subscription> subscriptions) {
        if (Objects.nonNull(subscriptions)) {
            subscriptions.stream()
                    .map(Subscription::getMeetingCategory)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

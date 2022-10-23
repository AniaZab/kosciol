package com.apka.kosciol.repository;

import com.apka.kosciol.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEvent extends JpaRepository<Event, Integer> {
    //@Query("SELECT * FROM User_Table WHERE email = email_adress")
    //Optional<EventTableEntity> update(@Param("id") int id);
}

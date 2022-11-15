package com.apka.kosciol.repository;

import com.apka.kosciol.entity.Event;
import com.apka.kosciol.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IEvent extends JpaRepository<Event, Integer> {
    //@Query("SELECT * FROM User_Table WHERE email = email_adress")
    //Optional<EventTableEntity> update(@Param("id") int id);
    boolean existsEventByTitle(@Param("title") String title);
}

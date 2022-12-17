package com.apka.kosciol.repository;

import com.apka.kosciol.entity.Recipient;
import com.apka.kosciol.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface ISubscription extends JpaRepository<Subscription, Integer> {
    int deleteAllByRecipient(@Param("recipient") Recipient recipient);
    //public int deleteAllByRecipientId(Recipient recipient);
}

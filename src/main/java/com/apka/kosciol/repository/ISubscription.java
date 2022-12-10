package com.apka.kosciol.repository;

import com.apka.kosciol.entity.Recipient;
import com.apka.kosciol.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ISubscription extends JpaRepository<Subscription, Integer> {
public int deleteAllByRecipient(Recipient recipient);
}

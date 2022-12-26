package com.apka.kosciol.repository;

import com.apka.kosciol.entity.MeetingCategory;
import com.apka.kosciol.entity.Recipient;
import com.apka.kosciol.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ISubscription extends JpaRepository<Subscription, Integer> {
    int deleteAllByRecipient(@Param("recipient") Recipient recipient);
    List<Subscription> findAllByMeetingCategory(@Param("meeting_category") MeetingCategory meeting_category);
    //public int deleteAllByRecipientId(Recipient recipient);

}

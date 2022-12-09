package com.apka.kosciol.repository;

import com.apka.kosciol.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IRecipient extends JpaRepository<Recipient, Integer> {
    boolean existsRecipientByEmail(@Param("email") String email);
}

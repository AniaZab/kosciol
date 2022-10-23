package com.apka.kosciol.repository;

import com.apka.kosciol.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUser extends JpaRepository<User, Integer> {
    @Query("SELECT * FROM User_Table WHERE email = email_adress")
    Optional<User> findByEmailAdress(@Param("email_adress") String email_adress);
//    //List<User> search(String stringFilter);
/*    @Query("select * from [church].[dbo].[User_Table] " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);*/
}

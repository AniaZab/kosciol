package com.apka.kosciol.repository;

import com.apka.kosciol.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUser extends JpaRepository<User, Integer> {
   // @Query("SELECT * FROM User_Table WHERE email = email_adress")
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findByLogin(@Param("login") String login);
    boolean existsEventByEmail(@Param("email") String email);
    boolean existsEventByLogin(@Param("login") String login);
    //    //List<User> search(String stringFilter);
/*    @Query("select * from [church].[dbo].[User_Table] " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);*/
}

package com.apka.kosciol.service;

import com.apka.kosciol.entity.User;
import com.apka.kosciol.exceptions.UserAlreadyExistException;
import com.apka.kosciol.repository.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractChangeService {

    private final IUser userRepository;



    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void delete(Object changeEntity) {
        if (changeEntity != null && changeEntity instanceof User) {
            userRepository.delete((User) changeEntity);
        } else {
            System.err.println("Your user doesn't have data.");
        }
    }

    @Override
    public void save(Object changeEntity) {
        if (changeEntity instanceof User && changeEntity != null) {
            userRepository.save((User) changeEntity);
        } else {
            System.err.println("Your user doesn't have data.");
        }
    }

    @Override
    public Optional<Object> findById(int id) {
        return Optional.of(userRepository.findById(id));
    }

    @Override
    public void update(int id, Object changeEntity) {
        User newUser;
        if (changeEntity instanceof User && changeEntity != null) {
            newUser = ((User) changeEntity);
        } else {
            System.err.println("Your user doesn't have data.");
            return;
        }
        User userTableEntity = userRepository.getOne(id);

        userTableEntity.setLogin(newUser.getLogin());
        userTableEntity.setEmail(newUser.getEmail());
        userTableEntity.setFirstName(newUser.getFirstName());
        userTableEntity.setLastName(newUser.getLastName());
        userTableEntity.setPassword(newUser.getPassword());
        userTableEntity.setRole(newUser.getRole());

        userRepository.save(userTableEntity);
    }

    // zwraca znalezionego usera o danym adresie email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerNewUserAccount(User user) throws UserAlreadyExistException {
//        if (loginExists(user.getLogin())) { //emailExists(user.getEmail()) &&
//            throw new UserAlreadyExistException("There is an account with that email address: "
//                    + user.getEmail() +" or login: " + user.getLogin());
//        }
        user.setRole("USER");
        userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    private boolean loginExists(String login) {
        return userRepository.findByLogin(login) != null;
    }
}

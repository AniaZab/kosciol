package com.apka.kosciol.service;

import com.apka.kosciol.entity.User;
import com.apka.kosciol.repository.IUser;

import java.util.Optional;

public class UserService extends AbstractChangeService{

    private IUser userRepository;

    public UserService(IUser userRepository){this.userRepository=userRepository;}


    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void delete(Object changeEntity) {
        if(changeEntity instanceof User && changeEntity != null){
            userRepository.delete((User) changeEntity);
        }
        else{
            System.err.println("Your event doesn't have data.");
        }
    }

    @Override
    public void save(Object changeEntity) {
        if(changeEntity instanceof User && changeEntity != null){
            userRepository.save((User) changeEntity);
        }
        else{
            System.err.println("Your event doesn't have data.");
        }
    }

    @Override
    public Optional<Object> findById(int id) {
        return Optional.of(userRepository.findById(id));
    }

    @Override
    public void update(int id, Object changeEntity) {
        User newUser;
        if(changeEntity instanceof User && changeEntity != null){
            newUser = ((User) changeEntity);
        }
        else{
            System.err.println("Your event doesn't have data.");
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
    public Optional<User> findUserByEmailAdress(String emailAdress) {
        return userRepository.findByEmailAdress(emailAdress);
    }

}

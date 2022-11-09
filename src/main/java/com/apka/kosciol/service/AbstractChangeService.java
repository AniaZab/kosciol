package com.apka.kosciol.service;

import com.apka.kosciol.entity.User;

import java.util.Optional;

public abstract class AbstractChangeService{
    public abstract long count();
    public abstract void delete(Object changeEntity);
    public abstract void save(Object changeEntity);
    public abstract Optional<Object> findById(int id);
    public abstract void update(int id, Object changeEntity);
    public Optional<User> findUserByEmailAdress(String emailAdress){
        return Optional.empty();
    }
}

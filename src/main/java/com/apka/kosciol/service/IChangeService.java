package com.apka.kosciol.service;

import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface IChangeService {
    public long count();
    public void delete(Object changeEntity);
    public void save(Object changeEntity);
    public Optional<Object> findById(int id);
    public void update(int id, Object changeEntity);
}

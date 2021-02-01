package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.users.Person;

import java.util.List;

public interface IPersonService {
    Person findOne(Long id);

    List<Person> findAll();

    Person save(Person person);

    void remove(Long id);
}

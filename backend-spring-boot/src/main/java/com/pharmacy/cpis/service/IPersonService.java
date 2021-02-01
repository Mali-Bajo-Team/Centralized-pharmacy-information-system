package com.pharmacy.cpis.service;

import com.pharmacy.cpis.dto.UserRegisterDTO;
import com.pharmacy.cpis.users.model.Person;

import java.util.List;

public interface IPersonService {
    Person findOne(Long id);

    List<Person> findAll();

    Person save(Person person);

    void remove(Long id);
}

package com.pharmacy.cpis.service.impl;

import com.pharmacy.cpis.repository.IPersonRepository;
import com.pharmacy.cpis.service.IPersonService;
import com.pharmacy.cpis.userservice.model.users.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private IPersonRepository personRepository;

    @Override
    public Person findOne(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void remove(Long id) {
        personRepository.deleteById(id);
    }
}

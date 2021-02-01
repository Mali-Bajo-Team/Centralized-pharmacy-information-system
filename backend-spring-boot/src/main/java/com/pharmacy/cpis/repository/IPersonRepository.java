package com.pharmacy.cpis.repository;

import com.pharmacy.cpis.userservice.model.users.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Long> {
}

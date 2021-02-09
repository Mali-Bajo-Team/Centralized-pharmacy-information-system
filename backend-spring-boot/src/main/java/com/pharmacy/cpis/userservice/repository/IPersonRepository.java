package com.pharmacy.cpis.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.userservice.model.users.Person;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

public interface IPersonRepository extends JpaRepository<Person, Long> {
	
	Person findByAccount(UserAccount account);
	
}

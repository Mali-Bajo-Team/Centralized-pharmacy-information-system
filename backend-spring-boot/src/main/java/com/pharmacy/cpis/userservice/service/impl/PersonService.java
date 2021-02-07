package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.userservice.dto.PersonalInformationDTO;
import com.pharmacy.cpis.userservice.model.users.Person;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPersonRepository;
import com.pharmacy.cpis.userservice.service.IPersonService;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private IPersonRepository repository;

	@Override
	public Person getPersonByUserAccount(UserAccount account) {
		Person person = repository.findByAccount(account);
		if (person == null)
			throw new PSNotFoundException("Personal information not found.");
		return person;
	}


	@Override
	public Person update(UserAccount account, PersonalInformationDTO personalInfo) {
		Person person = repository.findByAccount(account);
		if (person == null)
			throw new PSNotFoundException("Personal information not found.");

		person.setName(personalInfo.getName());
		person.setSurname(personalInfo.getSurname());
		person.setAddress(personalInfo.getAddress());
		person.setCity(personalInfo.getCity());
		person.setCountry(personalInfo.getCountry());
		person.setPhoneNumber(personalInfo.getPhone());
		return repository.save(person);
	}

}

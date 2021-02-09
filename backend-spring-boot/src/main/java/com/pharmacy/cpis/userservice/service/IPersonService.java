package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.PersonalInformationDTO;
import com.pharmacy.cpis.userservice.model.users.Person;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

public interface IPersonService {

	Person getPersonByUserAccount(UserAccount account);

	Person update(UserAccount account, PersonalInformationDTO personalInfo);
	
}

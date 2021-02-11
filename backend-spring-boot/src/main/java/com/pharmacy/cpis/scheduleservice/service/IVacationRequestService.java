package com.pharmacy.cpis.scheduleservice.service;

import java.util.Collection;

import com.pharmacy.cpis.scheduleservice.dto.AddVacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

public interface IVacationRequestService {

	AddVacationRequestDTO createVacationRequest(AddVacationRequestDTO vacationRequestDTO);

	Collection<VacationRequest> getVacationRequests(UserAccount admin);

	public VacationRequest reject(UserAccount user, Long id, String response);
	
	VacationRequest accept(UserAccount user, Long id);

}

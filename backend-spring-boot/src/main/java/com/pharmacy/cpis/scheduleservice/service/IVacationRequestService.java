package com.pharmacy.cpis.scheduleservice.service;

import java.util.Collection;

import com.pharmacy.cpis.scheduleservice.dto.AddVacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;

public interface IVacationRequestService {

	AddVacationRequestDTO createVacationRequest(AddVacationRequestDTO vacationRequestDTO);

	Collection<VacationRequest> getDermatologistRequests();

	Collection<VacationRequest> getPharmacistRequestsByPharmacy(Long pharmacyId);

}

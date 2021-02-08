package com.pharmacy.cpis.scheduleservice.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.scheduleservice.dto.AddVacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequestStatus;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRequestRepository;
import com.pharmacy.cpis.scheduleservice.service.IVacationRequestService;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.DateRange;

@Service
public class VacationRequestService implements IVacationRequestService {

	@Autowired
	private IVacationRequestRepository vacationRequestRepository;
	@Autowired
	private IConsultantService consultantService;

	@Override
	public AddVacationRequestDTO createVacationRequest(AddVacationRequestDTO vacationRequestDTO) {
		VacationRequest vacationRequest = new VacationRequest();
		DateRange vacReqDateRange = new DateRange();
		vacReqDateRange
				.setStart(DateConversionsAndComparisons.getUtilDate(vacationRequestDTO.getStartVacationReqDate()));
		vacReqDateRange.setEnd(DateConversionsAndComparisons.getUtilDate(vacationRequestDTO.getEndVacatonReqDate()));

		vacationRequest.setConsultant(consultantService.findByEmail(vacationRequestDTO.getConsultantEmail()));
		vacationRequest.setDateRange(vacReqDateRange);
		vacationRequest.setStatus(VacationRequestStatus.PENDING);

		vacationRequestRepository.save(vacationRequest);

		return vacationRequestDTO;
	}

	@Override
	public Collection<VacationRequest> getDermatologistRequests() {
		Collection<VacationRequest> requests = vacationRequestRepository.findAll();

		return CollectionUtil.findAll(requests,
				request -> request.getConsultant().getType().equals(ConsultantType.DERMATOLOGIST));
	}

	public Collection<VacationRequest> getPharmacistRequestsByPharmacy(Long pharmacyId) {
		Collection<VacationRequest> requests = vacationRequestRepository.findAll();
		requests = CollectionUtil.findAll(requests,
				request -> request.getConsultant().getType().equals(ConsultantType.PHARMACIST));

		return CollectionUtil.findAll(requests,
				request -> isPharmacistAndWorksForPharmacy(request.getConsultant(), pharmacyId));
	}

	private Boolean isPharmacistAndWorksForPharmacy(Consultant consultant, Long pharmacyId) {
		if (!consultant.getType().equals(ConsultantType.PHARMACIST))
			return false;

		return CollectionUtil.contains(consultant.getWorkingTimes(), wt -> wt.getPharmacy().getId().equals(pharmacyId));
	}
}

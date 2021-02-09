package com.pharmacy.cpis.scheduleservice.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.scheduleservice.dto.AddVacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.Vacation;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequestStatus;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRequestRepository;
import com.pharmacy.cpis.scheduleservice.service.IVacationRequestService;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.util.CollectionUtil;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.DateRange;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class VacationRequestService implements IVacationRequestService {

	@Autowired
	private IVacationRequestRepository vacationRequestRepository;

	@Autowired
	private IConsultantService consultantService;

	@Autowired
	private IVacationRepository vacationRepository;

	@Autowired
	private IConsultationRepository consultationRepository;

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
	public VacationRequest reject(UserAccount user, Long id, String response) {
		VacationRequest request = vacationRequestRepository.findById(id).orElse(null);
		if (request == null)
			throw new PSNotFoundException("The requested vacation request does not exist.");
		if (!canInteractWith(user, request))
			throw new PSForbiddenException("You are not authorized to review the requested vacation request.");

		if (!request.getStatus().equals(VacationRequestStatus.PENDING))
			throw new PSConflictException("The vacation request has already been reviewed.");

		request.setStatus(VacationRequestStatus.REJECTED);
		request.setResponse(response);
		request = vacationRequestRepository.save(request);

		return request;
	}

	@Override
	public VacationRequest accept(UserAccount user, Long id, String response) {
		VacationRequest request = vacationRequestRepository.findById(id).orElse(null);
		if (request == null)
			throw new PSNotFoundException("The requested vacation request does not exist.");
		if (!canInteractWith(user, request))
			throw new PSForbiddenException("You are not authorized to review the requested vacation request.");

		if (!request.getStatus().equals(VacationRequestStatus.PENDING))
			throw new PSConflictException("The vacation request has already been reviewed.");

		request.setStatus(VacationRequestStatus.ACCEPTED);
		request.setResponse(response);
		createVacation(request);
		request = vacationRequestRepository.save(request);

		return request;
	}

	private void createVacation(VacationRequest request) {
		Vacation vacation = new Vacation();
		vacation.setConsultant(request.getConsultant());
		vacation.setDateRange(request.getDateRange());

		for (Consultation consultation : consultationRepository.findAllByConsultantAndStatus(request.getConsultant(),
				ConsultationStatus.SCHEDULED)) {
			if (DateConversionsAndComparisons.overlapsWithoutTime(vacation.getDateRange(), consultation.getTime())) {
				throw new PSConflictException(
						"Vacation cannot be approved because there are already consultations scheduled for that time period.");
			}
		}

		for (Consultation consultation : consultationRepository.findAllByConsultantAndStatus(request.getConsultant(),
				ConsultationStatus.PREDEFINED)) {
			if (DateConversionsAndComparisons.overlapsWithoutTime(vacation.getDateRange(), consultation.getTime())) {
				throw new PSConflictException(
						"Vacation cannot be approved because there are already consultations scheduled for that time period.");
			}
		}

		vacationRepository.save(vacation);
	}

	@Override
	public Collection<VacationRequest> getVacationRequests(UserAccount admin) {
		Collection<VacationRequest> requests = vacationRequestRepository.findAll();

		return CollectionUtil.findAll(requests, request -> canInteractWith(admin, request));
	}

	private Boolean canInteractWith(UserAccount user, VacationRequest request) {
		if (user.getRole().equals("ADMIN"))
			return request.getConsultant().getType().equals(ConsultantType.DERMATOLOGIST);
		else {
			return isPharmacistAndWorksForPharmacy(request.getConsultant(), user.getPharmacyId());
		}
	}

	private Boolean isPharmacistAndWorksForPharmacy(Consultant consultant, Long pharmacyId) {
		if (!consultant.getType().equals(ConsultantType.PHARMACIST))
			return false;

		return CollectionUtil.contains(consultant.getWorkingTimes(), wt -> wt.getPharmacy().getId().equals(pharmacyId));
	}
}

package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.scheduleservice.dto.VacationRequestDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequestStatus;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRequestRepository;
import com.pharmacy.cpis.scheduleservice.service.IVacationRequestService;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationRequestService implements IVacationRequestService {

    @Autowired
    private IVacationRequestRepository vacationRequestRepository;
    @Autowired
    private IConsultantService consultantService;

    @Override
    public VacationRequestDTO createVacationRequest(VacationRequestDTO vacationRequestDTO) {
        VacationRequest vacationRequest = new VacationRequest();
        DateRange vacReqDateRange = new DateRange();
        vacReqDateRange.setStart(DateConversionsAndComparisons.getUtilDate(vacationRequestDTO.getStartVacationReqDate()));
        vacReqDateRange.setEnd(DateConversionsAndComparisons.getUtilDate(vacationRequestDTO.getEndVacatonReqDate()));

        vacationRequest.setConsultant(consultantService.findByEmail(vacationRequestDTO.getConsultantEmail()));
        vacationRequest.setDateRange(vacReqDateRange);
        vacationRequest.setStatus(VacationRequestStatus.PENDING);

        vacationRequestRepository.save(vacationRequest);

        return vacationRequestDTO;
    }
}

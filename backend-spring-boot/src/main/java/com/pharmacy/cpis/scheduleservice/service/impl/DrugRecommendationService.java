package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.scheduleservice.dto.DrugRecommendationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationReport;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.DrugRecommendation;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationReportRepository;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IDrugRecommendationRepository;
import com.pharmacy.cpis.scheduleservice.service.IDrugRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrugRecommendationService implements IDrugRecommendationService {
    @Autowired
    private IDrugRecommendationRepository drugRecommendationRepository;

    @Autowired
    private IDrugRepository drugRepository;

    @Autowired
    private IAvailableDrugService availableDrugService;

    @Autowired
    private IConsultationRepository consultationRepository;

    @Autowired
    private IConsultationReportRepository consultationReportRepository;
    @Override
    public DrugRecommendationDTO recommendDrug(DrugRecommendationDTO drugRecommendationDTO) {
        Consultation consultation = consultationRepository.getOne(drugRecommendationDTO.getConsultationID());

        ConsultationReport consultationReport = new ConsultationReport();
        consultationReport.setAnamnesis(drugRecommendationDTO.getConsultationReport());
        consultationReport.setConsultation(consultation);
        consultationReport.setDiagnosis(drugRecommendationDTO.getConsultationReport());
        consultationReportRepository.save(consultationReport);

        DrugRecommendation drugRecommendation = new DrugRecommendation();
        drugRecommendation.setConsultationReport(consultationReport);
        drugRecommendation.setDrug(drugRepository.getOne(drugRecommendationDTO.getDrugCode()));
        drugRecommendation.setDuration(drugRecommendationDTO.getDuration());
        drugRecommendation.setDuration(drugRecommendationDTO.getDuration());

        drugRecommendationRepository.save(drugRecommendation);
        //SACUVAJ I CONSULATTION REPORT
        return drugRecommendationDTO;
    }

    @Override
    public DrugRecommendationDTO isDrugAvailable(DrugRecommendationDTO drugRecommendationDTO) {

        Consultation consultation = consultationRepository.getOne(drugRecommendationDTO.getConsultationID());

        if(availableDrugService.checkIsAvailableinPharmacy(consultation.getPharmacy().getId(),drugRecommendationDTO.getDrugCode()) != null){
            drugRecommendationDTO.setAvailable(true);
            return drugRecommendationDTO;
        }
        drugRecommendationDTO.setAvailable(false);
        return drugRecommendationDTO;

    }
}

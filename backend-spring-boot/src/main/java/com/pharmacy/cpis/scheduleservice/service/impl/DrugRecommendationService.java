package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.AlternateDrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSpecificationDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.scheduleservice.dto.DrugRecommendationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationReport;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.DrugRecommendation;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationReportRepository;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IDrugRecommendationRepository;
import com.pharmacy.cpis.scheduleservice.service.IDrugRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        consultation.setStatus(ConsultationStatus.FINISHED);
        consultationRepository.save(consultation);

        return drugRecommendationDTO;
    }

    @Override
    public DrugRecommendationDTO isDrugAvailable(DrugRecommendationDTO drugRecommendationDTO) {

        Consultation consultation = consultationRepository.getOne(drugRecommendationDTO.getConsultationID());
        Drug drug = drugRepository.getOne(drugRecommendationDTO.getDrugCode());

        if(availableDrugService.checkIsAvailableinPharmacy(consultation.getPharmacy().getId(),drugRecommendationDTO.getDrugCode()) != null){
            drugRecommendationDTO.setAvailable(true);
            return drugRecommendationDTO;
        }

        Set<Drug> alternateDrugs = drug.getAlternateDrugs();
        Set<AlternateDrugDTO> alternateDrugsDTOs = new HashSet<>();

        for (Drug alterDrug: alternateDrugs) {
            AlternateDrugDTO alternateDrugDTO = new AlternateDrugDTO();
            alternateDrugDTO.setName(alterDrug.getName());
            alternateDrugDTO.setCode(alterDrug.getCode());
            alternateDrugDTO.setDrugSpecificationDTO(new DrugSpecificationDTO(alterDrug.getSpecification()));
            alternateDrugsDTOs.add(alternateDrugDTO);
        }

        drugRecommendationDTO.setAlternateDrugsDTO(alternateDrugsDTOs);

        return drugRecommendationDTO;
    }
}

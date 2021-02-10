package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.scheduleservice.dto.DrugRecommendationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.service.IDrugRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugRecommendationService implements IDrugRecommendationService {
    @Autowired
    private IDrugRecommendationService drugRecommendationService;

    @Autowired
    private IAvailableDrugService availableDrugService;

    @Autowired
    private IConsultationRepository consultationRepository;
    @Override
    public DrugRecommendationDTO recommendDrug(DrugRecommendationDTO drugRecommendationDTO) {

        Consultation consultation = consultationRepository.getOne(drugRecommendationDTO.getConsultationID());

        if(availableDrugService.checkIsAvailableinPharmacy(consultation.getPharmacy().getId(),drugRecommendationDTO.getDrugCode()) != null){
            System.out.println("IMA LEKA BOLAN");
            drugRecommendationDTO.setAvailable(true);
            return drugRecommendationDTO;
        }
        drugRecommendationDTO.setAvailable(false);
        return drugRecommendationDTO;

    }
}

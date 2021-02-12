package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.AlternateDrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSpecificationDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugRequest;
import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.drugservice.model.drugsales.DrugPurchase;
import com.pharmacy.cpis.drugservice.model.drugsales.DrugPurchaseType;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.repository.IDrugPurchaseRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugRequestRepository;
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
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import com.pharmacy.cpis.userservice.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private IDrugPurchaseRepository drugPurchaseRepository;

    @Autowired
    private ILoyaltyProgramService loyaltyProgramService;

    @Autowired
    private IDrugRequestRepository drugRequestRepository;

    @Autowired
    private IPatientRepository patientRepository;
    @Override
    @Transactional
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


        if(consultation.getStatus().equals(ConsultationStatus.SCHEDULED)){
            consultation.setStatus(ConsultationStatus.FINISHED);
            consultationRepository.save(consultation);
        }


        availableDrugService.updateAmount(consultation.getPharmacy().getId(),drugRecommendationDTO.getDrugCode(),1);

        DrugPurchase drugPurchase = new DrugPurchase();
        drugPurchase.setType(DrugPurchaseType.RECOMMENDATION);
        drugPurchase.setTimestamp(new Date());
        drugPurchase.setPrice(calculatePriceDrugPurchaseWithDiscount(drugRecommendationDTO,consultation));
        drugPurchase.setPharmacy(consultation.getPharmacy());
        drugPurchase.setPatient(consultation.getPatient());
        drugPurchase.setDrug(drugRepository.getOne(drugRecommendationDTO.getDrugCode()));
        drugPurchase.setAmount(1);

        drugPurchaseRepository.save(drugPurchase);
        return drugRecommendationDTO;
    }

    private Double calculatePriceDrugPurchaseWithDiscount(DrugRecommendationDTO drugRecommendationDTO, Consultation consultation) {
        Double priceWithDiscount;

        AvailableDrug availableDrug = availableDrugService.getByPharmacyAndDrug(consultation.getPharmacy().getId(), drugRecommendationDTO.getDrugCode());
        Double priceWithoutDiscount = availableDrug.findPrice(new Date()).getPrice();
        UserCategory userCategory = loyaltyProgramService.findUserCategoryByLoyaltyPoints(consultation.getPatient().getLoyaltyPoints());
        Double discount = userCategory.getReservationDiscount();

        if(discount != 0){
            priceWithDiscount = (priceWithoutDiscount -(priceWithoutDiscount*(discount/100))) * 1;
        }else{
            priceWithDiscount =priceWithoutDiscount;
        }
        return priceWithDiscount;
    }
    @Override
    public DrugRecommendationDTO isDrugAvailable(DrugRecommendationDTO drugRecommendationDTO) {

        Consultation consultation = consultationRepository.getOne(drugRecommendationDTO.getConsultationID());
        Drug drug = drugRepository.getOne(drugRecommendationDTO.getDrugCode());
        Patient patient = patientRepository.getOne(drugRecommendationDTO.getPatientID());

        if(availableDrugService.checkIsAvailableinPharmacy(consultation.getPharmacy().getId(),drugRecommendationDTO.getDrugCode()) != null){
            drugRecommendationDTO.setAvailable(true);
            return drugRecommendationDTO;
        }

        Set<Drug> alternateDrugs = drug.getAlternateDrugs();
        Set<AlternateDrugDTO> alternateDrugsDTOs = new HashSet<>();
        Set<Drug> allergis = patient.getAllergies();

        for (Drug alterDrug: alternateDrugs) {
            if(!allergis.contains(alterDrug)){
                AlternateDrugDTO alternateDrugDTO = new AlternateDrugDTO();
                alternateDrugDTO.setName(alterDrug.getName());
                alternateDrugDTO.setCode(alterDrug.getCode());
                alternateDrugDTO.setDrugSpecificationDTO(new DrugSpecificationDTO(alterDrug.getSpecification()));
                alternateDrugsDTOs.add(alternateDrugDTO);
            }
        }

        drugRecommendationDTO.setAlternateDrugsDTO(alternateDrugsDTOs);

        DrugRequest drugRequest = new DrugRequest();
        drugRequest.setConsultant(consultation.getConsultant());
        drugRequest.setDrug(drug);
        drugRequest.setPharmacy(consultation.getPharmacy());
        drugRequest.setTimestamp(new Date());
        drugRequestRepository.save(drugRequest);

        return drugRecommendationDTO;
    }
}

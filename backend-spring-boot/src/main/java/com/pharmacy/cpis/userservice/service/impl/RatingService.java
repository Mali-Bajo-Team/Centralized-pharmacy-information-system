package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingCreateDTO;
import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingUpdateDTO;
import com.pharmacy.cpis.userservice.dto.ratings.PharmacyRatingCreateDTO;
import com.pharmacy.cpis.userservice.dto.ratings.PharmacyRatingUpdateDTO;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;
import com.pharmacy.cpis.userservice.model.ratings.Rating;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.repository.ratings.IConsultantRatingRepository;
import com.pharmacy.cpis.userservice.repository.ratings.IPharmacyRatingRepository;
import com.pharmacy.cpis.userservice.service.IRatingService;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private IConsultantRatingRepository consultantRatingRepository;

    @Autowired
    private IPharmacyRatingRepository pharmacyRatingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IConsultantRepository consultantRepository;

    @Autowired
    private IPharmacyRepository pharmacyRepository;

    @Override
    public List<ConsultantRating> getAllConsultantRatingsByPatient(String patientEmail) {
        return consultantRatingRepository.findAllByPatientId(userRepository.findByEmail(patientEmail).getPerson().getId());
    }

    @Override
    public ConsultantRating createConsultantRating(ConsultantRatingCreateDTO consultantRatingCreateDTO) {
        ConsultantRating consultantRating = new ConsultantRating();
        Rating rating = new Rating();
        rating.setRating(consultantRatingCreateDTO.getRating());
        Long patientId = userRepository.findByEmail(consultantRatingCreateDTO.getPatientEmail()).getPerson().getId();
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null) throw new PSNotFoundException("Not found that patient");
        Consultant consultant = consultantRepository.findById(consultantRatingCreateDTO.getConsultantId()).orElse(null);
        if(consultant == null) throw new PSNotFoundException("Not found that consultant");

        consultantRating.setRating(rating);
        consultantRating.setPatient(patient);
        consultantRating.setConsultant(consultant);

        return consultantRatingRepository.save(consultantRating);
    }

    @Override
    public ConsultantRating updateConsultantRating(ConsultantRatingUpdateDTO consultantRatingUpdateDTO) {
        ConsultantRating consultantRating = consultantRatingRepository.findById(consultantRatingUpdateDTO.getId()).orElse(null);
        if(consultantRating == null) throw new PSNotFoundException("Not found that rating...");
        Rating newRating = new Rating();
        newRating.setRating(consultantRatingUpdateDTO.getNewRating());
        consultantRating.setRating(newRating);
        return consultantRatingRepository.save(consultantRating);
    }

    @Override
    public List<PharmacyRating> getAllPharmacyRatingsByPatient(String patientEmail) {
        return pharmacyRatingRepository.findAllByPatientId(userRepository.findByEmail(patientEmail).getPerson().getId());
    }

    @Override
    public PharmacyRating createPharmacyRating(PharmacyRatingCreateDTO pharmacyRatingCreateDTO) {
        PharmacyRating pharmacyRating = new PharmacyRating();
        Rating rating = new Rating();
        rating.setRating(pharmacyRatingCreateDTO.getRating());
        Long patientId = userRepository.findByEmail(pharmacyRatingCreateDTO.getPatientEmail()).getPerson().getId();
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null) throw new PSNotFoundException("Not found that patient");
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyRatingCreateDTO.getPharmacyId()).orElse(null);
        if(pharmacy == null) throw new PSNotFoundException("Not found that pharmacy");

        pharmacyRating.setRating(rating);
        pharmacyRating.setPatient(patient);
        pharmacyRating.setPharmacy(pharmacy);

        return pharmacyRatingRepository.save(pharmacyRating);
    }

    @Override
    public PharmacyRating updatePharmacyRating(PharmacyRatingUpdateDTO pharmacyRatingUpdateDTO) {
        PharmacyRating pharmacyRating = pharmacyRatingRepository.findById(pharmacyRatingUpdateDTO.getId()).orElse(null);
        if(pharmacyRating == null) throw new PSNotFoundException("Not found that pharmacy rating");
        Rating rating = new Rating();
        rating.setRating(pharmacyRatingUpdateDTO.getNewRating());
        pharmacyRating.setRating(rating);
        return pharmacyRatingRepository.save(pharmacyRating);
    }

}

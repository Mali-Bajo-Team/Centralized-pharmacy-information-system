package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.ratings.ConsultantRatingCreateDTO;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.model.ratings.Rating;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.repository.ratings.IConsultantRatingRepository;
import com.pharmacy.cpis.userservice.service.IRatingService;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private IConsultantRatingRepository consultantRatingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IConsultantRepository consultantRepository;

    @Override
    public List<ConsultantRating> getAllConsultantRatingsByPatient(String patientEmail) {
        return consultantRatingRepository.findAllByPatientId(userRepository.findByEmail(patientEmail).getPerson().getId());
    }

    @Override
    public ConsultantRating createConsultantRating(ConsultantRatingCreateDTO consultantRatingCreateDTO) {
        ConsultantRating consultantRating = new ConsultantRating();
        Long patientId = userRepository.findByEmail(consultantRatingCreateDTO.getPatientEmail()).getPerson().getId();
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null) throw new PSNotFoundException("Not found that patient");
        Consultant consultant = consultantRepository.findById(consultantRatingCreateDTO.getConsultantId()).orElse(null);
        if(consultant == null) throw new PSNotFoundException("Not found that consultant");

        consultantRating.setPatient(patient);
        consultantRating.setConsultant(consultant);
        Rating rating = new Rating();
        rating.setRating(consultantRatingCreateDTO.getRating());
        consultantRating.setRating(rating);

        return consultantRatingRepository.save(consultantRating);
    }
}

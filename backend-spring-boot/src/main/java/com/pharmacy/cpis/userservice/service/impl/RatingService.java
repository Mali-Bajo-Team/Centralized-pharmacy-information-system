package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.repository.ratings.IConsultantRatingRepository;
import com.pharmacy.cpis.userservice.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private IConsultantRatingRepository consultantRatingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<ConsultantRating> getAllConsultantRatingsByPatient(String patientEmail) {
        return consultantRatingRepository.findAllByPatientId(userRepository.findByEmail(patientEmail).getPerson().getId());
    }
}

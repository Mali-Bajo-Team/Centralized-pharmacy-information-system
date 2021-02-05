package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.LoyaltyProgramDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.repository.ILoyaltyProgramRepository;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyProgramService implements ILoyaltyProgramService {

    @Autowired
    private ILoyaltyProgramRepository loyaltyProgramRepository;

    @Override
    public List<LoyaltyProgram> findAll() {
        return loyaltyProgramRepository.findAll();
    }

    @Override
    public LoyaltyProgram update(LoyaltyProgramDTO loyaltyProgramDTO) {
        // Our logic is such that we assume that we have one loyalty program in the system and that we always change it
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findAll().get(0); // we take first and only one loyalty program in our system

        loyaltyProgram.setActiveUntil(loyaltyProgramDTO.getActiveUntil());
        loyaltyProgram.setIsActive(loyaltyProgramDTO.getActive());
        loyaltyProgram.setPointsPerConsultation(loyaltyProgramDTO.getPointsPerConsultation());
        return loyaltyProgramRepository.save(loyaltyProgram);
    }
}

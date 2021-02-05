package com.pharmacy.cpis.userservice.service.impl;

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
}

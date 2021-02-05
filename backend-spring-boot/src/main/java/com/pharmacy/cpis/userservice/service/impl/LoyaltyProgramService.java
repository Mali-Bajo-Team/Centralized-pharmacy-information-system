package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.LoyaltyProgramDTO;
import com.pharmacy.cpis.userservice.dto.UserCategoryDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import com.pharmacy.cpis.userservice.repository.ILoyaltyProgramRepository;
import com.pharmacy.cpis.userservice.repository.IUserCategoryRepository;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyProgramService implements ILoyaltyProgramService {

    @Autowired
    private ILoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    private IUserCategoryRepository userCategoryRepository;

    @Override
    public List<LoyaltyProgram> findAll() {
        return loyaltyProgramRepository.findAll();
    }

    @Override
    public LoyaltyProgram update(LoyaltyProgramDTO loyaltyProgramDTO) {
        LoyaltyProgram loyaltyProgram = getLoyaltyProgram();

        loyaltyProgram.setActiveUntil(loyaltyProgramDTO.getActiveUntil());
        loyaltyProgram.setIsActive(loyaltyProgramDTO.getActive());
        loyaltyProgram.setPointsPerConsultation(loyaltyProgramDTO.getPointsPerConsultation());
        return loyaltyProgramRepository.save(loyaltyProgram);
    }

    @Override
    public UserCategory saveCategory(UserCategoryDTO userCategoryDTO) {
        UserCategory userCategory = new UserCategory();
        userCategory.setLoyaltyProgram(getLoyaltyProgram());
        userCategory.setName(userCategoryDTO.getName());
        userCategory.setMinimumPoints(userCategoryDTO.getMinimumPoints());
        userCategory.setReservationDiscount(userCategoryDTO.getReservationDiscount());
        userCategory.setConsultationDiscount(userCategoryDTO.getConsultationDiscount());
        return userCategoryRepository.save(userCategory);
    }

    /**
     * Our logic is such that we assume that we have one loyalty program in the system and that we always change it
     */
    private LoyaltyProgram getLoyaltyProgram() {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findAll().get(0); // we take first and only one loyalty program in our system
        return loyaltyProgram;
    }
}

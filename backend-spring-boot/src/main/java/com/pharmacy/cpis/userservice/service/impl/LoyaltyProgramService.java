package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.dto.LoyaltyProgramDTO;
import com.pharmacy.cpis.userservice.dto.UserCategoryDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import com.pharmacy.cpis.userservice.repository.ILoyaltyProgramRepository;
import com.pharmacy.cpis.userservice.repository.IUserCategoryRepository;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
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
    public LoyaltyProgram updateLoyaltyProgram(LoyaltyProgramDTO loyaltyProgramDTO) {
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
        if(userCategoryRepository.existsByMinimumPoints(userCategoryDTO.getMinimumPoints()))
            throw new PSConflictException("User category with this amount of minimum points already exist");
        if(userCategoryRepository.existsByName(userCategoryDTO.getName()))
            throw new PSConflictException("User category with this name already exist");
        userCategory.setName(userCategoryDTO.getName());
        userCategory.setMinimumPoints(userCategoryDTO.getMinimumPoints());
        userCategory.setReservationDiscount(userCategoryDTO.getReservationDiscount());
        userCategory.setConsultationDiscount(userCategoryDTO.getConsultationDiscount());
        return userCategoryRepository.save(userCategory);
    }

    @Override
    public UserCategory findUserCategoryByLoyaltyPoints(Integer loyaltyPoints) {
        UserCategory userCategory = new UserCategory();

        int difference = 0;
        int minPositiveDifference = 999999;
        for (UserCategory tempUserCategory : userCategoryRepository.findAll()) {
            difference = loyaltyPoints - tempUserCategory.getMinimumPoints();
            if (difference >= 0 && difference <= minPositiveDifference) {
                minPositiveDifference = difference;
                userCategory = tempUserCategory;
            }
        }

        return userCategory;
    }

    @Override
    public UserCategory updateCategory(UserCategoryDTO userCategoryDTO) {
        UserCategory updatedUserCategory = userCategoryRepository.findByName(userCategoryDTO.getName());
        if(updatedUserCategory == null) throw new PSNotFoundException("User category with this name does not exist");
        if(!updatedUserCategory.getMinimumPoints().equals(userCategoryDTO.getMinimumPoints()) && userCategoryRepository.existsByMinimumPoints(userCategoryDTO.getMinimumPoints()))
            throw new PSConflictException("User category with this amount of minimum points already exist");
        updatedUserCategory.setMinimumPoints(userCategoryDTO.getMinimumPoints());
        updatedUserCategory.setReservationDiscount(userCategoryDTO.getReservationDiscount());
        updatedUserCategory.setConsultationDiscount(userCategoryDTO.getConsultationDiscount());
        return userCategoryRepository.save(updatedUserCategory);
    }

    @Override
    public void removeCategory(UserCategoryDTO userCategoryDTO) {
        UserCategory userCategory = userCategoryRepository.findByName(userCategoryDTO.getName());
        if(userCategory == null) throw new PSNotFoundException("User category with this name was not found");
        userCategoryRepository.deleteById(userCategory.getId());
    }

    /**
     * Our logic is such that we assume that we have one loyalty program in the system and that we always change it
     */
    private LoyaltyProgram getLoyaltyProgram() {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findAll().get(0); // we take first and only one loyalty program in our system
        return loyaltyProgram;
    }
}

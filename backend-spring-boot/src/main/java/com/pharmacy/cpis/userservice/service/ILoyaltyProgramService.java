package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.dto.LoyaltyProgramDTO;
import com.pharmacy.cpis.userservice.dto.UserCategoryDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;

import java.util.List;

public interface ILoyaltyProgramService {
    List<LoyaltyProgram> findAll();

    LoyaltyProgram updateLoyaltyProgram(LoyaltyProgramDTO loyaltyProgramDTO);

    UserCategory saveCategory(UserCategoryDTO userCategoryDTO);

    UserCategory updateCategory(UserCategoryDTO userCategoryDTO);
}

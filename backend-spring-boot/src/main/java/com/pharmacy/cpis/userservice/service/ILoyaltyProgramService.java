package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;

import java.util.List;

public interface ILoyaltyProgramService {
    List<LoyaltyProgram> findAll();
}

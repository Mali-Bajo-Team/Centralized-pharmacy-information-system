package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {

}

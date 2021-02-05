package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserCategoryRepository extends JpaRepository<UserCategory, Long> {

}

package com.pharmacy.cpis.repository;

import com.pharmacy.cpis.model.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserAcc, Long>{
    UserAcc findByEmail(String email );
}

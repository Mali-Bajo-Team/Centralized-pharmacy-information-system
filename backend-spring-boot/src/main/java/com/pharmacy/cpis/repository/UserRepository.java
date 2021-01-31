package com.pharmacy.cpis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.users.model.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, Long>{
}

package com.pharmacy.cpis.repository;

import com.pharmacy.cpis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}

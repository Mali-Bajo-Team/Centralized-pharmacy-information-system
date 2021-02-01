package com.pharmacy.cpis.repository;

import com.pharmacy.cpis.userservice.model.users.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByEmail(String email );
}

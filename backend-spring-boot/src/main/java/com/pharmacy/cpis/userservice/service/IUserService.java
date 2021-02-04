package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.List;

public interface IUserService {
    UserAccount findByEmail(String email );

    List<UserAccount> findAll();

    String getUserRole(UserAccount userAccount);

    void activatePatientAccount(Long userId);
}

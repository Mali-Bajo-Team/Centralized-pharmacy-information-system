package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.List;

public interface IUserService {

    UserAccount findOne(Long id);

    List<UserAccount> findAll();

    UserAccount save(UserAccount userRequest);

    UserAccount findByEmail(String email);

    String getUserRole(UserAccount userAccount);

    void activateUserAccount(Long userId);
}

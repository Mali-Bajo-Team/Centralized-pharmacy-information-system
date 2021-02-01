package com.pharmacy.cpis.service;

import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.List;

public interface IUserService {

    UserAccount findOne(Long id);

    List<UserAccount> findAll();

    UserAccount save(UserAccount userRequest);

    void remove(Long id);

    UserAccount findByEmail(String email);
}

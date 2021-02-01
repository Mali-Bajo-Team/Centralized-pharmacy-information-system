package com.pharmacy.cpis.service;

import com.pharmacy.cpis.dto.UserRegisterDTO;
import com.pharmacy.cpis.users.model.UserAccount;

import java.util.List;

public interface IUserService {

    UserAccount findOne(Long id);

    List<UserAccount> findAll();

    UserAccount save(UserAccount userRequest);

    void remove(Long id);
}

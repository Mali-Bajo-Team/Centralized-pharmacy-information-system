package com.pharmacy.cpis.service;

import com.pharmacy.cpis.users.model.UserAccount;

import java.util.List;

public interface IUserService {

    public UserAccount findOne(Long id);

    public List<UserAccount> findAll();

    public UserAccount save(UserAccount userRequest);

    public void remove(Long id);
}

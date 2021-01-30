package com.pharmacy.cpis.service;

import com.pharmacy.cpis.dto.UserAccDTO;
import com.pharmacy.cpis.model.UserAcc;

import java.util.List;

public interface IUserService {

    public UserAcc findOne(Long id);

    public List<UserAcc> findAll();

    public UserAcc save(UserAcc userRequest);

    public void remove(Long id);
}

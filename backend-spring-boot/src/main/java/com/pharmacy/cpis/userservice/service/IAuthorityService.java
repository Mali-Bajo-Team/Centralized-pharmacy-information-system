package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.userservice.model.users.Authority;

import java.util.List;

public interface IAuthorityService {

    List<Authority> findById(Long id);

    List<Authority> findByName(String name);
}

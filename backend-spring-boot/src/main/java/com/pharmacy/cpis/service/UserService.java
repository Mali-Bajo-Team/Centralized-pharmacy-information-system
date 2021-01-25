package com.pharmacy.cpis.service;

import com.pharmacy.cpis.model.UserAcc;
import com.pharmacy.cpis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserAcc findOne(Long id) { return userRepository.findById(id).orElseGet(null); }

    public List<UserAcc> findAll() { return userRepository.findAll(); }

    public UserAcc save(UserAcc user) { return userRepository.save(user); }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}

package com.pharmacy.cpis.service;

import com.pharmacy.cpis.repository.UserRepository;
import com.pharmacy.cpis.users.model.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserAccount findOne(Long id) { return userRepository.findById(id).orElseGet(null); }

    public List<UserAccount> findAll() { return userRepository.findAll(); }

    public UserAccount save(UserAccount user) { return userRepository.save(user); }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}

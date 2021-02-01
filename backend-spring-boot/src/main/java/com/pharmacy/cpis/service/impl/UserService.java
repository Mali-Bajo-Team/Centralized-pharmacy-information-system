package com.pharmacy.cpis.service.impl;

import com.pharmacy.cpis.model.Authority;
import com.pharmacy.cpis.repository.IUserRepository;
import com.pharmacy.cpis.service.IAuthorityService;
import com.pharmacy.cpis.service.IUserService;
import com.pharmacy.cpis.users.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthorityService authService;

    @Override
    public UserAccount findOne(Long id) {
        return userRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<UserAccount> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserAccount save(UserAccount userRequest) {
        UserAccount newUserAccount = new UserAccount();

        newUserAccount.setEmail(userRequest.getEmail());
        newUserAccount.setPassword(userRequest.getPassword());  //TODO: Implement ENCODER/DECODER for password
        newUserAccount.setActive(false);                        //TODO: Change this to true when user activate account

        List<Authority> auth = authService.findByName("ROLE_USER");
        newUserAccount.setAuthorities(auth);

        newUserAccount = userRepository.save(newUserAccount);
        return newUserAccount;
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    // Username is unique identifier in UserDetailsService, in our system that is email !
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            return user;
        }
    }
}

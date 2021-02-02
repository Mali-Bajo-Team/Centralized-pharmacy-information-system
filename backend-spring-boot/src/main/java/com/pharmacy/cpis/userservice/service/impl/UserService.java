package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IAuthorityService;
import com.pharmacy.cpis.userservice.service.IUserService;
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
        return userRepository.save(userRequest);
    }

    @Override
    public UserAccount findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public String getUserRole(UserAccount user) {
        List<Authority> auth = (List<Authority>) user.getAuthorities();
        Authority userAuthority = auth.get(0);      // take only first one or make some logic to choose needed one
        String userRole = userAuthority.getAuthority();             // we have now for example "ROLE_ADMIN"
        userRole = userRole.substring(5, userRole.length());    // to take "ADMIN" only, we substring "ROLE_"
        return userRole;
    }

    @Override
    public void activateUserAccount(Long userId) {
        // a userAcc must exist
        UserAccount userAccForUpdate = this.findOne(userId);

        if (userAccForUpdate == null) {
            return;
        }

        userAccForUpdate.setActive(true);
        this.save(userAccForUpdate);
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

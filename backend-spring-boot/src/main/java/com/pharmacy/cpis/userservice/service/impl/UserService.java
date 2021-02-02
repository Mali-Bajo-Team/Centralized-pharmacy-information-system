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
    public List<UserAccount> findAll() {
        return userRepository.findAll();
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
    public void activatePatientAccount(Long userId) {
        // a userAcc must exist
        UserAccount userAccForUpdate = userRepository.findById(userId).orElseGet(null);

        if (userAccForUpdate == null) {
            //TODO: Throw exception
            return;
        }

        userAccForUpdate.setActive(true);
        userRepository.save(userAccForUpdate);
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

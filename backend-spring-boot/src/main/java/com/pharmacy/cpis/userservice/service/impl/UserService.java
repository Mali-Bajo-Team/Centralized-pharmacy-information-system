package com.pharmacy.cpis.userservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.userservice.dto.UserTokenState;
import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IUserService;
import com.pharmacy.cpis.util.security.TokenUtils;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenUtils tokenUtils;

	@Override
	public UserAccount findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserAccount> findAll() {
		return userRepository.findAll();
	}

	@Override
	public String getUserRole(UserAccount user) {
		List<Authority> auth = (List<Authority>) user.getAuthorities();
		Authority userAuthority = auth.get(0); // take only first one or make some logic to choose needed one
		String userRole = userAuthority.getAuthority(); // we have now for example "ROLE_ADMIN"
		userRole = userRole.substring(5, userRole.length()); // to take "ADMIN" only, we substring "ROLE_"
		return userRole;
	}

	@Override
	public void activatePatientAccount(Long userId) {
		// a userAcc must exist
		UserAccount userAccForUpdate = userRepository.findById(userId).orElseGet(null);

		if (userAccForUpdate == null) {
			// TODO: Throw exception
			return;
		}

		userAccForUpdate.setActive(true);
		userRepository.save(userAccForUpdate);
	}

	@Override
	public UserAccount changePassword(String oldPassword, String newPassword) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// re-authenticate before password change
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));

		UserAccount user = userRepository.findByEmail(username);
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setNeedsPasswordChange(false);

		return userRepository.save(user);
	}

	@Override
	public UserTokenState logIn(String email, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		// Insert the user into the current security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Create a token for that user
		UserAccount user = (UserAccount) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user);
		int expiresIn = tokenUtils.getExpiredIn();

		return new UserTokenState(jwt, expiresIn);
	}
}

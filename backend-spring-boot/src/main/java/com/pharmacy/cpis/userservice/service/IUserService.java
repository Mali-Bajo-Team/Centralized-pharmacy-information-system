package com.pharmacy.cpis.userservice.service;

import java.util.List;

import com.pharmacy.cpis.userservice.dto.UserTokenState;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

public interface IUserService {
	List<UserAccount> findAll();

	String getUserRole(UserAccount userAccount);

	void activatePatientAccount(Long userId);

	UserAccount changePassword(String oldPassword, String newPassword);
	
	UserTokenState logIn(String email, String password);
}

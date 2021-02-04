package com.pharmacy.cpis.userservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PasswordChangeDTO {

	@NotEmpty(message = "Old password is required.")
	private String oldPassword;
	@NotEmpty(message = "New password is required.")
	@Size(min = 6, message = "New password must have at least 6 characters.")
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}

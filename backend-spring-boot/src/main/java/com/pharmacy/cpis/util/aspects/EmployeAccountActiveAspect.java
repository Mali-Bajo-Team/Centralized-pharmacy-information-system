package com.pharmacy.cpis.util.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;

@Aspect
@Component
public class EmployeAccountActiveAspect {

	@Before("@annotation(com.pharmacy.cpis.util.aspects.EmployeeAccountActive)")
	public void check(JoinPoint joinPoint) {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.isNeedsPasswordChange())
			throw new PSForbiddenException("Password must be changed before accessing the resource.");
	}

}

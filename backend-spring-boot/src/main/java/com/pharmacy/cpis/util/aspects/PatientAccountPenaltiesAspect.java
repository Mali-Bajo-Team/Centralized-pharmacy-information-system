package com.pharmacy.cpis.util.aspects;

import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.impl.PatientService;
import com.pharmacy.cpis.util.exceptions.PSForbiddenException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PatientAccountPenaltiesAspect {
        private final int PENALTY_LIMIT= 3;
        @Autowired
        private PatientService patientService;

        @Before("@annotation(com.pharmacy.cpis.util.aspects.PatientAccountPenalties)")
        public void check(JoinPoint joinPoint)
        {
            UserAccount user= (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Patient patient= patientService.findByEmail(user.getEmail());
            if(patient.getPenalties() != null && patient.getPenalties() >= PENALTY_LIMIT) {
                throw new PSForbiddenException("Penalty limit reached.");
            }
        }

}

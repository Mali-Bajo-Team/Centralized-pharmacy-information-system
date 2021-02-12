package com.pharmacy.cpis.util.scheduled;

import com.pharmacy.cpis.userservice.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PenaltiesCleaner {
    @Autowired
    private IPatientService patientService;

    @Scheduled(cron="0 0 0 1 1/1 *")
    public void cleanPenalties() {
        System.out.println("Penalties cleaning started.");
        patientService.resetPenalties();
    }
}

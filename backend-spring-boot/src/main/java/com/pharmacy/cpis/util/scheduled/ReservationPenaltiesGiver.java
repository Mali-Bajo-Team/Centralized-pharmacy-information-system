package com.pharmacy.cpis.util.scheduled;

import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.service.IPatientService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ReservationPenaltiesGiver {
    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IPatientService patientService;

    @Scheduled(cron="0 0 0 1 1/1 *")
    public void givePenaltiesForUnclaimedReservations() {
        LocalDate today= new LocalDate();
        if(today.getDayOfMonth() == 1)
        {System.out.println("Today is 1st of the month. No reason to give penalties.");
        return;
        }LocalDate yesterday= today.minusDays(1);
        System.out.println("Giving reservation penalties for "+ yesterday);
        Set<Reservation> unclaimedReservations= reservationService.getUnclaimedReservationsForDate(yesterday);
        for(Reservation r:unclaimedReservations) {
            Patient p= r.getPatient();
            patientService.addPenalty(p.getAccount().getEmail());
        }
    }
}

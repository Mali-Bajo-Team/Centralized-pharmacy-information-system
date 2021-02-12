package com.pharmacy.cpis.drugservice.service.impl;


import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IReservationRepository;
import com.pharmacy.cpis.drugservice.service.IAvailableDrugService;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.PrescribedDrug;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.service.EmailService;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.pharmacy.cpis.drugservice.dto.ReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.*;
import com.pharmacy.cpis.drugservice.repository.IDrugPurchaseRepository;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import com.pharmacy.cpis.util.DateConversionsAndComparisons;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IDrugRepository drugRepository;

    @Autowired
    private IPharmacyRepository pharmacyRepository;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IPharmacyService pharmacyService;

    @Autowired
    private IAvailableDrugService availableDrugService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IConsultantService consultantService;

    @Autowired
    private IDrugPurchaseRepository drugPurchaseRepository;

    @Autowired
    private ILoyaltyProgramService loyaltyProgramService;
    @Autowired
    private IUserRepository userRepository;

    @Override
    public Reservation makeReservation(DrugReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setAmount(reservationDTO.getAmount());
        reservation.setDeadline(reservationDTO.getDeadline());
        reservation.setPatient(patientService.findByEmail(reservationDTO.getPatientEmail()));
        reservation.setDateOfCreation(new Date());
        reservation.setPharmacy(pharmacyService.getById(reservationDTO.getPharmacyID()));
        reservation.setDrug(drugRepository.findByCode(reservationDTO.getDrugCode()));
        reservation.setIsPickedUp(false);

        availableDrugService.updateAmount(reservationDTO.getPharmacyID(), reservationDTO.getDrugCode(), reservationDTO.getAmount());
        Reservation savedReservation = reservationRepository.save(reservation);
        try {
            System.out.println("Sending mail in process ..");
            emailService.sendConfirmReservationOfDrugEmailAsync(reservationDTO.getPatientEmail(),
                    reservationDTO, savedReservation);

        } catch (Exception e) {
            System.out.println("Error during sending email: " + e.getMessage());
        }
        return savedReservation;
    }

    @Override
    public void makeReservationForEPrescription(EPrescription ePrescription, Long pharmacyIdWhereIsMadeReservation) {
        List<Reservation> reservationsForEPrescriptions = new ArrayList<>();
        for (PrescribedDrug prescribedDrug : ePrescription.getPrescribedDrugs()) {
            Reservation reservation = new Reservation();
            reservation.setAmount(prescribedDrug.getAmount());
            // Make this deadline for ePrescription drugs reservation because that is not defined by project specification
            reservation.setDeadline(DateConversionsAndComparisons.getUtilDate("2022-01-01 12:00:00"));
            reservation.setPatient(ePrescription.getPatient());
            reservation.setDateOfCreation(new Date());
            Pharmacy pharmacyWhereIsMadeReservation = pharmacyRepository.findById(pharmacyIdWhereIsMadeReservation).orElse(null);
            if(pharmacyWhereIsMadeReservation == null) throw new PSBadRequestException("That pharmacy does not exist in our system");
            reservation.setPharmacy(pharmacyWhereIsMadeReservation);
            reservation.setDrug(prescribedDrug.getDrug());
            reservation.setIsPickedUp(false);
            availableDrugService.updateAmount(pharmacyIdWhereIsMadeReservation, prescribedDrug.getDrug().getCode(), prescribedDrug.getAmount());
            Reservation savedReservation = reservationRepository.save(reservation);
            reservationsForEPrescriptions.add(savedReservation);
        }

        try {
            System.out.println("Sending mail in process ..");
            emailService.sendConfirmEPrescriptionDrugsReservationEmailAsync(reservationsForEPrescriptions);

        } catch (Exception e) {
            System.out.println("Error during sending email: " + e.getMessage());
        }
    }

    @Override
    public ReservationDTO isReservationValid(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.getOne(reservationDTO.getReservationID());

        Boolean isValid = false;

        Instant now = Instant.now(); //current date
        Instant before24h = now.plus(Duration.ofDays(1));
        Date dateBefore24h = Date.from(before24h);

        Consultant consultant = consultantService.findByEmail(reservationDTO.getConsultantEmail());

        Pharmacy pharmacy = findPharmacyWhereConsultantWork(consultant.getId());
        if (pharmacy.getId().equals(reservation.getPharmacy().getId())) {
            if (reservationRepository.existsById(reservationDTO.getReservationID()) && reservation.getDeadline().compareTo(dateBefore24h) > 0) {

                if (Boolean.FALSE.equals(reservation.getIsPickedUp())) {
                    isValid = true;
                    reservationDTO.setValid(isValid);
                    reservationDTO.setAmount(reservation.getAmount());
                    reservationDTO.setDateOfCreation(reservation.getDateOfCreation());
                    reservationDTO.setDeadLine(reservation.getDeadline());
                    reservationDTO.setPhatientName(reservation.getPatient().getName());
                    reservationDTO.setPharmacyName(reservation.getPharmacy().getName());
                    //send mail
                    String phatientEmail = userRepository.getOne(reservation.getPatient().getId()).getEmail();
                    try {
                        emailService.sendConfirmDisepnsingToPatientAsync(reservationDTO.getPhatientName(), phatientEmail, reservation);
                    } catch (Exception e) {
                        System.err.println("Error sending cinfirm mail for dispensing drug");
                    }

                    return reservationDTO;
                }
            }
        }

        reservationDTO.setValid(isValid);

        return reservationDTO;
    }

    @Override
    @Transactional
    public ReservationDTO dispensingMedicine(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.getOne(reservationDTO.getReservationID());

        reservation.setIsPickedUp(true);
        reservationRepository.save(reservation);
        sendDrugPurchase(reservation);

        return reservationDTO;
    }

    @Override
    public List<Pharmacy> findAllPatientPharmacies(Patient patient) {
        List<Pharmacy> allPatientPharmacies = new ArrayList<>();
        for (Reservation reservation : reservationRepository.findAllByPatient(patient)) {
            boolean alreadyExistPharmacy = false;
            for (Pharmacy pharmacy : allPatientPharmacies) {
                if (pharmacy.getId().equals(reservation.getPharmacy().getId())) {
                    alreadyExistPharmacy = true;
                    break;
                }
            }
            if (!alreadyExistPharmacy)
                allPatientPharmacies.add(reservation.getPharmacy());
        }
        return allPatientPharmacies;
    }

    @Override
    public List<Reservation> findAllPatientReservations(PatientEmailDTO patientEmailDTO) {
        Patient patient = patientService.findByEmail(patientEmailDTO.getEmail());
        return reservationRepository.findAllByPatient(patient);
    }

    @Override
    public void removeReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Drug> findAllPatientDrugsAvailableForRating(String patientEmail) { // drugs which patient had contact (made reservation for them)
        Patient patient = patientService.findByEmail(patientEmail);
        List<Reservation> reservations = reservationRepository.findAllByPatient(patient);
        List<Drug> patientDrugs = new ArrayList<>();

        for(Reservation reservation : reservations){
            boolean alreadyAdded = false;
            for(Drug drug : patientDrugs){
                if(drug.getCode().equals(reservation.getDrug().getCode())){
                    alreadyAdded = true;
                    break;
                }
            }
            if(!alreadyAdded){
                patientDrugs.add(reservation.getDrug());
            }
        }

        return patientDrugs;
    }

    private void sendDrugPurchase(Reservation reservation) {
        DrugPurchase drugPurchase = new DrugPurchase();

        drugPurchase.setAmount(reservation.getAmount());
        drugPurchase.setDrug(reservation.getDrug());
        drugPurchase.setPatient(reservation.getPatient());
        drugPurchase.setPharmacy(reservation.getPharmacy());

        drugPurchase.setPrice(calculatePriceDrugPurchaseWithDiscount(reservation));
        drugPurchase.setTimestamp(new Date());
        drugPurchase.setType(DrugPurchaseType.RESERVATION);

        drugPurchaseRepository.save(drugPurchase);
    }

    private Double calculatePriceDrugPurchaseWithDiscount(Reservation reservation) {
        Double priceWithDiscount;

        AvailableDrug availableDrug = availableDrugService.getByPharmacyAndDrug(reservation.getPharmacy().getId(), reservation.getDrug().getCode());
        Double priceWithoutDiscount = availableDrug.findPrice(reservation.getDateOfCreation()).getPrice();
        UserCategory userCategory = loyaltyProgramService.findUserCategoryByLoyaltyPoints(reservation.getPatient().getLoyaltyPoints());
        Double discount = userCategory.getReservationDiscount();

        if (discount != 0) {
            priceWithDiscount = (priceWithoutDiscount - (priceWithoutDiscount * (discount / 100))) * reservation.getAmount();
        } else {
            priceWithDiscount = priceWithoutDiscount;
        }
        return priceWithDiscount;
    }


    Pharmacy findPharmacyWhereConsultantWork(Long consultantID) {
        List<Pharmacy> allPharmacies = pharmacyService.findAll();

        for (Pharmacy p : allPharmacies) {
            for (WorkingTimes wt : p.getConsultants()) {
                if (wt.getConsultant().getId().equals(consultantID)) {
                    return p;
                }
            }
        }
        return new Pharmacy();
    }

    @Override
    public Set<Reservation> getUnclaimedReservationsForDate(LocalDate date) {
        return reservationRepository.getUnclaimedReservationsForDate(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
    }
}

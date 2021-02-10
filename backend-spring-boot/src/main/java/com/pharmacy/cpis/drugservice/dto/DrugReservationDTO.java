package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import com.pharmacy.cpis.userservice.dto.PatientEmailDTO;

import java.util.Date;

public class DrugReservationDTO {

    private Long reservationId;
    private Integer amount;
    private Date dateOfCreation;
    private Date deadline;
    private String patientEmail;
    private String drugCode;
    private Long pharmacyID;
    private String pharmacyName;
    private String drugName;

    public DrugReservationDTO(){

    }

    public DrugReservationDTO(Integer amount, Date dateOfCreation, Date deadline, String patientEmail, String drugCode, Long pharmacyID, Long reservationId,String pharmacyName,String drugName) {
        this.amount = amount;
        this.dateOfCreation = dateOfCreation;
        this.deadline = deadline;
        this.patientEmail = patientEmail;
        this.drugCode = drugCode;
        this.pharmacyID = pharmacyID;
        this.reservationId=reservationId;
        this.pharmacyName=pharmacyName;
        this.drugName=drugName;
    }
    public DrugReservationDTO(Reservation reservation){
        this.setAmount(reservation.getAmount());
        this.setDrugCode(reservation.getDrug().getCode());
        this.setDeadline(reservation.getDeadline());
        this.setPatientEmail(reservation.getPatient().getAccount().getEmail());
        this.setPharmacyID(reservation.getPharmacy().getId());
        this.setDateOfCreation(reservation.getDateOfCreation());
        this.setReservationId(reservation.getId());
        this.setPharmacyName(reservation.getPharmacy().getName());
        this.setDrugName(reservation.getDrug().getName());

    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public Long getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(Long pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
}

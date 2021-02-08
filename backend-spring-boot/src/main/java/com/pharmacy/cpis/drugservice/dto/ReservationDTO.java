package com.pharmacy.cpis.drugservice.dto;

import java.util.Date;

public class ReservationDTO {
    Boolean isValid;
    Long reservationID;
    Date dateOfCreation;
    Date deadLine;
    Long amount;
    String phatientName;
    String pharmacyName;
    String consultantEmail;

    public ReservationDTO() {
    }

    public ReservationDTO(Boolean isValid, Long reservationID, Date dateOfCreation, Date deadLine, Long amount, String phatientName, String pharmacyName, String consultantEmail) {
        this.isValid = isValid;
        this.reservationID = reservationID;
        this.dateOfCreation = dateOfCreation;
        this.deadLine = deadLine;
        this.amount = amount;
        this.phatientName = phatientName;
        this.pharmacyName = pharmacyName;
        this.consultantEmail = consultantEmail;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPhatientName() {
        return phatientName;
    }

    public void setPhatientName(String phatientName) {
        this.phatientName = phatientName;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getConsultantEmail() {
        return consultantEmail;
    }

    public void setConsultantEmail(String consultantEmail) {
        this.consultantEmail = consultantEmail;
    }
}

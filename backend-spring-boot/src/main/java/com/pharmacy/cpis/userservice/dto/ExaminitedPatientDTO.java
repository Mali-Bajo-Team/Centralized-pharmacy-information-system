package com.pharmacy.cpis.userservice.dto;

import com.pharmacy.cpis.util.DateRange;

import java.util.Date;

public class ExaminitedPatientDTO {
    private String consultantEmail;
    private String name;
    private String surname;
    private DateRange examinitedDate;

    public ExaminitedPatientDTO( String consultantEmail, String name, String surname, DateRange examinitedDate) {
        this.consultantEmail = consultantEmail;
        this.name = name;
        this.surname = surname;
        this.examinitedDate = examinitedDate;
    }

    public ExaminitedPatientDTO() {

    }

    public String getConsultantEmail() {
        return consultantEmail;
    }

    public void setConsultantEmail(String consultantEmail) {
        this.consultantEmail = consultantEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public DateRange getExaminitedDate() {
        return examinitedDate;
    }

    public void setExaminitedDate(DateRange examinitedDate) {
        this.examinitedDate = examinitedDate;
    }
}

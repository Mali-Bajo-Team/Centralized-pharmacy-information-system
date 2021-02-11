package com.pharmacy.cpis.scheduleservice.dto;

import java.util.Date;

public class EPrescriptionCreateDTO {
    private Date creationDate;
    private String patientEmail;

    public EPrescriptionCreateDTO(){

    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}

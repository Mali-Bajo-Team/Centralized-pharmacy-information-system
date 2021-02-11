package com.pharmacy.cpis.scheduleservice.dto;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescriptionStatus;

import java.util.Date;

public class EPrescriptionReadDTO {
    private Long prescriptionId;
    private Date creationDate;
    private EPrescriptionStatus status;
    private Long patientId;

    public EPrescriptionReadDTO(){

    }

    public EPrescriptionReadDTO(EPrescription ePrescription){
        this.setPrescriptionId(ePrescription.getId());
        this.setCreationDate(ePrescription.getCreationDate());
        this.setStatus(ePrescription.getStatus());
        this.setPatientId(ePrescription.getPatient().getId());
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public EPrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(EPrescriptionStatus status) {
        this.status = status;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}

package com.pharmacy.cpis.scheduleservice.dto;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;

import java.util.Date;

public class ScheduleExaminationDTO {
    private String consultantEmail;
    private Long id;
    private String startDate;
    private String endDate;
    private String patientName;
    private String patientSurname;
    private Long patientId;
    private String patientEmail;
    private Long consultantId;
    private Long pharmacyID;
    private Long predefinedConsultationID;

    public ScheduleExaminationDTO() {
    }

    public ScheduleExaminationDTO(String consultantEmail, Long id, String startDate, String endDate, String patientName, String patientSurname, Long patientId, Long consultantId, Long pharmacyID, Long predefinedConsultationID) {
        this.consultantEmail = consultantEmail;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientId = patientId;
        this.consultantId = consultantId;
        this.pharmacyID = pharmacyID;
        this.predefinedConsultationID = predefinedConsultationID;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getConsultantEmail() {
        return consultantEmail;
    }

    public void setConsultantEmail(String consultantEmail) {
        this.consultantEmail = consultantEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSurname() {
        return patientSurname;
    }

    public void setPatientSurname(String patientSurname) {
        this.patientSurname = patientSurname;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    public Long getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(Long pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public Long getPredefinedConsultationID() {
        return predefinedConsultationID;
    }

    public void setPredefinedConsultationID(Long predefinedConsultationID) {
        this.predefinedConsultationID = predefinedConsultationID;
    }
}

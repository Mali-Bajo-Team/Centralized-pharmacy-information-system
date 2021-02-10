package com.pharmacy.cpis.scheduleservice.dto;

import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationReport;

public class DrugRecommendationDTO {
    private Integer duration;
    private String consultationReport;
    private String drugCode;
    private Long drugID;
    private Long patientID;
    private Long consultationID;
    private String consultantEmail;
    private Boolean isAvailable;

    public DrugRecommendationDTO() {
    }

    public DrugRecommendationDTO(Integer duration, String consultationReport, String drugCode, Long drugID, Long patientID, Long consultationID, String consultantEmail, Boolean isAvailable) {
        this.duration = duration;
        this.consultationReport = consultationReport;
        this.drugCode = drugCode;
        this.drugID = drugID;
        this.patientID = patientID;
        this.consultationID = consultationID;
        this.consultantEmail = consultantEmail;
        this.isAvailable = isAvailable;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getConsultationReport() {
        return consultationReport;
    }

    public void setConsultationReport(String consultationReport) {
        this.consultationReport = consultationReport;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public Long getDrugID() {
        return drugID;
    }

    public void setDrugID(Long drugID) {
        this.drugID = drugID;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public Long getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(Long consultationID) {
        this.consultationID = consultationID;
    }

    public String getConsultantEmail() {
        return consultantEmail;
    }

    public void setConsultantEmail(String consultantEmail) {
        this.consultantEmail = consultantEmail;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}

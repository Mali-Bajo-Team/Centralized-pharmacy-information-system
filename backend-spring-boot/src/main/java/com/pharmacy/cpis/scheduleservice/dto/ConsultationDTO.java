package com.pharmacy.cpis.scheduleservice.dto;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import java.util.Date;

public class ConsultationDTO {
    private String consultantEmail;
    private Long id;
    private Date startDate;
    private Date endDate;
    private String patientName;
    private String patientSurname;
    private Long patientId;
    private Long consultantId;
    private Long pharmacyID;
    private String pharmacyName;

    public ConsultationDTO() {
    }

    public ConsultationDTO(Consultation consultation) {
        this(consultation.getConsultant().getAccount().getEmail() , consultation.getId(), consultation.getTime().getStart(), consultation.getTime().getEnd(), consultation.getPatient().getName(), consultation.getPatient().getSurname(), consultation.getPatient().getId(), consultation.getConsultant().getId(), consultation.getPharmacy().getId(), consultation.getPharmacy().getName());
    }

    public ConsultationDTO(String consultantEmail, Long id, Date startDate, Date endDate, String patientName, String patientSurname, Long patientId, Long consultantId, Long pharmacyID, String pharmacyName) {
        super();
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientId = patientId;
        this.consultantId = consultantId;
        this.consultantEmail = consultantEmail;
        this.pharmacyID = pharmacyID;
        this.pharmacyName = pharmacyName;
    }

    public Long getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(Long pharmacyID) {
        this.pharmacyID = pharmacyID;
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientSurname() {
        return patientSurname;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientSurname(String patientSurname) {
        this.patientSurname = patientSurname;
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }
}

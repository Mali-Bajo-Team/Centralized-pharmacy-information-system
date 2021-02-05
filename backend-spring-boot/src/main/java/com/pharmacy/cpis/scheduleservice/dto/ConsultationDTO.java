package com.pharmacy.cpis.scheduleservice.dto;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import java.util.Date;

public class ConsultationDTO {

    private Long id;
    private Date startDate;
    private Date endDate;
    private String patientName;
    private String patientSurname;
    private Long patientId;

    public ConsultationDTO(Consultation consultation) {
        this(consultation.getId(), consultation.getTime().getStart(), consultation.getTime().getEnd(), consultation.getPatient().getName(), consultation.getPatient().getSurname(), consultation.getPatient().getId());
    }

    public ConsultationDTO(Long id, Date startDate, Date endDate, String patientName, String patientSurname, Long patientId) {
        super();
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientId = patientId;
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
}

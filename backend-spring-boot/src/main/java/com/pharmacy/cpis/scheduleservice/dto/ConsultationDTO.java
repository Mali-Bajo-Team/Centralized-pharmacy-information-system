package com.pharmacy.cpis.scheduleservice.dto;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import java.util.Date;

public class ConsultationDTO {

    private Long id;
    private Date startDate;
    private Date endDate;

    public ConsultationDTO(Consultation consultation) {
        this(consultation.getId(), consultation.getTime().getStart(), consultation.getTime().getEnd());
    }

    public ConsultationDTO(Long id, Date startDate, Date endDate) {
        super();
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
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

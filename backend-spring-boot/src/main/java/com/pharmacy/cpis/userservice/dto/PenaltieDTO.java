package com.pharmacy.cpis.userservice.dto;

public class PenaltieDTO {
    Long phatientID;
    Long consultationID;

    public PenaltieDTO() {
    }

    public PenaltieDTO(Long phatientID, Long consultationID) {
        this.phatientID = phatientID;
        this.consultationID = consultationID;
    }

    public Long getPhatientID() {
        return phatientID;
    }

    public void setPhatientID(Long phatientID) {
        this.phatientID = phatientID;
    }

    public Long getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(Long consultationID) {
        this.consultationID = consultationID;
    }
}

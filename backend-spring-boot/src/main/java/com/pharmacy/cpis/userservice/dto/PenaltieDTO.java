package com.pharmacy.cpis.userservice.dto;

public class PenaltieDTO {
    Long phatientID;

    public PenaltieDTO(Long phatientID) {
        this.phatientID = phatientID;
    }

    public PenaltieDTO() {
    }

    public Long getPhatientID() {
        return phatientID;
    }

    public void setPhatientID(Long phatientID) {
        this.phatientID = phatientID;
    }
}

package com.pharmacy.cpis.drugservice.dto;

public class ReservationDTO {
    Boolean isValid;
    Long reservationID;

    public ReservationDTO() {
    }

    public ReservationDTO(Boolean isValid, Long reservationID) {
        this.isValid = isValid;
        this.reservationID = reservationID;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }
}

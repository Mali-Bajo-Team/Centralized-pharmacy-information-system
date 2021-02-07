package com.pharmacy.cpis.scheduleservice.dto;

import java.sql.Date;

public class VacationRequestDTO {
    private String consultantEmail;
    private String startVacationReqDate;
    private String endVacatonReqDate;

    public VacationRequestDTO() {
    }

    public VacationRequestDTO(String consultantEmail, String startVacationReqDate, String endVacatonReqDate, String response) {
        this.consultantEmail = consultantEmail;
        this.startVacationReqDate = startVacationReqDate;
        this.endVacatonReqDate = endVacatonReqDate;
    }

    public String getConsultantEmail() {
        return consultantEmail;
    }

    public void setConsultantEmail(String consultantEmail) {
        this.consultantEmail = consultantEmail;
    }

    public String getStartVacationReqDate() {
        return startVacationReqDate;
    }

    public void setStartVacationReqDate(String startVacationReqDate) {
        this.startVacationReqDate = startVacationReqDate;
    }

    public String getEndVacatonReqDate() {
        return endVacatonReqDate;
    }

    public void setEndVacatonReqDate(String endVacatonReqDate) {
        this.endVacatonReqDate = endVacatonReqDate;
    }

}

package com.pharmacy.cpis.scheduleservice.dto;

import java.sql.Date;

public class VacationRequestDTO {
    private String consultantEmail;
    private Date startVacationReqDate;
    private Date endVacatonReqDate;
    private String response;

    public VacationRequestDTO(String consultantEmail, Date startVacationReqDate, Date endVacatonReqDate, String response) {
        this.consultantEmail = consultantEmail;
        this.startVacationReqDate = startVacationReqDate;
        this.endVacatonReqDate = endVacatonReqDate;
        this.response = response;
    }

    public String getConsultantEmail() {
        return consultantEmail;
    }

    public void setConsultantEmail(String consultantEmail) {
        this.consultantEmail = consultantEmail;
    }

    public Date getStartVacationReqDate() {
        return startVacationReqDate;
    }

    public void setStartVacationReqDate(Date startVacationReqDate) {
        this.startVacationReqDate = startVacationReqDate;
    }

    public Date getEndVacatonReqDate() {
        return endVacatonReqDate;
    }

    public void setEndVacatonReqDate(Date endVacatonReqDate) {
        this.endVacatonReqDate = endVacatonReqDate;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

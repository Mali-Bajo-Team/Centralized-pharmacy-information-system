package com.pharmacy.cpis.scheduleservice.dto;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import java.util.Date;

public class WorkingTimesDTO {

    private Long id;

    private Long consultantID;

    private Long pharmacyID;

    private Date monday_start;
    private Date monday_end;

    private Date tuesday_start;
    private Date tuesday_end;

    private Date wednesday_start;
    private Date wednesday_end;

    private Date thursday_start;
    private Date thursday_end;

    private Date friday_start;
    private Date friday_end;

    private Date saturday_start;
    private Date saturday_end;

    private Date sunday_start;
    private Date sunday_end;

    public WorkingTimesDTO(WorkingTimes wt) {
        this.id = wt.getId();
        this.consultantID = wt.getConsultant().getId();
        this.pharmacyID = wt.getPharmacy().getId();
        this.monday_start = wt.getMonday().getStart();
        this.monday_end = wt.getMonday().getEnd();
        this.tuesday_start = wt.getThursday().getStart();
        this.tuesday_end = wt.getThursday().getEnd();
        this.wednesday_start = wt.getWednesday().getStart();
        this.wednesday_end = wt.getWednesday().getEnd();
        this.thursday_start = wt.getThursday().getStart();
        this.thursday_end = wt.getThursday().getEnd();
        this.friday_start = wt.getFriday().getStart();
        this.friday_end = wt.getFriday().getEnd();
        this.saturday_start = wt.getSaturday().getStart();
        this.saturday_end = wt.getSaturday().getEnd();
        this.sunday_start = wt.getSunday().getStart();
        this.sunday_end = wt.getSunday().getEnd();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(Long pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public Long getConsultantID() {
        return consultantID;
    }

    public void setConsultantID(Long consultantID) {
        this.consultantID = consultantID;
    }

    public Date getMonday_start() {
        return monday_start;
    }

    public void setMonday_start(Date monday_start) {
        this.monday_start = monday_start;
    }

    public Date getMonday_end() {
        return monday_end;
    }

    public void setMonday_end(Date monday_end) {
        this.monday_end = monday_end;
    }

    public Date getTuesday_start() {
        return tuesday_start;
    }

    public void setTuesday_start(Date tuesday_start) {
        this.tuesday_start = tuesday_start;
    }

    public Date getTuesday_end() {
        return tuesday_end;
    }

    public void setTuesday_end(Date tuesday_end) {
        this.tuesday_end = tuesday_end;
    }

    public Date getWednesday_start() {
        return wednesday_start;
    }

    public void setWednesday_start(Date wednesday_start) {
        this.wednesday_start = wednesday_start;
    }

    public Date getWednesday_end() {
        return wednesday_end;
    }

    public void setWednesday_end(Date wednesday_end) {
        this.wednesday_end = wednesday_end;
    }

    public Date getThursday_start() {
        return thursday_start;
    }

    public void setThursday_start(Date thursday_start) {
        this.thursday_start = thursday_start;
    }

    public Date getThursday_end() {
        return thursday_end;
    }

    public void setThursday_end(Date thursday_end) {
        this.thursday_end = thursday_end;
    }

    public Date getFriday_start() {
        return friday_start;
    }

    public void setFriday_start(Date friday_start) {
        this.friday_start = friday_start;
    }

    public Date getFriday_end() {
        return friday_end;
    }

    public void setFriday_end(Date friday_end) {
        this.friday_end = friday_end;
    }

    public Date getSaturday_start() {
        return saturday_start;
    }

    public void setSaturday_start(Date saturday_start) {
        this.saturday_start = saturday_start;
    }

    public Date getSaturday_end() {
        return saturday_end;
    }

    public void setSaturday_end(Date saturday_end) {
        this.saturday_end = saturday_end;
    }

    public Date getSunday_start() {
        return sunday_start;
    }

    public void setSunday_start(Date sunday_start) {
        this.sunday_start = sunday_start;
    }

    public Date getSunday_end() {
        return sunday_end;
    }

    public void setSunday_end(Date sunday_end) {
        this.sunday_end = sunday_end;
    }
}

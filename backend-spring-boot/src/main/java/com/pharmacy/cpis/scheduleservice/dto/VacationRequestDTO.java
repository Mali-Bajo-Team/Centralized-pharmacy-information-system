package com.pharmacy.cpis.scheduleservice.dto;

import java.util.Date;

import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequestStatus;

public class VacationRequestDTO {

	private String consultantEmail;
	private String consultantName;
	private String consultantSurname;
	private Date start;
	private Date end;
	private VacationRequestStatus status;
	private String response;

	public VacationRequestDTO(VacationRequest request) {
		super();
		this.consultantEmail = request.getConsultant().getAccount().getEmail();
		this.consultantName = request.getConsultant().getName();
		this.consultantSurname = request.getConsultant().getSurname();
		this.start = request.getDateRange().getStart();
		this.end = request.getDateRange().getEnd();
		this.status = request.getStatus();
		this.response = request.getResponse();
	}

	public String getConsultantEmail() {
		return consultantEmail;
	}

	public String getConsultantName() {
		return consultantName;
	}

	public String getConsultantSurname() {
		return consultantSurname;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public VacationRequestStatus getStatus() {
		return status;
	}

	public String getResponse() {
		return response;
	}

}

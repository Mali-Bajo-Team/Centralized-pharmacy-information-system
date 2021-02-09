package com.pharmacy.cpis.scheduleservice.dto;

import java.util.Date;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;

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
	private Double price;
	private String consultantName;
	private String consultantSurname;

	public ConsultationDTO() {
	}

	public ConsultationDTO(Consultation consultation) {
		this.consultantEmail = consultation.getConsultant().getAccount().getEmail();
		this.id = consultation.getId();
		this.startDate = consultation.getTime().getStart();
		this.endDate = consultation.getTime().getEnd();
		this.consultantId = consultation.getConsultant().getId();
		this.consultantName = consultation.getConsultant().getName();
		this.consultantSurname = consultation.getConsultant().getSurname();
		this.pharmacyID = consultation.getPharmacy().getId();
		this.pharmacyName = consultation.getPharmacy().getName();
		this.price = consultation.getPrice();
		if (consultation.getPatient() != null) {
			this.patientId = consultation.getPatient().getId();
			this.patientName = consultation.getPatient().getName();
			this.patientSurname = consultation.getPatient().getSurname();
		}
	}

	public ConsultationDTO(String consultantEmail, Long id, Date startDate, Date endDate, String patientName,
			String patientSurname, Long patientId, Long consultantId, Long pharmacyID, String pharmacyName) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getConsultantName() {
		return consultantName;
	}

	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	public String getConsultantSurname() {
		return consultantSurname;
	}

	public void setConsultantSurname(String consultantSurname) {
		this.consultantSurname = consultantSurname;
	}

}

package com.pharmacy.cpis.drugservice.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrderStatus;
import com.pharmacy.cpis.drugservice.model.drugprocurement.OrderedDrug;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;

public class DrugOrderDTO {

	private Long id;

	private Date timestamp;

	private Date deadline;

	private DrugOrderStatus status;

	private PharmacyDTO pharmacy;

	private String creatorEmail;

	private Set<OrderedDrugDTO> orderedDrugs;

	public DrugOrderDTO(DrugOrder drugOrder) {
		this.setId(drugOrder.getId());
		this.setTimestamp(drugOrder.getTimestamp());
		this.setDeadline(drugOrder.getDeadline());
		this.setStatus(drugOrder.getStatus());
		this.setPharmacy(new PharmacyDTO(drugOrder.getPharmacy()));
		this.setOrderedDrugs(drugOrder.getOrderedDrugs());
		this.creatorEmail = drugOrder.getAdministrator().getAccount().getEmail();
	}

	public DrugOrderDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public DrugOrderStatus getStatus() {
		return status;
	}

	public void setStatus(DrugOrderStatus status) {
		this.status = status;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public Set<OrderedDrugDTO> getOrderedDrugs() {
		return orderedDrugs;
	}

	public void setOrderedDrugs(Set<OrderedDrug> orderedDrugs) {
		this.orderedDrugs = new HashSet<>();
		for (OrderedDrug orderedDrug : orderedDrugs) {
			this.orderedDrugs.add(new OrderedDrugDTO(orderedDrug));
		}
	}
}

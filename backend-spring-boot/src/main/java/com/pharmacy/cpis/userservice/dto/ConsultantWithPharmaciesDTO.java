package com.pharmacy.cpis.userservice.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.pharmacy.cpis.pharmacyservice.dto.MiniPharmacyDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.userservice.model.users.Consultant;

public class ConsultantWithPharmaciesDTO {

	private Long id;
	private String name;
	private String surname;
	private Double rating;
	private Collection<MiniPharmacyDTO> pharmacies;

	public ConsultantWithPharmaciesDTO(Consultant consultant) {
		super();
		this.id = consultant.getId();
		this.name = consultant.getName();
		this.surname = consultant.getSurname();
		this.rating = consultant.getAverageRating();
		this.pharmacies = new ArrayList<>();
		for (WorkingTimes employment : consultant.getWorkingTimes()) {
			pharmacies.add(new MiniPharmacyDTO(employment.getPharmacy().getName(), employment.getPharmacy().getId()));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Double getRating() {
		return rating;
	}

	public Collection<MiniPharmacyDTO> getPharmacies() {
		return pharmacies;
	}

}

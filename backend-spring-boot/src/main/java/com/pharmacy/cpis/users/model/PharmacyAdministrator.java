package com.pharmacy.cpis.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.pharmacy.cpis.pharmacy.model.Pharmacy;

@Entity
@DiscriminatorValue("PharmacyAdmin")
public class PharmacyAdministrator extends Person {
	@ManyToOne
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

}

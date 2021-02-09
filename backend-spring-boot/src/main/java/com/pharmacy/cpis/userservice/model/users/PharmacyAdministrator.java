package com.pharmacy.cpis.userservice.model.users;


import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

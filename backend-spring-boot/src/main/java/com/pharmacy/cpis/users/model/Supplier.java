package com.pharmacy.cpis.users.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.pharmacy.cpis.drug.model.Drug;

@Entity
@DiscriminatorValue("Supplier")
public class Supplier extends Person {
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "supplier_drugs", joinColumns = @JoinColumn(name = "supplier_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "drug_code", referencedColumnName = "code"))
	private Set<Drug> availableDrugs;

	public Set<Drug> getAvailableDrugs() {
		return availableDrugs;
	}

	public void setAvailableDrugs(Set<Drug> availableDrugs) {
		this.availableDrugs = availableDrugs;
	}

}

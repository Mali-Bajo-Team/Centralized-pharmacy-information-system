package com.pharmacy.cpis.userservice.model.users;


import com.pharmacy.cpis.drugservice.model.drug.Drug;

import javax.persistence.*;
import java.util.Set;

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

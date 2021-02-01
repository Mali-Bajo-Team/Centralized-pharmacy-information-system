package com.pharmacy.cpis.userservice.model.users;

import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("Patient")
public class Patient extends Person {
	@Column
	private Integer loyaltyPoints;

	@Column
	private Integer penalties;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "allergies", inverseJoinColumns = @JoinColumn(name = "drug_code", referencedColumnName = "code"))
	private Set<Drug> allergies;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subscriptions", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
	private Set<Pharmacy> subscriptions;

	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private Set<Consultation> consultations;

	public Patient() {
		super();
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}

	public Set<Drug> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<Drug> allergies) {
		this.allergies = allergies;
	}

	public Set<Pharmacy> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<Pharmacy> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Set<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

}

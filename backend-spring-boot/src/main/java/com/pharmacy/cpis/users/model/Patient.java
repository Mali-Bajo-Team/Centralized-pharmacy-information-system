package com.pharmacy.cpis.users.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.pharmacy.cpis.drug.model.Drug;
import com.pharmacy.cpis.pharmacy.model.Pharmacy;

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

}

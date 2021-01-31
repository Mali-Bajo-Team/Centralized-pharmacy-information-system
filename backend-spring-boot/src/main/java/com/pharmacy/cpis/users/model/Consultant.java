package com.pharmacy.cpis.users.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.pharmacy.cpis.consultations.model.Consultation;

@Entity
@DiscriminatorValue("Consultant")
public class Consultant extends Person {
	@Column
	private ConsultantType type;

	@OneToMany(mappedBy = "consultant", fetch = FetchType.LAZY)
	private Set<Consultation> consultations;

	public ConsultantType getType() {
		return type;
	}

	public void setType(ConsultantType type) {
		this.type = type;
	}

	public Set<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

}

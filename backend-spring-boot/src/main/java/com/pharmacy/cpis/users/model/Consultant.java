package com.pharmacy.cpis.users.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.pharmacy.cpis.consultations.model.Consultation;
import com.pharmacy.cpis.ratings.model.ConsultantRating;
import com.pharmacy.cpis.workschedule.model.Vacation;
import com.pharmacy.cpis.workschedule.model.WorkingTimes;

@Entity
@DiscriminatorValue("Consultant")
public class Consultant extends Person {
	@Column
	private ConsultantType type;

	@OneToMany(mappedBy = "consultant", fetch = FetchType.LAZY)
	private Set<Consultation> consultations;

	@OneToMany(mappedBy = "consultant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Vacation> vacations;

	@OneToMany(mappedBy = "consultant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WorkingTimes> workingTimes;

	@OneToMany(mappedBy = "consultant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ConsultantRating> ratings;

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

	public Set<Vacation> getVacations() {
		return vacations;
	}

	public void setVacations(Set<Vacation> vacations) {
		this.vacations = vacations;
	}

	public Set<WorkingTimes> getWorkingTimes() {
		return workingTimes;
	}

	public void setWorkingTimes(Set<WorkingTimes> workingTimes) {
		this.workingTimes = workingTimes;
	}

	public Set<ConsultantRating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<ConsultantRating> ratings) {
		this.ratings = ratings;
	}

}

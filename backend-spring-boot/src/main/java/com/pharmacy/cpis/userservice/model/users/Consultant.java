package com.pharmacy.cpis.userservice.model.users;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.workschedule.Vacation;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.userservice.model.ratings.ConsultantRating;

import javax.persistence.*;
import java.util.Set;

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

package com.pharmacy.cpis.userservice.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.util.DateRange;

public class ConsultantWithBusyTimesDTO {

	private Long id;
	private String name;
	private String surname;
	private Collection<DateRange> monday;
	private Collection<DateRange> tuesday;
	private Collection<DateRange> wednesday;
	private Collection<DateRange> thursday;
	private Collection<DateRange> friday;
	private Collection<DateRange> saturday;
	private Collection<DateRange> sunday;

	public ConsultantWithBusyTimesDTO(Consultant consultant) {
		super();
		this.id = consultant.getId();
		this.name = consultant.getName();
		this.surname = consultant.getSurname();
		this.monday = new ArrayList<>();
		this.tuesday = new ArrayList<>();
		this.wednesday = new ArrayList<>();
		this.thursday = new ArrayList<>();
		this.friday = new ArrayList<>();
		this.saturday = new ArrayList<>();
		this.sunday = new ArrayList<>();
		for (WorkingTimes wt : consultant.getWorkingTimes()) {
			if (wt.getMonday() != null)
				monday.add(wt.getMonday());
			if (wt.getTuesday() != null)
				tuesday.add(wt.getMonday());
			if (wt.getWednesday() != null)
				wednesday.add(wt.getMonday());
			if (wt.getThursday() != null)
				thursday.add(wt.getMonday());
			if (wt.getFriday() != null)
				friday.add(wt.getMonday());
			if (wt.getSaturday() != null)
				saturday.add(wt.getMonday());
			if (wt.getSunday() != null)
				sunday.add(wt.getMonday());
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Collection<DateRange> getMonday() {
		return monday;
	}

	public Collection<DateRange> getTuesday() {
		return tuesday;
	}

	public Collection<DateRange> getWednesday() {
		return wednesday;
	}

	public Collection<DateRange> getThursday() {
		return thursday;
	}

	public Collection<DateRange> getFriday() {
		return friday;
	}

	public Collection<DateRange> getSaturday() {
		return saturday;
	}

	public Collection<DateRange> getSunday() {
		return sunday;
	}

}

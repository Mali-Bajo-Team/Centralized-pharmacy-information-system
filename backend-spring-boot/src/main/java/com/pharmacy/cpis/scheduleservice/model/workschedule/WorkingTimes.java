package com.pharmacy.cpis.scheduleservice.model.workschedule;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.util.DateRange;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "consultant_id", "pharmacy_id" }) })
public class WorkingTimes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Consultant consultant;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Pharmacy pharmacy;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "start", column = @Column(nullable = true, name = "monday_start")),
			@AttributeOverride(name = "end", column = @Column(nullable = true, name = "monday_end")) })
	private DateRange monday;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "start", column = @Column(nullable = true, name = "tuesday_start")),
			@AttributeOverride(name = "end", column = @Column(nullable = true, name = "tuesday_end")) })
	private DateRange tuesday;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "start", column = @Column(nullable = true, name = "wednesday_start")),
			@AttributeOverride(name = "end", column = @Column(nullable = true, name = "wednesday_end")) })
	private DateRange wednesday;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "start", column = @Column(nullable = true, name = "thursday_start")),
			@AttributeOverride(name = "end", column = @Column(nullable = true, name = "thursday_end")) })
	private DateRange thursday;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "start", column = @Column(nullable = true, name = "friday_start")),
			@AttributeOverride(name = "end", column = @Column(nullable = true, name = "friday_end")) })
	private DateRange friday;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "start", column = @Column(nullable = false, name = "saturday_start")),
			@AttributeOverride(name = "end", column = @Column(nullable = false, name = "saturday_end")) })
	private DateRange saturday;

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "start", column = @Column(nullable = true, name = "sunday_start")),
			@AttributeOverride(name = "end", column = @Column(nullable = true, name = "sunday_end")) })
	private DateRange sunday;

	public WorkingTimes() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Consultant getConsultant() {
		return consultant;
	}

	public void setConsultant(Consultant consultant) {
		this.consultant = consultant;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public DateRange getMonday() {
		return monday;
	}

	public void setMonday(DateRange monday) {
		this.monday = monday;
	}

	public DateRange getTuesday() {
		return tuesday;
	}

	public void setTuesday(DateRange tuesday) {
		this.tuesday = tuesday;
	}

	public DateRange getWednesday() {
		return wednesday;
	}

	public void setWednesday(DateRange wednesday) {
		this.wednesday = wednesday;
	}

	public DateRange getThursday() {
		return thursday;
	}

	public void setThursday(DateRange thursday) {
		this.thursday = thursday;
	}

	public DateRange getFriday() {
		return friday;
	}

	public void setFriday(DateRange friday) {
		this.friday = friday;
	}

	public DateRange getSaturday() {
		return saturday;
	}

	public void setSaturday(DateRange saturday) {
		this.saturday = saturday;
	}

	public DateRange getSunday() {
		return sunday;
	}

	public void setSunday(DateRange sunday) {
		this.sunday = sunday;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkingTimes other = (WorkingTimes) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

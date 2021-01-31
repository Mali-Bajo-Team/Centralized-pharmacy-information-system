package com.pharmacy.cpis.pharmacy.model;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.pharmacy.cpis.drugsales.model.AvailableDrug;
import com.pharmacy.cpis.users.model.Patient;
import com.pharmacy.cpis.workschedule.model.WorkingTimes;

@Entity
public class Pharmacy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(length = 500)
	private String description;

	@Column(nullable = false)
	private Double dermatologistConsultationPrice;

	@Column(nullable = false)
	private Double pharmacistConsultationPrice;

	@Embedded
	@AttributeOverrides(value = { @AttributeOverride(name = "latitude", column = @Column(nullable = false)),
			@AttributeOverride(name = "longitude", column = @Column(nullable = false)) })
	private Location location;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "subscriptions")
	private Set<Patient> subscribers;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacy", cascade = CascadeType.ALL)
	private Set<AvailableDrug> availableDrugs;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacy")
	private Set<WorkingTimes> consultants;

	public Pharmacy() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getDermatologistConsultationPrice() {
		return dermatologistConsultationPrice;
	}

	public void setDermatologistConsultationPrice(Double dermatologistConsultationPrice) {
		this.dermatologistConsultationPrice = dermatologistConsultationPrice;
	}

	public Double getPharmacistConsultationPrice() {
		return pharmacistConsultationPrice;
	}

	public void setPharmacistConsultationPrice(Double pharmacistConsultationPrice) {
		this.pharmacistConsultationPrice = pharmacistConsultationPrice;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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
		Pharmacy other = (Pharmacy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

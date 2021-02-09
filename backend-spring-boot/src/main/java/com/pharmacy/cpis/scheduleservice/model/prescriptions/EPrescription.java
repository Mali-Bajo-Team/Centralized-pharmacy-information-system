package com.pharmacy.cpis.scheduleservice.model.prescriptions;

import com.pharmacy.cpis.userservice.model.users.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class EPrescription {
	@Id
	private String code;

	@Column(nullable = false)
	private Date creationDate;

	@Column(nullable = false)
	private EPrescriptionStatus status;

	@ManyToOne(optional = false)
	private Patient patient;

	@OneToMany(mappedBy = "prescription", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PrescribedDrug> drugs;

	public EPrescription() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public EPrescriptionStatus getStatus() {
		return status;
	}

	public void setStatus(EPrescriptionStatus status) {
		this.status = status;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Set<PrescribedDrug> getDrugs() {
		return drugs;
	}

	public void setDrugs(Set<PrescribedDrug> drugs) {
		this.drugs = drugs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		EPrescription other = (EPrescription) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}

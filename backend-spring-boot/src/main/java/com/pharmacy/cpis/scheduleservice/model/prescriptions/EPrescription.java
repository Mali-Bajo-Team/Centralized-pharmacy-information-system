package com.pharmacy.cpis.scheduleservice.model.prescriptions;

import com.pharmacy.cpis.userservice.model.users.Patient;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class EPrescription {
	@Version
	@Column(nullable = false)
	private Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date creationDate;

	@Column(nullable = false)
	private EPrescriptionStatus status;

	@ManyToOne(optional = false)
	private Patient patient;

	@OneToMany(mappedBy = "prescription", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PrescribedDrug> prescribedDrugs;

	public EPrescription() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<PrescribedDrug> getPrescribedDrugs() {
		return prescribedDrugs;
	}

	public void setPrescribedDrugs(Set<PrescribedDrug> prescribedDrugs) {
		this.prescribedDrugs = prescribedDrugs;
	}

	public Long getVersion() {
		return version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EPrescription that = (EPrescription) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(creationDate, that.creationDate) &&
				status == that.status &&
				Objects.equals(patient, that.patient) &&
				Objects.equals(prescribedDrugs, that.prescribedDrugs);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, creationDate, status, patient, prescribedDrugs);
	}

}

package com.pharmacy.cpis.scheduleservice.model.prescriptions;

import com.pharmacy.cpis.drugservice.model.drug.Drug;

import javax.persistence.*;

@Entity
public class PrescribedDrug {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer amount;

	@ManyToOne(optional = false)
	private Drug drug;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private EPrescription prescription;

	public PrescribedDrug() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public EPrescription getPrescription() {
		return prescription;
	}

	public void setPrescription(EPrescription prescription) {
		this.prescription = prescription;
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
		PrescribedDrug other = (PrescribedDrug) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

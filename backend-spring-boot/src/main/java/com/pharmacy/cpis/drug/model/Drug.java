package com.pharmacy.cpis.drug.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Drug {
	@Id
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer loyaltyPoints;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private DrugForm drugForm;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private DrugClass drugClass;

	@OneToOne(mappedBy = "drug", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private DrugSpecification specification;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "alternate_drugs", joinColumns = @JoinColumn(name = "drug_code", referencedColumnName = "code"), inverseJoinColumns = @JoinColumn(name = "alternate_drug_code", referencedColumnName = "code"))
	private Set<Drug> alternateDrugs;

	public Drug() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public DrugForm getDrugForm() {
		return drugForm;
	}

	public void setDrugForm(DrugForm drugForm) {
		this.drugForm = drugForm;
	}

	public DrugClass getDrugClass() {
		return drugClass;
	}

	public void setDrugClass(DrugClass drugClass) {
		this.drugClass = drugClass;
	}

	public DrugSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(DrugSpecification specification) {
		this.specification = specification;
	}

	public Set<Drug> getAlternateDrugs() {
		return alternateDrugs;
	}

	public void setAlternateDrugs(Set<Drug> alternateDrugs) {
		this.alternateDrugs = alternateDrugs;
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
		Drug other = (Drug) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}

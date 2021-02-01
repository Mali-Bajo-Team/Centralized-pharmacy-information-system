package com.pharmacy.cpis.drugservice.model.drug;

import javax.persistence.*;
import java.util.Set;

@Entity
public class DrugSpecification {
	@Id
	private Integer id;

	@Column(nullable = false)
	private String manufacturer;

	@Column(length = 500)
	private String contraindications;

	@Column(length = 500)
	private String additionalNotes;

	@Column
	private Integer recommendedDailyDose;

	@Column(nullable = false)
	private Boolean prescriptionRequired;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "drug_code", referencedColumnName = "code")
	private Drug drug;

	@OneToMany(mappedBy = "specification", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Ingredient> ingredients;

	public DrugSpecification() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getContraindications() {
		return contraindications;
	}

	public void setContraindications(String contraindications) {
		this.contraindications = contraindications;
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public Integer getRecommendedDailyDose() {
		return recommendedDailyDose;
	}

	public void setRecommendedDailyDose(Integer recommendedDailyDose) {
		this.recommendedDailyDose = recommendedDailyDose;
	}

	public Boolean getPrescriptionRequired() {
		return prescriptionRequired;
	}

	public void setPrescriptionRequired(Boolean prescriptionRequired) {
		this.prescriptionRequired = prescriptionRequired;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
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
		DrugSpecification other = (DrugSpecification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

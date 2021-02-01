package com.pharmacy.cpis.drugservice.model.drugsales;

import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AvailableDrug {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer availableAmount;

	@Column(nullable = false)
	private Double defaultPrice;

	@ManyToOne(optional = false)
	private Drug drug;

	@ManyToOne(optional = false)
	private Pharmacy pharmacy;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "drug", cascade = CascadeType.ALL)
	private Set<Price> prices;

	public AvailableDrug() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(Integer availableAmount) {
		this.availableAmount = availableAmount;
	}

	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Set<Price> getPrices() {
		return prices;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
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
		AvailableDrug other = (AvailableDrug) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
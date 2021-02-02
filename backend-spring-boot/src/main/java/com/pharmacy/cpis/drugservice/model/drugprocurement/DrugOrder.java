package com.pharmacy.cpis.drugservice.model.drugprocurement;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.users.PharmacyAdministrator;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class DrugOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date timestamp;

	@Column(nullable = false)
	private Date deadline;

	@Column(nullable = false)
	private DrugOrderStatus status;

	@ManyToOne(optional = false)
	private Pharmacy pharmacy;

	@ManyToOne(optional = false)
	private PharmacyAdministrator administrator;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OrderedDrug> orderedDrugs;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Offer> offers;

	public DrugOrder() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public DrugOrderStatus getStatus() {
		return status;
	}

	public void setStatus(DrugOrderStatus status) {
		this.status = status;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public PharmacyAdministrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(PharmacyAdministrator administrator) {
		this.administrator = administrator;
	}

	public Set<OrderedDrug> getOrderedDrugs() {
		return orderedDrugs;
	}

	public void setOrderedDrugs(Set<OrderedDrug> orderedDrugs) {
		this.orderedDrugs = orderedDrugs;
	}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
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
		DrugOrder other = (DrugOrder) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

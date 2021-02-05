package com.pharmacy.cpis.userservice.model.loyaltyprogram;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Check(constraints = "(active_until is not null and is_active) or (active_until is null and not is_active)")
public class LoyaltyProgram {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "is_active")
	private Boolean isActive;

	@Column(name = "active_until")
	private Date activeUntil;

	@Column(nullable = false)
	private Integer pointsPerConsultation;

	@OneToMany(mappedBy = "loyaltyProgram", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserCategory> categories;

	public LoyaltyProgram() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getActiveUntil() {
		return activeUntil;
	}

	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}

	public Integer getPointsPerConsultation() {
		return pointsPerConsultation;
	}

	public void setPointsPerConsultation(Integer pointsPerConsultation) {
		this.pointsPerConsultation = pointsPerConsultation;
	}

	public Set<UserCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<UserCategory> categories) {
		this.categories = categories;
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
		LoyaltyProgram other = (LoyaltyProgram) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

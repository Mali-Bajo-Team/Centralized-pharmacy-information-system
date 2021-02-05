package com.pharmacy.cpis.userservice.model.loyaltyprogram;

import javax.persistence.*;

@Entity
public class UserCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, unique = true)
	private Integer minimumPoints;

	@Column(nullable = false)
	private Double reservationDiscount;

	@Column(nullable = false)
	private Double consultationDiscount;

	@ManyToOne(optional = false)
	private LoyaltyProgram loyaltyProgram;

	public UserCategory() {
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

	public Integer getMinimumPoints() {
		return minimumPoints;
	}

	public void setMinimumPoints(Integer minimumPoints) {
		this.minimumPoints = minimumPoints;
	}

	public Double getReservationDiscount() {
		return reservationDiscount;
	}

	public void setReservationDiscount(Double reservationDiscount) {
		this.reservationDiscount = reservationDiscount;
	}

	public Double getConsultationDiscount() {
		return consultationDiscount;
	}

	public void setConsultationDiscount(Double consultationDiscount) {
		this.consultationDiscount = consultationDiscount;
	}

	public LoyaltyProgram getLoyaltyProgram() {
		return loyaltyProgram;
	}

	public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
		this.loyaltyProgram = loyaltyProgram;
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
		UserCategory other = (UserCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

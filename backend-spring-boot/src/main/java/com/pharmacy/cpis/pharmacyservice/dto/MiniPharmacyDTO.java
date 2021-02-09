package com.pharmacy.cpis.pharmacyservice.dto;

public class MiniPharmacyDTO {

	private String name;
	private Long id;

	public MiniPharmacyDTO(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

}

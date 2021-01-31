package com.pharmacy.cpis.users.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Consultant")
public class Consultant extends Person {
	@Column
	private ConsultantType type;
}

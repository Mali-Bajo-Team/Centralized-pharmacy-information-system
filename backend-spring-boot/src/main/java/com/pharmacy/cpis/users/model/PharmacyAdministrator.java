package com.pharmacy.cpis.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PharmacyAdmin")
public class PharmacyAdministrator extends Person {

}

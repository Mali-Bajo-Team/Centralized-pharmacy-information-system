package com.pharmacy.cpis.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SystemAdmin")
public class SystemAdministrator extends Person {

}

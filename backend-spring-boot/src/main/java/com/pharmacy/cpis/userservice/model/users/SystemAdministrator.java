package com.pharmacy.cpis.userservice.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SystemAdmin")
public class SystemAdministrator extends Person {

}

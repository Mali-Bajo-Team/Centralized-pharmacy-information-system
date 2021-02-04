package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.users.PharmacyAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPharmacyAdministratorRepository extends JpaRepository<PharmacyAdministrator, Long> {
}

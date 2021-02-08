package com.pharmacy.cpis.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.userservice.model.users.PharmacyAdministrator;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

public interface IPharmacyAdministratorRepository extends JpaRepository<PharmacyAdministrator, Long> {

	Optional<PharmacyAdministrator> findByAccount(UserAccount account);

}

package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.users.SystemAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISystemAdministratorRepository extends JpaRepository<SystemAdministrator, Long> {
}

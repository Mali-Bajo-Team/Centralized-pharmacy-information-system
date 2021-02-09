package com.pharmacy.cpis.userservice.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;

public interface IConsultantRepository extends JpaRepository<Consultant, Long> {

	Collection<Consultant> findAllByType(ConsultantType type);

}

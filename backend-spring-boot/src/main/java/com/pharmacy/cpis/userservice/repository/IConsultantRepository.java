package com.pharmacy.cpis.userservice.repository;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;

public interface IConsultantRepository extends JpaRepository<Consultant, Long> {

	Collection<Consultant> findAllByType(ConsultantType type);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="2000")})
	Optional<Consultant> findLockedById(Long id);

}

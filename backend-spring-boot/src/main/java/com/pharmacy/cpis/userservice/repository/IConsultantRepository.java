package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.users.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsultantRepository extends JpaRepository<Consultant, Long> {

}

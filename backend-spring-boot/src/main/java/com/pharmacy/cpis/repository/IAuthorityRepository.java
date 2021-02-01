package com.pharmacy.cpis.repository;

import com.pharmacy.cpis.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}


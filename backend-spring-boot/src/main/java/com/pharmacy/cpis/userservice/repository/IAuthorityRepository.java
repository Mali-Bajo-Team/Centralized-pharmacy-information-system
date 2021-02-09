package com.pharmacy.cpis.userservice.repository;
import com.pharmacy.cpis.userservice.model.users.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}


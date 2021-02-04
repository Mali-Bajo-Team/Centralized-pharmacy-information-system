package com.pharmacy.cpis.userservice.repository;

import com.pharmacy.cpis.userservice.model.users.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISupplierRepository extends JpaRepository<Supplier, Long> {

}

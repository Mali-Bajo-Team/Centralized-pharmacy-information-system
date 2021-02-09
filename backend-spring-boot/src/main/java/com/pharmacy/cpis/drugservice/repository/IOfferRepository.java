package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findOffersBySupplier(Supplier supplier);
}

package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDrugOrderRepository extends JpaRepository<DrugOrder, Long> {
}

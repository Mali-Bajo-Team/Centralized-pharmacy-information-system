package com.pharmacy.cpis.drugservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugprocurement.OrderedDrug;

public interface IOrderedDrugRepository extends JpaRepository<OrderedDrug, Long> {

}

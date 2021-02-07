package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAvailableDrugRepository extends JpaRepository<AvailableDrug, Long> {
    List<AvailableDrug> findByDrugCode(String drugCode);
}

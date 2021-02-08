package com.pharmacy.cpis.drugservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.cpis.drugservice.model.drugsales.Price;

public interface IPriceRepository extends JpaRepository<Price, Long> {

}

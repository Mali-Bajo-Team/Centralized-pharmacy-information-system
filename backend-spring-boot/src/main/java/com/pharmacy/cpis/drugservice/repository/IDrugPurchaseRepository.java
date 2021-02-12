package com.pharmacy.cpis.drugservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pharmacy.cpis.drugservice.model.drugsales.DrugPurchase;

public interface IDrugPurchaseRepository extends JpaRepository<DrugPurchase, Long> {
	@Query("select sum(d.price * d.amount) from DrugPurchase d where " + "d.pharmacy.id = :id and "
			+ "date_part('year',d.timestamp) = :year and " + "date_part('month',d.timestamp) = :month and "
			+ "date_part('day',d.timestamp) = :day")
	Double getProfitForDate(Long id, int year, int month, int day);

	@Query("select sum(d.amount) from DrugPurchase d where " + "d.pharmacy.id = :id and "
			+ "date_part('year',d.timestamp) = :year and " + "date_part('month',d.timestamp) = :month")
	Integer getAmountUsed(Long id, int month, int year);
}

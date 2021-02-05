package com.pharmacy.cpis.drugservice.repository;

import com.pharmacy.cpis.drugservice.model.drug.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIngredientsRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(String name);
}

package com.pharmacy.cpis.drugservice.dto;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pharmacy.cpis.drugservice.model.drug.DrugSpecification;
import com.pharmacy.cpis.drugservice.model.drug.Ingredient;

public class DrugSpecificationDTO {

    private String manufacturer;

    private String contraindications;

    private Set<Ingredient> ingredients;

    private Integer recommendedDailyDose;

    public DrugSpecificationDTO(){}

    public DrugSpecificationDTO(DrugSpecification drugSpecification){
        this.setManufacturer(drugSpecification.getManufacturer());
        this.setContraindications(drugSpecification.getContraindications());
        this.setRecommendedDailyDose(drugSpecification.getRecommendedDailyDose());
        if(drugSpecification.getIngredients() != null)
            this.setIngredients(drugSpecification.getIngredients());
    }

    public DrugSpecificationDTO(@NotEmpty(message = "Manufacturer is required.") String manufacturer, @NotEmpty(message = "Contraindications is required.") String contraindications, @NotEmpty(message = "Ingredients is required.") Set<Ingredient> ingredients, @NotEmpty(message = "Recommended daily dose is required.") Integer recommendedDailyDose) {
        this.manufacturer = manufacturer;
        this.contraindications = contraindications;
        this.ingredients = ingredients;
        this.recommendedDailyDose = recommendedDailyDose;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getRecommendedDailyDose() {
        return recommendedDailyDose;
    }

    public void setRecommendedDailyDose(Integer recommendedDailyDose) {
        this.recommendedDailyDose = recommendedDailyDose;
    }
}

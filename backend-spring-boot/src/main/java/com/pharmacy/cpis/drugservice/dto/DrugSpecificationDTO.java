package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drug.Ingredient;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class DrugSpecificationDTO {

    @NotEmpty(message = "Manufacturer is required.")
    private String manufacturer;

    @NotEmpty(message = "Contraindications is required.")
    private String contraindications;

//    @NotEmpty(message = "Ingredients is required.")
    private List<Ingredient> ingredients;

    @NotEmpty(message = "Recommended daily dose is required.")
    private Integer recommendedDailyDose;

    public DrugSpecificationDTO(){}

    public DrugSpecificationDTO(@NotEmpty(message = "Manufacturer is required.") String manufacturer, @NotEmpty(message = "Contraindications is required.") String contraindications, @NotEmpty(message = "Ingredients is required.") List<Ingredient> ingredients, @NotEmpty(message = "Recommended daily dose is required.") Integer recommendedDailyDose) {
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getRecommendedDailyDose() {
        return recommendedDailyDose;
    }

    public void setRecommendedDailyDose(Integer recommendedDailyDose) {
        this.recommendedDailyDose = recommendedDailyDose;
    }
}

package com.pharmacy.cpis.userservice.dto;

import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import org.apache.catalina.User;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LoyaltyProgramDTO {

    @NotEmpty(message = "Is active is required.")
    private Boolean isActive;

    @NotEmpty(message = "Active until is required.")
    private Date activeUntil;

    @NotEmpty(message = "Points per consultation is required.")
    private Integer pointsPerConsultation;

    private Set<UserCategoryDTO> categories;

    public LoyaltyProgramDTO(){

    }

    public LoyaltyProgramDTO(LoyaltyProgram loyaltyProgram){
        this.setActive(loyaltyProgram.getIsActive());
        this.setActiveUntil(loyaltyProgram.getActiveUntil());
        this.setPointsPerConsultation(loyaltyProgram.getPointsPerConsultation());
        if(loyaltyProgram.getCategories() != null && !loyaltyProgram.getCategories().isEmpty()){

            Set<UserCategoryDTO> tempCategories = new HashSet<>();
            for(UserCategory userCategory : loyaltyProgram.getCategories()){
                UserCategoryDTO userCategoryDTO = new UserCategoryDTO(userCategory);
                tempCategories.add(userCategoryDTO);
            }
            this.setCategories(tempCategories);

        }
    }

    public LoyaltyProgramDTO(@NotEmpty(message = "Is active is required.") Boolean isActive, @NotEmpty(message = "Active until is required.") Date activeUntil, @NotEmpty(message = "Points per consultation is required.") Integer pointsPerConsultation, Set<UserCategoryDTO> categories) {
        this.isActive = isActive;
        this.activeUntil = activeUntil;
        this.pointsPerConsultation = pointsPerConsultation;
        this.categories = categories;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getActiveUntil() {
        return activeUntil;
    }

    public void setActiveUntil(Date activeUntil) {
        this.activeUntil = activeUntil;
    }

    public Integer getPointsPerConsultation() {
        return pointsPerConsultation;
    }

    public void setPointsPerConsultation(Integer pointsPerConsultation) {
        this.pointsPerConsultation = pointsPerConsultation;
    }

    public Set<UserCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<UserCategoryDTO> categories) {
        this.categories = categories;
    }
}

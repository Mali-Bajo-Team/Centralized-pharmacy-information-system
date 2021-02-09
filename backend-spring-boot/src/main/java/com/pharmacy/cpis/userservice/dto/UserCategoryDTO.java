package com.pharmacy.cpis.userservice.dto;

import com.pharmacy.cpis.userservice.model.loyaltyprogram.LoyaltyProgram;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class UserCategoryDTO {

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Minimum points is required.")
    private Integer minimumPoints;

    @NotEmpty(message = "Reservation discount is required.")
    private Double reservationDiscount;

    @NotEmpty(message = "Consultation discount is required.")
    private Double consultationDiscount;

    private LoyaltyProgram loyaltyProgram;

    public UserCategoryDTO(){}

    public UserCategoryDTO(UserCategory userCategory){
        this.setName(userCategory.getName());
        this.setMinimumPoints(userCategory.getMinimumPoints());
        this.setReservationDiscount(userCategory.getReservationDiscount());
        this.setConsultationDiscount(userCategory.getConsultationDiscount());
    }

    public UserCategoryDTO(@NotEmpty(message = "Name is required.") String name, @NotEmpty(message = "Minimum points is required.") Integer minimumPoints, @NotEmpty(message = "Reservation discount is required.") Double reservationDiscount, @NotEmpty(message = "Consultation discount is required.") Double consultationDiscount, LoyaltyProgram loyaltyProgram) {
        this.name = name;
        this.minimumPoints = minimumPoints;
        this.reservationDiscount = reservationDiscount;
        this.consultationDiscount = consultationDiscount;
        this.loyaltyProgram = loyaltyProgram;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinimumPoints() {
        return minimumPoints;
    }

    public void setMinimumPoints(Integer minimumPoints) {
        this.minimumPoints = minimumPoints;
    }

    public Double getReservationDiscount() {
        return reservationDiscount;
    }

    public void setReservationDiscount(Double reservationDiscount) {
        this.reservationDiscount = reservationDiscount;
    }

    public Double getConsultationDiscount() {
        return consultationDiscount;
    }

    public void setConsultationDiscount(Double consultationDiscount) {
        this.consultationDiscount = consultationDiscount;
    }

    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgram;
    }

    public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        this.loyaltyProgram = loyaltyProgram;
    }
}

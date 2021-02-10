package com.pharmacy.cpis.pharmacyservice.dto;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

public class PharmacyTotalPriceDTO {
    private Long pharmacyId;
    private Double totalPrice;
    private Double pharmacyRating;
    private String pharmacyName;
    private String pharmacyLocation;

    public PharmacyTotalPriceDTO(){}

    public PharmacyTotalPriceDTO(Pharmacy pharmacy, Double totalPrice){
        this.setPharmacyId(pharmacy.getId());
        this.setTotalPrice(totalPrice);
        this.setPharmacyRating(pharmacy.getAverageRating());
        this.setPharmacyName(pharmacy.getName());
        this.setPharmacyLocation(pharmacy.getLocation().getStreet() +", " + pharmacy.getLocation().getCity());
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getPharmacyRating() {
        return pharmacyRating;
    }

    public void setPharmacyRating(Double pharmacyRating) {
        this.pharmacyRating = pharmacyRating;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyLocation() {
        return pharmacyLocation;
    }

    public void setPharmacyLocation(String pharmacyLocation) {
        this.pharmacyLocation = pharmacyLocation;
    }
}

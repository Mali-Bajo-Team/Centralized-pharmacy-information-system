package com.pharmacy.cpis.scheduleservice.dto;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

import java.util.List;

// Pharmacy which at some point have free min one pharmacist
public class FreePharmacyReadDTO {
    private Long pharmacyId;
    private Double pharmacyRating;
    private Double consultationPrice;
    private String pharmacyName;
    private String pharmacyLocation;
    private List<FreePharmacistReadDTO> freePharmacist;

    public FreePharmacyReadDTO(){}

    public FreePharmacyReadDTO(Pharmacy pharmacy, List<FreePharmacistReadDTO> freePharmacist){
        this.setPharmacyId(pharmacy.getId());
        this.setPharmacyRating(pharmacy.getAverageRating());
        this.setConsultationPrice(pharmacy.getPharmacistConsultationPrice());
        this.setPharmacyName(pharmacy.getName());
        this.setPharmacyLocation(pharmacy.getLocation().getStreet() +", "+pharmacy.getLocation().getCity());
        this.setFreePharmacist(freePharmacist);
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Double getPharmacyRating() {
        return pharmacyRating;
    }

    public void setPharmacyRating(Double pharmacyRating) {
        this.pharmacyRating = pharmacyRating;
    }

    public Double getConsultationPrice() {
        return consultationPrice;
    }

    public void setConsultationPrice(Double consultationPrice) {
        this.consultationPrice = consultationPrice;
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

    public List<FreePharmacistReadDTO> getFreePharmacist() {
        return freePharmacist;
    }

    public void setFreePharmacist(List<FreePharmacistReadDTO> freePharmacist) {
        this.freePharmacist = freePharmacist;
    }
}

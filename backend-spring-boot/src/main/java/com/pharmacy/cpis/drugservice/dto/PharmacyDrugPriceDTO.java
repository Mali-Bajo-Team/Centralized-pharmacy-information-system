package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

public class PharmacyDrugPriceDTO {

    private String pharmacyName;
    private Double priceOfDrug;
    private Long pharmacyID;

    public PharmacyDrugPriceDTO(){}

    public PharmacyDrugPriceDTO(String pharmacyName, Double priceOfDrug,Long pharmacyID) {
        this.pharmacyName = pharmacyName;
        this.priceOfDrug = priceOfDrug;
        this.pharmacyID = pharmacyID;
    }

    public PharmacyDrugPriceDTO(AvailableDrug availableDrug){
        this.setPharmacyName(availableDrug.getPharmacy().getName());
        this.setPharmacyID(availableDrug.getPharmacy().getId());
        //TODO: For logged patient, make different price (based on loyalty program)
        this.setPriceOfDrug(availableDrug.getDefaultPrice());
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public Double getPriceOfDrug() {
        return priceOfDrug;
    }

    public void setPriceOfDrug(Double priceOfDrug) {
        this.priceOfDrug = priceOfDrug;
    }

    public Long getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(Long pharmacyID) {
        this.pharmacyID = pharmacyID;
    }
}

package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drugsales.AvailableDrug;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;

public class PharmacyDrugPriceDTO {

    private String pharmacyName;
    private Double priceOfDrug;

    public PharmacyDrugPriceDTO(){}

    public PharmacyDrugPriceDTO(String pharmacyName, Double priceOfDrug) {
        this.pharmacyName = pharmacyName;
        this.priceOfDrug = priceOfDrug;
    }

    public PharmacyDrugPriceDTO(AvailableDrug availableDrug){
        this.setPharmacyName(availableDrug.getPharmacy().getName());
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
}

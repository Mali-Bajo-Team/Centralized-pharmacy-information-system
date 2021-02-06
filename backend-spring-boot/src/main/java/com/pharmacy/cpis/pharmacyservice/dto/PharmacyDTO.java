package com.pharmacy.cpis.pharmacyservice.dto;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Location;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class PharmacyDTO {

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Location is required.")
    private String location;

    @NotEmpty(message = "Rating is required.")
    private Double rating;

    public PharmacyDTO(){

    }

    public PharmacyDTO(Pharmacy pharmacy){
        getPharmacyLocation(pharmacy);
        getAvgRatings(pharmacy);
        this.setName(pharmacy.getName());
        this.setLocation(getPharmacyLocation(pharmacy));
        this.setRating(getAvgRatings(pharmacy));
    }

    private Double getAvgRatings(Pharmacy pharmacy) {
        Set<PharmacyRating> ratings = pharmacy.getRatings();
        double avgRatings = 0;
        for (PharmacyRating rating : ratings) {
            avgRatings += rating.getRating().getRating();
        }

        return avgRatings / ratings.size();
    }

    private String getPharmacyLocation(Pharmacy pharmacy) {
        Location pharmacyLocation = pharmacy.getLocation();
        String location = pharmacyLocation.getStreet() + " "
                + pharmacyLocation.getHouseNumber() + ", "
                + pharmacyLocation.getCity();
        return location;
    }

    public PharmacyDTO(String name, String location, Double rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

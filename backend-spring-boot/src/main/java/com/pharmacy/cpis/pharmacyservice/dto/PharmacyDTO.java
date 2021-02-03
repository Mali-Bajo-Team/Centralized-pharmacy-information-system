package com.pharmacy.cpis.pharmacyservice.dto;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Location;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;

import java.util.Set;

public class PharmacyDTO {

    private String name;
    private String location;
    private String rating;

    public PharmacyDTO(){

    }

    public PharmacyDTO(Pharmacy pharmacy){
        getPharmacyLocation(pharmacy);
        getAvgRatings(pharmacy);
        this.setName(pharmacy.getName());
        this.setLocation(getPharmacyLocation(pharmacy));
        this.setRating(getAvgRatings(pharmacy));
    }

    private String getAvgRatings(Pharmacy pharmacy) {
        Set<PharmacyRating> ratings = pharmacy.getRatings();
        double avgRatings = 0;
        for (PharmacyRating rating : ratings)
        {
            avgRatings += rating.getRating().getRating();
        }
        avgRatings = avgRatings / ratings.size();
        String avgRate = Double.toString(avgRatings);
        return avgRate;
    }

    private String getPharmacyLocation(Pharmacy pharmacy) {
        Location pharmacyLocation = pharmacy.getLocation();
        String location = pharmacyLocation.getStreet() + " "
                + pharmacyLocation.getHouseNumber() + ", "
                + pharmacyLocation.getCity();
        return location;
    }

    public PharmacyDTO(String name, String location, String rating) {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

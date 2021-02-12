package com.pharmacy.cpis.userservice.dto.ratings;

import com.pharmacy.cpis.userservice.model.ratings.PharmacyRating;

public class PharmacyRatingUpdateDTO {
    private Long id;
    private Integer newRating;

    public PharmacyRatingUpdateDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNewRating() {
        return newRating;
    }

    public void setNewRating(Integer newRating) {
        this.newRating = newRating;
    }
}

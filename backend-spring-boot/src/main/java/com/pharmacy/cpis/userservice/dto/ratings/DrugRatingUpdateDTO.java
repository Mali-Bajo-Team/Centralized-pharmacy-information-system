package com.pharmacy.cpis.userservice.dto.ratings;

public class DrugRatingUpdateDTO {
    private Long id;
    private Integer newRating;

    public DrugRatingUpdateDTO(){}

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

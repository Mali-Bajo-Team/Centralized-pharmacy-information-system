package com.pharmacy.cpis.userservice.dto.ratings;

public class ConsultantRatingUpdateDTO {
    private Long id;
    private Integer newRating;

    public ConsultantRatingUpdateDTO(){}

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

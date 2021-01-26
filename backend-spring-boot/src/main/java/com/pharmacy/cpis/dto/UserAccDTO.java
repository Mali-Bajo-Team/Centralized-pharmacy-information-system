package com.pharmacy.cpis.dto;

import com.pharmacy.cpis.model.UserAcc;

public class UserAccDTO {
    private Long id;
    private String email;

    public UserAccDTO() {

    }

    public UserAccDTO(UserAcc user) { this(user.getId(), user.getEmail()); }

    public UserAccDTO(Long id, String email) {
        super();
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}

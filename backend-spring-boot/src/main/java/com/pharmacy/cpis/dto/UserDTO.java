package com.pharmacy.cpis.dto;

import com.pharmacy.cpis.model.UserAcc;

public class UserDTO {
    private Long id;
    private String email;

    public UserDTO() {

    }

    public UserDTO(UserAcc user) { this(user.getId(), user.getEmail()); }

    public UserDTO(Long id, String email) {
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

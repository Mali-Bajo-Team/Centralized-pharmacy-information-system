package com.pharmacy.cpis.userservice.dto;

import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.Set;

public class PatientDTO {
    private Long id;
    private String name;
    private String surname;

    public PatientDTO(){}

    public PatientDTO(Patient p) { this(p.getId(), p.getName(), p.getSurname()); }

    public PatientDTO(Long id, String name, String surname) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


}

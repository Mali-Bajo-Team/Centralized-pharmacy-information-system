package com.pharmacy.cpis.userservice.dto;


import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.userservice.model.users.Patient;

import java.util.Set;

public class PatientDTO {
    private Long id;
    private String name;
    private String surname;
    private Set<Drug> alergies;

    public PatientDTO(Patient p) { this(p.getId(), p.getName(), p.getSurname(), p.getAllergies()); }

    public PatientDTO(Long id, String name, String surname,Set<Drug> alergies) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.alergies = alergies;
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

    public Set<Drug> getAlergies() {
        return alergies;
    }

    public void setAlergies(Set<Drug> alergies) {
        this.alergies = alergies;
    }


}

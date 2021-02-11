package com.pharmacy.cpis.userservice.dto;

import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatientProfileDTO {

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Surname is required.")
    private String surname;

    @NotEmpty(message = "Phone number is required.")
    private String phoneNumber;

    @NotEmpty(message = "Address is required.")
    private String address;

    @NotEmpty(message = "City is required.")
    private String city;

    @NotEmpty(message = "Country is required.")
    private String country;

    @NotEmpty(message = "Email is required.")
    private String email;

    @NotEmpty(message = "Loyalty points are required.")
    private Integer loyaltyPoints;

    @NotEmpty(message = "User category is required.")
    private UserCategoryDTO userCategoryDTO;

    private List<DrugDTO> allergies;

    public PatientProfileDTO() {

    }

    public PatientProfileDTO(Patient patient, String patientEmail, ILoyaltyProgramService loyaltyProgramService){
        this.setName(patient.getName());
        this.setSurname(patient.getSurname());
        this.setAddress(patient.getAddress());
        this.setCountry(patient.getCountry());
        this.setCity(patient.getCity());
        this.setPhoneNumber(patient.getPhoneNumber());
        this.setLoyaltyPoints(patient.getLoyaltyPoints());
        this.setEmail(patientEmail);
        this.setUserCategoryDTO(new UserCategoryDTO(loyaltyProgramService.findUserCategoryByLoyaltyPoints(patient.getLoyaltyPoints())));
        this.setAllergiesWithSet(patient.getAllergies());

    }

    public PatientProfileDTO(String name, String surname, String phoneNumber, String address, String city, String country, String email, Integer loyaltyPoints,
                             UserCategoryDTO userCategoryDTO, List<DrugDTO> allergies) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.loyaltyPoints = loyaltyPoints;
        this.userCategoryDTO = userCategoryDTO;
        this.allergies = allergies;
    }

    public List<DrugDTO> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<DrugDTO> allergies) {
        this.allergies = allergies;
    }

    public void setAllergiesWithSet(Set<Drug> allergies) {
        List<DrugDTO> allergiesDTO = new ArrayList<>();
        for(Drug drug : allergies){
            allergiesDTO.add(new DrugDTO(drug));
        }
        this.allergies = allergiesDTO;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public UserCategoryDTO getUserCategoryDTO() {
        return userCategoryDTO;
    }

    public void setUserCategoryDTO(UserCategoryDTO userCategoryDTO) {
        this.userCategoryDTO = userCategoryDTO;
    }
}

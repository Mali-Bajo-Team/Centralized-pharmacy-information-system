package com.pharmacy.cpis.scheduleservice.dto.prescription;

import java.util.List;

public class EPrescriptionCreateDTO {
    private String patientEmail;
    private Long pharmacyId;
    private List<PrescribedDrugCreateDTO> prescribedDrugs;

    public EPrescriptionCreateDTO(){

    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public List<PrescribedDrugCreateDTO> getPrescribedDrugs() {
        return prescribedDrugs;
    }

    public void setPrescribedDrugs(List<PrescribedDrugCreateDTO> prescribedDrugs) {
        this.prescribedDrugs = prescribedDrugs;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}

package com.pharmacy.cpis.scheduleservice.dto.prescription;

import java.util.List;

public class EPrescriptionCreateDTO {
    private String patientEmail;
    private List<PrescribedDrugCreateDTO> prescribedDrugs;

    public EPrescriptionCreateDTO(){

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

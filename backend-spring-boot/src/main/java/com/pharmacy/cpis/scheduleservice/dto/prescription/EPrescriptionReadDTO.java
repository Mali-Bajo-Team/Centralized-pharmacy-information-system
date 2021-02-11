package com.pharmacy.cpis.scheduleservice.dto.prescription;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescriptionStatus;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.PrescribedDrug;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EPrescriptionReadDTO {
    private Long prescriptionId;
    private Date creationDate;
    private EPrescriptionStatus status;
    private Long patientId;
    private List<PrescribedDrugReadDTO> prescribedDrugs;

    public EPrescriptionReadDTO(){

    }

    public EPrescriptionReadDTO(EPrescription ePrescription){
        this.setPrescriptionId(ePrescription.getId());
        this.setCreationDate(ePrescription.getCreationDate());
        this.setStatus(ePrescription.getStatus());
        this.setPatientId(ePrescription.getPatient().getId());
        this.setPrescribedDrugs(mapPrescribedDrugsToDTO(ePrescription.getPrescribedDrugs()));
    }

    private List<PrescribedDrugReadDTO> mapPrescribedDrugsToDTO(Set<PrescribedDrug> prescribedDrugs){
        List<PrescribedDrugReadDTO> prescribedDrugReadDTOS = new ArrayList<>();
        for(PrescribedDrug prescribedDrug : prescribedDrugs){
            prescribedDrugReadDTOS.add(new PrescribedDrugReadDTO(prescribedDrug));
        }
        return prescribedDrugReadDTOS;
    }

    public List<PrescribedDrugReadDTO> getPrescribedDrugs() {
        return prescribedDrugs;
    }

    public void setPrescribedDrugs(List<PrescribedDrugReadDTO> prescribedDrugs) {
        this.prescribedDrugs = prescribedDrugs;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public EPrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(EPrescriptionStatus status) {
        this.status = status;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}

package com.pharmacy.cpis.userservice.dto;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;
import com.pharmacy.cpis.userservice.model.complaints.Complaint;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import java.util.Date;

public class ComplaintDTO {

    private String content;
    private String response;
    private Date creationTimestamp;
    private PatientDTO creator;
    private PharmacyDTO pharmacy;
    private ConsultantDTO consultant;

    public ComplaintDTO(){}

    public ComplaintDTO(Complaint complaint){
        this.setContent(complaint.getContent());
        this.setResponse(complaint.getResponse());
        this.setCreationTimestamp(complaint.getCreationTimestamp());
        this.setCreator(new PatientDTO(complaint.getCreator()));
        this.setPharmacy(new PharmacyDTO(complaint.getPharmacy()));
        this.setConsultant(new ConsultantDTO(complaint.getConsultant()));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public PatientDTO getCreator() {
        return creator;
    }

    public void setCreator(PatientDTO creator) {
        this.creator = creator;
    }

    public PharmacyDTO getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyDTO pharmacy) {
        this.pharmacy = pharmacy;
    }

    public ConsultantDTO getConsultant() {
        return consultant;
    }

    public void setConsultant(ConsultantDTO consultant) {
        this.consultant = consultant;
    }
}

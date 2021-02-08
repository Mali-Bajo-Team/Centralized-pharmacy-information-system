package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrderStatus;
import com.pharmacy.cpis.drugservice.model.drugprocurement.OrderedDrug;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.model.users.PharmacyAdministrator;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DrugOrderDTO {

    private Long id;

    private Date timestamp;

    private Date deadline;

    private DrugOrderStatus status;

    private PharmacyDTO pharmacy;

    private Set<OrderedDrugDTO> orderedDrugs;

    public DrugOrderDTO(DrugOrder drugOrder){
        this.setId(drugOrder.getId());
        this.setTimestamp(drugOrder.getTimestamp());
        this.setDeadline(drugOrder.getDeadline());
        this.setStatus(drugOrder.getStatus());
        this.setPharmacy( new PharmacyDTO(drugOrder.getPharmacy()));
        this.setOrderedDrugs(drugOrder.getOrderedDrugs());
    }

    public DrugOrderDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public DrugOrderStatus getStatus() {
        return status;
    }

    public void setStatus(DrugOrderStatus status) {
        this.status = status;
    }

    public PharmacyDTO getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(PharmacyDTO pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Set<OrderedDrugDTO> getOrderedDrugs() {
        return orderedDrugs;
    }

    public void setOrderedDrugs(Set<OrderedDrug> orderedDrugs) {
        this.orderedDrugs = new HashSet<>();
        for(OrderedDrug orderedDrug : orderedDrugs){
            this.orderedDrugs.add( new OrderedDrugDTO(orderedDrug));
        }
    }
}

package com.pharmacy.cpis.drugservice.dto;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.drugservice.model.drugprocurement.OfferStatus;
import com.pharmacy.cpis.userservice.model.users.Supplier;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class SupplierOfferDTO {
    @NotEmpty(message = "Shipment date is required.")
    private Date shipmentDate;

    @NotEmpty(message = "Price is required.")
    private Double price;

    @NotEmpty(message = "Status is required.")
    private OfferStatus status;

    public SupplierOfferDTO(){}

    public SupplierOfferDTO(@NotEmpty(message = "Shipment date is required.") Date shipmentDate, @NotEmpty(message = "Price is required.") Double price, @NotEmpty(message = "Status is required.") OfferStatus status) {
        this.shipmentDate = shipmentDate;
        this.price = price;
        this.status = status;
    }

    public SupplierOfferDTO(Offer offer){
        this.setShipmentDate(offer.getShipmentDate());
        this.setPrice(offer.getPrice());
        this.setStatus(offer.getStatus());
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }
}


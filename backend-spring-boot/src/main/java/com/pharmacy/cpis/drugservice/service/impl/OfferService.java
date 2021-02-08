package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.SupplierOfferDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.drugservice.model.drugprocurement.OfferStatus;
import com.pharmacy.cpis.drugservice.repository.IDrugOrderRepository;
import com.pharmacy.cpis.drugservice.repository.IOfferRepository;
import com.pharmacy.cpis.drugservice.service.IOfferService;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.service.ISupplierService;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService implements IOfferService {

    @Autowired
    private IOfferRepository offerRepository;

    @Autowired
    private IDrugOrderRepository drugOrderRepository;

    @Autowired
    private ISupplierService supplierService;


    @Override
    public List<Offer> findOffersBySupplier(Supplier supplier) {
        return offerRepository.findOffersBySupplier(supplier);
    }

    @Override
    public Offer updateOffer(SupplierOfferDTO offerDTO) {
        Offer offer = offerRepository.findById(offerDTO.getId()).orElse(null);
        if (offer == null) throw new PSNotFoundException("Not found that offer");
        offer.setPrice(offerDTO.getPrice());
        offer.setShipmentDate(offerDTO.getShipmentDate());
        return offerRepository.save(offer);
    }

    @Override
    public Offer saveOffer(SupplierOfferDTO offerDTO) {
        Offer offer = new Offer();

        offer.setShipmentDate(offerDTO.getShipmentDate());
        offer.setPrice(offerDTO.getPrice());
        offer.setStatus(OfferStatus.PENDING);
        if (supplierService.getLoggedSupplier() == null) throw new PSBadRequestException("There is no logged supplier");
        offer.setSupplier(supplierService.getLoggedSupplier());
        if (drugOrderRepository.findById(offerDTO.getOrder().getId()).orElse(null) == null)
            throw new PSBadRequestException("There is no drg order with that id");
        offer.setOrder(drugOrderRepository.findById(offerDTO.getOrder().getId()).orElse(null));

        return offerRepository.save(offer);
    }
}

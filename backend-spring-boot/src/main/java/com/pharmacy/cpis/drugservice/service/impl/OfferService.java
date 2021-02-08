package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.SupplierOfferDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.drugservice.repository.IOfferRepository;
import com.pharmacy.cpis.drugservice.service.IOfferService;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService implements IOfferService {

    @Autowired
    private IOfferRepository offerRepository;

    @Override
    public List<Offer> findOffersBySupplier(Supplier supplier) {
        return offerRepository.findOffersBySupplier(supplier);
    }

    @Override
    public Offer updateOffer(SupplierOfferDTO offerDTO) {
        Offer offer = offerRepository.findById(offerDTO.getId()).orElse(null);
        if(offer == null) throw new PSNotFoundException("Not found that offer");
        offer.setPrice(offerDTO.getPrice());
        offer.setShipmentDate(offerDTO.getShipmentDate());
        return offerRepository.save(offer);
    }
}

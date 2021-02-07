package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.drugservice.repository.IOfferRepository;
import com.pharmacy.cpis.drugservice.service.IOfferService;
import com.pharmacy.cpis.userservice.model.users.Supplier;
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
}

package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.dto.SupplierOfferDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.userservice.model.users.Supplier;

import java.util.List;

public interface IOfferService {

    List<Offer> findOffersBySupplier(Supplier supplier);

    Offer updateOffer(SupplierOfferDTO offerDTO);
}

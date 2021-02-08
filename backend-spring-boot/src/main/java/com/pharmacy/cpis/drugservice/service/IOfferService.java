package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.dto.SupplierOfferDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.UserAccount;

import java.util.List;

public interface IOfferService {

    List<Offer> findOffersBySupplier(Supplier supplier);

    Offer updateOffer(SupplierOfferDTO offerDTO);

    Offer saveOffer(SupplierOfferDTO offerDTO);
    
    void accept(UserAccount user, Long offerId);
}

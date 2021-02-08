package com.pharmacy.cpis.drugservice.controller;

import com.pharmacy.cpis.drugservice.dto.DrugOrderDTO;
import com.pharmacy.cpis.drugservice.dto.SupplierOfferDTO;
import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.drugservice.service.IDrugOrderService;
import com.pharmacy.cpis.drugservice.service.IOfferService;
import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/procurement")
public class ProcurementController {

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IOfferService offerService;

    @Autowired
    private IDrugOrderService drugOrderService;

    @GetMapping(value = "/offers")
    public ResponseEntity<List<SupplierOfferDTO>> getSupplierOffers() {
        List<SupplierOfferDTO> supplierOfferDTOS = new ArrayList<>();
        for (Offer offer : offerService.findOffersBySupplier(supplierService.getLoggedSupplier())) {
            supplierOfferDTOS.add(new SupplierOfferDTO(offer));
        }
        return new ResponseEntity<>(supplierOfferDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/offers")
    public ResponseEntity<SupplierOfferDTO> updateSupplierOffer(@RequestBody SupplierOfferDTO supplierOffer) {
        Offer offer = offerService.updateOffer(supplierOffer);
        return new ResponseEntity<>(new SupplierOfferDTO(offer), HttpStatus.OK);
    }

    @PostMapping(value = "/offers")
    public ResponseEntity<SupplierOfferDTO> createSupplierOffer(@RequestBody SupplierOfferDTO supplierOffer) {
        Offer offer = offerService.saveOffer(supplierOffer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<List<DrugOrderDTO>> getAllDrugOrders() {
        List<DrugOrderDTO> drugOrderDTOS = new ArrayList<>();
        for (DrugOrder drugOrder : drugOrderService.findAll()) {
            drugOrderDTOS.add(new DrugOrderDTO(drugOrder));
        }
        return new ResponseEntity<>(drugOrderDTOS, HttpStatus.OK);
    }

}

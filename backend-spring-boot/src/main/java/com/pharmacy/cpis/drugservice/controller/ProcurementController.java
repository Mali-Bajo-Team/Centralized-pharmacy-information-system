package com.pharmacy.cpis.drugservice.controller;

import com.pharmacy.cpis.userservice.model.users.Supplier;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/procurement")
public class ProcurementController {

    @Autowired
    private ISupplierService supplierService;

    @GetMapping
    public ResponseEntity<Void> getSupplierOffers(){
        UserAccount account = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Supplier supplier = supplierService.getSupplierByUserAccount(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.repository.IDrugPurchaseRepository;
import com.pharmacy.cpis.drugservice.service.IDrugPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugPurchaseService implements IDrugPurchaseService {
    @Autowired
    private IDrugPurchaseRepository drugPurchaseRepository;

}

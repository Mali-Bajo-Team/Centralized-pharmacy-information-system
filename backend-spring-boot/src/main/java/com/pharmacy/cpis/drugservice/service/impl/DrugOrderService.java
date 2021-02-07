package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;
import com.pharmacy.cpis.drugservice.repository.IDrugOrderRepository;
import com.pharmacy.cpis.drugservice.service.IDrugOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugOrderService implements IDrugOrderService {

    @Autowired
    private IDrugOrderRepository drugOrderRepository;

    @Override
    public List<DrugOrder> findAll() {
        return drugOrderRepository.findAll();
    }
}

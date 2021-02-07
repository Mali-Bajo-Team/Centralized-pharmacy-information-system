package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.model.drugprocurement.DrugOrder;

import java.util.List;

public interface IDrugOrderService {
    List<DrugOrder> findAll();
}

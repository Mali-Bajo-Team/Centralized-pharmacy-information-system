package com.pharmacy.cpis.drugservice.service;

import com.pharmacy.cpis.drugservice.dto.DrugRegisterDTO;
import com.pharmacy.cpis.drugservice.model.drug.Drug;

public interface IDrugService {

    Drug registerDrug(DrugRegisterDTO drug);

}

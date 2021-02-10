package com.pharmacy.cpis.scheduleservice.service;

import com.pharmacy.cpis.scheduleservice.dto.DrugRecommendationDTO;

public interface IDrugRecommendationService {
    DrugRecommendationDTO recommendDrug(DrugRecommendationDTO drugRecommendationDTO);
}

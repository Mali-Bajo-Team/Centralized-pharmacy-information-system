package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugRegisterDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSpecificationDTO;
import com.pharmacy.cpis.drugservice.model.drug.*;
import com.pharmacy.cpis.drugservice.repository.IDrugClassRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugFormRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.repository.IDrugSpecificationRepository;
import com.pharmacy.cpis.drugservice.service.IDrugService;
import com.pharmacy.cpis.util.exceptions.PSAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DrugService implements IDrugService {

    @Autowired
    private IDrugClassRepository drugClassRepository;

    @Autowired
    private IDrugFormRepository drugFormRepository;

    @Autowired
    private IDrugSpecificationRepository drugSpecificationRepository;

    @Autowired
    private IDrugRepository drugRepository;

    @Override
    public Drug registerDrug(DrugRegisterDTO drug) {
        if(drugRepository.existsByCode(drug.getDrug().getCode()))
            throw new PSAlreadyExistsException("A drug with this code already exists");
        Drug addedDrug = addNewDrug(drug.getDrug());
        DrugSpecification addedSpecification = addNewDrugSpecification(drug.getSpecification(), addedDrug);
        return addedDrug;
    }

    private DrugSpecification addNewDrugSpecification(DrugSpecificationDTO specification, Drug addedDrug) {
        DrugSpecification newDrugSpecification = new DrugSpecification();
        newDrugSpecification.setManufacturer(specification.getManufacturer());
        newDrugSpecification.setContraindications(specification.getContraindications());
        newDrugSpecification.setRecommendedDailyDose(specification.getRecommendedDailyDose());
        if(specification.getIngredients() != null && !specification.getIngredients().isEmpty())
            newDrugSpecification.setIngredients(getSpecificationIngredients(specification));
        newDrugSpecification.setDrug(addedDrug);
        newDrugSpecification.setPrescriptionRequired(false);

        DrugSpecification addedDrugSpecification = drugSpecificationRepository.save(newDrugSpecification);
        return addedDrugSpecification;
    }

    private Set<Ingredient> getSpecificationIngredients(DrugSpecificationDTO specification) {
        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        for(Ingredient ingredient : specification.getIngredients())
            ingredients.add(ingredient);
        return ingredients;
    }

    private Drug addNewDrug(DrugDTO drug){
        Drug newDrug = new Drug();
        newDrug.setName(drug.getName());
        newDrug.setCode(drug.getCode());
        newDrug.setLoyaltyPoints(Integer.parseInt(drug.getLoyaltyPoints()));
        DrugClass drugClass = drugClassRepository.findByName(drug.getTypeOfDrug());
        newDrug.setDrugClass(drugClass);
        // TODO: Choose which form to put, if there is no one from frontend
        // for now is this
        DrugForm drugForm = drugFormRepository.findByName("Tablet");
        newDrug.setDrugForm(drugForm);

        if(drug.getAlternateDrugs() != null && !drug.getAlternateDrugs().isEmpty())
            newDrug.setAlternateDrugs(getAlternateDrugs(drug));

        Drug addedDrug = drugRepository.save(newDrug);
        return addedDrug;
    }

    private Set<Drug> getAlternateDrugs(DrugDTO drug) {
        Set<Drug> drugAlternateDrugs = new HashSet<Drug>();
        for(Drug alternateDrug : drug.getAlternateDrugs())
            drugAlternateDrugs.add(alternateDrug);
        return drugAlternateDrugs;
    }
}

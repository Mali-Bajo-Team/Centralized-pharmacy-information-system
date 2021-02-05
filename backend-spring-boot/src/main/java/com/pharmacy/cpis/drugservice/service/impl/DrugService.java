package com.pharmacy.cpis.drugservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.dto.DrugRegisterDTO;
import com.pharmacy.cpis.drugservice.dto.DrugSpecificationDTO;
import com.pharmacy.cpis.drugservice.model.drug.*;
import com.pharmacy.cpis.drugservice.repository.*;
import com.pharmacy.cpis.drugservice.service.IDrugService;
import com.pharmacy.cpis.util.exceptions.PSAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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

    @Autowired
    private IIngredientsRepository ingredientsRepository;

    @Override
    public Drug registerDrug(DrugRegisterDTO drug) {
        if(drugRepository.existsByCode(drug.getDrug().getCode()))
            throw new PSAlreadyExistsException("A drug with this code already exists");
        Drug addedDrug = addNewDrug(drug.getDrug());
        DrugSpecification addedSpecification = addNewDrugSpecification(drug.getSpecification(), addedDrug);

        return addedDrug;
    }

    @Override
    public List<Drug> findAll() {
        return drugRepository.findAll();
    }

    @Override
    public List<DrugClass> findAllDrugClass() {
        return drugClassRepository.findAll();
    }

    private DrugSpecification addNewDrugSpecification(DrugSpecificationDTO specification, Drug addedDrug) {
        DrugSpecification newDrugSpecification = new DrugSpecification();
        newDrugSpecification.setManufacturer(specification.getManufacturer());
        newDrugSpecification.setContraindications(specification.getContraindications());
        newDrugSpecification.setRecommendedDailyDose(specification.getRecommendedDailyDose());
        newDrugSpecification.setDrug(addedDrug);
        newDrugSpecification.setPrescriptionRequired(false);
        DrugSpecification addedDrugSpecification = drugSpecificationRepository.save(newDrugSpecification);

        // This is after first save because i want to get id of added drug specification to can add ingredient(he need specification id)
        if(specification.getIngredients() != null && !specification.getIngredients().isEmpty()){
            addedDrugSpecification.setIngredients(getAndSaveSpecificationIngredients(specification, addedDrugSpecification));
            addedDrugSpecification = drugSpecificationRepository.save(addedDrugSpecification);
        }

        return addedDrugSpecification;
    }

    private Set<Ingredient> getAndSaveSpecificationIngredients(DrugSpecificationDTO specification, DrugSpecification addedDrugSpecification) {
        Set<Ingredient> ingredients = new HashSet<Ingredient>();
        for(Ingredient ingredient : specification.getIngredients()){
            Ingredient newIngredient = new Ingredient();
            newIngredient.setSpecification(addedDrugSpecification);
            newIngredient.setName(ingredient.getName());
            newIngredient.setAmount(ingredient.getAmount());
            ingredients.add(ingredientsRepository.save(newIngredient));
        }
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
        for(Drug alternateDrug : drug.getAlternateDrugs()){
            Drug realDrug = drugRepository.findByCode(alternateDrug.getCode());
            if(realDrug != null)
                drugAlternateDrugs.add(realDrug);
        }
        return drugAlternateDrugs;
    }
}

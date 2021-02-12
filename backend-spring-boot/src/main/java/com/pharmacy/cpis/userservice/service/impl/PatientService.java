package com.pharmacy.cpis.userservice.service.impl;

import com.pharmacy.cpis.drugservice.dto.DrugDTO;
import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.userservice.dto.PatientProfileDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IPatientService;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IUserRepository userAccountRepository;

    @Autowired
    private IConsultationRepository consultationRepository;

    @Autowired
    private IDrugRepository drugRepository;

    @Override
    public List<Patient> findAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findByEmail(String email) {
        UserAccount userAccount = userAccountRepository.findByEmail(email);
        return patientRepository.findById(userAccount.getPerson().getId()).orElse(null);
    }

    @Override
    public Set<Pharmacy> findPatientSubscriptions(String patientEmail) {
        Patient patient = findByEmail(patientEmail);
        return patient.getSubscriptions();
    }

    @Override
    public Patient update(PatientProfileDTO patientProfileDTO) {
        Long patientId = userAccountRepository.findByEmail(patientProfileDTO.getEmail()).getPerson().getId();
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) throw new PSNotFoundException("Not found patient with that id");
        patient.setName(patientProfileDTO.getName());
        patient.setSurname(patientProfileDTO.getSurname());
        patient.setPhoneNumber(patientProfileDTO.getPhoneNumber());
        patient.setAddress(patientProfileDTO.getAddress());
        patient.setCity(patientProfileDTO.getCity());
        patient.setCountry(patientProfileDTO.getCountry());
        if(patientProfileDTO.getAllergies() != null && !patientProfileDTO.getAllergies().isEmpty()){
            patient.setAllergies(new HashSet<>());
            for(DrugDTO drugDTO: patientProfileDTO.getAllergies()){
                patient.getAllergies().add(drugRepository.findByCode(drugDTO.getCode()));
            }
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient addPenaltie(String email, Long consultationID) {

        UserAccount userAccount = userAccountRepository.findByEmail(email);
        Patient patient = patientRepository.findById(userAccount.getPerson().getId()).orElse(null);
        if (patient.getPenalties() == null) {
            patient.setPenalties(1);
        } else {
            patient.setPenalties(patient.getPenalties() + 1);
        }

        patientRepository.save(patient);

        Consultation consultation = consultationRepository.getOne(consultationID);
        consultation.setStatus(ConsultationStatus.NO_SHOW);

        consultationRepository.save(consultation);

        return patient;
    }
    @Override
    @Transactional
    public void resetPenalties() {
        patientRepository.resetPenalties();
    }

    @Override
    public void addPenalty(String email) {
        Patient patient= findByEmail(email);
        if(patient.getPenalties() == null) {
            patient.setPenalties(1);
        } else{
            patient.setPenalties(patient.getPenalties() + 1);
        }patientRepository.save(patient);
    }
}

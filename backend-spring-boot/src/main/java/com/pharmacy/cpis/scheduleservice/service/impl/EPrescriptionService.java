package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.drugservice.repository.IDrugRepository;
import com.pharmacy.cpis.drugservice.service.IReservationService;
import com.pharmacy.cpis.scheduleservice.dto.prescription.EPrescriptionCreateDTO;
import com.pharmacy.cpis.scheduleservice.dto.prescription.PrescribedDrugCreateDTO;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescriptionStatus;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.PrescribedDrug;
import com.pharmacy.cpis.scheduleservice.repository.IEPrescriptionRepository;
import com.pharmacy.cpis.scheduleservice.repository.IPrescribedDrugRepository;
import com.pharmacy.cpis.scheduleservice.service.IEPrescriptionService;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EPrescriptionService implements IEPrescriptionService {

    @Autowired
    private IEPrescriptionRepository prescriptionRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IDrugRepository drugRepository;

    @Autowired
    private IPrescribedDrugRepository prescribedDrugRepository;

    @Autowired
    private IReservationService reservationService;

    @Override
    public List<EPrescription> findAllPatientEPrescription(String patientEmail) {
        Long patientId = userRepository.findByEmail(patientEmail).getPerson().getId();
        return prescriptionRepository.findAllByPatientId(patientId);
    }

    @Override
    public List<EPrescription> findAllPatientEPrescriptionByStatus(Long patientId, EPrescriptionStatus status) {
        return prescriptionRepository.findAllByStatusAndPatientId(status,patientId);
    }

    @Override
    @Transactional
    public EPrescription savePrescription(EPrescriptionCreateDTO prescriptionDTO) {
        Long patientId = userRepository.findByEmail(prescriptionDTO.getPatientEmail()).getPerson().getId();
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null) throw new PSNotFoundException("Not found patient with that email");

        EPrescription ePrescription = new EPrescription();
        ePrescription.setCreationDate(new Date());
        ePrescription.setStatus(EPrescriptionStatus.CREATED);
        ePrescription.setPatient(patient);
        EPrescription savedPrescription = prescriptionRepository.save(ePrescription); // First save, because i want to have ePrescription from DB(because of e-prescription id)
        ePrescription.setPrescribedDrugs(savePrescribedDrugs(prescriptionDTO.getPrescribedDrugs(),savedPrescription));

        // This is new with updated prescribed drugs
        EPrescription ePrescriptionSaved = prescriptionRepository.save(ePrescription);
        reservationService.makeReservationForEPrescription(ePrescription, prescriptionDTO.getPharmacyId());

        return ePrescriptionSaved;
    }

    private Set<PrescribedDrug> savePrescribedDrugs(List<PrescribedDrugCreateDTO> prescribedDrugCreateDTOS, EPrescription ePrescription){
        Set<PrescribedDrug> prescribedDrugs = new HashSet<>();
        for(PrescribedDrugCreateDTO prescribedDrugCreateDTO : prescribedDrugCreateDTOS){
            PrescribedDrug prescribedDrug = new PrescribedDrug();
            prescribedDrug.setAmount(prescribedDrugCreateDTO.getAmount());
            prescribedDrug.setDrug(drugRepository.findByCode(prescribedDrugCreateDTO.getDrugCode()));
            prescribedDrug.setPrescription(ePrescription);
            prescribedDrugs.add(prescribedDrugRepository.save(prescribedDrug));
        }
        return prescribedDrugs;
    }
}

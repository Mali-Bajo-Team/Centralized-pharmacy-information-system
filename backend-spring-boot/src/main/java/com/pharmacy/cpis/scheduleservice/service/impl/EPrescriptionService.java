package com.pharmacy.cpis.scheduleservice.service.impl;

import com.pharmacy.cpis.scheduleservice.dto.EPrescriptionCreateDTO;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescription;
import com.pharmacy.cpis.scheduleservice.model.prescriptions.EPrescriptionStatus;
import com.pharmacy.cpis.scheduleservice.repository.IEPrescriptionRepository;
import com.pharmacy.cpis.scheduleservice.service.IEPrescriptionService;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EPrescriptionService implements IEPrescriptionService {

    @Autowired
    private IEPrescriptionRepository prescriptionRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPatientRepository patientRepository;


    @Override
    public List<EPrescription> findAllPatientEPrescription(Long patientId) {
        return prescriptionRepository.findAllByPatientId(patientId);
    }

    @Override
    public List<EPrescription> findAllPatientEPrescriptionByStatus(Long patientId, EPrescriptionStatus status) {
        return prescriptionRepository.findAllByStatusAndPatientId(status,patientId);
    }

    @Override
    public EPrescription savePrescription(EPrescriptionCreateDTO prescriptionDTO) {
        Long patientId = userRepository.findByEmail(prescriptionDTO.getPatientEmail()).getPerson().getId();
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null) throw new PSNotFoundException("Not found patient with that email");

        EPrescription ePrescription = new EPrescription();
        ePrescription.setCreationDate(new Date());
        ePrescription.setStatus(EPrescriptionStatus.CREATED);
        ePrescription.setPatient(patient);
        // TODO: Check better way how to generate this CODE

        return null;
    }
}

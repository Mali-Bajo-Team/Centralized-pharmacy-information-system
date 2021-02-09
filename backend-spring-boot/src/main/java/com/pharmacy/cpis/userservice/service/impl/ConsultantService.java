package com.pharmacy.cpis.userservice.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.userservice.dto.ExaminitedPatientDTO;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IAuthorityService;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.util.CollectionUtil;

@Service
public class ConsultantService implements IConsultantService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IAuthorityService authService;

	@Autowired
	private IConsultantRepository consultantRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IWorkingTimesRepository workingTimesRepository;

	@Override
	public Collection<Consultant> getByType(ConsultantType type) {
		return consultantRepository.findAllByType(type);
	}

	@Override
	public Collection<Consultant> getByTypeAndPharmacy(ConsultantType type, Long pharmacyId) {
		Collection<WorkingTimes> workingTimes = workingTimesRepository.findAllByPharmacyId(pharmacyId);
		Collection<Consultant> consultants = CollectionUtil.map(workingTimes, WorkingTimes::getConsultant);

		return CollectionUtil.findAll(consultants, consultant -> consultant.getType().equals(type));
	}

	// TODO: Think about how to improve DRY, because this is similar as patient
	// registration service
	// only difference is that this is for dermatologist :/
	@Override
	public Consultant registerDermatologist(UserRegisterDTO dermatologist) {
		Consultant addedDermatologist = addNewDermatologist(dermatologist);
		UserAccount addedAccount = addNewDermatologistAccount(dermatologist, addedDermatologist);
		return addedDermatologist;
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	private UserAccount addNewDermatologistAccount(UserRegisterDTO userRequest, Consultant addedConsultant) {
		UserAccount newUserAccount = new UserAccount();
		newUserAccount.setEmail(userRequest.getEmail());
		newUserAccount.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		newUserAccount.setActive(true);
		newUserAccount.setNeedsPasswordChange(true);
		List<Authority> auth = authService.findByName("ROLE_DERMATOLOGIST");
		newUserAccount.setAuthorities(auth);
		newUserAccount.setPerson(addedConsultant);

		UserAccount addedAccount = userRepository.save(newUserAccount);
		return addedAccount;
	}

	private Consultant addNewDermatologist(UserRegisterDTO userRequest) {
		Consultant newDermatologist = new Consultant();
		newDermatologist.setType(ConsultantType.DERMATOLOGIST);
		newDermatologist.setAddress(userRequest.getAddress());
		newDermatologist.setCity(userRequest.getCity());
		newDermatologist.setCountry(userRequest.getCountry());
		newDermatologist.setName(userRequest.getName());
		newDermatologist.setSurname(userRequest.getSurname());
		newDermatologist.setPhoneNumber(userRequest.getMobile());
		Consultant addedDermatologist = consultantRepository.save(newDermatologist);
		return addedDermatologist;
	}

	@Override
	public Set<ExaminitedPatientDTO> getExaminitedPatients(Long consultantID) {
		Consultant consultant = consultantRepository.getOne(consultantID);

		Set<Consultation> allConsultations = consultant.getConsultations();
		Set<Patient> exainitedPatients = new HashSet<>();
		Set<ExaminitedPatientDTO> exeaminitedPatientDTOs = new HashSet<>();

		for (Consultation c : allConsultations) {
			ExaminitedPatientDTO exeaminitedPatientDTO = new ExaminitedPatientDTO();

			if (c.getTime().getStart().before(new Date())) {
				exeaminitedPatientDTO.setName(c.getPatient().getName());
				exeaminitedPatientDTO.setSurname(c.getPatient().getSurname());
				exeaminitedPatientDTO.setExaminitedDate(c.getTime().getStart());

				exainitedPatients.add(c.getPatient());
				exeaminitedPatientDTOs.add(exeaminitedPatientDTO);
			}
		}

		return exeaminitedPatientDTOs;
	}

	@Override
	public Consultant findByEmail(String email) {
		UserAccount userAccount = userRepository.findByEmail(email);

		Consultant consultant = consultantRepository.getOne(userAccount.getId());

		return consultant;
	}

}

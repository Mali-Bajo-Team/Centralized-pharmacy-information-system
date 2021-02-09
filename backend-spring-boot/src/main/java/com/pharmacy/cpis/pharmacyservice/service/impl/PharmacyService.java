package com.pharmacy.cpis.pharmacyservice.service.impl;

import java.util.List;

import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.pharmacyservice.dto.PharmacyRegisterDTO;
import com.pharmacy.cpis.pharmacyservice.dto.PharmacyUpdateDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Location;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.pharmacyservice.service.IPharmacyService;
import com.pharmacy.cpis.userservice.dto.UserRegisterDTO;
import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.PharmacyAdministrator;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IPharmacyAdministratorRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IAuthorityService;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;

@Service
public class PharmacyService implements IPharmacyService {

	@Autowired
	private IPharmacyRepository pharmacyRepository;

	@Autowired
	private IPharmacyAdministratorRepository pharmacyAdministratorRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IAuthorityService authService;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IPatientRepository patientRepository;

	@Override
	public Pharmacy getById(Long id) {
		Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

		if (pharmacy == null)
			throw new PSNotFoundException("The requested pharmacy does not exist.");

		return pharmacy;
	}

	public Pharmacy update(Long id, PharmacyUpdateDTO update) {
		Pharmacy pharmacy = getById(id);

		pharmacy.setName(update.getName());
		pharmacy.setDescription(update.getDescription());
		pharmacy.setPharmacistConsultationPrice(update.getPharmacistConsultationPrice());
		pharmacy.setDermatologistConsultationPrice(update.getDermatologistConsultationPrice());
		pharmacy.getLocation().setStreet(update.getStreet());
		pharmacy.getLocation().setCity(update.getCity());
		pharmacy.getLocation().setCountry(update.getCountry());
		pharmacy.getLocation().setLatitude(update.getLatitude());
		pharmacy.getLocation().setLongitude(update.getLongitude());

		return pharmacyRepository.save(pharmacy);
	}

	@Override
	public List<Pharmacy> findAll() {
		return pharmacyRepository.findAll();
	}

	@Override
	public void subscribePatientOnPharmacyPromotions(String patientEmail, Long pharmacyId) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElse(null);
		Long patientId = userRepository.findByEmail(patientEmail).getPerson().getId();
		if(patientId == null) throw new PSNotFoundException("That person does not exist");

		Patient patient = patientRepository.findById(patientId).orElse(null);
		if(pharmacy == null || patient == null) throw new PSNotFoundException("Not found that pharmacy or patient");
		pharmacy.getSubscribers().add(patient);
		patient.getSubscriptions().add(pharmacy);
		pharmacyRepository.save(pharmacy);
		patientRepository.save(patient);
	}

	@Override
	public Pharmacy registerPharmacy(PharmacyRegisterDTO pharmacy) {
		Pharmacy addedPharmacy = addPharmacy(pharmacy);
		PharmacyAdministrator addedPharmacyAdministrator = addNewPharmacyAdministrator(
				pharmacy.getPharmacyAdministrator(), addedPharmacy);
		UserAccount addedAccount = addNewPharmacyAdministratorAccount(pharmacy.getPharmacyAdministrator(),
				addedPharmacyAdministrator);
		return addedPharmacy;
	}

	private Pharmacy addPharmacy(PharmacyRegisterDTO pharmacy) {
		Pharmacy newPharmacy = new Pharmacy();
		newPharmacy.setName(pharmacy.getName());
		Location pharmacyLocation = new Location();
		pharmacyLocation.setCity(pharmacy.getCity());
		pharmacyLocation.setStreet(pharmacy.getAddress());
		pharmacyLocation.setCountry(pharmacy.getCountry());
		// TODO: MAKE SOME REAL SERVICE WHICH CALCULATE REAL LATITUDE & LONGITUDE, THIS
		// IS HARDCODED ONE !
		pharmacyLocation.setLatitude(10.0);
		pharmacyLocation.setLongitude(10.0);

		newPharmacy.setLocation(pharmacyLocation);
		newPharmacy.setDermatologistConsultationPrice(pharmacy.getDermatologistConsultationPrice());
		newPharmacy.setPharmacistConsultationPrice(pharmacy.getPharmacistConsultationPrice());
		newPharmacy.setDescription(pharmacy.getDescription());

		Pharmacy addedPharmacy = pharmacyRepository.save(newPharmacy);
		return addedPharmacy;
	}

	private PharmacyAdministrator addNewPharmacyAdministrator(UserRegisterDTO userRequest, Pharmacy addedPharmacy) {
		PharmacyAdministrator pharmacyAdministrator = new PharmacyAdministrator();
		pharmacyAdministrator.setAddress(userRequest.getAddress());
		pharmacyAdministrator.setCity(userRequest.getCity());
		pharmacyAdministrator.setCountry(userRequest.getCountry());
		pharmacyAdministrator.setName(userRequest.getName());
		pharmacyAdministrator.setSurname(userRequest.getSurname());
		pharmacyAdministrator.setPhoneNumber(userRequest.getMobile());
		pharmacyAdministrator.setPharmacy(addedPharmacy);
		PharmacyAdministrator addedPharmacyAdministrator = pharmacyAdministratorRepository.save(pharmacyAdministrator);
		return addedPharmacyAdministrator;
	}

	private UserAccount addNewPharmacyAdministratorAccount(UserRegisterDTO userRequest,
			PharmacyAdministrator addedAdministrator) {
		UserAccount newUserAccount = new UserAccount();
		newUserAccount.setEmail(userRequest.getEmail());
		newUserAccount.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		newUserAccount.setActive(true);
		newUserAccount.setNeedsPasswordChange(true);
		newUserAccount.setPharmacyId(addedAdministrator.getPharmacy().getId());
		List<Authority> auth = authService.findByName("ROLE_PHARMACY_ADMIN");
		newUserAccount.setAuthorities(auth);
		newUserAccount.setPerson(addedAdministrator);

		UserAccount addedAccount = userRepository.save(newUserAccount);
		return addedAccount;
	}

}

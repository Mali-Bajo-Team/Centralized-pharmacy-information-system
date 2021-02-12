package com.pharmacy.cpis.student3.integration;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.dto.DrugRecommendationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.student2.constants.WorkingTimesConstants;
import com.pharmacy.cpis.student3.constants.LoginConstants;
import com.pharmacy.cpis.userservice.dto.JwtAuthenticationRequest;
import com.pharmacy.cpis.userservice.dto.UserTokenState;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.Patient;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.util.DateRange;
import com.pharmacy.cpis.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DrugRecommendationControllerTest {

	private static final String URL_PREFIX = "/api/drugrecommendation/checkbeforerecommend";

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private TestRestTemplate restTemplate;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private String dermatologistAccesToken;

	private MockMvc mockMvc;
	private Consultant mockDermatologist;
	private WorkingTimes mockDermatologistWorkingTimes;
	private Consultant mockPharmacist;
	private WorkingTimes mockPharmacistWorkingTimes;
	private Collection<Consultation> mockConsultations;
	private Pharmacy mockPharmacy;
	private Patient mockPatient;
	@Before
	public void setup() {
		mockConsultations = new ArrayList<>();
		mockDermatologist = new Consultant();
		mockDermatologist.setType(ConsultantType.DERMATOLOGIST);
		mockDermatologist.setId(1L);
		mockDermatologistWorkingTimes = new WorkingTimes();
		mockDermatologistWorkingTimes.setId(1L);
		mockDermatologistWorkingTimes.setConsultant(mockDermatologist);
		mockDermatologistWorkingTimes.setPharmacy(new Pharmacy());
		mockDermatologistWorkingTimes.getPharmacy().setId(1L);
		mockDermatologistWorkingTimes
				.setMonday(new DateRange(WorkingTimesConstants.WT_START, WorkingTimesConstants.WT_END));
		mockDermatologist.setWorkingTimes(new HashSet<>());
		mockDermatologist.getWorkingTimes().add(mockDermatologistWorkingTimes);

		Consultation mockConsultation = new Consultation();
		mockConsultation.setId(1L);
		mockConsultation.setConsultant(mockDermatologist);
		mockConsultation.setStatus(ConsultationStatus.SCHEDULED);
		mockConsultation.setTime(new DateRange(WorkingTimesConstants.WT_START,
				new Date(WorkingTimesConstants.WT_START.getTime() + (60 * 60 * 1000L))));
		mockConsultations.add(mockConsultation);

		mockPharmacy = new Pharmacy();
		mockPharmacy.setId(2L);

		mockPharmacist = new Consultant();
		mockPharmacist.setType(ConsultantType.PHARMACIST);
		mockPharmacist.setId(2L);
		mockPharmacist.setAccount(new UserAccount());
		mockPharmacistWorkingTimes = new WorkingTimes();
		mockPharmacistWorkingTimes.setId(2L);
		mockPharmacistWorkingTimes.setConsultant(mockDermatologist);
		mockPharmacistWorkingTimes.setPharmacy(mockPharmacy);
		mockPharmacistWorkingTimes
				.setMonday(new DateRange(WorkingTimesConstants.WT_START, WorkingTimesConstants.WT_END));
		mockPharmacist.setWorkingTimes(new HashSet<>());
		mockPharmacist.getWorkingTimes().add(mockPharmacistWorkingTimes);

		mockPatient = new Patient();
		Set<Consultation> mockSetConsultations = new HashSet<>();
		mockSetConsultations.add(mockConsultation);

		mockPatient.setId(2L);
		mockPatient.setConsultations(mockSetConsultations);

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.build();

		ResponseEntity<UserTokenState> jankovicToken = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest(LoginConstants.DERMATOLOGIST_EMAIL, LoginConstants.DERMATOLOGIST_PASSWORD),
				UserTokenState.class);
		this.dermatologistAccesToken = jankovicToken.getBody().getAccessToken();


	}

	@Test
	@Transactional
	@Rollback(true)
	public void checkDrugAvailableInPharmacyWhereIsConsultation() throws Exception {

		DrugRecommendationDTO drugRecommendationDTO = new DrugRecommendationDTO();
		drugRecommendationDTO.setDrugCode("aspirin");
		drugRecommendationDTO.setConsultationID(1L);
		drugRecommendationDTO.setPatientID(2L);
		drugRecommendationDTO.setDuration(2);

		this.mockMvc
				.perform(post(URL_PREFIX).header("Authorization", "Bearer " + dermatologistAccesToken)
						.contentType(contentType).content(TestUtil.json(drugRecommendationDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType));
	}

}

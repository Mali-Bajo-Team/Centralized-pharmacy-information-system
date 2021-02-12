package com.pharmacy.cpis.student3.integration;

import com.pharmacy.cpis.drugservice.dto.AddAvailableDrugDTO;
import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.scheduleservice.dto.DrugRecommendationDTO;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
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
	private static final String URL_PREFIX_SCHEDULE_EXAMIANTION = "/api/consultations/scheduleconsultation";

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

	@Before
	public void setup() {

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

	@Test
	@Transactional
	@Rollback(true)
	public void scheduleExamiantionWithCorrectStartEndDate() throws Exception {

		ScheduleExaminationDTO scheduleExaminationDTO = new ScheduleExaminationDTO();
		scheduleExaminationDTO.setConsultantEmail(LoginConstants.DERMATOLOGIST_EMAIL);
		scheduleExaminationDTO.setConsultantId(9L);
		scheduleExaminationDTO.setStartDate("2021-02-16 12:00:00");
		scheduleExaminationDTO.setEndDate("2021-02-16 13:00:00");
		scheduleExaminationDTO.setPatientId(2L);
		scheduleExaminationDTO.setPharmacyID(2L);


		this.mockMvc
				.perform(post(URL_PREFIX_SCHEDULE_EXAMIANTION).header("Authorization", "Bearer " + dermatologistAccesToken)
						.contentType(contentType).content(TestUtil.json(scheduleExaminationDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void scheduleExamiantionWithOverllapingStartEndDate() throws Exception {

		ScheduleExaminationDTO scheduleExaminationDTO = new ScheduleExaminationDTO();
		scheduleExaminationDTO.setConsultantEmail(LoginConstants.DERMATOLOGIST_EMAIL);
		scheduleExaminationDTO.setConsultantId(9L);
		scheduleExaminationDTO.setStartDate("2021-02-16 19:00:00");
		scheduleExaminationDTO.setEndDate("2021-02-16 20:00:00");
		scheduleExaminationDTO.setPatientId(2L);
		scheduleExaminationDTO.setPharmacyID(2L);


		this.mockMvc
				.perform(post(URL_PREFIX_SCHEDULE_EXAMIANTION).header("Authorization", "Bearer " + dermatologistAccesToken)
						.contentType(contentType).content(TestUtil.json(scheduleExaminationDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(contentType));
	}

}

package com.pharmacy.cpis.student2.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;

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

import com.pharmacy.cpis.drugservice.dto.AddDrugOrderDTO;
import com.pharmacy.cpis.drugservice.dto.AddOrderedDrugDTO;
import com.pharmacy.cpis.student2.constants.DrugConstants;
import com.pharmacy.cpis.student2.constants.LoginConstants;
import com.pharmacy.cpis.userservice.dto.JwtAuthenticationRequest;
import com.pharmacy.cpis.userservice.dto.UserTokenState;
import com.pharmacy.cpis.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DrugProcurementControllerTest {

	private static final String URL_PREFIX = "/api/procurement";

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private TestRestTemplate restTemplate;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private String pharmacyAdminToken;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.build();

		ResponseEntity<UserTokenState> jankovicToken = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest(LoginConstants.JANKOVIC_EMAIL, LoginConstants.JANKOVIC_PASSWORD),
				UserTokenState.class);
		this.pharmacyAdminToken = jankovicToken.getBody().getAccessToken();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteOrderFailsWhenHasOffers() throws Exception {
		this.mockMvc.perform(delete(URL_PREFIX + "/orders/" + DrugConstants.ORDER_WITH_OFFERS_ID)
				.header("Authorization", "Bearer " + pharmacyAdminToken))
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void addDrugOrder() throws Exception {
		AddDrugOrderDTO order = new AddDrugOrderDTO();
		order.setDeadline(new Date(new Date().getTime() + 5 * 24 * 60 * 60 * 1000L));
		order.setOrderedDrugs(new HashSet<>());
		order.getOrderedDrugs().add(new AddOrderedDrugDTO(DrugConstants.DRUG_IN_PHARMACY_CODE, 20, 10.0));
		order.getOrderedDrugs().add(new AddOrderedDrugDTO(DrugConstants.DRUG_NOT_IN_PHARMACY_CODE_1, 20, 10.0));

		this.mockMvc
				.perform(post(URL_PREFIX + "/orders").header("Authorization", "Bearer " + pharmacyAdminToken)
						.contentType(contentType).content(TestUtil.json(order)))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}

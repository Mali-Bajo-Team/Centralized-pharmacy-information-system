package com.pharmacy.cpis.student4.unit;

import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequestStatus;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRequestRepository;
import com.pharmacy.cpis.scheduleservice.service.impl.VacationRequestService;
import com.pharmacy.cpis.userservice.dto.UserCategoryDTO;
import com.pharmacy.cpis.userservice.model.loyaltyprogram.UserCategory;
import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.ILoyaltyProgramService;
import com.pharmacy.cpis.util.DateRange;
import com.pharmacy.cpis.util.exceptions.PSConflictException;
import com.pharmacy.cpis.util.exceptions.PSNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoyaltyProgramServiceTests {

	@Mock
	private ILoyaltyProgramService loyaltyProgramServiceMock;


	private Consultant mockDermatologist;
	private VacationRequest mockVacationRequest;
	private UserAccount mockAdmin;

	@Before
	public void setup() {
		mockDermatologist = new Consultant();
		mockDermatologist.setType(ConsultantType.DERMATOLOGIST);
		mockDermatologist.setId(1L);

		Authority authorityMock = new Authority();
		authorityMock.setName("ROLE_ADMIN");
		authorityMock.setId(1L);
		mockAdmin = new UserAccount();
		mockAdmin.setId(1L);
		mockAdmin.setAuthorities(Arrays.asList(authorityMock));

		mockVacationRequest = new VacationRequest();
		mockVacationRequest.setConsultant(mockDermatologist);
		Date now = new Date();
		mockVacationRequest.setDateRange(new DateRange(new Date(now.getTime() + 3 * 24 * 60 * 60 * 1000L),
				new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000L)));
		mockVacationRequest.setStatus(VacationRequestStatus.PENDING);
		mockVacationRequest.setId(1L);
	}

	@Test(expected = NullPointerException.class)
	@Transactional
	@Rollback(true)
	public void removeCategoryTest() {

		UserCategoryDTO userCategoryDTO = null;
		userCategoryDTO.setName("test");

		loyaltyProgramServiceMock.removeCategory(userCategoryDTO);

	}

	@Test(expected = NullPointerException.class)
	@Transactional
	@Rollback(true)
	public void saveCategory() {

		UserCategoryDTO userCategoryDTO = null;
		userCategoryDTO.setName("test");

		loyaltyProgramServiceMock.removeCategory(userCategoryDTO);

	}

	@Test()
	@Transactional
	@Rollback(true)
	public void findAllTest() {

		UserCategoryDTO userCategoryDTO = new UserCategoryDTO();
		userCategoryDTO.setName("test");

		loyaltyProgramServiceMock.findAll();

	}

	@Test()
	@Transactional
	@Rollback(true)
	public void updateCategoryTest() {

		UserCategoryDTO userCategoryDTO = new UserCategoryDTO();
		userCategoryDTO.setName("test");

		loyaltyProgramServiceMock.updateCategory(userCategoryDTO);

	}

	@Test()
	@Transactional
	@Rollback(true)
	public void updateLoyaltyProgramTest() {

		UserCategoryDTO userCategoryDTO = new UserCategoryDTO();
		userCategoryDTO.setName("test");

		UserCategory result = loyaltyProgramServiceMock.findUserCategoryByLoyaltyPoints(50);

		assertEquals(null, result);

	}

}

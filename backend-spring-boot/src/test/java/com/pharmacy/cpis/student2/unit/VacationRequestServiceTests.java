package com.pharmacy.cpis.student2.unit;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

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

import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequestStatus;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRequestRepository;
import com.pharmacy.cpis.scheduleservice.service.impl.VacationRequestService;
import com.pharmacy.cpis.userservice.model.users.Authority;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.util.DateRange;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VacationRequestServiceTests {

	@Mock
	private IVacationRequestRepository vacationRequestRepository;

	@Mock
	private IConsultantService consultantService;

	@Mock
	private IVacationRepository vacationRepository;

	@Mock
	private IConsultationRepository consultationRepository;

	@InjectMocks
	private VacationRequestService vacationService;

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

	@Test
	@Transactional
	@Rollback(true)
	public void rejectVacationRequest() {
		Mockito.when(vacationRequestRepository.findById(mockVacationRequest.getId()))
				.thenReturn(Optional.of(mockVacationRequest));
		Mockito.when(vacationRequestRepository.save(Mockito.any())).thenAnswer(new Answer<VacationRequest>() {
			@Override
			public VacationRequest answer(InvocationOnMock invocation) throws Throwable {
				return (VacationRequest) invocation.getArgument(0);
			}
		});

		String response = "test response";
		VacationRequest returned = vacationService.reject(mockAdmin, mockVacationRequest.getId(), response);

		Assertions.assertThat(returned).isNotNull();
		Assertions.assertThat(returned.getStatus()).isEqualTo(VacationRequestStatus.REJECTED);
		Assertions.assertThat(returned.getResponse()).isEqualTo(response);
		Mockito.verifyZeroInteractions(vacationRepository);
	}

}

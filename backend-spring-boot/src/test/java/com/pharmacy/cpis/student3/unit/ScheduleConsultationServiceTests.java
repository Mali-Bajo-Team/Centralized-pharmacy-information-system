package com.pharmacy.cpis.student3.unit;

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.scheduleservice.controller.ConsultationController;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequestStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IVacationRequestRepository;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.scheduleservice.service.IConsultationService;
import com.pharmacy.cpis.scheduleservice.service.impl.VacationRequestService;
import com.pharmacy.cpis.student2.constants.WorkingTimesConstants;
import com.pharmacy.cpis.userservice.dto.AddWorkingDayDTO;
import com.pharmacy.cpis.userservice.dto.AddWorkingTimeDTO;
import com.pharmacy.cpis.userservice.dto.EmployDermatologistDTO;
import com.pharmacy.cpis.userservice.model.users.*;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.impl.PharmacyEmployeeService;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.pharmacy.cpis.student3.constants.WorkingTimesConstants.*;
import static java.lang.Boolean.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleConsultationServiceTests {

	@Mock
	private IWorkingTimesRepository workingTimesRepositoryMock;

	@Mock
	private IUserRepository userRepositoryMock;

	@Mock
	private IConsultationRepository consultationRepositoryMock;

	@Mock
	private IConsultantRepository consultantRepositoryMock;

	@Mock
	private ConsultationController consultationController;

	@Mock
	private IConsultationService consultationServiceMock;

	@Mock
	private IPharmacyRepository pharmacyRepositoryMock;

	@InjectMocks
	private PharmacyEmployeeService pharmacyEmployeService;

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

		mockPatient.setId(5l);
		mockPatient.setConsultations(mockSetConsultations);

	}

	@Test()
	@Transactional
	@Rollback(true)
	public void scheduleConsultationFailsWhenOverlappingDermatologistWorkingTime() {
		Mockito.when(pharmacyRepositoryMock.findById(2L)).thenReturn(Optional.of(mockPharmacy));
		Mockito.when(consultantRepositoryMock.findLockedById(1L)).thenReturn(Optional.of(mockDermatologist));

		Boolean result = consultationServiceMock.isConsultantFreeForConsultation(1L,1L,WT_OVERLAPPING_START ,WT_OVERLAPPING_END);

		Mockito.verify(workingTimesRepositoryMock, Mockito.never()).save(Mockito.any());

		assertEquals(false, result);
	}

	@Test()
	@Transactional
	@Rollback(true)
	public void scheduleConsultationFailsWhenOverlappingPatientScheduledExamination() {
		Mockito.when(pharmacyRepositoryMock.findById(2L)).thenReturn(Optional.of(mockPharmacy));
		Mockito.when(consultantRepositoryMock.findLockedById(1L)).thenReturn(Optional.of(mockDermatologist));

		Boolean result = consultationServiceMock.isPhatientFreeForConsultation(5L,WT_OVERLAPPING_START ,WT_OVERLAPPING_END);

		Mockito.verify(workingTimesRepositoryMock, Mockito.never()).save(Mockito.any());

		assertEquals(false, result);
	}



}

package com.pharmacy.cpis.student2.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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

import com.pharmacy.cpis.pharmacyservice.model.pharmacy.Pharmacy;
import com.pharmacy.cpis.pharmacyservice.repository.IPharmacyRepository;
import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.scheduleservice.model.workschedule.WorkingTimes;
import com.pharmacy.cpis.scheduleservice.repository.IConsultationRepository;
import com.pharmacy.cpis.scheduleservice.repository.IWorkingTimesRepository;
import com.pharmacy.cpis.student2.constants.WorkingTimesConstants;
import com.pharmacy.cpis.userservice.dto.AddWorkingDayDTO;
import com.pharmacy.cpis.userservice.dto.AddWorkingTimeDTO;
import com.pharmacy.cpis.userservice.dto.EmployDermatologistDTO;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.ConsultantType;
import com.pharmacy.cpis.userservice.model.users.UserAccount;
import com.pharmacy.cpis.userservice.repository.IConsultantRepository;
import com.pharmacy.cpis.userservice.repository.IUserRepository;
import com.pharmacy.cpis.userservice.service.IConsultantService;
import com.pharmacy.cpis.userservice.service.impl.PharmacyEmployeeService;
import com.pharmacy.cpis.util.DateRange;
import com.pharmacy.cpis.util.exceptions.PSConflictException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PharmacyEmployeeServiceTests {

	@Mock
	private IWorkingTimesRepository workingTimesRepositoryMock;

	@Mock
	private IUserRepository userRepositoryMock;

	@Mock
	private IConsultationRepository consultationRepositoryMock;

	@Mock
	private IConsultantRepository consultantRepositoryMock;

	@Mock
	private IConsultantService consultantServiceMock;

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
	}

	@Test(expected = PSConflictException.class)
	@Transactional
	@Rollback(true)
	public void employDermatologistFailsWhenOverlappingWorkingTimes() {
		Mockito.when(pharmacyRepositoryMock.findById(2L)).thenReturn(Optional.of(mockPharmacy));
		Mockito.when(consultantRepositoryMock.findLockedById(1L)).thenReturn(Optional.of(mockDermatologist));

		EmployDermatologistDTO details = new EmployDermatologistDTO();
		details.setDermatologistId(1L);
		details.setWorkingTimes(new AddWorkingTimeDTO());
		details.getWorkingTimes().setMonday(new AddWorkingDayDTO(WorkingTimesConstants.WT_OVERLAPPING_START,
				WorkingTimesConstants.WT_OVERLAPPING_END));

		pharmacyEmployeService.employDermatologist(2L, details);

		Mockito.verify(workingTimesRepositoryMock, Mockito.never()).save(Mockito.any());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void employDermatologist() {
		Mockito.when(pharmacyRepositoryMock.findById(2L)).thenReturn(Optional.of(mockPharmacy));
		Mockito.when(consultantRepositoryMock.findLockedById(1L)).thenReturn(Optional.of(mockDermatologist));
		Mockito.when(workingTimesRepositoryMock.save(Mockito.any())).thenAnswer(new Answer<WorkingTimes>() {
			@Override
			public WorkingTimes answer(InvocationOnMock invocation) throws Throwable {
				return (WorkingTimes) invocation.getArgument(0);
			}
		});

		EmployDermatologistDTO details = new EmployDermatologistDTO();
		details.setDermatologistId(1L);
		details.setWorkingTimes(new AddWorkingTimeDTO());
		details.getWorkingTimes().setMonday(new AddWorkingDayDTO(WorkingTimesConstants.WT_NOT_OVERLAPPING_START,
				WorkingTimesConstants.WT_NOT_OVERLAPPING_END));

		WorkingTimes retVal = pharmacyEmployeService.employDermatologist(2L, details);

		Mockito.verify(workingTimesRepositoryMock, Mockito.times(1)).save(Mockito.any());
		Assertions.assertThat(retVal).isNotNull();
		Assertions.assertThat(retVal.getConsultant()).isEqualTo(mockDermatologist);
		Assertions.assertThat(retVal.getPharmacy()).isEqualTo(mockPharmacy);
		Assertions.assertThat(retVal.getMonday()).isNotNull();
		Assertions.assertThat(retVal.getTuesday()).isNull();
		Assertions.assertThat(retVal.getWednesday()).isNull();
		Assertions.assertThat(retVal.getThursday()).isNull();
		Assertions.assertThat(retVal.getFriday()).isNull();
		Assertions.assertThat(retVal.getSaturday()).isNull();
		Assertions.assertThat(retVal.getSunday()).isNull();
	}

	@Test(expected = PSConflictException.class)
	@Transactional
	@Rollback(true)
	public void fireConsultantFailsWhenConsultantHasScheduledConsultations() {
		Mockito.when(workingTimesRepositoryMock.findByPharmacyIdAndConsultantId(1L, 1L))
				.thenReturn(Optional.of(mockDermatologistWorkingTimes));
		Mockito.when(consultationRepositoryMock.findAllByPharmacyIdAndConsultantAndStatus(1L, mockDermatologist,
				ConsultationStatus.SCHEDULED)).thenReturn(mockConsultations);
		Mockito.when(consultationRepositoryMock.findAllByPharmacyIdAndConsultantAndStatus(1L, mockDermatologist,
				ConsultationStatus.PREDEFINED)).thenReturn(mockConsultations);

		pharmacyEmployeService.fireConsultant(1L, 1L);

		Mockito.verify(workingTimesRepositoryMock, Mockito.never()).delete(Mockito.any());
		Mockito.verify(userRepositoryMock, Mockito.never()).delete(Mockito.any());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void fireConsultant() {
		Mockito.when(workingTimesRepositoryMock.findByPharmacyIdAndConsultantId(2L, 2L))
				.thenReturn(Optional.of(mockPharmacistWorkingTimes));
		Mockito.when(consultationRepositoryMock.findAllByPharmacyIdAndConsultantAndStatus(1L, mockDermatologist,
				ConsultationStatus.SCHEDULED)).thenReturn(new ArrayList<>());
		Mockito.when(consultationRepositoryMock.findAllByPharmacyIdAndConsultantAndStatus(1L, mockDermatologist,
				ConsultationStatus.PREDEFINED)).thenReturn(new ArrayList<>());

		pharmacyEmployeService.fireConsultant(2L, 2L);

		Mockito.verify(workingTimesRepositoryMock, Mockito.times(1)).delete(mockPharmacistWorkingTimes);
		Mockito.verify(userRepositoryMock, Mockito.never()).delete(mockPharmacist.getAccount());
	}

}

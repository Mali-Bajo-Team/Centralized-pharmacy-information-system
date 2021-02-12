package com.pharmacy.cpis.scheduleservice.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pharmacy.cpis.scheduleservice.model.consultations.Consultation;
import com.pharmacy.cpis.scheduleservice.model.consultations.ConsultationStatus;
import com.pharmacy.cpis.userservice.model.users.Consultant;
import com.pharmacy.cpis.userservice.model.users.Patient;

public interface IConsultationRepository extends JpaRepository<Consultation, Long> {

	Collection<Consultation> findAllByConsultantAndStatus(Consultant consultant, ConsultationStatus status);

	Set<Consultation> findAllByPatient(Patient patient);

	Set<Consultation> findAllByPatientAndStatus(Patient patient, ConsultationStatus status);

	Collection<Consultation> findAllByPharmacyIdAndStatus(Long pharmacyId, ConsultationStatus status);

	Collection<Consultation> findAllByPharmacyIdAndConsultantAndStatus(Long pharmacyId, Consultant consultant,
			ConsultationStatus status);

	@Query("select sum(c.price) from Consultation c where c.pharmacy.id = :id and " + "c.status = 3 and "
			+ "date_part('year',c.time.start) = :year and " + "date_part('month',c.time.start) = :month and "
			+ "date_part('day',c.time.start) = :day")
	Double getProfitForDate(Long id, int year, int month, int day);

	@Query("select count(*) from Consultation c where c.pharmacy.id = :id and " + "c.status = 3 and "
			+ "date_part('month',c.time.start) = :month and " + "date_part('year',c.time.start) = :year")
	Integer getConsultationsCount(Long id, int month, int year);

}

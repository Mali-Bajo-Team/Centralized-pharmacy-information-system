package com.pharmacy.cpis.scheduleservice.model.consultations;

import com.pharmacy.cpis.scheduleservice.model.prescriptions.DrugRecommendation;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ConsultationReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String diagnosis;

	@Column(length = 800, nullable = false)
	private String anamnesis;

	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "consultation_id", referencedColumnName = "id")
	private Consultation consultation;

	@OneToMany(mappedBy = "consultationReport", fetch = FetchType.LAZY)
	private Set<DrugRecommendation> drugRecommendations;

	public ConsultationReport() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getAnamnesis() {
		return anamnesis;
	}

	public void setAnamnesis(String anamnesis) {
		this.anamnesis = anamnesis;
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public Set<DrugRecommendation> getDrugRecommendations() {
		return drugRecommendations;
	}

	public void setDrugRecommendations(Set<DrugRecommendation> drugRecommendations) {
		this.drugRecommendations = drugRecommendations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultationReport other = (ConsultationReport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

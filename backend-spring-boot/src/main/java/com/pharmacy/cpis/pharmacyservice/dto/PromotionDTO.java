package com.pharmacy.cpis.pharmacyservice.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pharmacy.cpis.pharmacyservice.model.promotions.Promotion;

public class PromotionDTO {
	@NotEmpty(message = "Title is required.")
	private String title;
	@NotEmpty(message = "Content is required.")
	private String content;
	@NotNull(message = "Validity start date is required.")
	@Future(message = "Validity start date must be in the future.")
	private Date validityStart;
	@NotNull(message = "Validity end date is required.")
	@Future(message = "Validity end date must be in the future.")
	private Date validityEnd;

	public PromotionDTO() {
		super();
	}

	public PromotionDTO(Promotion promotion) {
		super();
		this.title = promotion.getTitle();
		this.content = promotion.getContent();
		this.validityStart = promotion.getValidityPeriod().getStart();
		this.validityEnd = promotion.getValidityPeriod().getEnd();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getValidityStart() {
		return validityStart;
	}

	public void setValidityStart(Date validityStart) {
		this.validityStart = validityStart;
	}

	public Date getValidityEnd() {
		return validityEnd;
	}

	public void setValidityEnd(Date validityEnd) {
		this.validityEnd = validityEnd;
	}

}

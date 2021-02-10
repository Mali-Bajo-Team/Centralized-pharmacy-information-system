package com.pharmacy.cpis.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DateRange {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "start_date")
	private Date start;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "end_date")
	private Date end;

	public DateRange(Date start, Date end) {
		super();
		this.start = start;
		this.end = end;
	}

	public DateRange() {
		super();
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}

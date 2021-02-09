package com.pharmacy.cpis.userservice.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.drugservice.model.drugprocurement.Offer;
import com.pharmacy.cpis.pharmacyservice.model.promotions.Promotion;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.scheduleservice.model.workschedule.VacationRequest;
import com.pharmacy.cpis.userservice.dto.UserActivationDTO;

@Service
public class EmailService {
//TODO: Make IEmailService

	@Autowired
	private JavaMailSender javaMailSender;

	// Use a class to read values from the application.properties file
	@Autowired
	private Environment env;

	/*
	 * Annotation for marking an asynchronous task More info on:
	 * https://docs.spring.io/spring/docs/current/spring-framework-reference/
	 * integration.html#scheduling
	 */
	@Async
	public void sendActivationEmailAsync(UserActivationDTO user) throws MailException, InterruptedException {
		System.out.println("Email sending...\n\n");

		String endPointForAccountActivation = "/auth/" + "activate/" + user.getId();

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Activate account [CPis]");
		mail.setText("Hello " + user.getName() + "," + " activate your account on this link: "
				+ env.getProperty("APP_HOST") + env.getProperty("server.port") + endPointForAccountActivation);
		javaMailSender.send(mail);

		System.out.println("Email was send!");
	}

	@Async
	public void sendConfirmConsultationEmailAsync(String userName, String phatientEmail,
			ScheduleExaminationDTO scheduleExaminationDTO) throws MailException, InterruptedException {
		System.out.println("Email sending...\n\n");

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(phatientEmail);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Confirmation consultation [CPis]");
		mail.setText("Hello " + userName + "," + " your consultation is succesfully scheduled. Consultation start at "
				+ scheduleExaminationDTO.getStartDate() + " and end at " + scheduleExaminationDTO.getEndDate() + "."
				+ " For more information contact your Consultant at mail: "
				+ scheduleExaminationDTO.getConsultantEmail());
		javaMailSender.send(mail);

		System.out.println("Email was send!");
	}

	@Async
	public void sendPromotionEmailAsync(String email, Promotion promotion) {
		System.out.println("Email sending...\n\n");

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.YYYY");
		LocalDate start = LocalDate.ofInstant(Instant.ofEpochMilli(promotion.getValidityPeriod().getStart().getTime()),
				ZoneId.systemDefault());
		LocalDate end = LocalDate.ofInstant(Instant.ofEpochMilli(promotion.getValidityPeriod().getEnd().getTime()),
				ZoneId.systemDefault());

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("New promotion from " + promotion.getPharmacy().getName() + " [CPis]");
		StringBuilder message = new StringBuilder();
		message.append(promotion.getTitle());
		message.append("\n\n");
		message.append(promotion.getContent());
		message.append("\n\nValid from ");
		message.append(format.format(start));
		message.append(" to ");
		message.append(format.format(end));
		message.append(".");
		mail.setText(message.toString());
		javaMailSender.send(mail);

		System.out.println("Email was sent!");
	}

	@Async
	public void sendOfferUpdateEmailAsync(String email, Offer offer) {
		System.out.println("Email sending...\n\n");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Offer reviewed [CPis]");
		mail.setText("Your offer #" + offer.getId() + " for order #" + offer.getOrder().getId() + " has been "
				+ offer.getStatus() + ".");
		javaMailSender.send(mail);

		System.out.println("Email was sent!");
	}

	@Async
	public void sendVacationRejectedEmailAsync(String email, VacationRequest request) {
		System.out.println("Email sending...\n\n");

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.YYYY");
		LocalDate start = LocalDate.ofInstant(Instant.ofEpochMilli(request.getDateRange().getStart().getTime()),
				ZoneId.systemDefault());
		LocalDate end = LocalDate.ofInstant(Instant.ofEpochMilli(request.getDateRange().getEnd().getTime()),
				ZoneId.systemDefault());

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vacation request rejected [CPis]");
		StringBuilder message = new StringBuilder();
		message.append("Your request for vacation from ");
		message.append(format.format(start));
		message.append(" to ");
		message.append(format.format(end));
		message.append(" has been rejected.\n");
		message.append("Reason of rejection: ");
		message.append(request.getResponse());
		mail.setText(message.toString());
		javaMailSender.send(mail);

		System.out.println("Email was sent!");
	}

	@Async
	public void sendVacationApprovedEmailAsync(String email, VacationRequest request) {
		System.out.println("Email sending...\n\n");

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.YYYY");
		LocalDate start = LocalDate.ofInstant(Instant.ofEpochMilli(request.getDateRange().getStart().getTime()),
				ZoneId.systemDefault());
		LocalDate end = LocalDate.ofInstant(Instant.ofEpochMilli(request.getDateRange().getEnd().getTime()),
				ZoneId.systemDefault());

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Vacation request approved [CPis]");
		StringBuilder message = new StringBuilder();
		message.append("Your request for vacation from ");
		message.append(format.format(start));
		message.append(" to ");
		message.append(format.format(end));
		message.append(" has been approved.\n");
		message.append("Message from administrator: ");
		message.append(request.getResponse());
		mail.setText(message.toString());
		javaMailSender.send(mail);

		System.out.println("Email was sent!");
	}

}

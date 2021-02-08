package com.pharmacy.cpis.userservice.service;

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

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("New promotion from " + promotion.getPharmacy().getName() + " [CPis]");
		mail.setText(promotion.getTitle() + "\n\n" + promotion.getContent());
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

}

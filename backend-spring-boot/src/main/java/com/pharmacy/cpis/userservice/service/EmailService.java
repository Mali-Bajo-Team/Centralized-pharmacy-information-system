package com.pharmacy.cpis.userservice.service;

import com.pharmacy.cpis.drugservice.dto.DrugReservationDTO;
import com.pharmacy.cpis.drugservice.model.drugsales.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pharmacy.cpis.pharmacyservice.model.promotions.Promotion;
import com.pharmacy.cpis.scheduleservice.dto.ScheduleExaminationDTO;
import com.pharmacy.cpis.userservice.dto.UserActivationDTO;
import com.pharmacy.cpis.userservice.model.users.Patient;

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
		mail.setText("Hello " + userName + "," + " your consultation is succesfully scheduled. Consultation starts at "
				+ scheduleExaminationDTO.getStartDate() + " and ends at " + scheduleExaminationDTO.getEndDate() + "."
				+ " For more information contact your Consultant at mail: "
				+ scheduleExaminationDTO.getConsultantEmail());
		javaMailSender.send(mail);

		System.out.println("Email was sent!");
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
	public void sendConfirmReservationOfDrugEmailAsync(String patientEmail,
													   DrugReservationDTO drugReservationDTO, Reservation reservation) throws MailException, InterruptedException {
		System.out.println("Email sending...\n\n");

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(patientEmail);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Confirmation reservation of drug");
		mail.setText("Hello " + patientEmail + "," + " your reservation of drug "+reservation.getDrug().getName()+" is succesfully scheduled in "+reservation.getPharmacy().getName()+"."+
				"Deadline to pick up the drug is "+drugReservationDTO.getDeadline()+".The amount of drug you made reservation for is "+
				drugReservationDTO.getAmount()+
				" mg.Id of your reservation is "+reservation.getId());
		javaMailSender.send(mail);

		System.out.println("Email was sent!");
	}


}

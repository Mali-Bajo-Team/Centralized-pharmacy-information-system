package com.pharmacy.cpis.service;

import com.pharmacy.cpis.dto.UserActivationDTO;
import com.pharmacy.cpis.dto.UserRegisterDTO;
import com.pharmacy.cpis.model.UserAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // Use a class to read values from the application.properties file
    @Autowired
    private Environment env;

    /*
     * Annotation for marking an asynchronous task
     * More info on: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
     */
    @Async
    public void sendNotificaitionAsync(UserActivationDTO user) throws MailException, InterruptedException {
        System.out.println("Email sending...\n\n");

        String endPointForAccountActivation = "/api/users/" + "activate/" + user.getId();

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Activate account [CPis]");
        mail.setText(
                "Hello " + user.getName() + "," + " activate your account on this link: "
                        + env.getProperty("APP_HOST") + env.getProperty("server.port") + endPointForAccountActivation
        );
        javaMailSender.send(mail);

        System.out.println("Email was send!");
    }

}

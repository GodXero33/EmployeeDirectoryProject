package dev.icet.edp.service.custom.impl;

import dev.icet.edp.dto.Employee;
import dev.icet.edp.service.custom.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	private final JavaMailSender mailSender;
	private final Logger logger;
	@Value("${spring.from}")
	private String fromEmail;

	private int sendEmail (String to, String subject, String text) {
		new Thread(() -> {
			try {
				final MimeMessage message = mailSender.createMimeMessage();
				final MimeMessageHelper helper = new MimeMessageHelper(message, true);

				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(text, true);
				helper.setFrom(fromEmail);

				mailSender.send(message);
				logger.info("Email sent successfully to {}", to);
			} catch (MessagingException | MailException mailException) {
				logger.info("Failed to send email to {}: {}", to, mailException.getMessage());
			}
		}).start();

		return 200;
	}

	@Override
	public int sendEmployeeAddMail (Employee employee) {
		return this.sendEmail(
			employee.getEmail(),
			"Welcome to the Team!",
			"""
			<html>
			    <head>
			        <style>
			        body {
			                font-family: Arial, sans-serif;
			                background-color: #f4f6f8;
			                color: #333;
			                padding: 20px;
			            }
			            .container {
			                background-color: #fff;
			                padding: 20px;
			                border-radius: 10px;
			                max-width: 600px;
			                margin: auto;
			                box-shadow: 0 2px 10px rgba(0,0,0,0.05);
			            }
			            h2 {
			                color: #1d72b8;
			            }
			            .info {
			               margin-top: 20px;
			                line-height: 1.6;
			            }
			            .label {
			                font-weight: bold;
			            }
			        </style>
			    </head>
			    <body>
			        <div class="container">
			            <h2>Welcome aboard, %s!</h2>
			            <p>We're excited to have you join our team. Here's a quick overview of your details:</p>
			            <div class="info">
			                <p><span class="label">Employee ID:</span> %d</p>
			                <p><span class="label">Email:</span> %s</p>
			                <p><span class="label">Department:</span> %s</p>
			            </div>
			            <p>Let's do great things together!</p>
			            <p style="margin-top: 30px;">Best regards,<br/>The Team</p>
			        </div>
			    </body>
			</html>
			""".formatted(employee.getName(), employee.getId(), employee.getEmail(), employee.getDepartment()));
	}
}

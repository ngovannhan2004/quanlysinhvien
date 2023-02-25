package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class MailUtil {
	String email = "buivannhan20032004@gmail.com";
	String password = "ylidraoxzasfzwgb";
	String smtpHost = "smtp.gmail.com";
	String smtpPort = "587";
	Session session;

	public MailUtil() {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(email, password);
			}
		});
//		session.setDebug(true);
	}

	public void sendMail(String to, String subject, String body) throws MessagingException {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(email));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setContent(body, "text/html; charset=utf-8");

		Transport.send(msg);
	}

	public void sendNewPasswordFromHtml(String to, String name, String code, String loginUrl) throws Exception {
		String subject = "Your new password";
		String htmlBody = getTemplateMail("./forgetPassword.html"); // replace with the actual path to your HTML file
		htmlBody = htmlBody.replace("{name}", name);
		htmlBody = htmlBody.replace("{code}", code);
		htmlBody = htmlBody.replace("{loginUrl}", loginUrl);

		sendMail(to, subject, htmlBody);
	}

	public String getTemplateMail(String fileName) throws IOException {
		InputStream inputStream = MailUtil.class.getClassLoader().getResourceAsStream("templateMail/" + fileName);
		if (inputStream != null) {
			try (Scanner scanner = new Scanner(inputStream)) {
				String content = scanner.useDelimiter("\\A").next();
				return content;
			}
		} else {
			throw new FileNotFoundException("File not found: " + fileName);
		}
	}

}

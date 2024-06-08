package com.supportportal.service;

import com.sun.mail.smtp.SMTPTransport;
import com.supportportal.domain.GazStation;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static com.supportportal.constant.EmailConstant.*;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

@Service
public class EmailService {

    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    private Message createEmail(String firstName, String password, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hello " + firstName + ", \n \n Your new account password is: " + password + "\n \n The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }

    public void createAssistanceRequestEmail(Map<String, Object> requestDetails) throws MessagingException {
        String email = (String) requestDetails.get("email");
        String carNumber = (String) requestDetails.get("carNumber");
        String carColor = (String) requestDetails.get("carColor");
        String fuelType = (String) requestDetails.get("fuelType");
        String phoneNumber = (String) requestDetails.get("phoneNumber");
        String appointmentDate = (String) requestDetails.get("appointmentDate");
        String appointmentTime = (String) requestDetails.get("appointmentTime");
        boolean arrivalConfirmation = (Boolean) requestDetails.get("arrivalConfirmation");

        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
        message.setSubject("Cerere Asistență Benzinărie");

        String emailContent = String.format(
            "Bună ziua!\n\nS-a efectuat o programare de asistență pentru o persoană cu dizabilități.\n\nDetalii programare:\n" +
                "- Număr mașină: %s\n" +
                "- Culoare mașină: %s\n" +
                "- Tip combustibil: %s\n" +
                "- Număr de telefon: %s\n" +
                "- Data programării: %s\n" +
                "- Ora programării: %s\n" +
                "- Sosesc în aproximativ 30 de minute +/-: %s\n\n" +
                "Cu respect,\nEchipa HelpXpress",
            carNumber, carColor, fuelType, phoneNumber, appointmentDate, appointmentTime,
            arrivalConfirmation ? "Da" : "Nu"
        );

        message.setText(emailContent);
        message.setSentDate(new Date());
        message.saveChanges();

        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    public void createContactEmail(Map<String, Object> contactDetails) throws MessagingException {
        String name = (String) contactDetails.get("name");
        String email = (String) contactDetails.get("email");
        String messageContent = (String) contactDetails.get("message");

        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("HelpXpress1@gmail.com", false));
        message.setSubject("Mesaj de la Contact HelpXpress");

        String emailContent = String.format(
            "Bună ziua!\n\nAți primit un nou mesaj de la utilizatorul %s.\n\nDetalii mesaj:\n" +
                "- Email: %s\n" +
                "- Mesaj: %s\n\n" +
                "Cu respect,\nEchipa HelpXpress",
            name, email, messageContent
        );

        message.setText(emailContent);
        message.setSentDate(new Date());
        message.saveChanges();

        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    public void createReviewEmail(Map<String, Object> reviewDetails) throws MessagingException {
        String comment = (String) reviewDetails.get("comment");
        String rating = (String) reviewDetails.get("rating");
        String doctor = (String) reviewDetails.get("doctor");
        String organization = (String) reviewDetails.get("organization");

        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("HelpXpress1@gmail.com", false));
        message.setSubject("Nouă Recenzie de la Utilizator");

        String emailContent = String.format(
            "Bună ziua!\n\nAți primit o nouă recenzie.\n\nDetalii recenzie:\n" +
                "- Comentariu: %s\n" +
                "- Rating: %s\n" +
                "- Doctor: %s\n" +
                "- Organizație: %s\n\n" +
                "Cu respect,\nEchipa HelpXpress",
            comment, rating, doctor, organization
        );

        message.setText(emailContent);
        message.setSentDate(new Date());
        message.saveChanges();

        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
}

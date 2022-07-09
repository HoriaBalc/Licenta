package io.getarrays.userservice.service;

import io.getarrays.userservice.domain.AppUser;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    //sportvent19@gmail.com

    private static final String USER_NAME = "sportvent19";
    private static final String PASSWORD = "kztincqopigubmbl";
    private static final String FROM = "sportvent19@gmail.com";

    public static void sendRegisterEmail(AppUser user) {
        String text = "User successfully registered to Sportvent" +
                "\nUsername: " + user.getUsername() + "\n Your verification code: " + user.getVerificationCode();

        String subject = "Registration to Sportvent";
        String email = user.getEmail();
        sendEmail(email, subject, text);
    }
    public static void sendPasswordResetEmail(AppUser user) {
        String text = "For changing the password you need your verification code: " + user.getVerificationCode();
        String subject = "Reset password";
        String email = user.getEmail();
        sendEmail(email, subject, text);
    }


    private static void sendEmail(String email, String subject, String text) {
        Properties props = System.getProperties();

        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(FROM));
            InternetAddress[] toAddress = new InternetAddress[1];
            toAddress[0] = new InternetAddress(email);
            for (InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            message.setSubject(subject);
            message.setText(text);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, FROM, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException me) {
            me.printStackTrace();
        }

    }
}

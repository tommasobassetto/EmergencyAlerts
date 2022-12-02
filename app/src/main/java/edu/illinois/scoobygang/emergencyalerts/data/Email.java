package edu.illinois.scoobygang.emergencyalerts.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;


public class Email extends javax.mail.Authenticator implements ContactPlatform, Runnable {

    private final String platformName;
    private final String username;
    private final String password;

    public Email() {
        platformName = "email";
        username = "emergency.alerts.notify@gmail.com";
        password = "scoobygang123";
    }

    @Override
    public void run() {

    }

    @Override
    public String getPlatformName() { return platformName; }

    @Override
    public void onSend(Login info, String message) {
        int x = 1;  // not sure what the original intent of this method was...
    }

    @Override
    public void send(Context context, ArrayList<Contact> contacts, String msg) {
        String subject = "Emergency Alerts Notification";
        InternetAddress[] recipients = new InternetAddress[contacts.size()];
        for (int i = 0; i < contacts.size(); ++i) {
            try {
                recipients[i] = new InternetAddress(contacts.get(i).getEmailAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, recipients);
            message.setSubject(subject);
            message.setText(msg);

            EmailThread helper = new EmailThread(message);
            new Thread(helper).start();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

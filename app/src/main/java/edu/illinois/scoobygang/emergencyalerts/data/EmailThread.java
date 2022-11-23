package edu.illinois.scoobygang.emergencyalerts.data;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;


public class EmailThread implements Runnable {

    private final Message message;

    public EmailThread(Message message) { this.message = message; }

    @Override
    public void run() {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

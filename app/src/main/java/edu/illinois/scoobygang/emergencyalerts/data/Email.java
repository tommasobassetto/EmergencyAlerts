package edu.illinois.scoobygang.emergencyalerts.data;

import android.content.Intent;

import java.util.ArrayList;

public class Email implements ContactPlatform {

    private final String platformName;

    public Email(String platformName) { this.platformName = platformName; }

    @Override
    public String getPlatformName() { return this.platformName; }

    @Override
    public void onSend(Login info, String message) {
        int x = 1;  // not sure what the original intent of this method was...
    }

    @Override
    public void send(ArrayList<Contact> contacts, String message) {
        String subject = "Emergency Alerts Notification";
        ArrayList<String> emailAddresses = null;
        for (Contact contact : contacts) {
            try {
                emailAddresses.add(contact.getEmailAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Contact contact : contacts) {
            try {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

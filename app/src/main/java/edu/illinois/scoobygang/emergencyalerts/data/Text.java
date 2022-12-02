package edu.illinois.scoobygang.emergencyalerts.data;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class Text implements ContactPlatform {

    private final String platformName;

    public Text(String platformName) { this.platformName = platformName; }

    @Override
    public String getPlatformName() { return this.platformName; }

    @Override
    public void onSend(Login info, String message) {
        int x = 1;  // not sure what the original intent of this method was...
    }

    @Override
    public void send(Context context, ArrayList<Contact> contacts, String message) {
        SmsManager sms = SmsManager.getDefault();
        for (Contact contact : contacts) {
            try {
                sms.sendTextMessage(contact.getPhoneNumber(), null, message, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

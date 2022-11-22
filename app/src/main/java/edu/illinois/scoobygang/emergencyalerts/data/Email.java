package edu.illinois.scoobygang.emergencyalerts.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

public class Email implements ContactPlatform {

    private final String platformName;
    private Context context;

    public Email(Context context) {
        this.platformName = "email";
        this.context = context;
    }

    @Override
    public String getPlatformName() { return this.platformName; }

    @Override
    public void onSend(Login info, String message) {
        int x = 1;  // not sure what the original intent of this method was...
    }

    @Override
    public void send(ArrayList<Contact> contacts, String msg) {
        String subject = "Emergency Alerts Notification";
        String[] emails = new String[contacts.size()];
        for (int i = 0; i < contacts.size(); ++i) {
            try {
                emails[i] = contacts.get(i).getEmailAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.putExtra(Intent.EXTRA_EMAIL, emails);
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, msg);
            i.setData(Uri.parse("mailto:"));

            if (i.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

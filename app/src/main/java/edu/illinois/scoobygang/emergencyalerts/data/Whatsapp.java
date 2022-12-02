package edu.illinois.scoobygang.emergencyalerts.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;

import edu.illinois.scoobygang.emergencyalerts.WhatsAppService;

public class Whatsapp implements ContactPlatform {

    private String platformName;

    public Whatsapp() {
        platformName = "whatsapp";
    }

    @Override
    public String getPlatformName() {
        return platformName;
    }

    @Override
    public void onSend(Login info, String message) {
        return;
    }

    @Override
    public void send(Context context, ArrayList<Contact> contacts, String message) {
        WhatsAppService autoMsgService = WhatsAppService.getInstance();
        if (autoMsgService == null)
            Toast.makeText(context, "Service is not running", Toast.LENGTH_LONG).show();
        else {
            autoMsgService.sActive = true;
            autoMsgService.sContact =contacts.get(0).getName();
            autoMsgService.sMsg = message;

            Uri uri = Uri.parse("smsto:" + contacts.get(0).getPhoneNumber() + "@s.whatsapp.net");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            context.startActivity(i);
        }
    }

}

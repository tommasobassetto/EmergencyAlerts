package edu.illinois.scoobygang.emergencyalerts.data;

import java.util.Map;

public interface ContactPlatform {
    // To delete, simply look at the Contact and call delete from there
    void onCreate(Map<String, String> params); // Create or load a contact
    Map<String, String> getDrawableInfo();

    String getPlatformName(); // get platform name to send in login info
    void onSend(Login info, String message);
}
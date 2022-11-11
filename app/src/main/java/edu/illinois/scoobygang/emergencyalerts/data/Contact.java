package edu.illinois.scoobygang.emergencyalerts.data;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private List<ContactPlatform> contactPlatformList;
    private ContactPlatform defaultPlatform;

    private String name, phoneNumber, emailAddress;

    public Contact() {
        contactPlatformList = new ArrayList<>();
        name = phoneNumber = emailAddress = "";
    }

    public void addPlatform(ContactPlatform platform) {
        contactPlatformList.add(platform);
    }

    public void removePlatform(ContactPlatform platform) {
        contactPlatformList.remove(platform);
    }

    public List<ContactPlatform> getPlatforms() {
        return contactPlatformList;
    }

    public ContactPlatform getDefaultPlatform() {
        return defaultPlatform;
    }

    public void setDefaultPlatform(ContactPlatform platform) {
        defaultPlatform = platform;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmailAddress (String emailAddress) { this.emailAddress = emailAddress; }

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmailAddress() { return emailAddress; }
}
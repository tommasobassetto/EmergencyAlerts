package edu.illinois.scoobygang.emergencyalerts.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable {
    private List<ContactPlatform> contactPlatformList;
    private String name, defaultPlatform, phoneNumber, emailAddress, contactID;

    public Contact() {
        this.contactPlatformList = new ArrayList<>();
        this.name = this.phoneNumber = this.emailAddress = "";
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


    public void setName(String name) {
        this.name = name;
    }
    public void setContactID(String contactID) { this.contactID = contactID; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmailAddress (String emailAddress) { this.emailAddress = emailAddress; }
    public void setDefaultPlatform(String platform) {
        defaultPlatform = platform;
    }

    public String getName() { return this.name; }
    public String getContactID() { return this.contactID; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getEmailAddress() { return this.emailAddress; }
    public String getDefaultPlatform() {
        return defaultPlatform;
    }
}
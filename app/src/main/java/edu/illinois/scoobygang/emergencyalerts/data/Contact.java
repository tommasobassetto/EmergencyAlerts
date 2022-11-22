package edu.illinois.scoobygang.emergencyalerts.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable {
    private List<ContactPlatform> contactPlatformList;
    private ContactPlatform defaultPlatform;

    private String name, phoneNumber, emailAddress, contactID;

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

    public ContactPlatform getDefaultPlatform() {
        return defaultPlatform;
    }

    public void setDefaultPlatform(ContactPlatform platform) {
        defaultPlatform = platform;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setContactID(String contactID) { this.contactID = contactID; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmailAddress (String emailAddress) { this.emailAddress = emailAddress; }

    public String getName() { return this.name; }
    public String getContactID() { return this.contactID; }
    public String getPhoneNumber() { return this.phoneNumber; }
    public String getEmailAddress() { return this.emailAddress; }
}
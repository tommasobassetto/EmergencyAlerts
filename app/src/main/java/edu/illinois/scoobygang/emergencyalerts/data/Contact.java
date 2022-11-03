package edu.illinois.scoobygang.emergencyalerts.data;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private List<ContactPlatform> contactPlatformList;
    private ContactPlatform defaultPlatform;

    private String name;

    public Contact() {
        contactPlatformList = new ArrayList<>();
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

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }
}
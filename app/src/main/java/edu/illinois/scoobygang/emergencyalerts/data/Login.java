package edu.illinois.scoobygang.emergencyalerts.data;

import java.util.Map;

interface Login {
    void setLoginDetails(Map<String, String> details);
    Map<String, String> getSendInfo();
}
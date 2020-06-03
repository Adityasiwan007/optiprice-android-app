package com.ril.digitalwardrobeAI.Model;

import java.util.ArrayList;

public class VersionResponse {

    boolean success;
    ArrayList<ApplicationVersion> applications;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<ApplicationVersion> getApplications() {
        return applications;
    }

    public void setApplications(ArrayList<ApplicationVersion> applications) {
        this.applications = applications;
    }
}

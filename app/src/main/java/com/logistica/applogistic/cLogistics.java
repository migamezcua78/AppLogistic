package com.logistica.applogistic;

public class cLogistics {

    public cLogistics(){

        SiteID = "";
        LifeCycleStatusCode = "";
        CycleCountPlannedDuration = "";
    }

    public String SiteID;
    public String LifeCycleStatusCode;
    public String CycleCountPlannedDuration;

    public String getSiteID() {
        return SiteID;
    }

    public void setSiteID(String siteID) {
        SiteID = siteID;
    }

    public String getLifeCycleStatusCode() {
        return LifeCycleStatusCode;
    }

    public void setLifeCycleStatusCode(String lifeCycleStatusCode) {
        LifeCycleStatusCode = lifeCycleStatusCode;
    }

    public String getCycleCountPlannedDuration() {
        return CycleCountPlannedDuration;
    }

    public void setCycleCountPlannedDuration(String cycleCountPlannedDuration) {
        CycleCountPlannedDuration = cycleCountPlannedDuration;
    }
}

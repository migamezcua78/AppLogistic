package com.logistica.applogistic;

public class cAvailabilityConfirmation {

    public cAvailabilityConfirmation()
    {
        PlanningAreaID = "";
        LifeCycleStatusCode = "";
        AvailabilityCheckScopeCode = "";
        GoodsIssueProcessingDuration = "";
    }

    public String PlanningAreaID;
    public String LifeCycleStatusCode;
    public String AvailabilityCheckScopeCode;
    public String GoodsIssueProcessingDuration;

    public String getPlanningAreaID() {
        return PlanningAreaID;
    }

    public String getLifeCycleStatusCode() {
        return LifeCycleStatusCode;
    }

    public String getAvailabilityCheckScopeCode() {
        return AvailabilityCheckScopeCode;
    }

    public String getGoodsIssueProcessingDuration() {
        return GoodsIssueProcessingDuration;
    }

    public void setPlanningAreaID(String planningAreaID) {
        PlanningAreaID = planningAreaID;
    }

    public void setLifeCycleStatusCode(String lifeCycleStatusCode) {
        LifeCycleStatusCode = lifeCycleStatusCode;
    }

    public void setAvailabilityCheckScopeCode(String availabilityCheckScopeCode) {
        AvailabilityCheckScopeCode = availabilityCheckScopeCode;
    }

    public void setGoodsIssueProcessingDuration(String goodsIssueProcessingDuration) {
        GoodsIssueProcessingDuration = goodsIssueProcessingDuration;
    }
}

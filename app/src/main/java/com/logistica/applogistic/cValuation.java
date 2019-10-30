package com.logistica.applogistic;

public class cValuation {

    public cValuation(){

        setLifeCycleStatusCode("");
        setCompanyID("");
        setBusinessResidenceID("");
    }

    public String LifeCycleStatusCode;
    public String CompanyID;
    public String BusinessResidenceID;

    public String getLifeCycleStatusCode() {
        return LifeCycleStatusCode;
    }

    public void setLifeCycleStatusCode(String lifeCycleStatusCode) {
        LifeCycleStatusCode = lifeCycleStatusCode;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getBusinessResidenceID() {
        return BusinessResidenceID;
    }

    public void setBusinessResidenceID(String businessResidenceID) {
        BusinessResidenceID = businessResidenceID;
    }
}

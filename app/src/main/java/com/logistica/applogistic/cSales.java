package com.logistica.applogistic;

public class cSales {

    public cSales(){
        SalesOrganisationID = "";
        DistributionChannelCode = "";
        LifeCycleStatusCode = "";
        SalesMeasureUnitCode = "";
        ItemGroupCode = "";

    }
    public String  SalesOrganisationID;
    public String  DistributionChannelCode;
    public String  LifeCycleStatusCode;
    public String  SalesMeasureUnitCode;
    public String  ItemGroupCode;

    public String getSalesOrganisationID() {
        return SalesOrganisationID;
    }

    public String getDistributionChannelCode() {
        return DistributionChannelCode;
    }

    public String getLifeCycleStatusCode() {
        return LifeCycleStatusCode;
    }

    public String getSalesMeasureUnitCode() {
        return SalesMeasureUnitCode;
    }

    public String getItemGroupCode() {
        return ItemGroupCode;
    }

    public void setSalesOrganisationID(String salesOrganisationID) {
        SalesOrganisationID = salesOrganisationID;
    }

    public void setDistributionChannelCode(String distributionChannelCode) {
        DistributionChannelCode = distributionChannelCode;
    }

    public void setLifeCycleStatusCode(String lifeCycleStatusCode) {
        LifeCycleStatusCode = lifeCycleStatusCode;
    }

    public void setSalesMeasureUnitCode(String salesMeasureUnitCode) {
        SalesMeasureUnitCode = salesMeasureUnitCode;
    }

    public void setItemGroupCode(String itemGroupCode) {
        ItemGroupCode = itemGroupCode;
    }
}

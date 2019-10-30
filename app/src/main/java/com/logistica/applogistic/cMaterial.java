package com.logistica.applogistic;

import java.util.ArrayList;

public class cMaterial {

    public  cMaterial()
    {
        ChangeStateID = "";
        InternalID = "";
        UUID = "";
        CreationDateTime = "";
        CreationIdentityUUID = "";
        LastChangeDateTime = "";
        LastChangeIdentityUUID = "";
        ProductCategoryID = "";
        BaseMeasureUnitCode = "";
        InventoryValuationMeasureUnitCode = "";
        PlanningMeasureUnitCode = "";
        Description = "";
        DescriptionlanguageCode = "";
        Quantity = "";
        QuantityUnitCode = "";
        CorrespondingQuantity = "";
        CorrespondingQuantityUnitCode = "";
        LifeCycleStatusCode = "";
        PurchasingMeasureUnitCode = "";
        Planning = new ArrayList<>();
        AvailabilityConfirmation = new ArrayList<>();
        Sales = new ArrayList<>();
        Logistics = new ArrayList<>();
        CountryCode = "";
        RegionCode = "";
        RegionCodelistID = "";
        TaxTypeCode = "";
        TaxTypeCodelistID = "";
        TaxRateTypeCode = "";
        TaxRateTypeCodelistID = "";
        TaxExemptionReasonCode = "";
        TaxExemptionReasonCodelistID = "";
        Valuation = new ArrayList<>();
    }

    public String  ChangeStateID;
    public String  InternalID;
    public String  UUID;
    public String  CreationDateTime;
    public String  CreationIdentityUUID;
    public String  LastChangeDateTime;
    public String  LastChangeIdentityUUID;
    public String  ProductCategoryID;
    public String  BaseMeasureUnitCode;
    public String  InventoryValuationMeasureUnitCode;
    public String  PlanningMeasureUnitCode;
    public String  Description;
    public String  DescriptionlanguageCode;
    public String  Quantity;
    public String  QuantityUnitCode;
    public String  CorrespondingQuantity;
    public String  CorrespondingQuantityUnitCode;
    public String  LifeCycleStatusCode;
    public String PurchasingMeasureUnitCode;
    public ArrayList<cSupplyPlanning> Planning;
    public ArrayList<cAvailabilityConfirmation> AvailabilityConfirmation;
    public ArrayList<cSales> Sales;
    public ArrayList<cLogistics> Logistics;
    public String  CountryCode;
    public String  RegionCode;
    public String  RegionCodelistID;
    public String  TaxTypeCode;
    public String  TaxTypeCodelistID;
    public String  TaxRateTypeCode;
    public String  TaxRateTypeCodelistID;
    public String  TaxExemptionReasonCode;
    public String  TaxExemptionReasonCodelistID;
    public ArrayList<cValuation> Valuation;

    public String getDescriptionlanguageCode() {
        return DescriptionlanguageCode;
    }

    public void setDescriptionlanguageCode(String descriptionlanguageCode) {
        DescriptionlanguageCode = descriptionlanguageCode;
    }

    public String getRegionCodelistID() {
        return RegionCodelistID;
    }

    public void setRegionCodelistID(String regionCodelistID) {
        RegionCodelistID = regionCodelistID;
    }

    public String getTaxTypeCodelistID() {
        return TaxTypeCodelistID;
    }

    public void setTaxTypeCodelistID(String taxTypeCodelistID) {
        TaxTypeCodelistID = taxTypeCodelistID;
    }

    public String getTaxRateTypeCodelistID() {
        return TaxRateTypeCodelistID;
    }

    public void setTaxRateTypeCodelistID(String taxRateTypeCodelistID) {
        TaxRateTypeCodelistID = taxRateTypeCodelistID;
    }

    public String getTaxExemptionReasonCodelistID() {
        return TaxExemptionReasonCodelistID;
    }

    public void setTaxExemptionReasonCodelistID(String taxExemptionReasonCodelistID) {
        TaxExemptionReasonCodelistID = taxExemptionReasonCodelistID;
    }

    public String getPurchasingMeasureUnitCode() {
        return PurchasingMeasureUnitCode;
    }

    public void setPurchasingMeasureUnitCode(String purchasingMeasureUnitCode) {
        PurchasingMeasureUnitCode = purchasingMeasureUnitCode;
    }

    public String getChangeStateID() {
        return ChangeStateID;
    }

    public void setChangeStateID(String changeStateID) {
        ChangeStateID = changeStateID;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String internalID) {
        InternalID = internalID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getCreationDateTime() {
        return CreationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        CreationDateTime = creationDateTime;
    }

    public String getCreationIdentityUUID() {
        return CreationIdentityUUID;
    }

    public void setCreationIdentityUUID(String creationIdentityUUID) {
        CreationIdentityUUID = creationIdentityUUID;
    }

    public String getLastChangeDateTime() {
        return LastChangeDateTime;
    }

    public void setLastChangeDateTime(String lastChangeDateTime) {
        LastChangeDateTime = lastChangeDateTime;
    }

    public String getLastChangeIdentityUUID() {
        return LastChangeIdentityUUID;
    }

    public void setLastChangeIdentityUUID(String lastChangeIdentityUUID) {
        LastChangeIdentityUUID = lastChangeIdentityUUID;
    }

    public String getProductCategoryID() {
        return ProductCategoryID;
    }

    public void setProductCategoryID(String productCategoryID) {
        ProductCategoryID = productCategoryID;
    }

    public String getBaseMeasureUnitCode() {
        return BaseMeasureUnitCode;
    }

    public void setBaseMeasureUnitCode(String baseMeasureUnitCode) {
        BaseMeasureUnitCode = baseMeasureUnitCode;
    }

    public String getInventoryValuationMeasureUnitCode() {
        return InventoryValuationMeasureUnitCode;
    }

    public void setInventoryValuationMeasureUnitCode(String inventoryValuationMeasureUnitCode) {
        InventoryValuationMeasureUnitCode = inventoryValuationMeasureUnitCode;
    }

    public String getPlanningMeasureUnitCode() {
        return PlanningMeasureUnitCode;
    }

    public void setPlanningMeasureUnitCode(String planningMeasureUnitCode) {
        PlanningMeasureUnitCode = planningMeasureUnitCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getQuantityUnitCode() {
        return QuantityUnitCode;
    }

    public void setQuantityUnitCode(String quantityUnitCode) {
        QuantityUnitCode = quantityUnitCode;
    }

    public String getCorrespondingQuantity() {
        return CorrespondingQuantity;
    }

    public void setCorrespondingQuantity(String correspondingQuantity) {
        CorrespondingQuantity = correspondingQuantity;
    }

    public String getCorrespondingQuantityUnitCode() {
        return CorrespondingQuantityUnitCode;
    }

    public void setCorrespondingQuantityUnitCode(String correspondingQuantityUnitCode) {
        CorrespondingQuantityUnitCode = correspondingQuantityUnitCode;
    }

    public String getLifeCycleStatusCode() {
        return LifeCycleStatusCode;
    }

    public void setLifeCycleStatusCode(String lifeCycleStatusCode) {
        LifeCycleStatusCode = lifeCycleStatusCode;
    }

    public ArrayList<cSupplyPlanning> getPlanning() {
        return Planning;
    }

    public void setPlanning(ArrayList<cSupplyPlanning> planning) {
        Planning = planning;
    }

    public ArrayList<cAvailabilityConfirmation> getAvailabilityConfirmation() {
        return AvailabilityConfirmation;
    }

    public void setAvailabilityConfirmation(ArrayList<cAvailabilityConfirmation> availabilityConfirmation) {
        AvailabilityConfirmation = availabilityConfirmation;
    }

    public ArrayList<cSales> getSales() {
        return Sales;
    }

    public void setSales(ArrayList<cSales> sales) {
        Sales = sales;
    }

    public ArrayList<cLogistics> getLogistics() {
        return Logistics;
    }

    public void setLogistics(ArrayList<cLogistics> logistics) {
        Logistics = logistics;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public String getTaxTypeCode() {
        return TaxTypeCode;
    }

    public void setTaxTypeCode(String taxTypeCode) {
        TaxTypeCode = taxTypeCode;
    }

    public String getTaxRateTypeCode() {
        return TaxRateTypeCode;
    }

    public void setTaxRateTypeCode(String taxRateTypeCode) {
        TaxRateTypeCode = taxRateTypeCode;
    }

    public String getTaxExemptionReasonCode() {
        return TaxExemptionReasonCode;
    }

    public void setTaxExemptionReasonCode(String taxExemptionReasonCode) {
        TaxExemptionReasonCode = taxExemptionReasonCode;
    }

    public ArrayList<cValuation> getValuation() {
        return Valuation;
    }

    public void setValuation(ArrayList<cValuation> valuation) {
        Valuation = valuation;
    }
}

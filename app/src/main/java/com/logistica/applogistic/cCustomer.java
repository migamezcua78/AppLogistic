package com.logistica.applogistic;

import java.util.ArrayList;

public class cCustomer {

    public  cCustomer(){
        InternalID = "";
        CategoryCode = "";
        LifeCycleStatusCode = "";
        GivenName = "";
        FamilyName = "";
        Relationship =   new  ArrayList<>();
        IndustrialSectorCode = "";
        CustomerABCClassificationCode = "";
        PaymentData =   new  ArrayList<>();
        SalesArrangement =   new  ArrayList<>();
        PartyRoleCode = "";
        EmployeeID = "";
    }

    public String InternalID;
    public String CategoryCode;
    public String LifeCycleStatusCode;
    public String GivenName;
    public String FamilyName;
    public ArrayList<cRelationship> Relationship;
    public String IndustrialSectorCode;
    public String CustomerABCClassificationCode;
    public ArrayList<cPaymentData> PaymentData;
    public ArrayList<cSalesArrangement> SalesArrangement;
    public String PartyRoleCode;
    public String EmployeeID;
}

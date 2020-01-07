package com.logistica.applogistic;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class cTaskResponse implements Serializable {

    public cTaskResponse (){
        SiteLogisticTaskID = "";
        SiteLogisticTaskUUID = "";
        OperationTypeCode = "";
        ReferenceObjectUUID = "";
        SiteLogisticsLotOperationActivityUUID  = "";

        MaterialsOutput = new ArrayList<>();
        MaterialsInput = new ArrayList<>();
    }

    public String SiteLogisticTaskID;
    public String SiteLogisticTaskUUID;


    public String OperationTypeCode;
    public String ReferenceObjectUUID;
    public String SiteLogisticsLotOperationActivityUUID;


    public List<cMaterialSimpleData> MaterialsOutput;
    public List<cMaterialSimpleData> MaterialsInput;

    public static class ChildClass implements Serializable {
        public ChildClass() {}
    }
}


package com.logistica.applogistic;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class cTaskResponse implements Serializable {

    public cTaskResponse (){
        SiteLogisticsTaskID = "";
        OperationTypeCode = "";
        ReferencedObjectUUID = "";
        SiteLogisticsLotOperationActivityUUID  = "";

        MaterialsOutput = new ArrayList<>();
        MaterialsInput = new ArrayList<>();
    }

    public String SiteLogisticsTaskID;
    public String OperationTypeCode;
    public String ReferencedObjectUUID;
    public String SiteLogisticsLotOperationActivityUUID;


    public List<cMaterialSimpleData> MaterialsOutput;
    public List<cMaterialSimpleData> MaterialsInput;

    public static class ChildClass implements Serializable {
        public ChildClass() {}
    }
}


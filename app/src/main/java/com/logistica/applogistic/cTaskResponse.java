package com.logistica.applogistic;

import java.util.ArrayList;
import java.util.List;

public class cTaskResponse {

    public cTaskResponse (){
        SiteLogisticsTaskID = "";
        OperationTypeCode = "";
        Materials = new ArrayList<>();
    }

    public String SiteLogisticsTaskID;
    public String OperationTypeCode;
    public List<cMaterialSimpleData> Materials;
}

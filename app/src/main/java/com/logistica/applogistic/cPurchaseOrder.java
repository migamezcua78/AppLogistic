package com.logistica.applogistic;

import java.util.ArrayList;

public class cPurchaseOrder {

    public cPurchaseOrder(){
        ID = "";
        TaskStatusId = "";
        TaskStatusName = "";
        lsPurchaseItem = new ArrayList<>();
    }

    public  String ID;
    public   String  TaskStatusId;
    public   String  TaskStatusName;
    ArrayList<cPurchaseItem>  lsPurchaseItem;
}

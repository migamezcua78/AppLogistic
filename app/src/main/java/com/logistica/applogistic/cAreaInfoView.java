package com.logistica.applogistic;

import java.io.Serializable;

public class cAreaInfoView implements Serializable {

    public  cAreaInfoView (){

        AreaId = "";
        TaskId = "";
        SourceId = "";
        TargetId = "";
        ProductId = "";
        Qty = "";
        IdentStock = "";
        Restricted = false;
        Lu = "";
        LuQty = "";
        FieldName = "";
        BarCode = "";
        msg = "";
    }

    public static class ChildClass implements Serializable {
        public ChildClass() {}
    }


    public  String  AreaId;
    public  String  TaskId;
    public  String  SourceId;
    public  String  TargetId;
    public  String  ProductId;
    public  String  Qty;
    public  String  IdentStock;
    public  Boolean  Restricted;
    public  String  Lu;
    public  String  LuQty;
    public  String  FieldName;
    public  String  BarCode;
    public  String  msg;
}

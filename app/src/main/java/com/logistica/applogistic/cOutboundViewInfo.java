package com.logistica.applogistic;

public class cOutboundViewInfo {

    public   cOutboundViewInfo(){

        TaskId = "";
        SourceId = "";
        TargetId = "";
        ProductId = "";
        Open = "";
        OpenUnit = "";
        Actual = "";
        DesviationReason = "";
        Qty = "0";
        IdentStock = "";
        Restricted = false;
        Confirmed = false;
        ConfirmedPartially = false;
        Lu = "";
        LuQty = "";
        BarCode = "";
        msg = "";
    }



    public  String  TaskId;
    public  String  SourceId;
    public  String  TargetId;
    public  String  ProductId;
    public  String  Open;
    public  String  OpenUnit;
    public  String  Actual;
    public  String  Qty;
    public  String  IdentStock;
    public  Boolean Restricted;
    public  Boolean Confirmed;
    public  Boolean ConfirmedPartially;
    public  String  Lu;
    public  String  LuQty;
    public  String  BarCode;

    public  String  DesviationReason;
    public  String  msg;

}

package com.logistica.applogistic;

import java.io.Serializable;

public class cMovementViewInfo implements Serializable {

    public  String  ReferenceId;
    public  String  LabelId;
    public  String  BarCodeId;
    public  String  TaskId;
    public  String  SourceId;
    public  String  TargetId;
    public  String  ProductId;
    public  String  Qty;
    public  String  QtyUnitCode;
    public  String  IdentStock;
    public  Boolean Restricted;
    public  String  Lu;
    public  String  LuQty;
    public  String  FieldName;
    public  String  BarCode;
    public  String  msg;

    public cMovementViewInfo(){

        this.TaskId = "";
        this.SourceId = "";
        this.TargetId = "";
        this.ProductId = "";
        this.Qty = "";
        this.QtyUnitCode = "";
        this.IdentStock = "";
        this.Restricted = false;
        this.Lu = "";
        this.LuQty = "";
        this.FieldName = "";
        this.BarCode = "";
        this.ReferenceId = "";
        this.LabelId = "";
        this.BarCodeId = "";
        this.msg = "";
    }

    public  void  Reset(){

        this.TaskId = "";
        this.SourceId = "";
        this.TargetId = "";
        this.ProductId = "";
        this.Qty = "";
        this.QtyUnitCode = "";
        this.IdentStock = "";
        this.Restricted = false;
        this.Lu = "";
        this.LuQty = "";
        this.FieldName = "";
        this.BarCode = "";
        this.ReferenceId = "";
        this.LabelId = "";
        this.BarCodeId = "";
        this.msg = "";
    }

    public static class ChildClass implements Serializable {
        public ChildClass() {}
    }
}

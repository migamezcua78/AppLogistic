package com.logistica.applogistic;

import java.util.ArrayList;

public class cConfirmOutboundItemsTask {

    public cConfirmOutboundItemsTask(){

        this.outboundDeliveryID = "";
        this.date = "";
        this.confirmationStatus = "";
        this.lsItems = new  ArrayList<>();
    }

    public  String  outboundDeliveryID;
    public  String  date;
    public  String  confirmationStatus;

    ArrayList <cConfirmOutboundItem>  lsItems;
}

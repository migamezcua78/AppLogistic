package com.logistica.applogistic;

import java.util.ArrayList;

public class cPurchaseOrder {

    public cPurchaseOrder(){
        ID = "";
        lsPurchaseItem = new ArrayList<>();
    }

    public  String ID;
    ArrayList<cPurchaseItem>  lsPurchaseItem;
}

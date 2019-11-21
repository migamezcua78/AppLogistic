package com.logistica.applogistic;

import android.app.Application;

import java.util.ArrayList;

public class cGlobalData extends Application {

    private String mGlobalVarValue;

    public int consecutive;

    public int Quantity;

    public int iterater;

    public int IterScan;

    public String lblCountItemsId;

    public String CurrentArea;

    public String CurrentTaskId;

    public String ReferenceId;


    public cAreaInfoView  CurrenAreaInfoView;

    public cOutboundViewInfo  CurrentOutboundViewInfo;

    public cInboundViewInfo  CurrentInboundViewInfo;



    public  ArrayList<cAreaInfoView> lsAreaInfoService;

    public  ArrayList<cAreaInfoView> lsAreaInfoView;

    public ArrayList<cOutboundViewInfo> LsOutboudItems;

    public ArrayList<cInboundViewInfo> LsIntboudItems;

    public cMovementViewInfo  CurrentMovementViewInfo;

    public String getGlobalVarValue() {
        return mGlobalVarValue;
    }

    public void setGlobalVarValue(String str) {
        mGlobalVarValue = str;
    }

}

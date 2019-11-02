package com.logistica.applogistic;

import android.app.Application;

import java.util.ArrayList;

public class cGlobalData extends Application {

    private String mGlobalVarValue;

    public ArrayList<cOutboundViewInfo> LsOutboudItems;

    public ArrayList<cInboundViewInfo> LsIntboudItems;

    public String getGlobalVarValue() {
        return mGlobalVarValue;
    }

    public void setGlobalVarValue(String str) {
        mGlobalVarValue = str;
    }

}

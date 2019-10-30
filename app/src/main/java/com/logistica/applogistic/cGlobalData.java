package com.logistica.applogistic;

import android.app.Application;

public class cGlobalData extends Application {

    private String mGlobalVarValue;

    public String getGlobalVarValue() {
        return mGlobalVarValue;
    }

    public void setGlobalVarValue(String str) {
        mGlobalVarValue = str;
    }
}

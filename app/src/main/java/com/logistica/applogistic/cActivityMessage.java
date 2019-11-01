package com.logistica.applogistic;

import java.io.Serializable;

public class cActivityMessage implements Serializable {

    public  cActivityMessage (){
        Message = "";
        Key01 = "";
        Key02 = "";
    }

    public String getKey01() {
        return Key01;
    }

    public void setKey01(String key01) {
        Key01 = key01;
    }

    public String getKey02() {
        return Key02;
    }

    public void setKey02(String key02) {
        Key02 = key02;
    }

    public static class ChildClass implements Serializable {
        public ChildClass() {}
    }

    private   String Message;

    private   String Key01;

    private   String Key02;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

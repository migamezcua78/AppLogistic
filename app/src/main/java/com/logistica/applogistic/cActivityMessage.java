package com.logistica.applogistic;

import java.io.Serializable;

public class cActivityMessage implements Serializable {

    private   String Message;
    private   String Key01;
    private   String Key02;
    private   String Key03;

    public  cActivityMessage (){
        Message = "";
        Key01 = "";
        Key02 = "";
        Key03 = "";
    }

    public  cActivityMessage (String Message){
        this.Message = Message;
        this.Key01 = "";
        this.Key02 = "";
        Key03 = "";
    }

    public  cActivityMessage (String Message, String Key01 ){
        this.Message = Message;
        this.Key01 = Key01;
        this.Key02 = "";
        this.Key03 = "";
    }

    public  cActivityMessage (String Message, String Key01, String Key02,String Key03){
        this.Message = Message;
        this.Key01 = Key01;
        this.Key02 = Key02;
        this.Key03 = Key03;
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


    public String getKey03() {
        return Key03;
    }

    public void setKey03(String key03) {
        Key03 = key03;
    }

    public static class ChildClass implements Serializable {
        public ChildClass() {}
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

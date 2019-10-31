package com.logistica.applogistic;

import java.io.Serializable;

public class cActivityMessage implements Serializable {

    public  cActivityMessage (){
        Message = "";
    }

    public static class ChildClass implements Serializable {
        public ChildClass() {}
    }

    private   String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class RequestRemoval extends AppCompatActivity {
    SoapPrimitive ResulString;
    String param1;
    String param2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_removal);
    }
    public void goTestWSDL(View view){
        String SOAPaccion = "http://sap.com/xi/A1S/Global/QueryMaterialIn/FindByElements";
        String metodo = "FindByElements";
        String namespace = "http://sap.com/xi/A1S/Global";
        String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/querymaterialin?sap-vhost=my346674.sapbydesign.com";

        try {
            SoapObject Request = new SoapObject(namespace,metodo);
            Request.addProperty("nom1",param1);
            Request.addProperty("nom2",param2);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet=true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport= new HttpTransportSE(url);
            transport.call(SOAPaccion,soapEnvelope);
            ResulString=(SoapPrimitive)soapEnvelope.getResponse();

        }
        catch (Exception ex){

        }

    }
}

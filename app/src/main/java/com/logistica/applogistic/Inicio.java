package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Inicio extends MainBaseActivity {
    DatabaseHelper BD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }


    public void onBackPressed() {
        super.onBackPressed();

        Intent oIntent = new Intent(this, MainActivity.class);
        oIntent.putExtra("oMsg", new cActivityMessage("ReStart"));
        startActivity(oIntent);
    }


    public  void  goInbound(View view){

        Intent oIntent = new Intent(this, ConfirmTask.class);
        oIntent.putExtra("oMsg", new cActivityMessage(cMessage.Message.START));
        startActivity(oIntent);
    }

    public  void  goLogistic(View view){

        Intent oIntent = new Intent(this, LogisticAreaCount.class);
        oIntent.putExtra("oMsg", new cActivityMessage(cMessage.Message.START));
        startActivity(oIntent);
    }

    public  void  goOutBound(View view){
        Intent oIntent = new Intent(this, OutBound.class);
        oIntent.putExtra("oMsg", new cActivityMessage(cMessage.Message.START));
        startActivity(oIntent);
    }

    public  void  goInBound(View view){
        Intent oIntent = new Intent(this, Inbound.class);
        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);

      //     Intent oIntent = new Intent(this, TestOfServices.class);
       //    startActivity(oIntent);
    }

    public  void  goInternal(View view){
        Intent oIntent = new Intent(this, InboundBD.class);
        startActivity(oIntent);
    }

    public void goGoodsMovement(View view){
        Intent oIntent = new Intent(this, GoodMovement.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Start"));
        startActivity(oIntent);
    }

    public void goMappingProducts(View view){
        Intent oIntent = new Intent(this, MappingProducts.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Start"));
        startActivity(oIntent);
    }

    public void goRegisterProducts(View view){
        Intent oIntent = new Intent(this, RegisterProducts.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Start"));
        startActivity(oIntent);
    }

    public void goShipmentConfirmation(View view){
        Intent oIntent = new Intent(this, ShipmentConfirmation.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Start"));
        startActivity(oIntent);
    }





    public static void selectSpinnerItemByValue(Spinner spnr, String value) {
        ArrayAdapter<cSpinnerItem> adapter = (ArrayAdapter<cSpinnerItem>) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(((cSpinnerItem)adapter.getItem(position)).getField().equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    public  static  String  Dif (String x1, String x2 ){

        int dif = 0;
        int ix1 = 0;
        int ix2 = 0;

        if (x1 != null  &&  !x1.trim().isEmpty() ){
            ix1 = Integer.valueOf(x1);
        }

        if (x2 != null  &&  !x2.trim().isEmpty() ){
            ix2 = Integer.valueOf(x2);
        }

        dif = ix1- ix2;

        return  String.valueOf(dif);
    }



/*    public void goRequestRemoval(View view){
        Intent oIntent = new Intent(this, MainCamara.class);
        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);
    }*/
}

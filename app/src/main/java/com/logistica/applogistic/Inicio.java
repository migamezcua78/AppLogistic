package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Inicio extends AppCompatActivity {
    DatabaseHelper BD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
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


/*    public void goRequestRemoval(View view){
        Intent oIntent = new Intent(this, MainCamara.class);
        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);
    }*/
}

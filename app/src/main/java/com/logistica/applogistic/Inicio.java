package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

       // Intent oIntent = new Intent(this,Inbound.class);
       // Intent oIntent = new Intent(this,PutAwayTarget.class);
        // Intent oIntent = new Intent(this, PickSource.class);
       // Intent oIntent = new Intent(this, TargetConfirmation.class);

       // Intent oIntent = new Intent(this, InboundBD.class);

       // Intent oIntent = new Intent(this, Remaining.class);

        Intent oIntent = new Intent(this, ConfirmTask.class);
        startActivity(oIntent);
    }

    public  void  goLogistic(View view){

        // Intent oIntent = new Intent(this,Inbound.class);
        // Intent oIntent = new Intent(this,PutAwayTarget.class);
        // Intent oIntent = new Intent(this, PickSource.class);
        Intent oIntent = new Intent(this, LogisticAreaCount.class);
        startActivity(oIntent);
    }

    public  void  goOutBound(View view){
        Intent oIntent = new Intent(this, OutBound.class);
       // Intent oIntent = new Intent(this, PickSourceEmb.class);
        //oIntent.setExtrasClassLoader();
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
      //  Intent oIntent = new Intent(this, InboundBD.class);
        //oIntent.setExtrasClassLoader();
       // Intent oIntent = new Intent(this, PickSourceBD.class);
      //  Intent oIntent = new Intent(this, PutAwayTargetBD.class);
      //  Intent oIntent = new Intent(this, TargetConfirmationBD.class);
        Intent oIntent = new Intent(this, InboundBD.class);
        startActivity(oIntent);
    }

    public void goGoodsMovement(View view){
        Intent oIntent = new Intent(this, GoodMovement.class);
        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);
    }

    public void goRequestRemoval(View view){
        Intent oIntent = new Intent(this, RequestRemoval.class);
        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);
    }
}

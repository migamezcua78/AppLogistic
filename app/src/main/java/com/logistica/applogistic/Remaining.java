package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Remaining extends AppCompatActivity {


    boolean ConfirmedPartially;
    boolean ConfirmedReason;

    private Spinner spinner;
    private List<cSpinnerItem> InfoFilter = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining);
        Init();
        fillDeviationReason();
    }


    private void Init(){
        spinner = findViewById(R.id.spiDeviationReasonId);
        ConfirmedPartially = false;
        ConfirmedReason= false;
    }

    private void fillDeviationReason (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }

    public void   onConfirmPartially(View view) {

        if ( !ConfirmedReason ){
            Toast.makeText(getApplicationContext(),"Please, Confirm deviation reason", Toast.LENGTH_SHORT).show();
        }else{
            ConfirmedPartially = true;

            Intent oIntent = new Intent(this, PickSourceEmb.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oMssg.setMessage("ConfirmedPartially");
            oMssg.setKey01("CR2200EO36AL00_1");
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);
        }
    }

    public void   onReportDeviation(View view) {

        ConfirmedReason = true;
        Toast.makeText(getApplicationContext(),"deviation reason confirmed", Toast.LENGTH_SHORT).show();
    }

    public void   onBack(View view) {

        Intent oIntent = new Intent(this, PickSourceEmb.class);
        cActivityMessage   oMssg = new  cActivityMessage();
        oMssg.setMessage("");
        oIntent.putExtra("oMssg",oMssg );
        startActivity(oIntent);
    }



    public void   onClickConfirm(View spinner) {

        // Intent oIntent = new Intent(this, PickSourceBD.class);

        Intent oIntent = new Intent(this, ConfirmTask.class);

        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);

        //  Toast.makeText(getApplicationContext(),"Confirm", Toast.LENGTH_SHORT).show();
    }

    private List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"Missing part"));
        InfoFilter.add(new cSpinnerItem(2,"Other"));

        return  InfoFilter;
    }

}

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


    cActivityMessage oMsg;
    cOutboundViewInfo  oCurrentItemViewInfo;
    ArrayList<cOutboundViewInfo>  lsOutbounItems;

    int Iter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining);
        Init();
        StartActivity();
    }

    public void StartActivity(){

        if ( oMsg.getMessage().equals("DeviationReason") ){

            if ( lsOutbounItems.size() > 0){
                Iter = Integer.valueOf(oMsg.getKey01());
                if ( Iter > 0 ){
                    oCurrentItemViewInfo = lsOutbounItems.get(Iter-1);
                }
            }
        }

        fillDeviationReason();
    }


    private void Init(){
        spinner = findViewById(R.id.spiDeviationReasonId);
        ConfirmedPartially = false;
        ConfirmedReason= false;
        Iter = 0;

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        lsOutbounItems = ((cGlobalData)getApplication()).LsOutboudItems;
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
            oMssg.setKey01(oCurrentItemViewInfo.ProductId);
            oIntent.putExtra("oMsg",oMssg);
            startActivity(oIntent);
        }
    }

    public void   onReportDeviation(View view) {

        ConfirmedReason = true;
        Toast.makeText(getApplicationContext(),"deviation reason confirmed", Toast.LENGTH_SHORT).show();
    }

    public void   onBack(View view) {

        Intent oIntent = new Intent(this, PickSourceEmb.class);
        oIntent.putExtra("oMsg", new cActivityMessage("NoConfirmedPartially", String.valueOf(Iter)));
        startActivity(oIntent);
    }

    private List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"Missing part"));
        InfoFilter.add(new cSpinnerItem(2,"Other"));

        return  InfoFilter;
    }

}

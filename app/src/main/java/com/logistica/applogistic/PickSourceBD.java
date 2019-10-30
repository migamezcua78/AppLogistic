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


public class PickSourceBD extends AppCompatActivity {

    private Spinner spinner;

    // DATA
    private List<cSpinnerItem> InfoFilter = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_source_bd);
        Init();
        fillDataOptions();
    }


    private void Init(){
        spinner = findViewById(R.id.spiOptions);
    }

    private void fillDataOptions (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }

    public void   onClickModify(View spinner) {

        Intent oIntent = new Intent(this, PutAwayTargetBD.class);
        startActivity(oIntent);

        //  Toast.makeText(getApplicationContext(),"Confirm", Toast.LENGTH_SHORT).show();
    }

    public void   onClickConfirm(View spinner) {

        Intent oIntent = new Intent(this, TargetConfirmationBD.class);
        startActivity(oIntent);

        //  Toast.makeText(getApplicationContext(),"Confirm", Toast.LENGTH_SHORT).show();
    }

    private List<cSpinnerItem> getInfoFilter(){

        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,"6A"));

        return  InfoFilter;
    }
}

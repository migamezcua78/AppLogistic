package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Remaining extends AppCompatActivity {


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
    }

    private void fillDeviationReason (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);

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

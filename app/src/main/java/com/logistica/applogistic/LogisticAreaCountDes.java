package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LogisticAreaCountDes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_area_count_des);
    }

    public void   onClickConfirm(View spinner) {
        Toast.makeText(getApplicationContext(),"F5 Confirmed", Toast.LENGTH_LONG).show();
        Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
        oIntent.putExtra("param_reset","1");
        startActivity(oIntent);
    }

}

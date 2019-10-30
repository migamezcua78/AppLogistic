package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LogisticAreaCount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_area_count);
    }

    public  void  goLogisticDes(View view){

        Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
        startActivity(oIntent);
    }
    public void   onClickConfirm(View spinner) {
        Toast.makeText(getApplicationContext(),"Task 131 Confirmed", Toast.LENGTH_LONG).show();
        Intent oIntent = new Intent(this, LogisticAreaCount.class);
        oIntent.putExtra("param_reset","1");
        startActivity(oIntent);
    }
}

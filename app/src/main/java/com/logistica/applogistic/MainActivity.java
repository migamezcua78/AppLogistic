package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public  void  goInicio (View view){
        // Intent oIntent = new Intent(this,Inbound.class);
        Intent oIntent = new Intent(this,Inicio.class);
        startActivity(oIntent);
    }
}

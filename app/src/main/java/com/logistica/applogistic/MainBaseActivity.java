package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu manu) {
        getMenuInflater().inflate(R.menu.main,manu);
        return super.onCreateOptionsMenu(manu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        String s = "";

        Intent oIntent = new Intent(this,MainActivity.class);
        startActivity(oIntent);

        return super.onOptionsItemSelected(item);
    }

}

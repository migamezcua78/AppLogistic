package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Test extends AppCompatActivity {

    private Spinner  spinner;
    private User  user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }


}

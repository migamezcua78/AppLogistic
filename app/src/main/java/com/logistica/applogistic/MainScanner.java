package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.StaticLayout;
import android.widget.Toast;

public class MainScanner extends AppCompatActivity {

    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scanner);
        init();
        StartActivity();
    }

    private void StartActivity() {

        if ( oMsg.getMessage().equals("Scan") ){
            launchActivity(FullScannerActivity.class);
        }else{
            Intent intent = new Intent(this, Goods_Movement_Source.class);
            intent.putExtra("oMsg", new cActivityMessage(""));
            startActivity(intent);
        }
    }

    private void init() {
        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        String s = "";
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    public  static  class  ScanType {
        public static final String PRODUCT = "PRODUCT";
        public static final String SOURCE = "SOURCE";
        public static final String TARGET = "TARGET";
        public static final String TASK = "TASK";
    }
}

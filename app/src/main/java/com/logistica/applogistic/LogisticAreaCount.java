package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LogisticAreaCount extends AppCompatActivity {

    EditText  txtAreaId;
    EditText  txtProductId;
    EditText  txtOrderId;
    EditText  txtBarCodeId;

    ProgressDialog vProgressDialog;
    cActivityMessage   oActivityMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_area_count);
        Init();

        oActivityMessage = (cActivityMessage)getIntent().getSerializableExtra("oMssg");

    }

    private void Init (){

        txtAreaId =  findViewById(R.id.txtSourceId);
        txtProductId = findViewById(R.id.txtProductId);
        txtOrderId = findViewById(R.id.txtQtyId);
        txtBarCodeId =findViewById(R.id.txtStockId);

        oActivityMessage = new cActivityMessage();
    }

    @Override
    protected void  onResume(){
        super.onResume();

        if ( oActivityMessage != null  )
        {
            if ( !oActivityMessage.getMessage().isEmpty())
            {
                Toast.makeText(getApplicationContext(),oActivityMessage.getMessage(), Toast.LENGTH_SHORT).show();
                oActivityMessage.setMessage("");
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Inicio.class);
        startActivity(oIntent);
    }


    public void onStartTask(View view) {

        cAreaInfoView  oViewInfo = new   cAreaInfoView();
        oViewInfo.TaskId =  txtAreaId.getText().toString();

        if (  oViewInfo.TaskId.trim().isEmpty() ){

            Toast.makeText(getApplicationContext(),"TASK field is required", Toast.LENGTH_SHORT).show();

        } else{

            Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
            oIntent.putExtra("oDataParam",oViewInfo);
            startActivity(oIntent);
        }
    }


    public void onScan(View view) {

        txtAreaId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");
    }


    public  void  goLogisticDes(View view){

        Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
        startActivity(oIntent);
    }

    public void   onClickConfirm(View spinner) {
        Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
        startActivity(oIntent);
    }


    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCount.this);
            vProgressDialog.setMessage("Scanning TASK...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(String lsData) {
            super.onPostExecute(lsData);
            txtAreaId.setText("13-95-4A");
            vProgressDialog.hide();
        }
    }
}

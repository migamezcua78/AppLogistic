package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class LogisticAreaCount extends AppCompatActivity {

    EditText  txtAreaId;
    EditText  txtProductId;
    EditText  txtOrderId;
    EditText  txtBarCodeId;

    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_area_count);
        Init();
        StarActivity();
    }

    private void StarActivity() {

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){
                // oCurrentItemViewInfo.SourceId = oMsg.getKey01();
                txtAreaId.setText("E01/E01-1");

            }  else if (!oMsg.getMessage().equals(cMessage.Message.START)){
                Toast.makeText(getApplicationContext(),oMsg.getMessage(), Toast.LENGTH_SHORT).show();
                oMsg.setMessage("");
            }
        }
    }

    private void Init (){

        txtAreaId =  findViewById(R.id.txtSourceId);
        txtProductId = findViewById(R.id.txtProductId);
        txtOrderId = findViewById(R.id.txtQtyId);
        txtBarCodeId =findViewById(R.id.txtStockId);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
    }


    @Override
    protected void  onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Inicio.class);
        startActivity(oIntent);
    }


    public void onStartTask(View view) {

        cAreaInfoView  oViewInfo = new   cAreaInfoView();
        oViewInfo.AreaId =  txtAreaId.getText().toString();

        if (  oViewInfo.AreaId.trim().isEmpty() ){

            Toast.makeText(getApplicationContext(),"AREA field is required", Toast.LENGTH_SHORT).show();

        } else{

            Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
            //oIntent.putExtra("oDataParam",oViewInfo);
            oIntent.putExtra("oMsg", new cActivityMessage(cMessage.Message.START, oViewInfo.AreaId ));
            startActivity(oIntent);
        }
    }


    public void onScan(View view) {

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("LogisticAreaCount",Scanner.ScanType.SCAN_TASK));
        startActivity(oIntent);


/*        txtAreaId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
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
            txtAreaId.setText("E01/E01-1");
            vProgressDialog.hide();
        }
    }
}

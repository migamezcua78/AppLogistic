package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PickSource extends AppCompatActivity {

    private Spinner spinner;

    private EditText txtSourceId;
    private EditText txtProductId;
    private TextView lblOpenValueId;
    private EditText txtQtyId;
    private EditText txtStockId;


    // DATA
    private List<cSpinnerItem> InfoFilter = new ArrayList<>();

    cActivityMessage   oActivityMessage;
    ArrayList<cInboundViewInfo>  lsInbounItems;
    cInboundViewInfo  oInboundViewInfo;


    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_source);
        initPickSource();
        fillDataOptions();

        cGlobalData  oGlobalData=  (cGlobalData)getApplication();
        lsInbounItems = oGlobalData.LsIntboudItems;


        if (lsInbounItems.size() > 0 ){
            iterater = 1;
            oInboundViewInfo = lsInbounItems.get(iterater - 1);
            setViewInfo(oInboundViewInfo);
        }
    }

    private void initPickSource (){
        spinner = findViewById(R.id.spiOptions);

        txtSourceId = findViewById(R.id.txtSourceId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtStockId = findViewById(R.id.txtStockId);

        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 0;
    }

    private void setViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            txtProductId.setText(pInboundViewInfo.ProductId);
            txtQtyId.setText(pInboundViewInfo.Qty);
            txtStockId.setText(pInboundViewInfo.IdentStock);
            lblOpenValueId.setText(pInboundViewInfo.Open + " " +  pInboundViewInfo.OpenUnit);


        } catch (Exception e){

            String s = e.getMessage();
        }
    }

    private void fillDataOptions (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }


    public void   onScan(View view) {

        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");
    }

    public void   onClickConfirm(View spinner) {

        if(oInboundViewInfo.SourceId.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"SOURCE field is required", Toast.LENGTH_SHORT).show();

        }else {

            Intent oIntent = new Intent(this, PutAwayTarget.class);
            cActivityMessage  cActivityMessage = new cActivityMessage();
            cActivityMessage.setMessage("ItemConfirmed");
            oIntent.putExtra("oMssg",cActivityMessage);
            startActivity(oIntent);
        }
    }

    private List<cSpinnerItem> getInfoFilter(){

        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,"6A"));

        return  InfoFilter;
    }


    ProgressDialog vProgressDialog;

    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PickSource.this);
            vProgressDialog.setMessage("Scanning ... ");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                Thread.sleep(1000);
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

            txtSourceId.setText("13-15-6A");
            oInboundViewInfo.SourceId = txtSourceId.getText().toString();

            vProgressDialog.hide();
        }
    }


}

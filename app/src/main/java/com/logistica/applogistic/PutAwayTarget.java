package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PutAwayTarget extends AppCompatActivity {

    private Spinner spinner;

    private EditText txtTargetId;
    private EditText txtProductId;
    private TextView lblOpenValueId;
    private EditText txtQtyId;
    private EditText txtSerialNumberId;
    private EditText txtLuQtyId;
    private EditText txtBarCodeId;
    private CheckBox cheRestrictedId;


    private TextView lblCountItemsId;



   // cActivityMessage   oActivityMessage;
    ArrayList<cInboundViewInfo>  lsInbounItems;
    cInboundViewInfo  oCurrentInboundViewInfo;
    cGlobalData  oGlobalData;

    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;

    String   sSerialNumber;


    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away_target);
        init();
        StartActivity();
    }

    private void init (){

        txtTargetId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtSerialNumberId = findViewById(R.id.txtSerialNumberId);
        txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtBarCodeId = findViewById(R.id.txtStockId);
        cheRestrictedId = findViewById(R.id.cheRestrictedId);
        lblCountItemsId = findViewById(R.id.lblCountItemsId);

        spinner = findViewById(R.id.spiUnitId);

        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 0;

        sSerialNumber = "SNID88046999927";

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oGlobalData=  (cGlobalData)getApplication();
        lsInbounItems = oGlobalData.LsIntboudItems;
    }

    public void  StartActivity (){

        if (oMsg.getMessage().equals("ItemConfirmed")){

            AsyncTaskAllItemConfirmed asyncTask=new AsyncTaskAllItemConfirmed();
            asyncTask.execute("params");
        }else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_SERIAL_NUMBER)){

            lsInbounItems = oGlobalData.LsIntboudItems;

            oCurrentInboundViewInfo =  oGlobalData.CurrentInboundViewInfo;

           // txtSerialNumberId.setText(sSerialNumber);
            int inQtyId =  Integer.valueOf(oCurrentInboundViewInfo.Qty);
            inQtyId = inQtyId + 1;
            oCurrentInboundViewInfo.Qty = String.valueOf(inQtyId);
           // txtQtyId.setText(oCurrentInboundViewInfo.Qty);

            txtSerialNumberId.setText(sSerialNumber + "_" + inQtyId);
            oCurrentInboundViewInfo.SerialNumber = txtSerialNumberId.getText().toString();

            setViewInfo(oCurrentInboundViewInfo);

        }


        else{

            if (lsInbounItems.size() > 0 ){
                iterater = 1;
                lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsInbounItems.size()) );
                oCurrentInboundViewInfo = lsInbounItems.get(iterater - 1);
                setViewInfo(oCurrentInboundViewInfo);
            }
        }

        fillDataUnits();
    }


    private void setViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            txtTargetId.setText(pInboundViewInfo.TargetId);
            txtProductId.setText(pInboundViewInfo.ProductId);
            txtQtyId.setText(pInboundViewInfo.Qty);
            cheRestrictedId.setChecked(pInboundViewInfo.Restricted);
            txtLuQtyId.setText(pInboundViewInfo.LuQty);
            txtBarCodeId.setText(pInboundViewInfo.BarCode);
            lblOpenValueId.setText(pInboundViewInfo.Open + " " +  pInboundViewInfo.OpenUnit);
            txtSerialNumberId.setText(pInboundViewInfo.SerialNumber);

        } catch (Exception e){

            String s = e.getMessage();
        }
    }



    private void getViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            pInboundViewInfo.TargetId = txtTargetId.getText().toString();
            pInboundViewInfo.ProductId = txtProductId.getText().toString();
            pInboundViewInfo.Qty = txtQtyId.getText().toString();
            pInboundViewInfo.Restricted = cheRestrictedId.isChecked();
            pInboundViewInfo.LuQty = txtLuQtyId.getText().toString();
            pInboundViewInfo.BarCode = txtBarCodeId.getText().toString();
            pInboundViewInfo.SerialNumber = txtSerialNumberId.getText().toString();


        } catch (Exception e){

            String s = e.getMessage();
        }
    }



    private void fillDataUnits (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }


    public void   onScan(View view) {

        getViewInfo(oCurrentInboundViewInfo);

        oGlobalData.CurrentInboundViewInfo = oCurrentInboundViewInfo;

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("PutAwayTarget",Scanner.ScanType.SCAN_SERIAL_NUMBER));
        startActivity(oIntent);


/*        txtSerialNumberId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }


    public void   onClickConfirm(View spinner) {

        Intent oIntent = new Intent(this, PickSource.class);
        oIntent.putExtra("oMsg",  new cActivityMessage(""));
        startActivity(oIntent);
    }

    public void   onClickNext(View spinner) {
        Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
        Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();
    }

    private List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"ea"));
        InfoFilter.add(new cSpinnerItem(1,"pick"));

        return  InfoFilter;
    }

    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PutAwayTarget.this);
            vProgressDialog.setMessage("Scanning Serial Number...");
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

            int inQtyId =  Integer.valueOf(txtQtyId.getText().toString());
            inQtyId = inQtyId + 1;
            oCurrentInboundViewInfo.Qty = String.valueOf(inQtyId);
            txtQtyId.setText(oCurrentInboundViewInfo.Qty);

            txtSerialNumberId.setText(sSerialNumber + "_" + inQtyId);

            vProgressDialog.hide();

            Toast.makeText(getApplicationContext(),"One item counted", Toast.LENGTH_SHORT).show();
        }
    }


    private class AsyncTaskAllItemConfirmed extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PutAwayTarget.this);
            vProgressDialog.setMessage("All items were confirmed");
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
            vProgressDialog.hide();

            Intent oIntent = new Intent(PutAwayTarget.this, TargetConfirmation.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);
        }
    }
}

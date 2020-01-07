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

  //  private EditText txtSourceId;
    private EditText txtProductId;
    private TextView lblOpenValueId;
    private EditText txtQtyId;
    private EditText txtStockId;


    // DATA
    private List<cSpinnerItem> InfoFilter = new ArrayList<>();


    cGlobalData  oGlobalData;
    ArrayList<cInboundViewInfo>  lsInbounItems;
    cInboundViewInfo  oCurrentInboundViewInfo;

    cActivityMessage oMsg;


    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_source);
        init();
        StartActivity();
    }


    private void init (){
        spinner = findViewById(R.id.spiOptions);

       // txtSourceId = findViewById(R.id.txtSourceId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtStockId = findViewById(R.id.txtStockId);

        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 0;

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oGlobalData=  (cGlobalData)getApplication();
        lsInbounItems = oGlobalData.LsIntboudItems;
    }

    public void StartActivity (){

        fillDataOptions();

        if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_SOURCE)){

            oCurrentInboundViewInfo =  oGlobalData.CurrentInboundViewInfo;
           // txtSourceId.setText("13-15-6A");
            //txtSourceId.setText(oMsg.getKey01());
           // oCurrentInboundViewInfo.SourceId = txtSourceId.getText().toString();

            setViewInfo(oCurrentInboundViewInfo);

        } else {


            oCurrentInboundViewInfo =  oGlobalData.CurrentInboundViewInfo;

            // txtSourceId.setText("13-15-6A");
           // txtSourceId.setText(oMsg.getKey01());
           // oCurrentInboundViewInfo.SourceId = txtSourceId.getText().toString();

            setViewInfo(oCurrentInboundViewInfo);


/*            if (lsInbounItems.size() > 0 ){
                iterater = 1;
                oCurrentInboundViewInfo = lsInbounItems.get(iterater - 1);
                setViewInfo(oCurrentInboundViewInfo);
            }*/
        }
    }


    private void fillDataOptions (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }


    private void setViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            //txtSourceId.setText(pInboundViewInfo.SourceId);
            txtProductId.setText(pInboundViewInfo.ProductId);
            txtQtyId.setText(pInboundViewInfo.Qty);
            txtStockId.setText(pInboundViewInfo.IdentStock);
            lblOpenValueId.setText(pInboundViewInfo.Open + " " +  pInboundViewInfo.OpenUnit);


        } catch (Exception e){

            String s = e.getMessage();
        }
    }



    private void getViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

           // pInboundViewInfo.SourceId = txtSourceId.getText().toString();
            pInboundViewInfo.ProductId = txtProductId.getText().toString();
            pInboundViewInfo.Qty = txtQtyId.getText().toString();
            pInboundViewInfo.IdentStock = txtStockId.getText().toString();

        } catch (Exception e){

            String s = e.getMessage();
        }
    }


    public void   onScan(View view) {

        getViewInfo(oCurrentInboundViewInfo);
        oGlobalData.CurrentInboundViewInfo = oCurrentInboundViewInfo;

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("PickSource",Scanner.ScanType.SCAN_SOURCE));
        startActivity(oIntent);


/*        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }

    public void   onClickConfirm(View spinner) {

        getViewInfo(oCurrentInboundViewInfo);

//        if(oCurrentInboundViewInfo.SourceId.trim().isEmpty()){
//            Toast.makeText(getApplicationContext(),"El Área Logística es requerida ", Toast.LENGTH_SHORT).show();
//
//        }else {

            oCurrentInboundViewInfo.Confirmed = true;
            oGlobalData.CurrentInboundViewInfo = oCurrentInboundViewInfo;

            Intent oIntent = new Intent(this, PutAwayTarget.class);
            cActivityMessage  cActivityMessage = new cActivityMessage();
            cActivityMessage.setMessage("ItemConfirmed");
            oIntent.putExtra("oMsg",cActivityMessage);
            startActivity(oIntent);
      //  }
    }

    private List<cSpinnerItem> getInfoFilter(){

        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,"ZPZ"));

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

            //txtSourceId.setText("13-15-6A");
          //  oCurrentInboundViewInfo.SourceId = txtSourceId.getText().toString();

            vProgressDialog.hide();
        }
    }


}

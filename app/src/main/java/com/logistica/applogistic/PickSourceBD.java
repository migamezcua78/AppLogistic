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


public class PickSourceBD extends AppCompatActivity {

    private Spinner spinner;

    private EditText txtSourceId;
    private EditText txtProductId;
    private TextView lblOpenValueId;
    private EditText txtQtyId;
    private EditText txtStockId;

    private CheckBox chkProductId;
    private CheckBox chkSourceId;

    // DATA
    private List<cSpinnerItem> InfoFilter = new ArrayList<>();
    cGlobalData  oGlobalData;

    cActivityMessage   oActivityMessage;
    ArrayList<cInboundViewInfo>  lsInbounItems;
    cInboundViewInfo  oCurrentItemViewInfo;


    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;
    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_source_bd);
        Init();
        StartActivity();
    }


    private void Init(){
        spinner = findViewById(R.id.spiOptions);

        txtSourceId = findViewById(R.id.txtSourceId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtStockId = findViewById(R.id.txtStockId);

        chkSourceId = findViewById(R.id.chkSourceId);
        chkProductId = findViewById(R.id.chkProductId);

        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 1;

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oGlobalData=  (cGlobalData)getApplication();
        lsInbounItems = ((cGlobalData)getApplication()).LsIntboudItems;
    }

    public void StartActivity(){

        fillDataOptions();

        if (oMsg != null ){

            if ( oMsg.getMessage().equals("OtherTargetConfirmed") )
            {

                if ( lsInbounItems.size() > 0){
                    iterater = Integer.valueOf(oMsg.getKey01());
                    if ( iterater > 0 ){
                        oCurrentItemViewInfo = lsInbounItems.get(iterater-1);
                        setViewInfo(oCurrentItemViewInfo);
                        // lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsInbounItems.size()));
                    }
                }
            } else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_SOURCE)){

                oCurrentItemViewInfo =  oGlobalData.CurrentInboundViewInfo;

              //  txtSourceId.setText("13-15-6A");
                txtSourceId.setText(oMsg.getKey01());
                chkSourceId.setChecked(true);
                oCurrentItemViewInfo.SourceId = txtSourceId.getText().toString();

                setViewInfo(oCurrentItemViewInfo);

            } else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_PRODUCT_QTY)){

                oCurrentItemViewInfo =  oGlobalData.CurrentInboundViewInfo;

               // txtProductId.setText(oCurrentItemViewInfo.ProductId);
                txtQtyId.setText("2");  //  proviene del scaner
                txtStockId.setText("43668");  //  proviene del scaner
                chkProductId.setChecked(true);
                //.setText("43668");

                // se asignan
              //  oCurrentItemViewInfo.SourceId = txtSourceId.getText().toString();
                oCurrentItemViewInfo.Qty = txtQtyId.getText().toString();
                oCurrentItemViewInfo.IdentStock = txtStockId.getText().toString();

                setViewInfo(oCurrentItemViewInfo);
            }

            else {

                // por  ahora solo se toma el primero
                if (lsInbounItems.size() > 0 ){
                    iterater = 1;
                    oCurrentItemViewInfo = lsInbounItems.get(iterater - 1);
                    setViewInfo(oCurrentItemViewInfo);
                }
            }
        }
    }


    public void   onScan(View view) {

        getViewInfo(oCurrentItemViewInfo);
        oGlobalData.CurrentInboundViewInfo = oCurrentItemViewInfo;

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("PickSourceBD",Scanner.ScanType.SCAN_SOURCE));
        startActivity(oIntent);

/*        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }


    public void   onScanProduct(View view) {

        getViewInfo(oCurrentItemViewInfo);
        oGlobalData.CurrentInboundViewInfo = oCurrentItemViewInfo;

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("PickSourceBD",Scanner.ScanType.SCAN_PRODUCT_QTY));
        startActivity(oIntent);


/*        AsyncTaskScanProduct asyncTask=new AsyncTaskScanProduct();
        asyncTask.execute("params");*/
    }


    public void   onClickConfirm(View spinner) {

        getViewInfo(oCurrentItemViewInfo);

        if(oCurrentItemViewInfo.SourceId.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"SOURCE field is required", Toast.LENGTH_SHORT).show();

        }else if (oCurrentItemViewInfo.ProductId.trim().isEmpty()){

            Toast.makeText(getApplicationContext(),"PRODUCT field is required", Toast.LENGTH_SHORT).show();

        }else if (oCurrentItemViewInfo.Qty.isEmpty()){

            Toast.makeText(getApplicationContext(),"ACTUAL field is required", Toast.LENGTH_SHORT).show();
        }

        else if (oCurrentItemViewInfo.IdentStock.isEmpty()){

            Toast.makeText(getApplicationContext(),"ID STOCK field is required", Toast.LENGTH_SHORT).show();
        }

        else {

            Intent oIntent = new Intent(this, TargetConfirmationBD.class);
            cActivityMessage  cActivityMessage = new cActivityMessage();
            cActivityMessage.setMessage("ItemConfirmed");
            oIntent.putExtra("oMssg",cActivityMessage);
            startActivity(oIntent);
        }
    }

    public void   onChangeArea(View view) {

        Intent oIntent = new Intent(this, PutAwayTargetBD.class);
        oIntent.putExtra("oMsg", new cActivityMessage("AssignOtherArea", String.valueOf(iterater)));
        startActivity(oIntent);

    }


    private void setViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            txtSourceId.setText(pInboundViewInfo.SourceId);
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

            pInboundViewInfo.SourceId = txtSourceId.getText().toString();
            pInboundViewInfo.ProductId = txtProductId.getText().toString();
            pInboundViewInfo.Qty = txtQtyId.getText().toString();
            pInboundViewInfo.IdentStock = txtStockId.getText().toString();

        } catch (Exception e){

            String s = e.getMessage();
        }
    }

    private void fillDataOptions (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }






    private List<cSpinnerItem> getInfoFilter(){

        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,"ZPZ"));

        return  InfoFilter;
    }


    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PickSourceBD.this);
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
            chkSourceId.setChecked(true);
            oCurrentItemViewInfo.SourceId = txtSourceId.getText().toString();

            vProgressDialog.hide();
        }
    }


    private class AsyncTaskScanProduct extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PickSourceBD.this);
            vProgressDialog.setMessage("Scanning PRODUCT... ");
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

            txtProductId.setText(oCurrentItemViewInfo.ProductId);
            txtQtyId.setText("2");  //  proviene del scaner
            txtStockId.setText("43668");  //  proviene del scaner
            chkProductId.setChecked(true);
            //.setText("43668");

            // se asignan
            oCurrentItemViewInfo.SourceId = txtSourceId.getText().toString();
            oCurrentItemViewInfo.Qty = txtQtyId.getText().toString();
            oCurrentItemViewInfo.IdentStock = txtStockId.getText().toString();

            vProgressDialog.hide();
        }
    }
}

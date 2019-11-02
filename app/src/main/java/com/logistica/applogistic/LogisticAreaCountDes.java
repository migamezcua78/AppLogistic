package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LogisticAreaCountDes extends AppCompatActivity {

    EditText txtProductId;
    EditText txtQtyId;
    EditText txtLuId;
    EditText txtLuQty;
    EditText txtBarCodeId;
    CheckBox chkRestrictedId;
    TextView lblCountItemsId;


    ProgressDialog vProgressDialog;
    AlertDialog  Dialog;

    ArrayList<cAreaInfoView> lsAreaInfoService;
    ArrayList<cAreaInfoView> lsAreaInfoView;

    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;


    Intent intent;

    cAreaInfoView oParamAreaInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_area_count_des);
        init();

        intent = getIntent();
        oParamAreaInfoView = (cAreaInfoView)intent.getSerializableExtra("oDataParam");

        if ( !oParamAreaInfoView.TaskId.trim().isEmpty()){

            AsyncTaskLoadingProduct AsyncTaskScanProduct = new AsyncTaskLoadingProduct();
            AsyncTaskScanProduct.execute("params");
        }
    }

    private void init() {
        txtProductId = findViewById(R.id.txtProductId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtLuId = findViewById(R.id.txtSerialNumberId);
        txtLuQty = findViewById(R.id.txtLuQty);
        txtBarCodeId = findViewById(R.id.txtStockId);
        chkRestrictedId = findViewById(R.id.chkRestrictedId);
        lblCountItemsId = findViewById(R.id.lblCountItemsId);


        lblCountItemsId.setText("0 of 0");
        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 0;

        lsAreaInfoService = new ArrayList<>();
        lsAreaInfoView = new ArrayList<>();

        BuildDialog ();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        oParamAreaInfoView = new cAreaInfoView();
        Intent oIntent = new Intent(this, LogisticAreaCount.class);
        startActivity(oIntent);
    }

    private void setViewInfo(cAreaInfoView pAreaInfoView){

        txtProductId.setText(pAreaInfoView.ProductId);
        txtQtyId.setText(pAreaInfoView.Qty);
        txtLuId.setText(pAreaInfoView.Lu);
        txtLuQty.setText(pAreaInfoView.LuQty);
        txtBarCodeId.setText(pAreaInfoView.BarCode);
        chkRestrictedId.setChecked(pAreaInfoView.Restricted);
    }

    public void RemoveItem (String codigo){
        if (!codigo.trim().isEmpty()){
            for ( cAreaInfoView e:lsAreaInfoView){
                if (e.ProductId.equals(codigo)){
                    lsAreaInfoView.remove(e);
                    return;
                }
            }
        }
    }


    public void   onBackItem(View spinner) {

        if ( iterater >  1){
            iterater = iterater-1;
            lblCountItemsId.setText(  iterater + " of " + lsAreaInfoView.size());

            cAreaInfoView  oAreaInfoView = lsAreaInfoView.get(iterater - 1);
            setViewInfo(oAreaInfoView);
        }

        if (  lsAreaInfoView.size() == 1 ){

            cAreaInfoView  oAreaInfoView = lsAreaInfoView.get(0);
            setViewInfo(oAreaInfoView);
        }
    }

    public void   onNextItem(View spinner) {

         if ( iterater < lsAreaInfoView.size()){
             iterater = iterater+1;
             lblCountItemsId.setText(  iterater + " of " + lsAreaInfoView.size());

             cAreaInfoView  oAreaInfoView = lsAreaInfoView.get(iterater - 1);
             setViewInfo(oAreaInfoView);
         }

         if ( lsAreaInfoView.size() > 0 & iterater == lsAreaInfoView.size()  ){

             cAreaInfoView  oAreaInfoView = lsAreaInfoView.get(iterater-1);
             setViewInfo(oAreaInfoView);
         }
    }



    public void   onConfirmItem(View spinner) {

        if (  txtProductId.getText().toString().trim().isEmpty()   ){

            Toast.makeText(getApplicationContext(),"PRODUCT field is required", Toast.LENGTH_SHORT).show();

        }else if ( txtQtyId.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"QUANTITY field is required", Toast.LENGTH_SHORT).show();
        }
        else{

            cAreaInfoView   cAreaInfoView = new  cAreaInfoView();

            cAreaInfoView.ProductId = txtProductId.getText().toString().trim();
            cAreaInfoView.Qty = txtQtyId.getText().toString().trim();
            cAreaInfoView.Lu = txtLuId.getText().toString().trim();
            cAreaInfoView.LuQty = txtLuQty.getText().toString().trim();
            cAreaInfoView.BarCode = txtBarCodeId.getText().toString().trim();
            cAreaInfoView.Restricted = chkRestrictedId.isChecked();

            // si ya esiste el elemento se debe de eliminar
            RemoveItem(cAreaInfoView.ProductId);

            // se agrega el nuevo Item
            lsAreaInfoView.add(cAreaInfoView);
            lblCountItemsId.setText(lsAreaInfoView.size()  + " of " + lsAreaInfoView.size());
            iterater = lsAreaInfoView.size();

            // se limpian campos
            txtProductId.setText("");
            txtQtyId.setText("");
            txtLuId.setText("");
            txtLuQty.setText("");
            txtBarCodeId.setText("");
            chkRestrictedId.setChecked(false);

            Toast.makeText(getApplicationContext(), "Item Confirmed", Toast.LENGTH_SHORT).show();
        }
    }


    private void BuildDialog (){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(LogisticAreaCountDes.this);
        builder1.setMessage("");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        AsyncTaskFinishTask AsyncTask = new AsyncTaskFinishTask();
                        AsyncTask.execute("params");
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        return;
                    }
                });

        Dialog = builder1.create();

    }


    public void   onConfirmTask(View spinner) {

        String   Porducts = "";
        Boolean  bCounted = false;
        for ( cAreaInfoView e:lsAreaInfoService){
            bCounted = false;
            for (cAreaInfoView e2:lsAreaInfoView){
                if (e2.ProductId.equals(e.ProductId)){
                    bCounted = true;
                }
            }

            if(!bCounted){
                Porducts = e.ProductId + " ";
            }
        }

        if(Porducts.isEmpty()){

            AsyncTaskFinishTask AsyncTask = new AsyncTaskFinishTask();
            AsyncTask.execute("params");

        }else  {
            Dialog.setMessage("Hay productos regitrados en el sistema que no fueron contabilizados. ¿Dese enviar dichos productos con cantidad 0?.");
            Dialog.show();
        }
    }

    public void   onScanProduct(View spinner) {

        AsyncTaskScanProduct AsyncTaskScanProduct = new AsyncTaskScanProduct();
        AsyncTaskScanProduct.execute("params");
    }


    public void   onScanQty(View spinner) {
        AsyncTaskScanQty AsyncTaskScanProduct = new AsyncTaskScanQty();
        AsyncTaskScanProduct.execute("params");
    }



    private class AsyncTaskScanQty extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCountDes.this);
            vProgressDialog.setMessage("Scanning QUANTITY ...");
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
            Quantity = Quantity + 1;
            txtQtyId.setText(String.valueOf(Quantity));
            vProgressDialog.hide();
        }
    }


    private class AsyncTaskScanProduct extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCountDes.this);
            vProgressDialog.setMessage("Scanning PRODUCT...");
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
            consecutive = consecutive  + 1;
            String  sProductId = "KECM0000608030_" + String.valueOf(consecutive);

            txtProductId.setText(sProductId);
            vProgressDialog.hide();
        }
    }


    private class AsyncTaskLoadingProduct extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCountDes.this);
            vProgressDialog.setMessage("Loading Task Info...");
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

            cAreaInfoView  oAreaInfoView = new cAreaInfoView();
            oAreaInfoView.ProductId = "KECM0000608030_1";
            oAreaInfoView.Qty = "4";
            lsAreaInfoService.add(oAreaInfoView);

            oAreaInfoView = new cAreaInfoView();
            oAreaInfoView.ProductId = "KECM0000608030_2";
            oAreaInfoView.Qty = "6";
            lsAreaInfoService.add(oAreaInfoView);

            vProgressDialog.hide();
        }
    }


    private class AsyncTaskFinishTask extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCountDes.this);
            vProgressDialog.setMessage("Sending Data...");
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

            Intent oIntent = new Intent(LogisticAreaCountDes.this, LogisticAreaCount.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oMssg.setMessage("Sending successful");
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);
        }
    }
}

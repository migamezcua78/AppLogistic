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

import java.util.ArrayList;
import java.util.List;

public class Goods_Movement_Source extends AppCompatActivity {

    ProgressDialog vProgressDialog;
    EditText txtSourceId;
    EditText txtProductId;
    EditText txtQtyId;
    EditText txIdentStockId;
    CheckBox chkRestrictedId;
    EditText txtLuId;
    EditText txtLuQtyId;
    EditText txtFieldNameId;
    EditText txtBarCodeId;

    Spinner spinner;

    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods__movement__source);
        init();
        fillDataFilter();
    }

    private void fillDataFilter (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }


    private List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"ea"));
//        InfoFilter.add(new cSpinnerItem(1,"Bar Code"));
//        InfoFilter.add(new cSpinnerItem(1,"Label ID"));
//        InfoFilter.add(new cSpinnerItem(1,"Reference"));

        return  InfoFilter;
    }

    private void init() {

        txtSourceId = findViewById(R.id.txtSourceId);
        txtProductId = findViewById(R.id.txtProductId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txIdentStockId = findViewById(R.id.txtStockId);
        chkRestrictedId = findViewById(R.id.chkRestrictedId);
        txtLuId = findViewById(R.id.txtLuId);
        txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtFieldNameId = findViewById(R.id.txtFieldName);
        txtBarCodeId = findViewById(R.id.txtBarCodeId);
        spinner = findViewById(R.id.spiUnitId);

    }


    public void onScanSource(View view){

        txtSourceId.setText("");
        AsyncTaskScanSource  AsyncTaskScanSource = new AsyncTaskScanSource();
        AsyncTaskScanSource.execute("params");
    }

    public void onScanProduct(View view){
        txtProductId.setText("KECM0000608030");
        txtQtyId.setText("5");
        txIdentStockId.setText("40567");
        txtFieldNameId.setText("1000020");
        AsyncTaskScanProduct  AsyncTaskScanProduct = new AsyncTaskScanProduct();
        AsyncTaskScanProduct.execute("params");
    }

    public void onClickConfirm(View view){

    }


    private class AsyncTaskScanSource extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(Goods_Movement_Source.this);
            vProgressDialog.setMessage("Scanning  Simulation of PRODUCT...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                Thread.sleep(1500);
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
            txtSourceId.setText("13-153-4");
            vProgressDialog.hide();
        }
    }


    private class AsyncTaskScanProduct extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(Goods_Movement_Source.this);
            vProgressDialog.setMessage("Scanning  Simulation of PRODUCT...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                Thread.sleep(1500);
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
            txtProductId.setText("KECM0000608030");
            txtQtyId.setText("5");
            txIdentStockId.setText("40567");
            txtFieldNameId.setText("1000020");

            vProgressDialog.hide();
        }
    }
}

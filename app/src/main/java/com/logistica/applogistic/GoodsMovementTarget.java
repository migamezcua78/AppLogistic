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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GoodsMovementTarget extends AppCompatActivity {

    ProgressDialog vProgressDialog;
    EditText txtTargetId;
    EditText txtProductId;
    EditText txtQtyId;
    EditText txIdentStockId;
    CheckBox chkRestrictedId;
    EditText txtLuId;
    EditText txtLuQtyId;
    EditText txtFieldNameId;
    EditText txtBarCodeId;

    Spinner spinner;

    Intent  intent;
    cMovement oMovementParam;

    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_movement_target);

        init();
        fillDataFilter();

        intent = getIntent();
        oMovementParam = (cMovement)intent.getSerializableExtra("oDataParam");
        SetInitViewValues(oMovementParam);
        String s = "";
    }

    private void init() {

        txtTargetId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txIdentStockId = findViewById(R.id.txtStockId);
        chkRestrictedId = findViewById(R.id.chkRestrictedId);
        txtLuId = findViewById(R.id.txtSerialNumberId);
        txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtFieldNameId = findViewById(R.id.txtFieldName);
        txtBarCodeId = findViewById(R.id.txtBarCode);
        spinner = findViewById(R.id.spiUnitId);
    }


    private void SetInitViewValues( cMovement  pMovement){

        txtProductId.setText(pMovement.ProductId);
        txtQtyId.setText(pMovement.Qty);
        txIdentStockId.setText(pMovement.IdentStock);
        chkRestrictedId.setChecked(pMovement.Restricted);
        txtLuId.setText(pMovement.Lu);
        txtLuQtyId.setText(pMovement.LuQty);
        txtFieldNameId.setText(pMovement.FieldName);
        txtBarCodeId.setText(pMovement.BarCode);
    }

    private void fillDataFilter (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }


    private List<cSpinnerItem> getInfoFilter(){
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"ea"));
        return  InfoFilter;
    }


    public void onScanTarget(View view){

        txtTargetId.setText("");
        AsyncTaskScanTarget AsyncTaskScantarget = new GoodsMovementTarget.AsyncTaskScanTarget();
        AsyncTaskScantarget.execute("params");
    }

    public void onClickConfirm(View view){

        oMovementParam.TargetId = txtTargetId.getText().toString().trim();

        if (  oMovementParam.TargetId.trim().isEmpty()  ){

            Toast.makeText(getApplicationContext(),"TARGET field is required", Toast.LENGTH_SHORT).show();
        }

        else if (  oMovementParam.SourceId.trim().isEmpty()  ){

            Toast.makeText(getApplicationContext(),"SOURCE field is required", Toast.LENGTH_SHORT).show();

        }else if ( oMovementParam.ProductId.trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"PRODUCT field is required", Toast.LENGTH_SHORT).show();
        }
        else if ( oMovementParam.Qty.trim().isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"QTY field is required", Toast.LENGTH_SHORT).show();

        }else{

            oMovementParam.TargetId =txtTargetId.getText().toString();
            AsyncTaskSendingInformation AsyncTaskSendingInformation = new GoodsMovementTarget.AsyncTaskSendingInformation();
            AsyncTaskSendingInformation.execute("params");
        }
    }


    private class AsyncTaskScanTarget extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(GoodsMovementTarget.this);
            vProgressDialog.setMessage("Scanning TARGET...");
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
            txtTargetId.setText("13-154-A");
            vProgressDialog.hide();
        }
    }


    private class AsyncTaskSendingInformation extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(GoodsMovementTarget.this);
            vProgressDialog.setMessage("Sending Information...");
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

            Intent oIntent = new Intent(GoodsMovementTarget.this, Goods_Movement_Source.class);
            cMovement  oMov = new   cMovement();
            oMov.TaskId = oMovementParam.TaskId;
            oMov.msg = "Sending successful";
            oIntent.putExtra("oDataParam",oMov);
            startActivity(oIntent);
        }
    }
}

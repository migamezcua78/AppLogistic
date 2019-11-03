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

public class Goods_Movement_Source extends AppCompatActivity {

    //  Views
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

    // Data
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();
    private  cMovementViewInfo  oCurrentItemViewInfo;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods__movement__source);
        init();
        StartActivity();
    }

    private void init() {

        txtSourceId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txIdentStockId = findViewById(R.id.txtStockId);
        chkRestrictedId = findViewById(R.id.chkRestrictedId);
        txtLuId = findViewById(R.id.txtSerialNumberId);
        txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtFieldNameId = findViewById(R.id.txtFieldName);
        txtBarCodeId = findViewById(R.id.txtBarCode);
        spinner = findViewById(R.id.spiUnitId);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oCurrentItemViewInfo = ((cGlobalData)getApplication()).CurrentMovementViewInfo;
    }

    private  void StartActivity(){

        if(oMsg.getMessage().equals("NewItem")){
            if ( oCurrentItemViewInfo  != null   )
            {
                oCurrentItemViewInfo.Reset();

            }else  {

                cGlobalData  oGlobalData=  (cGlobalData)getApplication();
                oGlobalData.CurrentMovementViewInfo = new cMovementViewInfo();
                oCurrentItemViewInfo =  oGlobalData.CurrentMovementViewInfo;
            }

            getParamInfo();

        }else if (oMsg.getMessage().equals("OtherItem")){

            if ( oCurrentItemViewInfo  != null   )
            {
                setViewInfo();

            }else  {

                oCurrentItemViewInfo = new cMovementViewInfo();
            }
        }

        fillDataFilter();
    }

    private void  getParamInfo() {

        switch (oMsg.getKey01()){
            case "TaskId":
                oCurrentItemViewInfo.TaskId = oMsg.getKey02();
                break;
            case "ReferenceId":
                oCurrentItemViewInfo.ReferenceId = oMsg.getKey02();
                break;
            case "LabelId":
                oCurrentItemViewInfo.LabelId = oMsg.getKey02();
                break;
            case "BarCodeId":
                oCurrentItemViewInfo.BarCodeId = oMsg.getKey02();
                break;
        }
    }

    private void getViewInfo(){

        oCurrentItemViewInfo.SourceId =txtSourceId.getText().toString();
        oCurrentItemViewInfo.ProductId =txtProductId.getText().toString();
        oCurrentItemViewInfo.Qty =txtQtyId.getText().toString();
        oCurrentItemViewInfo.IdentStock =txIdentStockId.getText().toString();
        oCurrentItemViewInfo.Restricted =chkRestrictedId.isChecked();
        oCurrentItemViewInfo.Lu =txtLuId.getText().toString();
        oCurrentItemViewInfo.LuQty =txtLuQtyId.getText().toString();
        oCurrentItemViewInfo.FieldName =txtFieldNameId.getText().toString();
        oCurrentItemViewInfo.BarCode =txtBarCodeId.getText().toString();
        oCurrentItemViewInfo.msg = "";
    }

    private void setViewInfo(){

        txtSourceId.setText(oCurrentItemViewInfo.SourceId);
        txtProductId.setText(oCurrentItemViewInfo.ProductId);
        txtQtyId.setText(oCurrentItemViewInfo.Qty);
        txIdentStockId.setText(oCurrentItemViewInfo.IdentStock);
        chkRestrictedId.setChecked(oCurrentItemViewInfo.Restricted);
        txtLuId.setText(oCurrentItemViewInfo.Lu);
        txtLuQtyId.setText(oCurrentItemViewInfo.LuQty);
        txtFieldNameId.setText(oCurrentItemViewInfo.FieldName);
        txtBarCodeId.setText(oCurrentItemViewInfo.BarCode);
    }



    @Override
    protected void  onResume(){
        super.onResume();

/*        if ( oMovementParam.msg != "")
        {
            Toast.makeText(getApplicationContext(),oMovementParam.msg , Toast.LENGTH_SHORT).show();
            oMovementParam.msg = "";
        }*/
    }

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, GoodMovement.class);
       // oIntent.putExtra("oDataParam",oMovementParam);
        startActivity(oIntent);
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

        if ( txtSourceId.getText().toString().trim().isEmpty()){

            Toast.makeText(getApplicationContext(),"SOURCE field is required", Toast.LENGTH_SHORT).show();

        }else if ( txtProductId.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"PRODUCT field is required", Toast.LENGTH_SHORT).show();
        }
        else if (txtQtyId.getText().toString().trim().isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"QTY field is required", Toast.LENGTH_SHORT).show();

        }else{

            getViewInfo();
            Intent oIntent = new Intent(this, GoodsMovementTarget.class);
           // oIntent.putExtra("oDataParam",oMovementParam);
            startActivity(oIntent);
        }
    }


    private class AsyncTaskScanSource extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(Goods_Movement_Source.this);
            vProgressDialog.setMessage("Scanning SOURCE ...");
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
            txtSourceId.setText("13-153-4");
            vProgressDialog.hide();
        }
    }


    private class AsyncTaskScanProduct extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(Goods_Movement_Source.this);
            vProgressDialog.setMessage("Scanning PRODUCT...");
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
            txtProductId.setText("KECM0000608030");
            txtQtyId.setText("5");
            txIdentStockId.setText("40567");
            txtFieldNameId.setText("1000020");

            vProgressDialog.hide();
        }
    }
}

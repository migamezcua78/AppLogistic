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

    //  Views
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

    // Data
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();
    private  cMovementViewInfo  oCurrentItemViewInfo;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_movement_target);
        init();
        StartActivity();
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

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oCurrentItemViewInfo = ((cGlobalData)getApplication()).CurrentMovementViewInfo;
    }


    private  void StartActivity(){

        if ( oCurrentItemViewInfo  != null   ) {

            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TARGET))
            {
                // oCurrentItemViewInfo.TargetId = oMsg.getKey01();
                // oCurrentItemViewInfo.TargetId = "13-154-A";
                oCurrentItemViewInfo.TargetId = oMsg.getKey01();
            }

            setViewInfo();
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

        oCurrentItemViewInfo.TargetId = txtTargetId.getText().toString();
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

        txtTargetId.setText(oCurrentItemViewInfo.TargetId);
        txtProductId.setText(oCurrentItemViewInfo.ProductId);
        txtQtyId.setText(oCurrentItemViewInfo.Qty);
        txIdentStockId.setText(oCurrentItemViewInfo.IdentStock);
        chkRestrictedId.setChecked(oCurrentItemViewInfo.Restricted);
        txtLuId.setText(oCurrentItemViewInfo.Lu);
        txtLuQtyId.setText(oCurrentItemViewInfo.LuQty);
        txtFieldNameId.setText(oCurrentItemViewInfo.FieldName);
        txtBarCodeId.setText(oCurrentItemViewInfo.BarCode);
    }


    private void SetInitViewValues( cMovementViewInfo pMovement){

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

        getViewInfo();
        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("GoodsMovementTarget",Scanner.ScanType.SCAN_TARGET));
        startActivity(oIntent);

/*        txtTargetId.setText("");
        AsyncTaskScanTarget AsyncTaskScantarget = new GoodsMovementTarget.AsyncTaskScanTarget();
        AsyncTaskScantarget.execute("params");*/
    }

    public void onClickConfirm(View view){

        if ( txtTargetId.getText().toString().trim().isEmpty()){

            Toast.makeText(getApplicationContext(),"TARGET field is required", Toast.LENGTH_SHORT).show();

        }else if ( txtProductId.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"PRODUCT field is required", Toast.LENGTH_SHORT).show();
        }
        else if (txtQtyId.getText().toString().trim().isEmpty() ) {
            Toast.makeText(getApplicationContext(), "QTY field is required", Toast.LENGTH_SHORT).show();

        }
        else if (txtFieldNameId.getText().toString().trim().isEmpty() ) {
            Toast.makeText(getApplicationContext(), "FieldName field is required", Toast.LENGTH_SHORT).show();

        }
        else{

            getViewInfo();
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



    // Save Filter Field
    private void SaveFilterValues(){

        String   TaskId = oCurrentItemViewInfo.TaskId;
        String   ReferenceId = oCurrentItemViewInfo.ReferenceId;
        String   LabelId = oCurrentItemViewInfo.LabelId;
        String   BarCodeId = oCurrentItemViewInfo.BarCodeId;

        oCurrentItemViewInfo.Reset();
        oCurrentItemViewInfo.ReferenceId = ReferenceId;
        oCurrentItemViewInfo.LabelId = LabelId;
        oCurrentItemViewInfo.BarCodeId = BarCodeId;
        oCurrentItemViewInfo.TaskId = TaskId;

    }

    private class AsyncTaskSendingInformation extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(GoodsMovementTarget.this);
            vProgressDialog.setMessage("Sending Item Information...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                //Thread.sleep(1000);

                cServices  oServices = new cServices();
                cInboundDelivery  oInboundDelivery = new cInboundDelivery();

                oInboundDelivery.ID = oCurrentItemViewInfo.TaskId;
                oInboundDelivery.oInboundDeliveryItem.ID = oCurrentItemViewInfo.ProductId;
                oInboundDelivery.oInboundDeliveryItem.CantidadConfirmada = oCurrentItemViewInfo.Qty;
                oInboundDelivery.oInboundDeliveryItem.IDAreaLogistica = oCurrentItemViewInfo.TargetId;
                oInboundDelivery.oInboundDeliveryItem.IDStockIdentificado = oCurrentItemViewInfo.IdentStock;

                oServices.PutInboundDeliveryServiceData(oInboundDelivery);

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

            Toast.makeText(getApplicationContext(), "Item Confirmed", Toast.LENGTH_SHORT).show();

            SaveFilterValues();
            Intent oIntent = new Intent(GoodsMovementTarget.this, Goods_Movement_Source.class);
            oIntent.putExtra("oMsg",new cActivityMessage("OtherItem"));
            startActivity(oIntent);
        }
    }
}

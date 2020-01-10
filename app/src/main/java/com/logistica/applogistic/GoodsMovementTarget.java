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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GoodsMovementTarget extends MainBaseActivity {

    //  Views
   // EditText txtTargetId;
    EditText txtProductId;
    EditText txtQtyId;
    EditText txIdentStockId;
    CheckBox chkRestrictedId;
    EditText txtLuId;
    EditText txtLuQtyId;
    EditText txtFieldNameId;
    EditText txtBarCodeId;
    Spinner spinner;
    Spinner spinnerLogisticAreas;

    // Data
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();
    private List<cSpinnerItem>  InfoFilterLogisticAreas = new ArrayList<>();
    private ArrayList<String>  LsCatalogLogisticAreas;
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

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Goods_Movement_Source.class);
        oIntent.putExtra("oMsg",new cActivityMessage(""));
        startActivity(oIntent);
    }

    private void init() {

        //txtTargetId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txIdentStockId = findViewById(R.id.txtStockId);
        chkRestrictedId = findViewById(R.id.chkRestrictedId);
        txtLuId = findViewById(R.id.txtSerialNumberId);
        txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtFieldNameId = findViewById(R.id.txtFieldName);
        txtBarCodeId = findViewById(R.id.txtBarCode);
        spinner = findViewById(R.id.spiUnitId);
        spinnerLogisticAreas = findViewById(R.id.spiLogisticAreas);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oCurrentItemViewInfo = ((cGlobalData)getApplication()).CurrentMovementViewInfo;
        LsCatalogLogisticAreas = ((cGlobalData)getApplication()).LsCatalogLogisticAreas;

        fillDataFilter();

        if (LsCatalogLogisticAreas == null  || LsCatalogLogisticAreas.size() == 0 ){
            AsyncTaskGetLogisticAreas asyncTask=new AsyncTaskGetLogisticAreas();
            asyncTask.execute("params");
        }else {
            fillDataFilterLogisticAreas();
        }

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

      //  fillDataFilter();
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

        //oCurrentItemViewInfo.TargetId = txtTargetId.getText().toString();
        oCurrentItemViewInfo.TargetId = ((cSpinnerItem)spinnerLogisticAreas.getSelectedItem()).getField();
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

      //  txtTargetId.setText(oCurrentItemViewInfo.TargetId);
        Inicio.selectSpinnerItemByValue(spinnerLogisticAreas, oCurrentItemViewInfo.TargetId);
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

    private void fillDataFilterLogisticAreas (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilterLogisticAreas());
        spinnerLogisticAreas.setAdapter(adapter);
    }


    private List<cSpinnerItem> getInfoFilter(){
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"ZPZ"));
        return  InfoFilter;
    }

    private List<cSpinnerItem> getInfoFilterLogisticAreas(){
        InfoFilterLogisticAreas = new ArrayList<>();
        if (LsCatalogLogisticAreas != null ){

            for ( int i =0; i < LsCatalogLogisticAreas.size(); i++ ){
                InfoFilterLogisticAreas.add(new cSpinnerItem(i,LsCatalogLogisticAreas.get(i),LsCatalogLogisticAreas.get(i)));
            }
        }
        return  InfoFilterLogisticAreas;
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

        getViewInfo();

        if ( oCurrentItemViewInfo.TargetId.trim().isEmpty()){

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

            if ( ((cGlobalData)getApplication()).ReferenceId  != null && !((cGlobalData)getApplication()).ReferenceId.isEmpty()){
                AsyncTaskSendingInformation AsyncTaskSendingInformation = new GoodsMovementTarget.AsyncTaskSendingInformation();
                AsyncTaskSendingInformation.execute("params");
            } else {
                Toast.makeText(getApplicationContext(), "El ID de Referencia es requerido", Toast.LENGTH_LONG).show();
            }
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
            //txtTargetId.setText("13-154-A");
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

    private class AsyncTaskSendingInformation extends AsyncTask<String, String,  cMovementResponse> {
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
        protected cMovementResponse doInBackground(String... strings) {

            cMovementResponse oResponse = new  cMovementResponse();

            try {

                cServices  oServices = new cServices();
                cMovementRequest  oRequest = new cMovementRequest();


/*                oRequest.ExternalID = "MOV01";
                oRequest.SiteID = "E01";
                oRequest.ExternalItemID = "10";
                oRequest.MaterialInternalID = "777";
                oRequest.OwnerPartyInternalID = "E01";
                oRequest.InventoryRestrictedUseIndicator = "false";
                oRequest.SourceLogisticsAreaID = "E01-1";
                oRequest.TargetLogisticsAreaID = "E01-8";
                oRequest.Quantity = "100";
                oRequest.QuantityUnitCode = "ZPZ";*/




                    oRequest.ExternalID =  oCurrentItemViewInfo.ReferenceId;
                    oRequest.SiteID =   oCurrentItemViewInfo.Lu;   // sede
                    oRequest.ExternalItemID =  oCurrentItemViewInfo.FieldName;  // ID externo de posición
                    oRequest.MaterialInternalID =  oCurrentItemViewInfo.ProductId;
                    oRequest.OwnerPartyInternalID = oCurrentItemViewInfo.LuQty;  //  Empresa
                    oRequest.InventoryRestrictedUseIndicator = String.valueOf(oCurrentItemViewInfo.Restricted);
                    oRequest.SourceLogisticsAreaID = oCurrentItemViewInfo.SourceId;
                    oRequest.TargetLogisticsAreaID = oCurrentItemViewInfo.TargetId;
                    oRequest.Quantity = oCurrentItemViewInfo.Qty;
                    oRequest.QuantityUnitCode = oCurrentItemViewInfo.QtyUnitCode;

                    // opcionales
                    oRequest.IdentifiedStockID =  oCurrentItemViewInfo.IdentStock;
                    oRequest.SerialID =  oCurrentItemViewInfo.BarCode;



                    //oRequest.QuantityUnitCode = "";

                    oResponse = oServices.PutMovementServiceData(oRequest);



                //Thread.sleep(1000);

/*                cServices  oServices = new cServices();
                cInboundDelivery  oInboundDelivery = new cInboundDelivery();

                oInboundDelivery.ID = oCurrentItemViewInfo.TaskId;
                oInboundDelivery.oInboundDeliveryItem.ID = oCurrentItemViewInfo.ProductId;
                oInboundDelivery.oInboundDeliveryItem.CantidadConfirmada = oCurrentItemViewInfo.Qty;
                oInboundDelivery.oInboundDeliveryItem.IDAreaLogistica = oCurrentItemViewInfo.TargetId;
                oInboundDelivery.oInboundDeliveryItem.IDStockIdentificado = oCurrentItemViewInfo.IdentStock;

                oServices.PutInboundDeliveryServiceData(oInboundDelivery);*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return oResponse;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(cMovementResponse lsData) {
            super.onPostExecute(lsData);
            vProgressDialog.hide();

            if( !lsData.GACID.isEmpty()   &&   lsData.GACID != "0"){
                Toast.makeText(getApplicationContext(), "Movimiento Realizado Correctamente, ID de movimiento: " + lsData.GACID , Toast.LENGTH_LONG).show();

                SaveFilterValues();
                Intent oIntent = new Intent(GoodsMovementTarget.this, Goods_Movement_Source.class);
                oIntent.putExtra("oMsg",new cActivityMessage("OtherItem"));
                startActivity(oIntent);

            } else {
                Toast.makeText(getApplicationContext(), lsData.MSG, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AsyncTaskGetLogisticAreas extends AsyncTask<String, String,  ArrayList<cLogisticsArea>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(GoodsMovementTarget.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cLogisticsArea> doInBackground(String... strings) {
            ArrayList<cLogisticsArea>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetLogisticAreaServiceData(cServices.LogisticAreaFilterType.SelectionByID,"*","");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsData;
        }

        @Override
        protected void onProgressUpdate(String... values){
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cLogisticsArea> lsData) {
            super.onPostExecute(lsData);
            Set<String> oSet =  new HashSet<>();
            LsCatalogLogisticAreas = new ArrayList<>();

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cLogisticsArea  oData  = lsData.get(i);

                if (!oData.ID.equals("")){
                    oSet.add(oData.ID);
                }
            }

            if ( oSet.size() > 0){

                LsCatalogLogisticAreas.addAll(oSet);
                Collections.sort(LsCatalogLogisticAreas);
                ((cGlobalData)getApplication()).LsCatalogLogisticAreas = LsCatalogLogisticAreas;
            }

            fillDataFilterLogisticAreas();
            vProgressDialog.hide();
        }
    }
}

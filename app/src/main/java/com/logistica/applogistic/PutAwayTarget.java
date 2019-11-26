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
   // private EditText txtLuQtyId;
    private EditText txtBarCodeId;
    private CheckBox cheRestrictedId;
    private CheckBox chkConfirmedId;

    private TextView lblCountItemsId;


   // cActivityMessage   oActivityMessage;
    ArrayList<cInboundViewInfo>  lsInbounItems;
    cInboundViewInfo  oCurrentInboundViewInfo;
    cProductViewInfo oCurrectProductViewInfo;
    cGlobalData  oGlobalData;

    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;

    String   sSerialNumber;

    boolean isProductAssigned;


    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away_target);
        init();
        StartActivity();
    }

    private void init (){

        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 1;


        txtTargetId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtSerialNumberId = findViewById(R.id.txtSerialNumberId);
       // txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtBarCodeId = findViewById(R.id.txtBarCodeId);
        cheRestrictedId = findViewById(R.id.cheRestrictedId);
        chkConfirmedId = findViewById(R.id.chkConfirmedId);

        lblCountItemsId = findViewById(R.id.lblCountItemsId);

        spinner = findViewById(R.id.spiUnitId);

        isProductAssigned = false;

        sSerialNumber = "SNID88046999927";

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oGlobalData=  (cGlobalData)getApplication();
        lsInbounItems = oGlobalData.LsIntboudItems;
        oCurrentInboundViewInfo =  oGlobalData.CurrentInboundViewInfo;
    }

    public void  StartActivity (){

        if (oMsg.getMessage().equals("ItemConfirmed")){

            Boolean finish = true;
            for ( cInboundViewInfo e:lsInbounItems){
                if ( !e.Confirmed)  {
                    finish = false;
                    break;
                }
            }

            if (finish){

                AsyncTaskAllItemConfirmed asyncTask=new AsyncTaskAllItemConfirmed();
                asyncTask.execute("params");

            } else {

                for(int i = 0; i <  lsInbounItems.size(); i++ ){

                    cInboundViewInfo   e = lsInbounItems.get(i);
                    if ( e.ProductId == oCurrentInboundViewInfo.ProductId){
                        iterater =  i + 1;
                        lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsInbounItems.size()) );
                        setViewInfo(oCurrentInboundViewInfo);
                    }
                }
            }


        }else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){

            lsInbounItems = oGlobalData.LsIntboudItems;
            oCurrentInboundViewInfo =  oGlobalData.CurrentInboundViewInfo;

            txtBarCodeId.setText(oMsg.getKey01());
            oCurrentInboundViewInfo.BarCode = txtBarCodeId.getText().toString();

            for(int i = 0; i <  lsInbounItems.size(); i++ ){

                cInboundViewInfo   e = lsInbounItems.get(i);
                if ( e.ProductId == oCurrentInboundViewInfo.ProductId){
                    iterater =  i + 1;
                    lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsInbounItems.size()) );
                    setViewInfo(oCurrentInboundViewInfo);
                }
            }

            oCurrectProductViewInfo = new cProductViewInfo();
            oCurrectProductViewInfo.ProductoSAPId = oCurrentInboundViewInfo.ProductId;
            oCurrectProductViewInfo.CodigoBarra = oCurrentInboundViewInfo.BarCode;

            AsyncTaskValidateProduct asyncTask=new AsyncTaskValidateProduct();
            asyncTask.execute("params");

           // setViewInfo(oCurrentInboundViewInfo);
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
            chkConfirmedId.setChecked(pInboundViewInfo.Confirmed);
          //  txtLuQtyId.setText(pInboundViewInfo.LuQty);
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
            pInboundViewInfo.Confirmed = chkConfirmedId.isChecked();
         //   pInboundViewInfo.LuQty = txtLuQtyId.getText().toString();
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
        oIntent.putExtra("oMsg", new cActivityMessage("PutAwayTarget",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);


/*        txtSerialNumberId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }


    public void   onClickConfirm(View spinner) {

        if (  txtTargetId.getText().toString().isEmpty() ){
            Toast.makeText(getApplicationContext(),"El Área Logística es requerida", Toast.LENGTH_SHORT).show();
        }  else {

            getViewInfo(oCurrentInboundViewInfo);
            oGlobalData.CurrentInboundViewInfo = oCurrentInboundViewInfo;


            Intent oIntent = new Intent(this, PickSource.class);
            oIntent.putExtra("oMsg",  new cActivityMessage(""));
            startActivity(oIntent);
        }
    }

    public void   onClickNext(View spinner) {

        if ( iterater < lsInbounItems.size()){
            iterater = iterater+1;
            lblCountItemsId.setText(  iterater + " of " + lsInbounItems.size());

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentInboundViewInfo);

            // se obtiene el sigiente
            oCurrentInboundViewInfo = lsInbounItems.get(iterater - 1);
            setViewInfo(oCurrentInboundViewInfo);
        }

        if ( lsInbounItems.size() > 0 && iterater == lsInbounItems.size()  ){

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentInboundViewInfo);

            oCurrentInboundViewInfo = lsInbounItems.get(iterater-1);
            setViewInfo(oCurrentInboundViewInfo);
        }


        //Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
      //  Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();

        if ( iterater >  1){
            iterater = iterater-1;
            lblCountItemsId.setText(  iterater + " of " + lsInbounItems.size());

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentInboundViewInfo);

            oCurrentInboundViewInfo = lsInbounItems.get(iterater - 1);
            setViewInfo(oCurrentInboundViewInfo);
        }

        if (  lsInbounItems.size() == 1 ){

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentInboundViewInfo);

            oCurrentInboundViewInfo = lsInbounItems.get(0);
            setViewInfo(oCurrentInboundViewInfo);
        }
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


    private class AsyncTaskValidateProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PutAwayTarget.this);
            vProgressDialog.setMessage("Validando Producto...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected cProductResponse doInBackground(String... strings) {
            cProductResponse oResp = new  cProductResponse();

            try {

                cServices ocServices = new cServices();

                //oCurrectProductViewInfo.ProductoSAPId = "1";
                //    oCurrectProductViewInfo.Nombre = "productoPrueba3";
                //   oCurrectProductViewInfo.Descripcion = "productoPruebaDescripcion3";
               // oCurrectProductViewInfo.CodigoBarra = "12312312312";
                //      oCurrectProductViewInfo.Estado = "Activo";

                //oCurrectProductViewInfo.Usuario = "tcabrera";

                oResp = ocServices.PostProductAssignedDataService(oCurrectProductViewInfo);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return oResp;
        }

        @Override
        protected void onProgressUpdate(String... values){
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(cProductResponse lsData) {
            super.onPostExecute(lsData);

            if  (lsData != null){

                if(!lsData.ResponseId.equals("-1")){

                    if (lsData.Assigned){

                        Toast.makeText(getApplicationContext(),"Producto ASIGNADO" , Toast.LENGTH_LONG).show();
                        isProductAssigned = true;

                        if (isProductAssigned){
                            int inQtyId =  Integer.valueOf(oCurrentInboundViewInfo.Qty);
                            inQtyId = inQtyId + 1;
                            oCurrentInboundViewInfo.Qty = String.valueOf(inQtyId);
                            txtQtyId.setText(oCurrentInboundViewInfo.Qty);
                        }

                    } else {

                        Toast.makeText(getApplicationContext(),"Producto NO ASIGNADO" , Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Error al intentar registrar el producto " , Toast.LENGTH_LONG).show();
                }
            }

            vProgressDialog.hide();
        }
    }
}

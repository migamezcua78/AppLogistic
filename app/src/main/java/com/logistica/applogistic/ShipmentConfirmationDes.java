package com.logistica.applogistic;

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

public class ShipmentConfirmationDes extends MainBaseActivity {


    private Spinner spinner;

    // private EditText txtTargetId;
    private EditText txtProductId;
    private TextView lblOpenValueId;
    private EditText txtQtyId;
    private EditText txtSerialNumberId;
    // private EditText txtLuQtyId;
    private EditText txtBarCodeId;
    private CheckBox cheRestrictedId;
    private CheckBox chkConfirmedId;
    private EditText txtProductName;

    private EditText txtCantidadCaja;

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
    int CantidadCaja;


    String   sSerialNumber;

    String   sSerialNumberGet;

    boolean isProductAssigned;

    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_confirmation_des);

        init();
        StartActivity();
    }


    public void onBackPressed() {
        // super.onBackPressed();

        Intent oIntent = new Intent(this, ShipmentConfirmation.class);
        oIntent.putExtra("oMsg", new cActivityMessage("ReStart"));
        startActivity(oIntent);
    }


    private void init (){

        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 1;
        CantidadCaja = 1;


        //txtTargetId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtSerialNumberId = findViewById(R.id.txtSerialNumberId);
        // txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtBarCodeId = findViewById(R.id.txtBarCodeId);
        cheRestrictedId = findViewById(R.id.cheRestrictedId);
        chkConfirmedId = findViewById(R.id.chkConfirmedId);
        lblCountItemsId = findViewById(R.id.lblCountItemsId);
        txtProductName = findViewById(R.id.txtProductName);
        txtCantidadCaja = findViewById(R.id.txtCantidadCaja);




        spinner = findViewById(R.id.spiUnitId);

        isProductAssigned = false;

        sSerialNumber = "SNID88046999927";

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oGlobalData=  (cGlobalData)getApplication();
        lsInbounItems = oGlobalData.LsIntboudIShipmentItems;
        oCurrentInboundViewInfo =  oGlobalData.CurrentInboundShipmentViewInfo;
    }

    public void  StartActivity (){

        if (oMsg.getMessage().equals("ItemConfirmed"))
        {

            Boolean finish = true;
            for ( cInboundViewInfo e:lsInbounItems){
                if ( !e.Confirmed)  {
                    finish = false;
                    break;
                }
            }

            oCurrentInboundViewInfo.ConfirmedOty =  oCurrentInboundViewInfo.Qty;

            //  int  dif = Integer.valueOf(oCurrentInboundViewInfo.PlanedQty) - Integer.valueOf(oCurrentInboundViewInfo.ConfirmedOty);
            //  oCurrentInboundViewInfo.QtyDiff = String.valueOf(dif);

            // oCurrentInboundViewInfo.QtyDiff =  oCurrentInboundViewInfo.Qty;

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


        } else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){

            lsInbounItems = oGlobalData.LsIntboudIShipmentItems;
            oCurrentInboundViewInfo =  oGlobalData.CurrentInboundShipmentViewInfo;

            txtSerialNumberId.setText(oMsg.getKey01());
            oCurrentInboundViewInfo.SerialNumber = txtSerialNumberId.getText().toString();
            sSerialNumberGet = txtSerialNumberId.getText().toString();

//            int iQty = 0;
//            if(!oCurrentInboundViewInfo.Qty.trim().isEmpty()){
//                iQty = Integer.parseInt(oCurrentInboundViewInfo.Qty);
//            }
//            iQty =  iQty + 1;
//            oCurrentInboundViewInfo.Qty =  String.valueOf(iQty);

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

            // mig: por ahora no se valida el producto, hsta que se confirme por el usuario
              AsyncTaskValidateProduct asyncTask=new AsyncTaskValidateProduct();
              asyncTask.execute("params");

            // setViewInfo(oCurrentInboundViewInfo);
        }

        else {

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

            //   txtTargetId.setText(pInboundViewInfo.TargetId);
            txtProductName.setText(pInboundViewInfo.ProductName);
            txtProductId.setText(pInboundViewInfo.ProductId);
            txtQtyId.setText(pInboundViewInfo.Qty);
          //  cheRestrictedId.setChecked(pInboundViewInfo.Restricted);
            chkConfirmedId.setChecked(pInboundViewInfo.Confirmed);
            //  txtLuQtyId.setText(pInboundViewInfo.LuQty);
          //  txtBarCodeId.setText(pInboundViewInfo.BarCode);
            // lblOpenValueId.setText(pInboundViewInfo.Open + " " +  pInboundViewInfo.OpenUnit);

            txtSerialNumberId.setText(pInboundViewInfo.SerialNumber);


/*
            int  Dif = 0;
            int  Qty = 0;
            int  PlanedOty = Integer.valueOf(pInboundViewInfo.PlanedQty);

           if (!pInboundViewInfo.Qty.trim().isEmpty()){
               Qty = Integer.valueOf(pInboundViewInfo.Qty.trim());
           }

            Dif = PlanedOty - Qty;
            lblOpenValueId.setText(Dif + "    " +  pInboundViewInfo.OpenUnit);
*/


/*
            int  Dif = 0;
            int  ConfirmedQty = 0;
            int  PlanedOty = Integer.valueOf(pInboundViewInfo.PlanedQty);

            if (!pInboundViewInfo.ConfirmedOty.isEmpty()){
                ConfirmedQty = Integer.valueOf(pInboundViewInfo.ConfirmedOty.trim());
            }

            Dif = PlanedOty - ConfirmedQty;
*/

           // lblOpenValueId.setText(Inicio.Dif(pInboundViewInfo.PlanedQty,pInboundViewInfo.ConfirmedOty) + "      " +  pInboundViewInfo.OpenUnit);

            lblOpenValueId.setText(pInboundViewInfo.PlanedQty + "      " +  pInboundViewInfo.OpenUnit);

            // lblOpenValueId.setText(Dif + "      " +  pInboundViewInfo.OpenUnit);
            // lblOpenValueId.setText(pInboundViewInfo.Open + " " +  pInboundViewInfo.OpenUnit);



        } catch (Exception e){

            String s = e.getMessage();
        }
    }

    private void getViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            //  pInboundViewInfo.TargetId = txtTargetId.getText().toString();
            pInboundViewInfo.ProductId = txtProductId.getText().toString();

            if (txtQtyId.getText().toString().isEmpty()){
                pInboundViewInfo.Qty = "0";
            } else    {
                pInboundViewInfo.Qty = txtQtyId.getText().toString();
            }

          //  pInboundViewInfo.Restricted = cheRestrictedId.isChecked();
            pInboundViewInfo.Confirmed = chkConfirmedId.isChecked();
            //   pInboundViewInfo.LuQty = txtLuQtyId.getText().toString();
           // pInboundViewInfo.BarCode = txtBarCodeId.getText().toString();
           //pInboundViewInfo.SerialNumber = txtSerialNumberId.getText().toString();


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

        oGlobalData.CurrentInboundShipmentViewInfo = oCurrentInboundViewInfo;

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("ShipmentConfirmationDes",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);


/*        txtSerialNumberId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }

    public void   onClickConfirm(View spinner) {

        if (  txtProductId.getText().toString().isEmpty() ){
            Toast.makeText(getApplicationContext(),"El ID de producto es requerido", Toast.LENGTH_SHORT).show();
        }  else {

            getViewInfo(oCurrentInboundViewInfo);
            oGlobalData.CurrentInboundShipmentViewInfo = oCurrentInboundViewInfo;

            oCurrentInboundViewInfo.ConfirmedOty =  oCurrentInboundViewInfo.Qty;
            oCurrentInboundViewInfo.Confirmed = true;

            Toast.makeText(getApplicationContext(),"Item Confirmed", Toast.LENGTH_SHORT).show();

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



/*            Intent oIntent = new Intent(this, PickSource.class);
            oIntent.putExtra("oMsg",  new cActivityMessage(""));
            startActivity(oIntent);*/
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

        InfoFilter.add(new cSpinnerItem(1,"ZPZ"));
        // InfoFilter.add(new cSpinnerItem(1,"pick"));

        return  InfoFilter;
    }

    private class AsyncTaskValidateProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(ShipmentConfirmationDes.this);
            vProgressDialog.setMessage("Obteniendo Cantidad...");
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


              //  oCurrectProductViewInfo.CodigoBarra =  "1313123123";

                oResp = ocServices.PostProductAssignedDataService_C_Desembarque(oCurrectProductViewInfo, sSerialNumberGet);

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

            CantidadCaja = 1;
            txtCantidadCaja.setText("1");

            if  (lsData != null){

                if(!lsData.ResponseId.equals("-1")){

                    if(!lsData.CantidadCaja.equals("")){
                        CantidadCaja =  Integer.valueOf(lsData.CantidadCaja);
                        txtCantidadCaja.setText(lsData.CantidadCaja);
                    }
                }
            }

            int iQty = 0;
            if(!oCurrentInboundViewInfo.Qty.trim().isEmpty()){
                iQty = Integer.parseInt(oCurrentInboundViewInfo.Qty);
            }
            iQty =  iQty + CantidadCaja;
            oCurrentInboundViewInfo.Qty =  String.valueOf(iQty);


            setViewInfo(oCurrentInboundViewInfo);

            vProgressDialog.hide();
        }
    }


    private class AsyncTaskAllItemConfirmed extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(ShipmentConfirmationDes.this);
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

            Intent oIntent = new Intent(ShipmentConfirmationDes.this, ShipmentConfirmationConf.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);
        }
    }
}

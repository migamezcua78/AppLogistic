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

public class LogisticAreaCountDes extends MainBaseActivity {

    EditText txtProductId;
    EditText txtQtyId;
   // EditText txtLuId;
    //EditText txtLuQty;
    EditText txtBarCodeId;
    CheckBox chkRestrictedId;
    TextView lblCountItemsId;

    ArrayList<cAreaInfoView> lsAreaInfoService;
    ArrayList<cAreaInfoView> lsAreaInfoView;

    cAreaInfoView oCurrenAreaInfoView;
    cProductViewInfo oCurrectProductViewInfo;

    // Process
    ProgressDialog vProgressDialog;
    AlertDialog  Dialog;
    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;
    int IterScan;


    String  sAreaId;
    Intent intent;

    // cAreaInfoView oParamAreaInfoView;
    cActivityMessage oMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_area_count_des);
        init();
        StartActivity();
    }

    private void init() {
        txtProductId = findViewById(R.id.txtProductId);
        txtQtyId = findViewById(R.id.txtQtyId);
        //txtLuId = findViewById(R.id.txtSerialNumberId);
       // txtLuQty = findViewById(R.id.txtLuQty);
        txtBarCodeId = findViewById(R.id.txtBarCodeId);
        chkRestrictedId = findViewById(R.id.chkRestrictedId);
        lblCountItemsId = findViewById(R.id.lblCountItemsId);

        lblCountItemsId.setText("0 of 0");
        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 0;
        IterScan = 1;

        lsAreaInfoService = new ArrayList<>();
        lsAreaInfoView = new ArrayList<>();
        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");


        BuildDialog ();
    }


    public void StartActivity (){

        if (oMsg.getMessage().equals(cMessage.Message.START)){

            sAreaId  = oMsg.getKey01();

            if ( !sAreaId.trim().isEmpty()){

                AsyncTaskLoadDataService AsyncTaskScanProduct = new AsyncTaskLoadDataService();
                AsyncTaskScanProduct.execute("params");
            }

        } else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_PRODUCT)){


            oCurrenAreaInfoView =  ((cGlobalData)getApplication()).CurrenAreaInfoView;
            lsAreaInfoService =  ((cGlobalData)getApplication()).lsAreaInfoService;
            lsAreaInfoView =  ((cGlobalData)getApplication()).lsAreaInfoView;

            consecutive = ((cGlobalData)getApplication()).consecutive;
            Quantity = ((cGlobalData)getApplication()).Quantity;
            iterater = ((cGlobalData)getApplication()).iterater;
            IterScan = ((cGlobalData)getApplication()).IterScan;


            lblCountItemsId.setText(((cGlobalData)getApplication()).lblCountItemsId);


            IterScan +=  1;
            String  sProductId = oMsg.getKey01();  // "KECM0000608030_" + String.valueOf(IterScan);
            txtProductId.setText(sProductId);

            oCurrenAreaInfoView.ProductId = sProductId;

            setViewInfo(oCurrenAreaInfoView);

        }
        else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_QTY)){


            oCurrenAreaInfoView =  ((cGlobalData)getApplication()).CurrenAreaInfoView;
            lsAreaInfoService =  ((cGlobalData)getApplication()).lsAreaInfoService;
            lsAreaInfoView =  ((cGlobalData)getApplication()).lsAreaInfoView;

            consecutive = ((cGlobalData)getApplication()).consecutive;
            Quantity = ((cGlobalData)getApplication()).Quantity;
            iterater = ((cGlobalData)getApplication()).iterater;
            IterScan = ((cGlobalData)getApplication()).IterScan;

            lblCountItemsId.setText(((cGlobalData)getApplication()).lblCountItemsId);

            oCurrenAreaInfoView.Qty = String.valueOf(IterScan);

            setViewInfo(oCurrenAreaInfoView);

        }  else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){


            oCurrenAreaInfoView =  ((cGlobalData)getApplication()).CurrenAreaInfoView;
            lsAreaInfoService =  ((cGlobalData)getApplication()).lsAreaInfoService;
            lsAreaInfoView =  ((cGlobalData)getApplication()).lsAreaInfoView;

            consecutive = ((cGlobalData)getApplication()).consecutive;
            Quantity = ((cGlobalData)getApplication()).Quantity;
            iterater = ((cGlobalData)getApplication()).iterater;
            IterScan = ((cGlobalData)getApplication()).IterScan;

            lblCountItemsId.setText(((cGlobalData)getApplication()).lblCountItemsId);

            oCurrenAreaInfoView.BarCode = oMsg.getKey01();

            setViewInfo(oCurrenAreaInfoView);

            oCurrectProductViewInfo = new cProductViewInfo();
            oCurrectProductViewInfo.ProductoSAPId = oCurrenAreaInfoView.ProductId;
            oCurrectProductViewInfo.CodigoBarra = oCurrenAreaInfoView.BarCode;

            AsyncTaskValidateProduct asyncTask=new AsyncTaskValidateProduct();
            asyncTask.execute("params");
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // oMsg = new cAreaInfoView();
        Intent oIntent = new Intent(this, LogisticAreaCount.class);
        startActivity(oIntent);
    }

    private void setViewInfo(cAreaInfoView pAreaInfoView){

        txtProductId.setText(pAreaInfoView.ProductId);
        txtQtyId.setText(pAreaInfoView.Qty);
        //txtLuId.setText(pAreaInfoView.Lu);
        //txtLuQty.setText(pAreaInfoView.LuQty);
        txtBarCodeId.setText(pAreaInfoView.BarCode);
        chkRestrictedId.setChecked(pAreaInfoView.Restricted);
    }

    private void getViewInfo(cAreaInfoView pAreaInfoView){

        pAreaInfoView.ProductId = txtProductId.getText().toString().trim();
        pAreaInfoView.Qty = txtQtyId.getText().toString().trim();
      //  pAreaInfoView.Lu = txtLuId.getText().toString().trim();
      //  pAreaInfoView.LuQty = txtLuQty.getText().toString().trim();
        pAreaInfoView.BarCode = txtBarCodeId.getText().toString().trim();
        pAreaInfoView.Restricted = chkRestrictedId.isChecked();
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
            //cAreaInfoView.Lu = txtLuId.getText().toString().trim();
           // cAreaInfoView.LuQty = txtLuQty.getText().toString().trim();
            cAreaInfoView.BarCode = txtBarCodeId.getText().toString().trim();
            cAreaInfoView.Restricted = chkRestrictedId.isChecked();

            // si ya esiste el elemento se debe de eliminar

            RemoveItem(cAreaInfoView.ProductId);

            // se agrega el nuevo Item
            lsAreaInfoView.add(cAreaInfoView);
            lblCountItemsId.setText(lsAreaInfoView.size()  + " of " + lsAreaInfoView.size());
            iterater = lsAreaInfoView.size();


            ((cGlobalData)getApplication()).lsAreaInfoView = lsAreaInfoView;


            // se limpian campos
            txtProductId.setText("");
            txtQtyId.setText("");
            //txtLuId.setText("");
           // txtLuQty.setText("");
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

        if(oCurrenAreaInfoView ==  null)
            oCurrenAreaInfoView = new cAreaInfoView();

        getViewInfo(oCurrenAreaInfoView);

        ((cGlobalData)getApplication()).CurrenAreaInfoView = oCurrenAreaInfoView;
        ((cGlobalData)getApplication()).lsAreaInfoService = lsAreaInfoService;
        ((cGlobalData)getApplication()).lsAreaInfoView = lsAreaInfoView;

        ((cGlobalData)getApplication()).consecutive = consecutive;
        ((cGlobalData)getApplication()).Quantity = Quantity;
        ((cGlobalData)getApplication()).iterater = iterater;
        ((cGlobalData)getApplication()).IterScan = IterScan;

        ((cGlobalData)getApplication()).lblCountItemsId = lblCountItemsId.getText().toString();



        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("LogisticAreaCountDes",Scanner.ScanType.SCAN_PRODUCT));
        startActivity(oIntent);

//        AsyncTaskScanProduct AsyncTaskScanProduct = new AsyncTaskScanProduct();
//        AsyncTaskScanProduct.execute("params");
    }


    public void   onScanQty(View spinner) {

        if(oCurrenAreaInfoView ==  null)
            oCurrenAreaInfoView = new cAreaInfoView();

        getViewInfo(oCurrenAreaInfoView);

        ((cGlobalData)getApplication()).CurrenAreaInfoView = oCurrenAreaInfoView;
        ((cGlobalData)getApplication()).lsAreaInfoService = lsAreaInfoService;
        ((cGlobalData)getApplication()).lsAreaInfoView = lsAreaInfoView;

        ((cGlobalData)getApplication()).consecutive = consecutive;
        ((cGlobalData)getApplication()).Quantity = Quantity;
        ((cGlobalData)getApplication()).iterater = iterater;
        ((cGlobalData)getApplication()).IterScan = IterScan;

        ((cGlobalData)getApplication()).lblCountItemsId = lblCountItemsId.getText().toString();

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("LogisticAreaCountDes",Scanner.ScanType.SCAN_QTY));
        startActivity(oIntent);

//        AsyncTaskScanQty AsyncTaskScanProduct = new AsyncTaskScanQty();
//        AsyncTaskScanProduct.execute("params");
    }


    public void   onScanBarCode(View spinner) {

        if(oCurrenAreaInfoView ==  null)
            oCurrenAreaInfoView = new cAreaInfoView();

        getViewInfo(oCurrenAreaInfoView);

        ((cGlobalData)getApplication()).CurrenAreaInfoView = oCurrenAreaInfoView;
        ((cGlobalData)getApplication()).lsAreaInfoService = lsAreaInfoService;
        ((cGlobalData)getApplication()).lsAreaInfoView = lsAreaInfoView;

        ((cGlobalData)getApplication()).consecutive = consecutive;
        ((cGlobalData)getApplication()).Quantity = Quantity;
        ((cGlobalData)getApplication()).iterater = iterater;
        ((cGlobalData)getApplication()).IterScan = IterScan;

        ((cGlobalData)getApplication()).lblCountItemsId = lblCountItemsId.getText().toString();

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("LogisticAreaCountDes",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);

//        AsyncTaskScanQty AsyncTaskScanProduct = new AsyncTaskScanQty();
//        AsyncTaskScanProduct.execute("params");
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
                //Thread.sleep(1000);

                if ( ((cGlobalData)getApplication()).CurrentArea != null){

                    cServices  oServices = new cServices();

                    for ( cAreaInfoView e:lsAreaInfoView){

                        cInboundDelivery  oInboundDelivery = new cInboundDelivery();

                        oInboundDelivery.ID = ((cGlobalData)getApplication()).CurrentArea ;
                        oInboundDelivery.oInboundDeliveryItem.ID = e.ProductId;
                        oInboundDelivery.oInboundDeliveryItem.CantidadConfirmada = e.Qty;
                        oInboundDelivery.oInboundDeliveryItem.IDAreaLogistica = ((cGlobalData)getApplication()).CurrentArea ;
                        oInboundDelivery.oInboundDeliveryItem.IDStockIdentificado = e.IdentStock;

                        oServices.PutInboundDeliveryServiceData(oInboundDelivery);
                    }


                    for ( cAreaInfoView e:lsAreaInfoService){

                        cInboundDelivery  oInboundDelivery = new cInboundDelivery();

                        oInboundDelivery.ID = ((cGlobalData)getApplication()).CurrentArea;
                        oInboundDelivery.oInboundDeliveryItem.ID = e.ProductId;
                        oInboundDelivery.oInboundDeliveryItem.CantidadConfirmada = e.Qty;
                        oInboundDelivery.oInboundDeliveryItem.IDAreaLogistica =((cGlobalData)getApplication()).CurrentArea;
                        oInboundDelivery.oInboundDeliveryItem.IDStockIdentificado = e.IdentStock;

                        oServices.PutInboundDeliveryServiceData(oInboundDelivery);
                    }

                }

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



    private class AsyncTaskLoadDataService extends AsyncTask<String, String,  ArrayList<cStock>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCountDes.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cStock> doInBackground(String... strings) {
            ArrayList<cStock> lsData = new ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetStockServiceData(cServices.StockFilterType.CLOG_AREA_UUID, sAreaId, "");

/*                ArrayList<cMaterial>  lsMaterials = new ArrayList<>();
                for (cStock  e: lsData){
                    lsMaterials = ocServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID, e.CMATERIAL_UUID, "");
                    if (lsMaterials != null && lsMaterials.size() > 0 ) {
                        e.PRODUCT_ID = lsMaterials.get(0).ProductCategoryID;
                    }
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            return lsData;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cStock> lsData) {

            super.onPostExecute(lsData);

            lsAreaInfoService = new  ArrayList<>();
            cAreaInfoView  oInboundViewInfo = new cAreaInfoView();

            for (int i = 0; i < lsData.size(); i++) {
                cStock oStock = lsData.get(i);

                if( !oStock.CMATERIAL_UUID.trim().isEmpty()){

                    oCurrenAreaInfoView= new  cAreaInfoView();
                    oCurrenAreaInfoView.ProductId = oStock.PRODUCT_ID;
                    lsAreaInfoService.add(oCurrenAreaInfoView);

                    // InfoData.add(new String[]{ oInboundViewInfo.ProductId, oInboundViewInfo.Open + "  " + oInboundViewInfo.OpenUnit });
                }
            }

            Toast.makeText(getApplicationContext(),lsAreaInfoService.size() +  " items founded", Toast.LENGTH_SHORT).show();

            //  cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            //  oGlobalData.LsAr = lsAreaInfoService;

            //   oDataGrid.RemoveAllItems();
            //   fillDataGrid();



            vProgressDialog.hide();
        }
    }


    private class AsyncTaskValidateProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCountDes.this);
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

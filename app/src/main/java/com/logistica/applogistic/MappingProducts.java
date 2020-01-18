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


public class MappingProducts extends MainBaseActivity {

    // View Info
    EditText txtProductId;
    EditText txtBarCodeId;
    EditText txtNombreId;
    EditText txtDescripcionId;
    CheckBox chkActivoId;
    Spinner spinnerStock;


    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;

//  Data
    public  cProductViewInfo oCurrectProductViewInfo;
    private List<cSpinnerItem> InfoFilterStock = new ArrayList<>();
    private ArrayList<String>  LsFilterStock;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_products);
        init();
        StartActivity();
    }

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Inicio.class);
        oIntent.putExtra("oMsg", new cActivityMessage(""));
        startActivity(oIntent);
    }

    public void onBack(View view) {
        Intent oIntent = new Intent(this, Inicio.class);
        oIntent.putExtra("oMsg", new cActivityMessage(""));
        startActivity(oIntent);
    }

    public void onSave(View view) {
        getViewInfo(oCurrectProductViewInfo);

        if (oCurrectProductViewInfo.ProductoSAPId.trim().isEmpty()){

            Toast.makeText(getApplicationContext(),"ID de Producto es requerido" , Toast.LENGTH_SHORT).show();

        } else  if (oCurrectProductViewInfo.CodigoBarra.trim().isEmpty()) {

            Toast.makeText(getApplicationContext(), "Código de Barras es requerido", Toast.LENGTH_SHORT).show();

        } else  if (oCurrectProductViewInfo.Nombre.trim().isEmpty()) {

            Toast.makeText(getApplicationContext(), "Nombre del Producto es requerido", Toast.LENGTH_SHORT).show();

        } else {

            AsyncTaskRegisterProduct asyncTask=new AsyncTaskRegisterProduct();
            asyncTask.execute("params");
        }


/*        AsyncTaskValidateProduct asyncTask=new AsyncTaskValidateProduct();
        asyncTask.execute("params");*/

    }



    public void onConsultProduct(View view) {

        getViewInfo(oCurrectProductViewInfo);

        AsyncTaskConsultProduct asyncTask=new AsyncTaskConsultProduct();
        asyncTask.execute("params");
    }


    public void onScan(View view) {

        getViewInfo(oCurrectProductViewInfo);
        ((cGlobalData)getApplication()).CurrentProductViewInfo = oCurrectProductViewInfo;
        ((cGlobalData)getApplication()).LsFilterStock = LsFilterStock;


        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("MappingProducts",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);
    }


    public  void  init (){

        txtProductId =  findViewById(R.id.txtProductId);
        txtBarCodeId =  findViewById(R.id.txtBarCodeId);
        txtNombreId =  findViewById(R.id.txtNombreId);
        txtDescripcionId =  findViewById(R.id.txtDescripcionId);
        chkActivoId =  findViewById(R.id.chkActivoId);
        txtBarCodeId =  findViewById(R.id.txtBarCodeId);

        chkActivoId.setChecked(true);
        spinnerStock = findViewById(R.id.spiStock);
        LsFilterStock = new ArrayList<>();

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oCurrectProductViewInfo = ((cGlobalData)getApplication()).CurrentProductViewInfo;
    }

    public  void  StartActivity (){

        if(oMsg != null ) {

            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){

                setViewInfo(oCurrectProductViewInfo);
                txtBarCodeId.setText(oMsg.getKey01());

                LsFilterStock =((cGlobalData)getApplication()).LsFilterStock;
                if (LsFilterStock == null){
                    LsFilterStock = new ArrayList<>();
                }

                fillDataFilterStock();

            } else {

                oCurrectProductViewInfo = new cProductViewInfo();
            }
        }
    }

    private void getViewInfo(cProductViewInfo oCurrectProductViewInfo) {

        oCurrectProductViewInfo.ProductoSAPId =  txtProductId.getText().toString();
        oCurrectProductViewInfo.CodigoBarra =  txtBarCodeId.getText().toString();
        oCurrectProductViewInfo.Nombre =  txtNombreId.getText().toString();
        oCurrectProductViewInfo.Descripcion =  txtDescripcionId.getText().toString();

        if (chkActivoId.isChecked()){
            oCurrectProductViewInfo.Estado = "Activo";
        } else {
            oCurrectProductViewInfo.Estado = "NoActivo";
        }
    }


    private void setViewInfo(cProductViewInfo oCurrectProductViewInfo) {

        txtProductId.setText(oCurrectProductViewInfo.ProductoSAPId);
        txtBarCodeId.setText(oCurrectProductViewInfo.CodigoBarra);
        txtNombreId.setText(oCurrectProductViewInfo.Nombre);
        txtDescripcionId.setText(oCurrectProductViewInfo.Descripcion);

        if (oCurrectProductViewInfo.Estado.equals("Activo")){
            chkActivoId.setChecked(true);
        } else {
            chkActivoId.setChecked(false);
        }
    }

    private void fillDataFilterStock (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilterStock());
        spinnerStock.setAdapter(adapter);
    }

    private List<cSpinnerItem> getInfoFilterStock(){
        InfoFilterStock = new ArrayList<>();
        if (LsFilterStock != null ){

            for ( int i =0; i < LsFilterStock.size(); i++ ){
                InfoFilterStock.add(new cSpinnerItem(i,LsFilterStock.get(i),LsFilterStock.get(i)));
            }
        }
        return  InfoFilterStock;
    }




    private class AsyncTaskRegisterProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(MappingProducts.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected cProductResponse doInBackground(String... strings) {
            cProductResponse oResp = new  cProductResponse();

            try {

                cServices ocServices = new cServices();

              /*  oCurrectProductViewInfo.ProductoSAPId = "3";
                oCurrectProductViewInfo.Nombre = "productoPrueba3";
                oCurrectProductViewInfo.Descripcion = "productoPruebaDescripcion3";
                oCurrectProductViewInfo.CodigoBarra = "12312312312";
                oCurrectProductViewInfo.Estado = "Activo";*/

                oCurrectProductViewInfo.Usuario = "tcabrera";

                oResp = ocServices.PostProductDataService(oCurrectProductViewInfo);

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

                if(!lsData.ResponseId.isEmpty() &&  !lsData.ResponseId.equals("0") ){
                    Toast.makeText(getApplicationContext(),"El Producto " + lsData.ResponseId  +  " fue registrado", Toast.LENGTH_LONG).show();
                    oCurrectProductViewInfo = new cProductViewInfo();
                    ((cGlobalData)getApplication()).CurrentProductViewInfo =  oCurrectProductViewInfo;
                    setViewInfo(oCurrectProductViewInfo);
                    LsFilterStock = new ArrayList<>();

                } else {

                    Toast.makeText(getApplicationContext(),"Error al intentar registrar el producto " , Toast.LENGTH_LONG).show();
                   // setViewInfo(oCurrectProductViewInfo);
                }
            }


            fillDataFilterStock();

            vProgressDialog.hide();
        }
    }




    private class AsyncTaskValidateProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(MappingProducts.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected cProductResponse doInBackground(String... strings) {
            cProductResponse oResp = new  cProductResponse();

            try {

                cServices ocServices = new cServices();

              // oCurrectProductViewInfo.ProductoSAPId = "1";
            //    oCurrectProductViewInfo.Nombre = "productoPrueba3";
             //   oCurrectProductViewInfo.Descripcion = "productoPruebaDescripcion3";
             //   oCurrectProductViewInfo.CodigoBarra = "12312312312";
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


    private class AsyncTaskConsultProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(MappingProducts.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected cProductResponse doInBackground(String... strings) {
            cProductResponse oResp = new  cProductResponse();
            ArrayList<cStock>  lsStockData = new  ArrayList<>();
            LsFilterStock = new ArrayList<>();

            try {

                cServices oServices = new cServices();

                //oCurrectProductViewInfo.ProductoSAPId = "1";
                //    oCurrectProductViewInfo.Nombre = "productoPrueba3";
                //   oCurrectProductViewInfo.Descripcion = "productoPruebaDescripcion3";
                //oCurrectProductViewInfo.CodigoBarra = "12312312312";
                //      oCurrectProductViewInfo.Estado = "Activo";

                //oCurrectProductViewInfo.Usuario = "tcabrera";

                oResp = oServices.PostConsultProductDataService(oCurrectProductViewInfo);

                if ( oResp != null   &&  !oResp.ResponseId.trim().equals("")  && !oResp.ResponseId.trim().equals("-1")){

                    lsStockData = oServices.GetStockServiceData(cServices.StockFilterType.CMATERIAL_UUID, oResp.ResponseId.trim(),"");

                       for( cStock  item:lsStockData){
                           if (!item.CMATERIAL_UUID.trim().equals("")){
                               String sStock =   String.valueOf(Math.round( Float.valueOf(item.KCON_HAND_STOCK)));
                               LsFilterStock.add(item.CLOG_AREA_UUID + "  " +  sStock + " " + item.CON_HAND_STOCK_UOM);
                           }
                       }
                }


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

                        Toast.makeText(getApplicationContext(),"Producto Encontrado" , Toast.LENGTH_LONG).show();
                        oCurrectProductViewInfo.ProductoSAPId  = lsData.ResponseId;
                        oCurrectProductViewInfo.CodigoBarra  = lsData.CodigoBarra;
                        oCurrectProductViewInfo.Nombre  = lsData.Nombre;
                        oCurrectProductViewInfo.Descripcion  = lsData.Descripcion;
                        oCurrectProductViewInfo.Estado  = lsData.Estado;
                        oCurrectProductViewInfo.Usuario  = lsData.Usuario;

                        setViewInfo(oCurrectProductViewInfo);

                    } else {

                        Toast.makeText(getApplicationContext(),"Producto No Encontrado" , Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Error al intentar registrar el producto " , Toast.LENGTH_LONG).show();
                }
            }

            fillDataFilterStock();

            vProgressDialog.hide();
        }
    }
}

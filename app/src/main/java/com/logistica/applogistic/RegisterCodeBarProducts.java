package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RegisterCodeBarProducts extends AppCompatActivity {


    // View Info
    EditText txtProductId;
    EditText txtBarCodeId;
    EditText txtNombreId;
    EditText txtDescripcionId;
    CheckBox chkActivoId;
    CheckBox  chkConfirmedId;
    TextView lblCountItemsId;


    // Data
    ArrayList<cProductViewInfo> lsViewItems;
    public  cProductViewInfo oCurrentProductViewInfo;
    cGlobalData  oGlobalData;

    // Proceso
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;
    int iterater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_code_bar_products);
        init();
        StartActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        oGlobalData.lsProductViewInfoFilter = new ArrayList<>();
        Intent oIntent = new Intent(this, RegisterProducts.class);
        oIntent.putExtra("oMsg", new cActivityMessage("ReStart"));
        startActivity(oIntent);
    }

    private void init (){

        iterater = 1;

        txtProductId =  findViewById(R.id.txtProductId);
        txtBarCodeId =  findViewById(R.id.txtBarCodeId);
        txtNombreId =  findViewById(R.id.txtNombreId);
        txtDescripcionId =  findViewById(R.id.txtDescripcionId);
        chkActivoId =  findViewById(R.id.chkActivoId);
        txtBarCodeId =  findViewById(R.id.txtBarCodeId);
        chkActivoId.setChecked(true);

        chkConfirmedId = findViewById(R.id.chkConfirmedId);
        lblCountItemsId = findViewById(R.id.lblCountItemsId);

        chkActivoId.setChecked(true);
        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oGlobalData=  (cGlobalData)getApplication();

        lsViewItems = oGlobalData.lsProductViewInfoFilter;
        oCurrentProductViewInfo=  oGlobalData.CurrentProductViewInfo;
    }

    public void  StartActivity (){

        if (oMsg  != null){

            if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){

                if(oCurrentProductViewInfo != null){

                    setViewInfo(oCurrentProductViewInfo);
                    txtBarCodeId.setText(oMsg.getKey01());

                    lsViewItems = oGlobalData.lsProductViewInfoFilter;
                    oCurrentProductViewInfo =  oGlobalData.CurrentProductViewInfo;

                    txtBarCodeId.setText(oMsg.getKey01());
                    oCurrentProductViewInfo.CodigoBarra = txtBarCodeId.getText().toString();

                    for(int i = 0; i <  lsViewItems.size(); i++ ){

                        cProductViewInfo   e = lsViewItems.get(i);
                        if ( e.ID == oCurrentProductViewInfo.ID){
                            iterater =  i + 1;
                            lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsViewItems.size()));
                            setViewInfo(oCurrentProductViewInfo);
                        }
                    }
                }

            }else {

                if(lsViewItems != null){

                    if (lsViewItems.size() > 0 ){

                        iterater = 1;
                        lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsViewItems.size()) );
                        oCurrentProductViewInfo = lsViewItems.get(iterater - 1);

                        oCurrentProductViewInfo.Activo = "Activo";
                        setViewInfo(oCurrentProductViewInfo);
                    }
                }
            }
        }




        /*
        if (oMsg.getMessage().equals("ItemConfirmed")){

            Boolean finish = true;
            for ( cInboundViewInfo e:lsInbounItems){
                if ( !e.Confirmed)  {
                    finish = false;
                    break;
                }
            }

            if (finish){

                PutAwayTarget.AsyncTaskAllItemConfirmed asyncTask=new PutAwayTarget.AsyncTaskAllItemConfirmed();
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

            PutAwayTarget.AsyncTaskValidateProduct asyncTask=new PutAwayTarget.AsyncTaskValidateProduct();
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
        }*/

      //  fillDataUnits();
    }



    public void   onClickConfirm(View spinner) {


        getViewInfo(oCurrentProductViewInfo);

        if (oCurrentProductViewInfo.ProductoSAPId.trim().isEmpty()){

            Toast.makeText(getApplicationContext(),"ID de Producto es requerido" , Toast.LENGTH_SHORT).show();

        } else  if (oCurrentProductViewInfo.CodigoBarra.trim().isEmpty()) {

            Toast.makeText(getApplicationContext(), "Código de Barras es requerido", Toast.LENGTH_SHORT).show();

        } else  if (oCurrentProductViewInfo.Nombre.trim().isEmpty()) {

            Toast.makeText(getApplicationContext(), "Nombre del Producto es requerido", Toast.LENGTH_SHORT).show();

        } else {

            AsyncTaskRegisterProduct asyncTask=new AsyncTaskRegisterProduct();
            asyncTask.execute("params");
        }




/*        if (  txtTargetId.getText().toString().isEmpty() ){
            Toast.makeText(getApplicationContext(),"El Área Logística es requerida", Toast.LENGTH_SHORT).show();
        }  else {

            getViewInfo(oCurrentInboundViewInfo);
            oGlobalData.CurrentInboundViewInfo = oCurrentInboundViewInfo;


            Intent oIntent = new Intent(this, PickSource.class);
            oIntent.putExtra("oMsg",  new cActivityMessage(""));
            startActivity(oIntent);
        }*/
    }



    public void onScan(View view) {

        getViewInfo(oCurrentProductViewInfo);
        ((cGlobalData)getApplication()).CurrentProductViewInfo = oCurrentProductViewInfo;

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("RegisterCodeBarProducts",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);
    }

    public void   onClickNext(View spinner) {

        if ( iterater < lsViewItems.size()){
            iterater = iterater+1;
            lblCountItemsId.setText(  iterater + " of " + lsViewItems.size());

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentProductViewInfo);

            // se obtiene el sigiente
            oCurrentProductViewInfo = lsViewItems.get(iterater - 1);
            setViewInfo(oCurrentProductViewInfo);
            chkActivoId.setChecked(true);
        }

        if ( lsViewItems.size() > 0 && iterater == lsViewItems.size()  ){

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentProductViewInfo);

            oCurrentProductViewInfo = lsViewItems.get(iterater-1);
            setViewInfo(oCurrentProductViewInfo);
            chkActivoId.setChecked(true);
        }


        //Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
        //  Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();

        if ( iterater >  1){
            iterater = iterater-1;
            lblCountItemsId.setText(  iterater + " of " + lsViewItems.size());

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentProductViewInfo);

            oCurrentProductViewInfo = lsViewItems.get(iterater - 1);
            setViewInfo(oCurrentProductViewInfo);
            chkActivoId.setChecked(true);
        }

        if (  lsViewItems.size() == 1 ){

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentProductViewInfo);

            oCurrentProductViewInfo = lsViewItems.get(0);
            setViewInfo(oCurrentProductViewInfo);
            chkActivoId.setChecked(true);
        }
    }


    private void getViewInfo(cProductViewInfo oCurrectProductViewInfo) {

        oCurrectProductViewInfo.ProductoSAPId =  txtProductId.getText().toString();
        oCurrectProductViewInfo.CodigoBarra =  txtBarCodeId.getText().toString();
        oCurrectProductViewInfo.Nombre =  txtNombreId.getText().toString();
        oCurrectProductViewInfo.Descripcion =  txtDescripcionId.getText().toString();

        if (chkActivoId.isChecked()){
            oCurrectProductViewInfo.Activo = "Activo";
        } else {
            oCurrectProductViewInfo.Activo = "NoActivo";
        }
    }


    private void setViewInfo(cProductViewInfo oCurrectProductViewInfo) {

        txtProductId.setText(oCurrectProductViewInfo.ProductoSAPId);
        txtBarCodeId.setText(oCurrectProductViewInfo.CodigoBarra);
        txtNombreId.setText(oCurrectProductViewInfo.Nombre);
        txtDescripcionId.setText(oCurrectProductViewInfo.Descripcion);
        chkConfirmedId.setChecked(oCurrectProductViewInfo.Confirmed);

        if (oCurrectProductViewInfo.Activo.equals("Activo")){
            chkActivoId.setChecked(true);
        } else {
            chkActivoId.setChecked(false);
        }
    }


    private class AsyncTaskRegisterProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(RegisterCodeBarProducts.this);
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

                oCurrentProductViewInfo.Usuario = "tcabrera";

                oResp = ocServices.PostProductDataService(oCurrentProductViewInfo);

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
                    Toast.makeText(getApplicationContext(),"El Producto " + lsData.ResponseId  +  " fué registrado", Toast.LENGTH_LONG).show();

                    oCurrentProductViewInfo.Confirmed = true;
                    setViewInfo(oCurrentProductViewInfo);


                    for(int i = 0; i <  lsViewItems.size(); i++ ){

                        cProductViewInfo   e = lsViewItems.get(i);
                        if (e.ProductoSAPId.equals(oCurrentProductViewInfo.ProductoSAPId)){

                            e.CodigoBarra = oCurrentProductViewInfo.CodigoBarra;
                            e.Descripcion = oCurrentProductViewInfo.Descripcion;
                            e.Activo = oCurrentProductViewInfo.Activo;
                            e.Confirmed = oCurrentProductViewInfo.Confirmed;
                        }
                    }

//                    oCurrentProductViewInfo = new cProductViewInfo();
//                    ((cGlobalData)getApplication()).CurrentProductViewInfo =  oCurrentProductViewInfo;
//                    setViewInfo(oCurrentProductViewInfo);


                } else {

                    Toast.makeText(getApplicationContext(),"Error al intentar registrar el producto " , Toast.LENGTH_LONG).show();
                    // setViewInfo(oCurrectProductViewInfo);
                }
            }

            vProgressDialog.hide();
        }
    }
}

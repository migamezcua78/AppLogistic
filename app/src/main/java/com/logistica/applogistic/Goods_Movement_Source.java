package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
   // EditText txtLuQtyId;
    EditText txtFieldNameId;
    EditText txtBarCodeId;
    Spinner spinner;
    Spinner spinnerCompany;

    // Data
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();
    private List<cSpinnerItem>  InfoFilterCompany = new ArrayList<>();


    private  cMovementViewInfo  oCurrentItemViewInfo;
    cProductViewInfo oCurrectProductViewInfo;



    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

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
        txtLuId = findViewById(R.id.txtSerialNumberId);  // Sede
       // txtLuQtyId = findViewById(R.id.txtLuQtyId);   // Empresa
        txtFieldNameId = findViewById(R.id.txtFieldName);  //  ID External Posicion
        txtBarCodeId = findViewById(R.id.txtBarCode);
        spinner = findViewById(R.id.spiUnitId);
        spinnerCompany = findViewById(R.id.spiCompany);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oCurrentItemViewInfo = ((cGlobalData)getApplication()).CurrentMovementViewInfo;

        fillDataFilter();
        fillDataFilterCompany();
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
                setViewInfo();
            }
        } else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_SOURCE)){

            // oCurrentItemViewInfo.SourceId = oMsg.getKey01();
           // oCurrentItemViewInfo.SourceId = "13-153-4";
            oCurrentItemViewInfo.SourceId = oMsg.getKey01();
            setViewInfo();
        }

        else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_PRODUCT)){

            //oCurrentItemViewInfo.SourceId = oMsg.getKey01();
          //  oCurrentItemViewInfo.ProductId = "KECM0000608030";
            oCurrentItemViewInfo.ProductId =  oMsg.getKey01();
            //oCurrentItemViewInfo.Qty = "5";
           // oCurrentItemViewInfo.IdentStock = "40567";
            // oCurrentItemViewInfo.FieldName = "1000020";
            setViewInfo();
        }else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){

            oCurrentItemViewInfo.BarCode =  oMsg.getKey01();
            setViewInfo();

            oCurrectProductViewInfo = new cProductViewInfo();
            oCurrectProductViewInfo.ProductoSAPId = oCurrentItemViewInfo.ProductId;
            oCurrectProductViewInfo.CodigoBarra = oCurrentItemViewInfo.BarCode;

            AsyncTaskValidateProduct asyncTask=new AsyncTaskValidateProduct();
            asyncTask.execute("params");



        } else {
            fillDataFilterCompany();
            setViewInfo();
        }
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
        oCurrentItemViewInfo.LuQty =  ((cSpinnerItem)spinnerCompany.getSelectedItem()).getField(); //txtLuQtyId.getText().toString();  //  Empresa
        oCurrentItemViewInfo.FieldName =txtFieldNameId.getText().toString();
        oCurrentItemViewInfo.BarCode =txtBarCodeId.getText().toString();
        oCurrentItemViewInfo.msg = "";
        oCurrentItemViewInfo.QtyUnitCode = ((cSpinnerItem)spinner.getSelectedItem()).getDescription();
    }

    private void setViewInfo(){

        txtSourceId.setText(oCurrentItemViewInfo.SourceId);
        txtProductId.setText(oCurrentItemViewInfo.ProductId);
        txtQtyId.setText(oCurrentItemViewInfo.Qty);
        txIdentStockId.setText(oCurrentItemViewInfo.IdentStock);
        chkRestrictedId.setChecked(oCurrentItemViewInfo.Restricted);
        txtLuId.setText(oCurrentItemViewInfo.Lu);
        //txtLuQtyId.setText(oCurrentItemViewInfo.LuQty);
       // spinnerCompany.setSelection(1);     // .setText(oCurrentItemViewInfo.LuQty);

        selectSpinnerItemByValue(spinnerCompany, oCurrentItemViewInfo.LuQty);

        txtFieldNameId.setText(oCurrentItemViewInfo.FieldName);
        txtBarCodeId.setText(oCurrentItemViewInfo.BarCode);
    }

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {
        ArrayAdapter<cSpinnerItem> adapter = (ArrayAdapter<cSpinnerItem>) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(((cSpinnerItem)adapter.getItem(position)).getField().equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
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

    private void fillDataFilterCompany (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilterCompany());
        spinnerCompany.setAdapter(adapter);
    }


    private List<cSpinnerItem> getInfoFilter(){
        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,"ZPZ"));
        return  InfoFilter;
    }

    private List<cSpinnerItem> getInfoFilterCompany(){
        InfoFilterCompany = new ArrayList<>();
        InfoFilterCompany.add(new cSpinnerItem(1,"E01-Goodwill","E01"));
        InfoFilterCompany.add(new cSpinnerItem(2,"E02-Importadora Regat","E02"));
        InfoFilterCompany.add(new cSpinnerItem(3,"E03-Bausse Cosméticos","E03"));
        return  InfoFilterCompany;
    }


    public void onScanSource(View view){

        getViewInfo();
        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Goods_Movement_Source",Scanner.ScanType.SCAN_SOURCE));
        startActivity(oIntent);

/*        txtSourceId.setText("");
        AsyncTaskScanSource  AsyncTaskScanSource = new AsyncTaskScanSource();
        AsyncTaskScanSource.execute("params");*/

    }

    public void onScanProduct(View view){

        getViewInfo();
        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Goods_Movement_Source",Scanner.ScanType.SCAN_PRODUCT));
        startActivity(oIntent);

//        AsyncTaskScanProduct  AsyncTaskScanProduct = new AsyncTaskScanProduct();
//        AsyncTaskScanProduct.execute("params");
    }


    public void onScanBarCode(View view){

        getViewInfo();
        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Goods_Movement_Source",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);

//        AsyncTaskScanProduct  AsyncTaskScanProduct = new AsyncTaskScanProduct();
//        AsyncTaskScanProduct.execute("params");
    }

    public void onClickConfirm(View view){

        if ( txtSourceId.getText().toString().trim().isEmpty()){

            Toast.makeText(getApplicationContext(),"Área Logística Origen es requerida", Toast.LENGTH_SHORT).show();

        }else if ( txtProductId.getText().toString().trim().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"PRODUCTO  es requerido", Toast.LENGTH_SHORT).show();
        }
        else if (txtQtyId.getText().toString().trim().isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"QTY es requerido", Toast.LENGTH_SHORT).show();

        }
        else if (txtFieldNameId.getText().toString().trim().isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"ID Externo de Posicion es requerido", Toast.LENGTH_SHORT).show();

        } else if (txtLuId.getText().toString().trim().isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"ID de Sede es requerida", Toast.LENGTH_SHORT).show();

        }

/*        else if (txtLuQtyId.getText().toString().trim().isEmpty() )
        {
            Toast.makeText(getApplicationContext(),"ID de Empresa es requerida", Toast.LENGTH_SHORT).show();

        }*/
        else{

            getViewInfo();
            Intent oIntent = new Intent(this, GoodsMovementTarget.class);
            oIntent.putExtra("oMsg", new cActivityMessage(""));
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


    private class AsyncTaskValidateProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(Goods_Movement_Source.this);
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

               // oCurrectProductViewInfo.ProductoSAPId = "1";
                //    oCurrectProductViewInfo.Nombre = "productoPrueba3";
                //   oCurrectProductViewInfo.Descripcion = "productoPruebaDescripcion3";
              //  oCurrectProductViewInfo.CodigoBarra = "12312312312";
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

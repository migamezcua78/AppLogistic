package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegisterProducts extends AppCompatActivity {

    // VIEWS
    private Spinner spinner;
    private TableLayout tableLayout;
    private cDataGrid  oDataGrid;
    private EditText txtImputFilterId;


    //  DATA
    private ArrayList<String[]> InfoData;
    private String[] InfoHeader;
    private List<cSpinnerItem> InfoFilter;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;
    Boolean Scanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products);

        init();
        StartActivity();
    }

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Inicio.class);
        startActivity(oIntent);
    }


    private void init (){

        txtImputFilterId = findViewById(R.id.txtImputFilterId);
        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);
        Scanned = false;

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");

        InfoData = new ArrayList <> ();
        InfoFilter = new ArrayList<>();
       // lsInbounItems =  new  ArrayList<>();
    }


    private void StartActivity (){

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){
                txtImputFilterId.setText(oMsg.getKey01());
                Scanned = true;

//                AsyncTaskExample asyncTask=new  AsyncTaskExample();
//                asyncTask.execute("params");
            }
        }

        fillDataFilter();
        fillDataGrid();
    }


    public void onConsultProducts(View view) {


        txtImputFilterId.setText("");
        AsyncTaskConsultProducts  asyncTask= new AsyncTaskConsultProducts();
        asyncTask.execute("params");

    }


    private void fillDataFilter (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);

    }

    private void fillDataGrid (){
        oDataGrid = new cDataGrid(tableLayout,getApplicationContext());
        oDataGrid.addHeader(getInfoHeader());
        oDataGrid.addData(getInfoData());
    }

    private  String[] getInfoHeader(){
        InfoHeader = new String[]{getString(R.string.ProductId),getString(R.string.ProductDesc),"Estado"};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){
        return  InfoData;
    }


    private  List<cSpinnerItem> getInfoFilter(){


        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,getString(R.string.CSITE_UUID),"CSITE_UUID"));
        InfoFilter.add(new cSpinnerItem(2,getString(R.string.CMATERIAL_UUID), "CMATERIAL_UUID"));
        InfoFilter.add(new cSpinnerItem(3,getString(R.string.CLOG_AREA_UUID),"CLOG_AREA_UUID"));

        return  InfoFilter;
    }



    private class AsyncTaskConsultProducts extends AsyncTask<String, String,  ArrayList<cProductViewInfo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(RegisterProducts.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cProductViewInfo> doInBackground(String... strings) {

            // salida
            ArrayList<cProductViewInfo>  lsData = new  ArrayList<>();

            // consultas
            ArrayList<cStock>  lsStockData = new  ArrayList<>();
            ArrayList<cMaterial>  lsMaterialData = new  ArrayList<>();
            cProductResponse  cProductResponse = new  cProductResponse();

            // unicidad
            Set<String>  setKeyProducts = new HashSet<>();


            try {

                cServices oServices = new cServices();
                lsStockData = oServices.GetStockServiceData(cServices.StockFilterType.CMATERIAL_UUID,"333","");
                if(lsStockData != null)
                {
                    for ( cStock e:lsStockData){
                        setKeyProducts.add(e.CMATERIAL_UUID);
                    }

                    for ( String e:setKeyProducts){
                        lsMaterialData = oServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID,"333","");

                         if (lsMaterialData.size() > 0){

                             cProductViewInfo  oObj = new cProductViewInfo();
                             oObj.ProductoSAPId = e;
                             oObj.Nombre = lsMaterialData.get(0).Description + "Mis descripciones mayores";

                             if (oObj.Nombre.length() > 20) {
                                 oObj.NombreShort = oObj.Nombre.substring(0,20) + " ...";
                             } else {
                                 oObj.NombreShort = oObj.Nombre;
                             }

                             cProductResponse = oServices.PostConsultProductDataService(oObj);
                             if(!cProductResponse.ResponseId.equals("-1")){

                                 oObj.CodigoBarra = cProductResponse.CodigoBarra;
                                 oObj.Estado = cProductResponse.mensaje;

                             } else {
                                 oObj.Estado = "Indefinido";
                             }

                             lsData.add(oObj);
                             lsData.add(oObj);
                         }
                    }
                }

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
        protected void onPostExecute(ArrayList<cProductViewInfo> lsData) {

            super.onPostExecute(lsData);
            InfoData = new ArrayList <> ();
            oDataGrid.RemoveAllItems();

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cProductViewInfo  oData =lsData.get(i);
                InfoData.add(new String[]{oData.ProductoSAPId, oData.NombreShort, oData.Estado});
            }

            fillDataGrid();
            vProgressDialog.hide();
        }
    }
}

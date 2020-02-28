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

public class RegisterProducts extends MainBaseActivity {

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

    private   ArrayList<cProductViewInfo>   lsProductViewInfo;
    private   ArrayList<cProductViewInfo>    lsProductViewInfoFilter;


    cGlobalData  oGlobalData;

    String ScanedBC;


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
        ScanedBC = "";

        oGlobalData = (cGlobalData)getApplication();
        InfoData = new ArrayList <> ();
        InfoFilter = new ArrayList<>();

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
    }


    private void StartActivity (){

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){
                txtImputFilterId.setText(oMsg.getKey01());
                Scanned = true;

//                AsyncTaskExample asyncTask=new  AsyncTaskExample();
//                asyncTask.execute("params");
            }

            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){
                //txtImputFilterId.setText(oMsg.getKey01());
                Scanned = true;
                ScanedBC = oMsg.getKey01();

                AsyncTaskConsultProduct  asyncTask= new AsyncTaskConsultProduct();
                asyncTask.execute("params");
            }
        }

        fillDataFilter();
        fillDataGrid();
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

    public void onConsultProducts(View view) {

      if(txtImputFilterId.getText().toString().trim().isEmpty()){

          Toast.makeText(getApplicationContext(),"Debe ingresar un valor de búsqueda", Toast.LENGTH_SHORT).show();
      } else {

          AsyncTaskConsultProducts  asyncTask= new AsyncTaskConsultProducts();
          asyncTask.execute("params");
      }
    }

    public void onAsignarTodos(View view) {

        if (lsProductViewInfo != null ){

            lsProductViewInfoFilter = new ArrayList<>();

            for (cProductViewInfo  item: lsProductViewInfo)
            {
                lsProductViewInfoFilter.add(item);
            }
        }
    }

    public void onScanProductFilter(View view) {


        // getViewInfo(oCurrectProductViewInfo);
         ((cGlobalData)getApplication()).CurrentSelectedSpinnerItem = (cSpinnerItem)spinner.getSelectedItem();

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("RegisterProducts",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);
    }



    public void onAsignarNoAsignados(View view) {

        if (lsProductViewInfo != null ){

            lsProductViewInfoFilter = new ArrayList<>();

            for (cProductViewInfo  item: lsProductViewInfo)
            {

                // mig:  descomentar para liberar.
               if(item.CodigoBarra.trim().isEmpty()){

                    lsProductViewInfoFilter.add(item);
                }

             //  lsProductViewInfoFilter.add(item);
            }

            if (lsProductViewInfoFilter.size() > 0){

                oGlobalData.lsProductViewInfoFilter =  lsProductViewInfoFilter;

                Intent oIntent = new Intent(this, RegisterCodeBarProducts.class);
                oIntent.putExtra("oMsg", new cActivityMessage("Start"));
                startActivity(oIntent);

            } else {

                Toast.makeText(getApplicationContext(), "No hay Productos sin asignar", Toast.LENGTH_SHORT).show();
            }
        }
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
        InfoHeader = new String[]{getString(R.string.ProductId)," Cantidad "," Área "," Estado ",getString(R.string.ProductDesc)};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){
        return  InfoData;
    }


    private  List<cSpinnerItem> getInfoFilter(){

        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,getString(R.string.CLOG_AREA_UUID),"CLOG_AREA_UUID"));
        InfoFilter.add(new cSpinnerItem(2,getString(R.string.CSITE_UUID),"CSITE_UUID"));
        InfoFilter.add(new cSpinnerItem(3,getString(R.string.CMATERIAL_UUID), "CMATERIAL_UUID"));

        return  InfoFilter;
    }

    private void  getParamInfo(cRegisterProductsInfoView  pViewInfo) {

        cSpinnerItem   oSpiItem=  (cSpinnerItem)spinner.getSelectedItem();
        switch (oSpiItem.getField()){
            case "CMATERIAL_UUID":
                pViewInfo.ProductId = txtImputFilterId.getText().toString().trim();
                break;
            case "CSITE_UUID":
                pViewInfo.SiteId = txtImputFilterId.getText().toString().trim();
                break;
            case "CLOG_AREA_UUID":
                pViewInfo.AreaId = txtImputFilterId.getText().toString().trim();
                break;
        }
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
            cSpinnerItem  oSelectedItem = (cSpinnerItem)spinner.getSelectedItem();

            int IDCount = 1;

            try {

                cServices oServices = new cServices();
                lsStockData = oServices.GetStockServiceData(oSelectedItem.getField(), txtImputFilterId.getText().toString().trim(),"");
                if(lsStockData != null  && lsStockData.size() > 0 )
                {

                    // mig: Borrar solo es de prueba
//                    for(int i = 0; i < lsStockData.size();  i++ ){
//                        if (i == 10){
//                            break;
//                        } else {
//                            setKeyProducts.add(lsStockData.get(i).CMATERIAL_UUID);
//                        }
//                    }

                    // mig: Este es el correcto
//                    for ( cStock e:lsStockData){
//                        setKeyProducts.add(e.CMATERIAL_UUID);
//                    }


                    for ( cStock  keyProduct:lsStockData){
                        lsMaterialData = oServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID,keyProduct.CMATERIAL_UUID,"");

                         if (lsMaterialData.size() > 0){

                             Float QtyD = Float.valueOf(keyProduct.KCON_HAND_STOCK);
                             int QtyI = (int) Math.round(QtyD);

                             cProductViewInfo  oObj = new cProductViewInfo();
                             oObj.ProductoSAPId = keyProduct.CMATERIAL_UUID;
                             oObj.Qty = String.valueOf(QtyI); // + " " + keyProduct.CON_HAND_STOCK_UOM;
                             oObj.LogisticAreaId = keyProduct.CLOG_AREA_UUID;
                             oObj.Nombre = lsMaterialData.get(0).Description;

                             if (oObj.Nombre.length() > 10) {
                                 oObj.NombreShort = oObj.Nombre.substring(0,10) + " ..";
                             } else {
                                 oObj.NombreShort = oObj.Nombre;
                             }

                             cProductResponse = oServices.PostConsultProductDataService_C(oObj);
                             if(!cProductResponse.ResponseId.equals("-1")){

                                 oObj.CodigoBarra = cProductResponse.CodigoBarra;
                                 oObj.CodigoCaja = cProductResponse.CodigoCaja;
                                 oObj.CantidadCaja = cProductResponse.CantidadCaja;

                                 oObj.Estado = cProductResponse.mensaje;

                             } else {
                                 oObj.Estado = "Indefinido";
                             }

                             oObj.ID = IDCount;

                             // solo de prueba
                            // oObj.CodigoBarra = "";

                             lsData.add(oObj);
                             IDCount = IDCount + 1;
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
                InfoData.add(new String[]{oData.ProductoSAPId, oData.Qty, oData.LogisticAreaId, oData.Estado, oData.NombreShort });
            }

            fillDataGrid();
            vProgressDialog.hide();

            if(lsData == null  || lsData.size() == 0 ) {

                lsProductViewInfo = new ArrayList<>();
                Toast.makeText(getApplicationContext(),"No se encontraron Productos con los parámetros de búsqueda", Toast.LENGTH_SHORT).show();
            } else {

                lsProductViewInfo = lsData;

                if (lsData.size() == 1){

                    Toast.makeText(getApplicationContext(),lsData.size() + " Producto encontrado", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(),lsData.size() + " Productos encontrados", Toast.LENGTH_SHORT).show();
            }

            oGlobalData.lsProductViewInfo = lsProductViewInfo;
        }
    }

    private class AsyncTaskConsultProduct extends AsyncTask<String, String,cProductResponse> {
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
        protected cProductResponse doInBackground(String... strings) {
            cProductResponse oResp = new  cProductResponse();
            cProductViewInfo  pProductViewInfo = new cProductViewInfo();

            try {

                cServices ocServices = new cServices();

                //oCurrectProductViewInfo.ProductoSAPId = "1";
                //    oCurrectProductViewInfo.Nombre = "productoPrueba3";
                //   oCurrectProductViewInfo.Descripcion = "productoPruebaDescripcion3";
                //oCurrectProductViewInfo.CodigoBarra = "12312312312";
                //      oCurrectProductViewInfo.Estado = "Activo";

                pProductViewInfo.Usuario = "tcabrera";
                pProductViewInfo.CodigoBarra = ScanedBC;
                //pProductViewInfo.CodigoBarra = "9023800691019";

                oResp = ocServices.PostConsultProductDataService(pProductViewInfo);

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

                        txtImputFilterId.setText(lsData.ResponseId);
                        Toast.makeText(getApplicationContext(),"Producto Encontrado" , Toast.LENGTH_LONG).show();

                    } else {
                        txtImputFilterId.setText("0");
                        Toast.makeText(getApplicationContext(),"Producto No Encontrado" , Toast.LENGTH_LONG).show();

                    }

                } else {
                    txtImputFilterId.setText("0");
                    Toast.makeText(getApplicationContext(),"Error al intentar registrar el producto " , Toast.LENGTH_LONG).show();
                }
            }

            selectSpinnerItemByValue(spinner,"CMATERIAL_UUID");
            vProgressDialog.hide();
        }
    }
}

package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Inbound extends AppCompatActivity {

    // VIEWS
    private Spinner     spinner;
    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;
    private EditText    txtImputFilterId;
    private TextView    lblTaskValueId;
    private TextView    lbOrderValueId;


    //  DATA
    private ArrayList <String[]> InfoData;
    private String[] InfoHeader;
    private List<cSpinnerItem>  InfoFilter;
    ArrayList<cInboundViewInfo>  lsInbounItems;

    DatabaseHelper BD;
    InputStream is = null;
    ImageView imageView= null;
    AlertDialog alert11;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound);

        cGlobalData  oGlobalData=  (cGlobalData)getApplication();
        oGlobalData.setGlobalVarValue("Hola");
        init();
        StartActivity();
    }

    private void init (){

        txtImputFilterId = findViewById(R.id.txtImputFilterId);
        lblTaskValueId = findViewById(R.id.lblTaskValueId);
        lbOrderValueId = findViewById(R.id.lbOrderValueId);

        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");

        InfoData = new ArrayList <> ();
        InfoFilter = new ArrayList<>();
        lsInbounItems =  new  ArrayList<>();
    }


    private void StartActivity (){

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){

               // txtImputFilterId.setText("");
                lblTaskValueId.setText("");
                lbOrderValueId.setText("");

                txtImputFilterId.setText(oMsg.getKey01());

                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("params");
            }
        }

        fillDataFilter();
        fillDataGrid();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Inicio.class);
        startActivity(oIntent);
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

    public void onScan(View view) {

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Inbound",Scanner.ScanType.SCAN_TASK));
        startActivity(oIntent);


//        txtImputFilterId.setText("");
//        AsyncTaskScan asyncTask= new AsyncTaskScan();
//        asyncTask.execute("params");
    }

    private cActivityMessage getActivityMsg(){
        cActivityMessage   oMsg = new  cActivityMessage();

        oMsg.setMessage("StartTask");
        cSpinnerItem   oSpiItem=  (cSpinnerItem)spinner.getSelectedItem();
        oMsg.setKey01(oSpiItem.getField());
        oMsg.setKey02(txtImputFilterId.getText().toString().trim());

        return  oMsg;
    }


    public void   onClickStartTask(View spinner) {


        if ( txtImputFilterId.getText().toString().trim().isEmpty() ){

            Toast.makeText(getApplicationContext(),"TASK field is required", Toast.LENGTH_SHORT).show();

        } else{

            if( lsInbounItems != null && lsInbounItems.size() > 0 ){

                for ( cInboundViewInfo e:lsInbounItems){
                    getParamInfo(e);
                }

                Intent oIntent = new Intent(this, PutAwayTarget.class);
                oIntent.putExtra("oMsg", getActivityMsg());
                startActivity(oIntent);

            }else {
                Toast.makeText(getApplicationContext(),"There are NO items in task selected", Toast.LENGTH_SHORT).show();
            }

/*            Intent oIntent = new Intent(this, PutAwayTarget.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);*/
        }



       // Intent oIntent = new Intent(this, PutAwayTarget.class);
        //oIntent.setExtrasClassLoader();
    //    startActivity(oIntent);


      /*
        //obtiene los datos de SQLite
        Cursor res= BD.getAllData();
        if (res.getCount()==0){

            showMessage("Datos","La consulta no devolvió información");
            return;

        }

        StringBuffer Buffer=new StringBuffer();
        while (res.moveToNext())
        {

            // Buffer.append("Id :" + getString(0)+"\n");
            //Buffer.append("Product :"+ getString(1)+"\n");
            //  Buffer.append("Planned_Quantity :"+ getString(2)+"\n");

            int cid =res.getInt(res.getColumnIndex(BD.Col1));
            String Product =res.getString(res.getColumnIndex(BD.Col2));
            String  Planned_Quantity =res.getString(res.getColumnIndex(BD.Col3));
            Buffer.append(cid+ "   " + Product + "   " + Planned_Quantity +" \n");

        }
        showMessage("Datos",Buffer.toString());
        //  Toast.makeText(getApplicationContext(),"Start Task", Toast.LENGTH_SHORT).show();*/

    }

    public void showMessage(String Title, String Message)
    {
        AlertDialog.Builder build =new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(Title);
        build.setMessage(Message);
        build.show();

    }


    public void   onClickNext(View spinner) {
        Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
        //Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void  getParamInfo(cInboundViewInfo  pViewInfo) {

        cSpinnerItem   oSpiItem=  (cSpinnerItem)spinner.getSelectedItem();

        switch (oSpiItem.getField()){
            case "TaskId":
                pViewInfo.TaskId = txtImputFilterId.getText().toString().trim();
                break;
            case "ReferenceId":
                pViewInfo.ReferenceId = txtImputFilterId.getText().toString().trim();
                break;
            case "LabelId":
                pViewInfo.LabelId = txtImputFilterId.getText().toString().trim();
                break;
            case "BarCodeId":
                pViewInfo.BarCodeId = txtImputFilterId.getText().toString().trim();
                break;
        }
    }


    private  List<cSpinnerItem> getInfoFilter(){


        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,getString(R.string.TaskId), "TaskId"));
        InfoFilter.add(new cSpinnerItem(2,getString(R.string.BarCodeId),"BarCodeId"));
        InfoFilter.add(new cSpinnerItem(3,getString(R.string.LabelId),"LabelId"));
        InfoFilter.add(new cSpinnerItem(4,getString(R.string.ReferenceId),"ReferenceId"));

        return  InfoFilter;
    }

    private  String[] getInfoHeader(){
        InfoHeader = new String[]{getString(R.string.Product),getString(R.string.PlannedQuantity)};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){
        return  InfoData;
    }

/*


    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cStock>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cStock> doInBackground(String... strings) {
            ArrayList<cStock>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetStockServiceData(cServices.StockFilterType.CMATERIAL_UUID,"333","3");

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
        protected void onPostExecute(ArrayList<cStock> lsData) {
            super.onPostExecute(lsData);

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cStock  oData =lsData.get(i);
                InfoData.add(new String[]{oData.CMATERIAL_UUID, oData.CLOG_AREA_UUID});
            }

            fillDataGrid();
            p.hide();
        }
    }
*/


/*
    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cLogisticsArea>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cLogisticsArea> doInBackground(String... strings) {
            ArrayList<cLogisticsArea>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetLogisticAreaServiceData(cServices.LogisticAreaFilterType.SelectionByID,"*","3");

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

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cLogisticsArea  oData  =lsData.get(i);
                InfoData.add(new String[]{oData.ID, oData.TypeCode});
            }

            fillDataGrid();
            p.hide();
        }
    }
    */

/*


    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cCustomer>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cCustomer> doInBackground(String... strings) {
            ArrayList<cCustomer>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetCustomersServ(cServices.CustomerFilterType.SelectionByInternalID,"*","5");

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
        protected void onPostExecute(ArrayList<cCustomer> lsData) {
            super.onPostExecute(lsData);

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cCustomer  oCustomer  =lsData.get(i);
                InfoData.add(new String[]{oCustomer.InternalID, oCustomer.CategoryCode});
            }

            fillDataGrid();
            p.hide();
        }
    }

*/

/*

    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cMaterial>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cMaterial> doInBackground(String... strings) {
            ArrayList<cMaterial> lsData = new ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetMaterialsServ(cServices.MaterialFilterType.SelectionByInternalID, "*", "8");

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
        protected void onPostExecute(ArrayList<cMaterial> lsData) {
            super.onPostExecute(lsData);


            for (int i = 0; i < lsData.size(); i++) {
                cMaterial oMaterial = lsData.get(i);
                InfoData.add(new String[]{oMaterial.getProductCategoryID(), oMaterial.getQuantity() + " " + oMaterial.getQuantityUnitCode()});
            }

            fillDataGrid();
            AddData(lsData);
            p.hide();
        }
    }

*/


    private void AddData(ArrayList<cMaterial> lsData) {
          /*  String Product="";
            String Planned_Quantity="";
            Product="MCFHITG-001";
            Planned_Quantity="5 ea";
*/
        for ( int i = 0; i <  lsData.size(); i ++  ){
            cMaterial  oMaterial  =lsData.get(i);
            //InfoData.add(new String[]{oMaterial.getProductCategoryID(), oMaterial.getQuantity() + " " +  oMaterial.getQuantityUnitCode() });


/*            boolean Inserted =BD.insertData(oMaterial.ProductCategoryID, oMaterial.Quantity);
            if (Inserted==true){
                Toast.makeText(Inbound.this,"Datos insertados correctamente...",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(Inbound.this,"Los datos no fueron Insertados...",Toast.LENGTH_LONG).show();
            }*/

        }
    }




    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cMaterial>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(Inbound.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cMaterial> doInBackground(String... strings) {
            ArrayList<cMaterial> lsData = new ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID, "*", "1");

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
        protected void onPostExecute(ArrayList<cMaterial> lsData) {

            super.onPostExecute(lsData);
            lblTaskValueId.setText( txtImputFilterId.getText().toString().trim() + "  "  +  "Pick");
            lbOrderValueId.setText("365");

            lsInbounItems = new  ArrayList<>();

            InfoData = new ArrayList <> ();
            cInboundViewInfo  oInboundViewInfo = new cInboundViewInfo();

            for (int i = 0; i < lsData.size(); i++) {
                cMaterial oMaterial = lsData.get(i);

                if( !oMaterial.getProductCategoryID().trim().isEmpty()){

                    oInboundViewInfo = new cInboundViewInfo();
                    oInboundViewInfo.ProductId = oMaterial.getProductCategoryID() +  "_" + String.valueOf(i);
                    oInboundViewInfo.Open = String.valueOf(i+2);   // oMaterial.getQuantity();
                    oInboundViewInfo.OpenUnit =  oMaterial.getQuantityUnitCode();
                    oInboundViewInfo.TargetId = "MC64920-50-10-10-04_" + String.valueOf(i);
                    oInboundViewInfo.IdentStock = "43668";

                    lsInbounItems.add(oInboundViewInfo);
                    InfoData.add(new String[]{ oInboundViewInfo.ProductId, oInboundViewInfo.Open + "  " + oInboundViewInfo.OpenUnit });
                }
            }


            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsIntboudItems = lsInbounItems;

            oDataGrid.RemoveAllItems();
            fillDataGrid();
            vProgressDialog.hide();
        }
    }


/*
    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(Inbound.this);
            vProgressDialog.setMessage("Scanning Simulation...");
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
            txtImputFilterId.setText("121");
            vProgressDialog.hide();
        }
    }*/
}


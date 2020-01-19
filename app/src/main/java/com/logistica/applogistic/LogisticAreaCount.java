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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class LogisticAreaCount extends MainBaseActivity {

    EditText  txtAreaId;
    EditText  txtProductId;
    EditText  txtOrderId;
    EditText  txtBarCodeId;
   // Spinner spinnerLogisticAreas;

    private List<cSpinnerItem>  InfoFilterLogisticAreas = new ArrayList<>();
    private ArrayList<String>  LsCatalogLogisticAreas;

    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistic_area_count);
        Init();
        StarActivity();
    }

    private void StarActivity() {

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){
                // oCurrentItemViewInfo.SourceId = oMsg.getKey01();
               // txtAreaId.setText("E01/E01-1");

                 /*   String  sArea =  oMsg.getKey01();
                   sArea = "E01-60";
                    if (!selectSpinnerItemByValue(spinnerLogisticAreas, sArea)){
                        LsCatalogLogisticAreas.add(sArea);
                        fillDataFilterLogisticAreas();
                        selectSpinnerItemByValue(spinnerLogisticAreas, sArea);
                    }*/

                txtAreaId.setText(oMsg.getKey01());

            }  else if (!oMsg.getMessage().equals(cMessage.Message.START)){
                Toast.makeText(getApplicationContext(),oMsg.getMessage(), Toast.LENGTH_SHORT).show();
                oMsg.setMessage("");
            }
        }
    }

    private void Init (){

        txtAreaId =  findViewById(R.id.txtSourceId);
        txtProductId = findViewById(R.id.txtProductId);
        txtOrderId = findViewById(R.id.txtQtyId);
        txtBarCodeId =findViewById(R.id.txtStockId);
       // spinnerLogisticAreas = findViewById(R.id.spiLogisticAreas);
        LsCatalogLogisticAreas = ((cGlobalData)getApplication()).LsCatalogLogisticAreasS;

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");

/*        if (LsCatalogLogisticAreas == null  || LsCatalogLogisticAreas.size() == 0 ){
            AsyncTaskGetLogisticAreas asyncTask=new AsyncTaskGetLogisticAreas();
            asyncTask.execute("params");
        }else {
            fillDataFilterLogisticAreas();
        }*/
    }


    @Override
    protected void  onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Inicio.class);
        startActivity(oIntent);
    }

/*    private void fillDataFilterLogisticAreas (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilterLogisticAreas());
        spinnerLogisticAreas.setAdapter(adapter);
    }*/

    private List<cSpinnerItem> getInfoFilterLogisticAreas(){
        InfoFilterLogisticAreas = new ArrayList<>();
        if (LsCatalogLogisticAreas != null ){

            for ( int i =0; i < LsCatalogLogisticAreas.size(); i++ ){
                InfoFilterLogisticAreas.add(new cSpinnerItem(i,LsCatalogLogisticAreas.get(i),LsCatalogLogisticAreas.get(i)));
            }
        }
        return  InfoFilterLogisticAreas;
    }


    public static boolean selectSpinnerItemByValue(Spinner spnr, String value) {
        ArrayAdapter<cSpinnerItem> adapter = (ArrayAdapter<cSpinnerItem>) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(((cSpinnerItem)adapter.getItem(position)).getField().equals(value)) {
                spnr.setSelection(position);
                return true;
            }
        }

        return  false;
    }



    public void onStartTask(View view) {

        cAreaInfoView  oViewInfo = new   cAreaInfoView();
        //oViewInfo.AreaId =  ((cSpinnerItem)spinnerLogisticAreas.getSelectedItem()).getField();  //txtAreaId.getText().toString();
        oViewInfo.AreaId = txtAreaId.getText().toString();


        if (  oViewInfo.AreaId.trim().isEmpty() ){

            Toast.makeText(getApplicationContext(),"AREA field is required", Toast.LENGTH_SHORT).show();

        } else{

            ((cGlobalData)getApplication()).CurrentArea = oViewInfo.AreaId.trim();

            Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
            //oIntent.putExtra("oDataParam",oViewInfo);
            oIntent.putExtra("oMsg", new cActivityMessage(cMessage.Message.START, oViewInfo.AreaId ));
            startActivity(oIntent);
        }
    }


    public void onScan(View view) {

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("LogisticAreaCount",Scanner.ScanType.SCAN_TASK));
        startActivity(oIntent);


/*        txtAreaId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }


    public  void  goLogisticDes(View view){

        Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
        startActivity(oIntent);
    }

    public void   onClickConfirm(View spinner) {
        Intent oIntent = new Intent(this, LogisticAreaCountDes.class);
        startActivity(oIntent);
    }


    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCount.this);
            vProgressDialog.setMessage("Scanning TASK...");
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
            txtAreaId.setText("E01/E01-1");
            vProgressDialog.hide();
        }
    }

    private class AsyncTaskGetLogisticAreas extends AsyncTask<String, String,  ArrayList<cLogisticsArea>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(LogisticAreaCount.this);
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

            // ArrayList<String>  lsAreas = new  ArrayList<>();

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cLogisticsArea  oData  = lsData.get(i);

                if (!oData.ID.equals("")){
                    oSet.add(  oData.SiteID + "/" + oData.ID);
                }
            }

            if ( oSet.size() > 0){

                LsCatalogLogisticAreas.addAll(oSet);
                Collections.sort(LsCatalogLogisticAreas);
                ((cGlobalData)getApplication()).LsCatalogLogisticAreasS = LsCatalogLogisticAreas;
            }

           // fillDataFilterLogisticAreas();
            vProgressDialog.hide();
        }
    }
}

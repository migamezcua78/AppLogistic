package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OutBound extends AppCompatActivity {

    private TextView  txtTaskId;
    private Spinner spinner;
    private TableLayout tableLayout;
    private cDataGrid  oDataGrid;

    private EditText  txtImputFilterId;
    private TextView  lbOrderValueId;
    private TextView  lbTaskValueId;


    //  DATA
    private ArrayList <String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();
    ArrayList<cOutboundViewInfo>  lsOutbounItems =  new  ArrayList<>();

    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;
    Boolean Scanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_bound);
        Init();
        StarActivity();
    }




    private void Init (){
        txtTaskId = findViewById(R.id.lbTaskId);
        txtImputFilterId = findViewById(R.id.txtImputFilterId);
        lbOrderValueId = findViewById(R.id.lbOrderValueId);
        lbTaskValueId = findViewById(R.id.lblTaskValueId);

        Scanned = false;

        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");

        InfoData = new ArrayList <> ();
    }


    private void StarActivity() {

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){

               //  txtImputFilterId.setText("15");


                txtImputFilterId.setText(oMsg.getKey01());

                Scanned = true;
                AsyncTaskScan asyncTask=new AsyncTaskScan();
                asyncTask.execute("params");

            }  else if (!oMsg.getMessage().equals(cMessage.Message.START)){

                AsyncTaskStart asyncTask=new AsyncTaskStart();
                asyncTask.execute("params");
            }
        }

        fillDataFilter();
        fillDataGrid();
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

    public void   onClickScanTask(View spinner) {

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("OutBound",Scanner.ScanType.SCAN_TASK));
        startActivity(oIntent);



/*
        txtImputFilterId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");
*/

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

            if( lsOutbounItems != null && lsOutbounItems.size() > 0 &&  Scanned == true ){

                for ( cOutboundViewInfo e:lsOutbounItems){
                    getParamInfo(e);
                }

                Intent oIntent = new Intent(this, PickSourceEmb.class);
                oIntent.putExtra("oMsg", getActivityMsg() );
                startActivity(oIntent);

            }else {

              //  Toast.makeText(getApplicationContext(),"There are NO items in task selected", Toast.LENGTH_SHORT).show();

                AsyncTaskStart asyncTaskConfirm=new AsyncTaskStart();
                asyncTaskConfirm.execute("params");
            }
        }
    }

    public void   onClickNext(View spinner) {
        Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
        Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();
    }


    private  List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,getString(R.string.TaskId), "TaskId"));
      //  InfoFilter.add(new cSpinnerItem(2,getString(R.string.BarCodeId),"BarCodeId"));
      //  InfoFilter.add(new cSpinnerItem(3,getString(R.string.LabelId),"LabelId"));
       // InfoFilter.add(new cSpinnerItem(4,getString(R.string.ReferenceId),"ReferenceId"));

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

    private class AsyncTaskScan extends AsyncTask<String, String,  ArrayList<cPurchaseItem>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(OutBound.this);
            vProgressDialog.setMessage("Loading Data...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            ArrayList<cPurchaseItem> lsData = new ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetPurchaseItemServiceData(cServices.PurchaseItemFilterType.ID, txtImputFilterId.getText().toString(), "");

                //  lsData = ocServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID, "*", "1");

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
        protected void onPostExecute(String lsData) {
            super.onPostExecute(lsData);



            InfoData = new ArrayList <> ();
            lsOutbounItems =  new  ArrayList<>();


            cOutboundViewInfo  oOutboundViewInfo = new cOutboundViewInfo();

            oOutboundViewInfo.SourceId = "Z03C";
            oOutboundViewInfo.ProductId = "CR2200EO36AL00_1";
            oOutboundViewInfo.Open = "5";
            oOutboundViewInfo.OpenUnit = "ea";
            oOutboundViewInfo.TaskId = "2362";
            oOutboundViewInfo.IdentStock = "30541";

            lsOutbounItems.add(oOutboundViewInfo);
            InfoData.add(new String[]{oOutboundViewInfo.ProductId,oOutboundViewInfo.Open + " " + oOutboundViewInfo.OpenUnit });

            oOutboundViewInfo = new cOutboundViewInfo();
            oOutboundViewInfo.SourceId = "Z03C";
            oOutboundViewInfo.ProductId = "CR2200EO36AL00_2";
            oOutboundViewInfo.Open = "2";
            oOutboundViewInfo.OpenUnit = "ea";
            oOutboundViewInfo.TaskId = "2362";
            oOutboundViewInfo.IdentStock = "30541";


            lsOutbounItems.add(oOutboundViewInfo);
            InfoData.add(new String[]{oOutboundViewInfo.ProductId,oOutboundViewInfo.Open + " " + oOutboundViewInfo.OpenUnit });


            oOutboundViewInfo = new cOutboundViewInfo();
            oOutboundViewInfo.SourceId = "Z03C";
            oOutboundViewInfo.ProductId = "CR2200EO36AL00_3";
            oOutboundViewInfo.Open = "4";
            oOutboundViewInfo.OpenUnit = "ea";
            oOutboundViewInfo.TaskId = "2362";
            oOutboundViewInfo.IdentStock = "30541";


            lsOutbounItems.add(oOutboundViewInfo);
            InfoData.add(new String[]{oOutboundViewInfo.ProductId,oOutboundViewInfo.Open + " " + oOutboundViewInfo.OpenUnit });


            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsOutboudItems = lsOutbounItems;

          //  lbOrderValueId.setText("1392");
            lbTaskValueId.setText(txtImputFilterId.getText().toString());

            oDataGrid.RemoveAllItems();

            fillDataGrid();

            vProgressDialog.hide();
        }
    }
*/






    private class AsyncTaskScan extends AsyncTask<String, String,  ArrayList<cPurchaseItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(OutBound.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cPurchaseItem> doInBackground(String... strings) {
            ArrayList<cPurchaseItem> lsData = new ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetPurchaseItemServiceData(cServices.PurchaseItemFilterType.ID, txtImputFilterId.getText().toString(), "");

                //  lsData = ocServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID, "*", "1");

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
        protected void onPostExecute(ArrayList<cPurchaseItem> lsData) {

            super.onPostExecute(lsData);

           // lblTaskValueId.setText( txtImputFilterId.getText().toString().trim() + "  "  +  "Pick");
            //lbOrderValueId.setText("365");

            lsOutbounItems = new  ArrayList<>();

            InfoData = new ArrayList <> ();
            cOutboundViewInfo  oItemView = new cOutboundViewInfo();

            for (int i = 0; i < lsData.size(); i++) {
                cPurchaseItem oData = lsData.get(i);

                if( !oData.ID.trim().isEmpty()){

                    oItemView = new cOutboundViewInfo();
                    oItemView.ProductId = oData.ID;
                    oItemView.Open = oData.Quantity;
                    oItemView.OpenUnit = oData.QuantityUnitCode;


                /*    oInboundViewInfo.ProductId = oData.getProductCategoryID() +  "_" + String.valueOf(i);
                    oInboundViewInfo.Open = String.valueOf(i+2);   // oMaterial.getQuantity();
                    oInboundViewInfo.OpenUnit =  oData.getQuantityUnitCode();
                    oInboundViewInfo.TargetId = "MC64920-50-10-10-04_" + String.valueOf(i);
                    oInboundViewInfo.IdentStock = "43668";*/

                    lsOutbounItems.add(oItemView);
                    InfoData.add(new String[]{ oItemView.ProductId, oItemView.Open + "  " + oItemView.OpenUnit });
                }
            }


            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsOutboudItems = lsOutbounItems;

            oDataGrid.RemoveAllItems();
            fillDataGrid();


/*            if( lsOutbounItems != null && lsOutbounItems.size() > 0 ){

                for ( cOutboundViewInfo e:lsOutbounItems){
                    getParamInfo(e);
                }

                Intent oIntent = new Intent(OutBound.this, PutAwayTarget.class);
                oIntent.putExtra("oMsg", getActivityMsg());
                startActivity(oIntent);

            }else {
                Toast.makeText(getApplicationContext(),"No hay elementos para la Tarea seleccionada", Toast.LENGTH_SHORT).show();
            }*/

            vProgressDialog.hide();
        }
    }

//  MIke Original
/*
    private class AsyncTaskStart extends AsyncTask<String, String,  ArrayList<cPurchaseItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(OutBound.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cPurchaseItem> doInBackground(String... strings) {
            ArrayList<cPurchaseItem> lsData = new ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetPurchaseItemServiceData(cServices.PurchaseItemFilterType.ID, txtImputFilterId.getText().toString(), "");

                //  lsData = ocServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID, "*", "1");

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
        protected void onPostExecute(ArrayList<cPurchaseItem> lsData) {

            super.onPostExecute(lsData);

            // lblTaskValueId.setText( txtImputFilterId.getText().toString().trim() + "  "  +  "Pick");
            //lbOrderValueId.setText("365");

            lsOutbounItems = new  ArrayList<>();

            InfoData = new ArrayList <> ();
            cOutboundViewInfo  oItemView = new cOutboundViewInfo();

            for (int i = 0; i < lsData.size(); i++) {
                cPurchaseItem oData = lsData.get(i);

                if( !oData.ID.trim().isEmpty()){

                    oItemView = new cOutboundViewInfo();
                    oItemView.ProductId = oData.ID;
                    oItemView.Open = oData.Quantity;
                    oItemView.OpenUnit = oData.QuantityUnitCode;


                */
/*    oInboundViewInfo.ProductId = oData.getProductCategoryID() +  "_" + String.valueOf(i);
                    oInboundViewInfo.Open = String.valueOf(i+2);   // oMaterial.getQuantity();
                    oInboundViewInfo.OpenUnit =  oData.getQuantityUnitCode();
                    oInboundViewInfo.TargetId = "MC64920-50-10-10-04_" + String.valueOf(i);
                    oInboundViewInfo.IdentStock = "43668";*//*


                    lsOutbounItems.add(oItemView);
                    InfoData.add(new String[]{ oItemView.ProductId, oItemView.Open + "  " + oItemView.OpenUnit });
                }
            }


            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsOutboudItems = lsOutbounItems;

            oDataGrid.RemoveAllItems();
            fillDataGrid();


            if( lsOutbounItems != null && lsOutbounItems.size() > 0 ){

                for ( cOutboundViewInfo e:lsOutbounItems){
                    getParamInfo(e);
                }

                Intent oIntent = new Intent(OutBound.this, PickSourceEmb.class);
                oIntent.putExtra("oMsg", getActivityMsg() );
                startActivity(oIntent);

            }else {
                Toast.makeText(getApplicationContext(),"No hay elementos para la Tarea seleccionada", Toast.LENGTH_SHORT).show();
            }

            vProgressDialog.hide();
        }
    }
*/


    private class AsyncTaskStart extends AsyncTask<String, String,  ArrayList<cTaskResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(OutBound.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cTaskResponse> doInBackground(String... strings) {
            ArrayList<cPurchaseItem> lsData = new ArrayList<>();
            ArrayList<cTaskResponse> lsData2 = new ArrayList<>();
            ArrayList<cMaterial>  lsMaterialData = new  ArrayList<>();
            cTaskResponse   oTaskResponse = new  cTaskResponse();
            cProductResponse oResp = new  cProductResponse();

            try {

                cServices ocServices = new cServices();
               // lsData = ocServices.GetPurchaseItemServiceData(cServices.PurchaseItemFilterType.ID, txtImputFilterId.getText().toString(), "");

                lsData2 = ocServices.GetTaskServiceData(cServices.GetTaskkFilterType.SelectionBySiteLogisticsTaskID, txtImputFilterId.getText().toString(), "");

                if (lsData2  != null   && lsData2.size() >  0 ){
                    oTaskResponse =  lsData2.get(0);

                    for(cMaterialSimpleData  item: oTaskResponse.MaterialsInput){
                       // lsMaterialData = ocServices.GetMaterialsServiceData(item.ProductID, txtImputFilterId.getText().toString().trim(),"1");

                        lsMaterialData = ocServices.GetMaterialsServiceData(cServices.MaterialFilterType.SelectionByInternalID,item.ProductID,"1");

                        if(lsMaterialData != null  && lsMaterialData.size() > 0 ){

                            if(!lsMaterialData.get(0).InternalID.trim().equals("")){
                                String  sInternalID = lsMaterialData.get(0).InternalID.trim();
                                String  sMaterialShortName = lsMaterialData.get(0).Description.replace(sInternalID,"").trim();

                                if( sMaterialShortName.length() > 30) {
                                    item.MaterialShortName = sMaterialShortName.substring(0,30) + " ..";
                                } else {
                                    item.MaterialShortName = sMaterialShortName;
                                }
                            }
                        }

                        cProductViewInfo oCurrectProductViewInfo = new cProductViewInfo();
                        oCurrectProductViewInfo.ProductoSAPId =  item.ProductID;
                        oResp = ocServices.PostConsultProductDataService(oCurrectProductViewInfo);

                        if  (oResp != null){
                            if(!oResp.ResponseId.equals("-1")){
                                if (oResp.Assigned){
                                    item.MaterialBarCode = oResp.CodigoBarra;
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsData2;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cTaskResponse> lsData) {

            super.onPostExecute(lsData);

            lsOutbounItems = new  ArrayList<>();

            InfoData = new ArrayList <> ();
            cOutboundViewInfo  oItemView = new cOutboundViewInfo();
            cTaskResponse   oTaskResponse = new  cTaskResponse();

            ((cGlobalData)getApplication()).CurrentTaskResponse = new cTaskResponse();

            if(  lsData.size() > 0) {
                oTaskResponse =  lsData.get(0);

                ((cGlobalData)getApplication()).CurrentTaskResponse = oTaskResponse;

                for (int i = 0; i < oTaskResponse.MaterialsInput.size(); i++) {
                    cMaterialSimpleData oData = oTaskResponse.MaterialsInput.get(i);

                    if( !oData.ProductID.trim().isEmpty()){

                        oItemView = new cOutboundViewInfo();
                        oItemView.ProductId = oData.ProductID;
                        oItemView.Open = oData.OpenQuantity;
                        oItemView.OpenUnit = oData.OpenQuantityUnitCode;
                        oItemView.SourceId = oData.SourceLogisticsAreaID;
                        oItemView.ProductName = oData.MaterialShortName;
                        oItemView.BarCode = oData.MaterialBarCode;


                      //  oItemView.SourceId


                /*    oInboundViewInfo.ProductId = oData.getProductCategoryID() +  "_" + String.valueOf(i);
                    oInboundViewInfo.Open = String.valueOf(i+2);   // oMaterial.getQuantity();
                    oInboundViewInfo.OpenUnit =  oData.getQuantityUnitCode();
                    oInboundViewInfo.TargetId = "MC64920-50-10-10-04_" + String.valueOf(i);
                    oInboundViewInfo.IdentStock = "43668";*/

                        lsOutbounItems.add(oItemView);
                        InfoData.add(new String[]{ oItemView.ProductId, oItemView.Open + "  " + oItemView.OpenUnit});
                    }
                }
            }

            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsOutboudItems = lsOutbounItems;

            oDataGrid.RemoveAllItems();
            fillDataGrid();


            if( lsOutbounItems != null && lsOutbounItems.size() > 0 ){

                for ( cOutboundViewInfo e:lsOutbounItems){
                    getParamInfo(e);
                }

                Intent oIntent = new Intent(OutBound.this, PickSourceEmb.class);
                oIntent.putExtra("oMsg", getActivityMsg() );
                startActivity(oIntent);

            }else {
                Toast.makeText(getApplicationContext(),"No hay elementos para la Tarea seleccionada", Toast.LENGTH_SHORT).show();
            }

            vProgressDialog.hide();
        }
    }


    private void  getParamInfo(cOutboundViewInfo  pViewInfo) {

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

}

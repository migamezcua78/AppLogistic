package com.logistica.applogistic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ShipmentConfirmationConf extends MainBaseActivity {

    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList<String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;
    cConfirmTaskResponse oConfirmTaskResponse = new  cConfirmTaskResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_confirmation_conf);

        Init();

        InfoData = new ArrayList <> ();
        cGlobalData  oGlobalData=  (cGlobalData)getApplication();

        if (oGlobalData.LsIntboudIShipmentItems != null){
            for ( cInboundViewInfo e:oGlobalData.LsIntboudIShipmentItems){

                InfoData.add(new String[]{ e.ProductId, e.ConfirmedOty + " " + e.OpenUnit });

            }
        }

        fillDataGrid();
    }


    private void Init (){
        tableLayout = findViewById(R.id.tgProductos);



    }

    public void   onClickConfirm(View spinner) {

        AsyncTaskFinishTask asyncTask=new AsyncTaskFinishTask();
        asyncTask.execute("params");
    }

    private void fillDataGrid (){
        oDataGrid = new cDataGrid(tableLayout,getApplicationContext());
        oDataGrid.addHeader(getInfoHeader());
        oDataGrid.addData(getInfoData());
    }

    private  String[] getInfoHeader(){
        InfoHeader = new String[]{"Product","Actual Qty"};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){
        return  InfoData;
    }


    ProgressDialog vProgressDialog;

    private class AsyncTaskFinishTask extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(ShipmentConfirmationConf.this);
            vProgressDialog.setMessage("Sending Task Information ... ");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                // Thread.sleep(1000);

                cConfirmOutboundItemsTask oConfirmOutboundItemsTask = new cConfirmOutboundItemsTask();

                cServices  oServices = new cServices();
                cGlobalData  oGlobalData=  (cGlobalData)getApplication();

                if  (oGlobalData.LsIntboudIShipmentItems != null ){

                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cConfirmOutboundItem oConfirmOutboundItem = new  cConfirmOutboundItem();

                    for ( cInboundViewInfo e:oGlobalData.LsIntboudIShipmentItems){

                        cInboundDelivery  oInboundDelivery = new cInboundDelivery();

                        oInboundDelivery.ID = e.TaskId;
                        oInboundDelivery.oInboundDeliveryItem.ID = e.ProductId;
                        oInboundDelivery.oInboundDeliveryItem.CantidadConfirmada = e.Qty;
                        oInboundDelivery.oInboundDeliveryItem.IDAreaLogistica = e.TargetId;
                        oInboundDelivery.oInboundDeliveryItem.IDStockIdentificado = e.IdentStock;


                        oConfirmOutboundItemsTask.outboundDeliveryID =  e.TaskId;
                        oConfirmOutboundItemsTask.date  = sdf.format(new Date());
                        oConfirmOutboundItemsTask.confirmationStatus  = "1";

                        oConfirmOutboundItem = new  cConfirmOutboundItem();
                        oConfirmOutboundItem.productID =  e.ProductId;
                        oConfirmOutboundItem.confirmedQuantity =  e.Qty;
                        oConfirmOutboundItem.confirmedQuantityUnitCode =  e.OpenUnit;
                        oConfirmOutboundItemsTask.lsItems.add(oConfirmOutboundItem);


                    }

                    oConfirmTaskResponse = oServices.PutConfirmOutboundTaskServiceData(oConfirmOutboundItemsTask);

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

            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsIntboudIShipmentItems = new ArrayList<>();

            vProgressDialog.hide();

            if (oConfirmTaskResponse.Msg.equals("") && !oConfirmTaskResponse.SiteLogisticsTaskSeverityCode.equals("") ){
                Toast.makeText(getApplicationContext(),"Task Confirmed SAP_UUID: " +  oConfirmTaskResponse.SiteLogisticsTaskSeverityCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),"Task NOT Confirmed ERROR: " +  oConfirmTaskResponse.Msg, Toast.LENGTH_LONG).show();
            }

            Intent oIntent = new Intent(ShipmentConfirmationConf.this, ShipmentConfirmation.class);
            startActivity(oIntent);
        }
    }
}

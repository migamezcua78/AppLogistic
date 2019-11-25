package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConfirmTask extends AppCompatActivity {

    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList<String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;


    // Data
    private List<cSpinnerItem> InfoFilter = new ArrayList<>();
    ArrayList<cOutboundViewInfo>  lsOutbounItems;
    cOutboundViewInfo  oCurrentItemViewInfo;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_task);
        Init();
        StartActivity();
    }

    private void Init (){
        tableLayout = findViewById(R.id.tgProductos);


        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        lsOutbounItems = ((cGlobalData)getApplication()).LsOutboudItems;
    }

    public void   onClickConfirm(View spinner) {
        AsyncTaskSendingTaskInformation asyncTask=new AsyncTaskSendingTaskInformation();
        asyncTask.execute("params");
    }

    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, PickSourceEmb.class);
        oIntent.putExtra("oMsg", new cActivityMessage("Reload"));
        startActivity(oIntent);
    }


    private void StartActivity (){

        if (lsOutbounItems != null & lsOutbounItems.size() > 0  )
        {
            InfoData = new ArrayList <> ();

            for ( cOutboundViewInfo e:lsOutbounItems){
                float  dif = 0;
                if (Float.parseFloat(e.Open) < Float.parseFloat(e.Qty)){
                    dif = 0;
                } else {

                    dif = Float.parseFloat(e.Open) - Float.parseFloat(e.Qty);
                }

                InfoData.add(new String[]{ e.ProductId, dif + " " + e.OpenUnit, e.SourceId });
            }
        }

        fillDataGrid();
    }

    private void fillDataGrid (){

        if ( oDataGrid != null   ){
            oDataGrid.RemoveAllItems();
        }
        oDataGrid = new cDataGrid(tableLayout,getApplicationContext());
        oDataGrid.addHeader(getInfoHeader());
        oDataGrid.addData(getInfoData());
    }

    private  String[] getInfoHeader(){
        InfoHeader = new String[]{"Product","Open", "Source"};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){
        return  InfoData;
    }




    private class AsyncTaskSendingTaskInformation extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(ConfirmTask.this);
            vProgressDialog.setMessage("Sending Task Information ...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {

              //  Thread.sleep(1000);


                if (lsOutbounItems != null & lsOutbounItems.size() > 0  )
                {
                   // InfoData = new ArrayList <> ();
                    cServices  oServices = new cServices();

                    for ( cOutboundViewInfo e:lsOutbounItems){
                        float  dif = 0;
                        if (Float.parseFloat(e.Open) < Float.parseFloat(e.Qty)){
                            dif = 0;
                        } else {

                            dif = Float.parseFloat(e.Open) - Float.parseFloat(e.Qty);
                        }

                        cInboundDelivery  oInboundDelivery = new cInboundDelivery();

                        oInboundDelivery.ID = e.TaskId;
                        oInboundDelivery.oInboundDeliveryItem.ID = e.ProductId;
                        oInboundDelivery.oInboundDeliveryItem.CantidadConfirmada =  String.valueOf(dif);
                        oInboundDelivery.oInboundDeliveryItem.IDAreaLogistica = e.SourceId;
                        oInboundDelivery.oInboundDeliveryItem.IDStockIdentificado = e.IdentStock;

                        oServices.PutInboundDeliveryServiceData(oInboundDelivery);

                       // InfoData.add(new String[]{ e.ProductId, dif + " " + e.OpenUnit, e.SourceId });
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

            Toast.makeText(getApplicationContext(),"Sending Successful", Toast.LENGTH_LONG).show();
            Intent oIntent = new Intent(ConfirmTask.this, OutBound.class);
            startActivity(oIntent);
        }
    }
}

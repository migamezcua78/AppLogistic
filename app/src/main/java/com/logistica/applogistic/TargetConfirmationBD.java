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

public class TargetConfirmationBD extends MainBaseActivity {

    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList<String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_confirmation_bd);
        Init();

        InfoData = new ArrayList <> ();
        cGlobalData  oGlobalData=  (cGlobalData)getApplication();
        for ( cInboundViewInfo e:oGlobalData.LsIntboudItems){

            if(e.Confirmed) {
                InfoData.add(new String[]{ e.ProductId, e.Qty + " " + e.OpenUnit });
            }
        }

        fillDataGrid();
    }


    private void Init (){
        tableLayout = findViewById(R.id.tgProductos);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        Intent oIntent = new Intent(this, InboundBD.class);
//        startActivity(oIntent);
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
            vProgressDialog = new ProgressDialog(TargetConfirmationBD.this);
            vProgressDialog.setMessage("Sending Task Information ... ");
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

            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsIntboudItems = new ArrayList<>();

            vProgressDialog.hide();

            Toast.makeText(getApplicationContext(),"Task Confirmed", Toast.LENGTH_LONG).show();
            Intent oIntent = new Intent(TargetConfirmationBD.this, InboundBD.class);
            startActivity(oIntent);
        }
    }
}

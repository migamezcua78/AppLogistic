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

public class ConfirmTask extends AppCompatActivity {

    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList<String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_task);
        Init();

        cGlobalData  oGlobalData=  (cGlobalData)getApplication();
        for ( cOutboundViewInfo e:oGlobalData.LsOutboudItems){
            int  dif = 0;
            if (Integer.parseInt(e.Open) < Integer.parseInt(e.Qty)){
                dif = 0;
            } else {

                dif = Integer.parseInt(e.Open) - Integer.parseInt(e.Qty);
            }

            InfoData = new ArrayList <> ();
            InfoData.add(new String[]{ e.ProductId, dif + " " + e.OpenUnit, e.SourceId });
        }

        fillDataGrid();
    }

    private void Init (){
        tableLayout = findViewById(R.id.tgProductos);
    }

    public void   onClickConfirm(View spinner) {
        AsyncTaskSendingTaskInformation asyncTask=new AsyncTaskSendingTaskInformation();
        asyncTask.execute("params");
    }

    private void fillDataGrid (){
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


    ProgressDialog vProgressDialog;

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
            vProgressDialog.hide();

            Toast.makeText(getApplicationContext(),"Sending Successful", Toast.LENGTH_LONG).show();
            Intent oIntent = new Intent(ConfirmTask.this, OutBound.class);
            startActivity(oIntent);
        }
    }
}

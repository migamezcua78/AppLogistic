package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GoodMovement extends AppCompatActivity {


    private TextView  txtFilter;
    private TextView  txtTaskId;
    private Spinner spinner;
    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList <String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    ProgressDialog vProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_movement);
        Init();
        fillDataFilter();
        fillDataGrid();

    }

    private void Init (){
        txtTaskId = findViewById(R.id.lbTaskId);
        txtFilter = findViewById(R.id.txtImputFilterId);
        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);
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


    private  List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"Task ID"));
        InfoFilter.add(new cSpinnerItem(1,"Bar Code"));
        InfoFilter.add(new cSpinnerItem(1,"Label ID"));
        InfoFilter.add(new cSpinnerItem(1,"Reference"));

        return  InfoFilter;
    }


    public void onBack(View view) {
        finish();
    }

    public void onStartTask(View view) {

        Intent oIntent = new Intent(this, Goods_Movement_Source.class);
        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);
    }

    public void onScan(View view) {

        txtFilter.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");
    }


    private  String[] getInfoHeader(){
        InfoHeader = new String[]{"Product","Planned Quantity"};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){

        InfoData = new ArrayList <> ();

      //  InfoData.add(new String[]{"MCF-0001","5 ea"});
        // InfoData.add(new String[]{"MCF-0002","6 ea"});
/*
        InfoData.add(new String[]{"MCF-0003","7 ea"});
        InfoData.add(new String[]{"MCF-0004","8 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","12 ea"});
*/
        return  InfoData;
    }


    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(GoodMovement.this);
            vProgressDialog.setMessage("Scanning Simulation...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {

            try {
                Thread.sleep(1500);
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
            txtFilter.setText("SCAN-VALUE-123");
            vProgressDialog.hide();
        }
    }
}

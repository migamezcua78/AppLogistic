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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GoodMovement extends MainBaseActivity {


    private TextView  txtFilterValue;
    private TextView  lblSelectedFilter;
    private Spinner spinner;
    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList <String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_movement);
        Init();
        StarActivity();
    }

    public   void StarActivity(){

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){
                // oCurrentItemViewInfo.SourceId = oMsg.getKey01();
              //  txtFilterValue.setText("SCAN-VALUE-123");

                txtFilterValue.setText(oMsg.getKey01());
            }
        }
        fillDataFilter();
        fillDataGrid();
    }


    private void Init (){
        lblSelectedFilter =  findViewById(R.id.lbTaskId);
        txtFilterValue = findViewById(R.id.txtImputFilterId);
        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        ((cGlobalData)getApplication()).ReferenceId = "";
    }


    @Override
    public void onBackPressed() {

        Intent oIntent = new Intent(this, Inicio.class);
        // oIntent.putExtra("oDataParam",oMovementParam);
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


    private  List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(4,getString(R.string.ReferenceId),"ReferenceId"));
        InfoFilter.add(new cSpinnerItem(1,getString(R.string.TaskId), "TaskId"));
      //  InfoFilter.add(new cSpinnerItem(2,getString(R.string.BarCodeId),"BarCodeId"));
      //  InfoFilter.add(new cSpinnerItem(3,getString(R.string.LabelId),"LabelId"));
      //  InfoFilter.add(new cSpinnerItem(4,getString(R.string.ReferenceId),"ReferenceId"));

        return  InfoFilter;
    }


    public void onBack(View view) {
        finish();
    }

    public void onStartTask(View view) {

        if (txtFilterValue.getText().toString().isEmpty()) {

            Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString() + " field is required", Toast.LENGTH_SHORT).show();

        } else {

            ((cGlobalData)getApplication()).ReferenceId = txtFilterValue.getText().toString();

            Intent oIntent = new Intent(this, Goods_Movement_Source.class);
            oIntent.putExtra("oMsg", getActivityMsg());
            startActivity(oIntent);
        }

/*        cMovementViewInfo  oMov = new   cMovementViewInfo();
        oMov.TaskId =  txtFilterValue.getText().toString();

        if (  oMov.TaskId.trim().isEmpty() ){

            Toast.makeText(getApplicationContext(),"TASK field is required", Toast.LENGTH_SHORT).show();

        } else{

            Intent oIntent = new Intent(this, Goods_Movement_Source.class);
            oIntent.putExtra("oDataParam",oMov);
            startActivity(oIntent);
        }
        */
    }

    private cActivityMessage getActivityMsg(){
        cActivityMessage   oMsg = new  cActivityMessage();

        oMsg.setMessage("NewItem");
        cSpinnerItem   oSpiItem=  (cSpinnerItem)spinner.getSelectedItem();
        oMsg.setKey01(oSpiItem.getField());
        oMsg.setKey02(txtFilterValue.getText().toString().trim());

        return  oMsg;
    }

    public void onScan(View view) {


        txtFilterValue.setText("SCAN-VALUE-123");
        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("GoodMovement",Scanner.ScanType.SCAN_TASK));
        startActivity(oIntent);

/*        txtFilterValue.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }


    private  String[] getInfoHeader(){
        InfoHeader = new String[]{ getString(R.string.Product)  ,getString(R.string.PlannedQuantity)};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){
        InfoData = new ArrayList <> ();
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
            txtFilterValue.setText("SCAN-VALUE-123");
            vProgressDialog.hide();
        }
    }
}

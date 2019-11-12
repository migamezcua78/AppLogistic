package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

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


        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");

        InfoData = new ArrayList <> ();
    }


    private void StarActivity() {

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){

                // txtImputFilterId.setText("");
                AsyncTaskScan asyncTask=new AsyncTaskScan();
                asyncTask.execute("params");

            }  else if (!oMsg.getMessage().equals(cMessage.Message.START)){

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

            if( lsOutbounItems != null && lsOutbounItems.size() > 0 ){

                for ( cOutboundViewInfo e:lsOutbounItems){
                    getParamInfo(e);
                }

                Intent oIntent = new Intent(this, PickSourceEmb.class);
                oIntent.putExtra("oMsg", getActivityMsg() );
                startActivity(oIntent);

            }else {
                Toast.makeText(getApplicationContext(),"There are NO items in task selected", Toast.LENGTH_SHORT).show();
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

        InfoFilter.add(new cSpinnerItem(1,"Task ID", "TaskId"));
        InfoFilter.add(new cSpinnerItem(2,"Bar Code","BarCodeId"));
        InfoFilter.add(new cSpinnerItem(3,"Label ID","LabelId"));
        InfoFilter.add(new cSpinnerItem(4,"Reference","ReferenceId"));

        return  InfoFilter;
    }

    private  String[] getInfoHeader(){
        InfoHeader = new String[]{"Product","Planned Quantity"};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){

        return  InfoData;
    }



    private class AsyncTaskScan extends AsyncTask<String, String,  String> {
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


            txtImputFilterId.setText("2362");
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

            lbOrderValueId.setText("1392");
            lbTaskValueId.setText(txtImputFilterId.getText().toString());

            oDataGrid.RemoveAllItems();

            fillDataGrid();

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

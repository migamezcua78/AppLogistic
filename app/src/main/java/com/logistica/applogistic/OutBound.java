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

import org.w3c.dom.Text;

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

    ProgressDialog vProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_bound);
        Init();
        fillDataFilter();
        fillDataGrid();
    }


    private void Init (){
        txtTaskId = findViewById(R.id.lbTaskId);
        txtImputFilterId = findViewById(R.id.txtImputFilterId);
        lbOrderValueId = findViewById(R.id.lbOrderValueId);
        lbTaskValueId = findViewById(R.id.lbTaskValueId);


        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);

        InfoData = new ArrayList <> ();
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
        txtImputFilterId.setText("");
        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");

    }


    public void   onClickStartTask(View spinner) {

        if ( txtImputFilterId.getText().toString().trim().isEmpty() ){

            Toast.makeText(getApplicationContext(),"TASK field is required", Toast.LENGTH_SHORT).show();

        } else{

            Intent oIntent = new Intent(this, PickSourceEmb.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);
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

        InfoFilter.add(new cSpinnerItem(1,"Task ID"));
        InfoFilter.add(new cSpinnerItem(1,"Reference"));
        InfoFilter.add(new cSpinnerItem(1,"Label ID"));
        InfoFilter.add(new cSpinnerItem(1,"Bar Code"));

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
            vProgressDialog.setMessage("Scanning Task...");
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


            ArrayList<cOutboundViewInfo>  lsOutbounItems =  new  ArrayList<>();
            cOutboundViewInfo  oOutboundViewInfo = new cOutboundViewInfo();

            oOutboundViewInfo.SourceId = "Z03C";
            oOutboundViewInfo.ProductId = "CR2200EO36AL00_1";
            oOutboundViewInfo.Open = "5";
            oOutboundViewInfo.OpenUnit = "ea";
            oOutboundViewInfo.TaskId = "2362";
            oOutboundViewInfo.IdentStock = "30541";


            lsOutbounItems.add(oOutboundViewInfo);
            InfoData.add(new String[]{oOutboundViewInfo.ProductId,oOutboundViewInfo.Open});


/*            oOutboundViewInfo = new cOutboundViewInfo();
            oOutboundViewInfo.SourceId = "Z03C";
            oOutboundViewInfo.ProductId = "CR2200EO36AL00_2";
            oOutboundViewInfo.Open = "2 ea";
            oOutboundViewInfo.TaskId = "2362";
            oOutboundViewInfo.IdentStock = "30541";

            lsOutbounItems.add(oOutboundViewInfo);
            InfoData.add(new String[]{oOutboundViewInfo.ProductId,oOutboundViewInfo.Open});*/

            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsOutboudItems = lsOutbounItems;

            lbOrderValueId.setText("1392");
            lbTaskValueId.setText(txtImputFilterId.getText().toString());

            oDataGrid.RemoveAllItems();

            fillDataGrid();

            vProgressDialog.hide();
        }
    }
}

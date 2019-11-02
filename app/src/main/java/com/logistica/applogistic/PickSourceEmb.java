package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PickSourceEmb extends AppCompatActivity {

    EditText txtProductId;
    EditText txtSourceId;
    EditText txtQtyId;
    EditText txtIdentStockId;
    CheckBox cheRestrictedId;
    CheckBox chkConfirmedId;

    EditText txtLuId;
    EditText txtLuQtyId;
    EditText txtBarCodeId;

    TextView lblOpenValueId;

    TextView lblCountItemsId;

    private Spinner spinner;

    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();
    cActivityMessage   oActivityMessage;
    ArrayList<cOutboundViewInfo>  lsOutbounItems;

    cOutboundViewInfo  oOutboundViewInfo;

    ProgressDialog vProgressDialog;

    int countItems;
    int TotalItems;
    int consecutive;
    int Quantity;
    int iterater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_source_emb);
        Init();

        oActivityMessage = (cActivityMessage)getIntent().getSerializableExtra("oMssg");

        cGlobalData  oGlobalData=  (cGlobalData)getApplication();
        lsOutbounItems = oGlobalData.LsOutboudItems;

      if ( oActivityMessage.getMessage().equals("ConfirmedPartially") )
      {
          for ( cOutboundViewInfo e:lsOutbounItems){
              if (e.ProductId.equals(oActivityMessage.getKey01())){

                  e.Confirmed = true;
                  e.ConfirmedPartially = true;
                  setViewInfo(e);

                  AsyncTaskAllItemConfirmed asyncTask=new AsyncTaskAllItemConfirmed();
                  asyncTask.execute("params");
              }
          }
      } else {

          if (lsOutbounItems.size() > 0 ){
              iterater = 1;
              lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsOutbounItems.size()) );
              cOutboundViewInfo   oOutboundViewInfo = lsOutbounItems.get(iterater - 1);
              setViewInfo(oOutboundViewInfo);
          }
      }

        fillDataUnits();
    }


    private void Init (){
        spinner = findViewById(R.id.spiUnitId);
        lsOutbounItems = new  ArrayList<>();

        txtSourceId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        txtQtyId = findViewById(R.id.txtQtyId);
        txtIdentStockId = findViewById(R.id.txtIdentStockId);
        cheRestrictedId = findViewById(R.id.cheRestrictedId);
        chkConfirmedId = findViewById(R.id.chkConfirmedId);
        txtLuId = findViewById(R.id.txtSerialNumberId);
        txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtBarCodeId = findViewById(R.id.txtStockId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        lblCountItemsId = findViewById(R.id.lblCountItemsId);


        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 0;

    }


    private void setViewInfo(cOutboundViewInfo pOutboundViewInfo){

        try {

            txtSourceId.setText(pOutboundViewInfo.SourceId);
            txtProductId.setText(pOutboundViewInfo.ProductId);
            txtQtyId.setText(pOutboundViewInfo.Qty);
            txtIdentStockId.setText(pOutboundViewInfo.IdentStock);
            cheRestrictedId.setChecked(pOutboundViewInfo.Restricted);
            txtLuId.setText(pOutboundViewInfo.Lu);
            txtLuQtyId.setText(pOutboundViewInfo.LuQty);
            txtBarCodeId.setText(pOutboundViewInfo.BarCode);
            lblOpenValueId.setText(pOutboundViewInfo.Open + " " +  pOutboundViewInfo.OpenUnit);
            chkConfirmedId.setChecked(pOutboundViewInfo.Confirmed);

        } catch (Exception e){

            String s = e.getMessage();
        }
    }



    private void getViewInfo(cOutboundViewInfo pOutboundViewInfo){

        try {

            pOutboundViewInfo.SourceId =  txtSourceId.getText().toString().trim();
            pOutboundViewInfo.Qty =  txtQtyId.getText().toString().trim();
            pOutboundViewInfo.Restricted =  cheRestrictedId.isChecked();
            pOutboundViewInfo.Lu =  txtLuId.getText().toString().trim();
            pOutboundViewInfo.LuQty =  txtLuQtyId.getText().toString().trim();
            pOutboundViewInfo.BarCode =  txtBarCodeId.getText().toString().trim();
            pOutboundViewInfo.Confirmed =  chkConfirmedId.isChecked();

        } catch (Exception e){

            String s = e.getMessage();
        }
    }


    private void fillDataUnits (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }



    public void   onClickConfirm(View spinner) {

        if (  txtQtyId.getText().toString().isEmpty() ){

            Toast.makeText(getApplicationContext(),"ACTUAL field is required", Toast.LENGTH_SHORT).show();

        } else{

            if(lsOutbounItems.size() > 0) {
                oOutboundViewInfo = lsOutbounItems.get(0);
                getViewInfo(oOutboundViewInfo);

                int  dif = 0;
                if (Integer.parseInt(oOutboundViewInfo.Open) < Integer.parseInt(oOutboundViewInfo.Qty)){
                    dif = 0;
                } else {

                    dif = Integer.parseInt(oOutboundViewInfo.Open) - Integer.parseInt(oOutboundViewInfo.Qty);
                }
                if(dif > 0){

                    Intent oIntent = new Intent(this, Remaining.class);
                    startActivity(oIntent);
                } else {

                    AsyncTaskAllItemConfirmed asyncTask=new AsyncTaskAllItemConfirmed();
                    asyncTask.execute("params");
                }
            }
        }
    }
    public void   onClickNext(View spinner) {
        Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
        Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();
    }

    private List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"ea"));
        InfoFilter.add(new cSpinnerItem(1,"pick"));

        return  InfoFilter;
    }


    private class AsyncTaskAllItemConfirmed extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PickSourceEmb.this);
            vProgressDialog.setMessage("All items were confirmed");
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

            Intent oIntent = new Intent(PickSourceEmb.this, ConfirmTask.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);
        }
    }
}

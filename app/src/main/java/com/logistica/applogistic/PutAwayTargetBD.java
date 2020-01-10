package com.logistica.applogistic;

import androidx.annotation.IntegerRes;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PutAwayTargetBD extends MainBaseActivity {

    private Spinner spinner;

    EditText txtTargetId;
    EditText txtProductId;
    TextView lblOpenValueId;
    EditText txtQtyId;
    CheckBox cheRestrictedId;
   // EditText txtLuId;
   // EditText txtLuQtyId;
    EditText txtBarCodeId;

    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    ArrayList<cInboundViewInfo>  lsInbounItems;
    cInboundViewInfo  oCurrentItemViewInfo;
    cGlobalData  oGlobalData;
    cProductViewInfo oCurrectProductViewInfo;

    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;

    int Iter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_away_target_bd);

        Init();
        StartActivity();
    }

    private void Init (){
        spinner = findViewById(R.id.spiUnitId);

        txtTargetId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        cheRestrictedId = findViewById(R.id.cheRestrictedId);
        //txtLuId = findViewById(R.id.txtLuId);
        // txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtBarCodeId = findViewById(R.id.txtBarCodeId);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        oGlobalData=  (cGlobalData)getApplication();
        lsInbounItems = ((cGlobalData)getApplication()).LsIntboudItems;
    }


    private void StartActivity (){

        if ( oMsg.getMessage().equals("AssignOtherArea") ){

            if ( lsInbounItems.size() > 0){
                Iter = Integer.valueOf(oMsg.getKey01());
                if ( Iter > 0 ){
                    oCurrentItemViewInfo = lsInbounItems.get(Iter-1);
                    setViewInfo(oCurrentItemViewInfo);
                }
            }
        }  else if (oMsg.getMessage().equals(Scanner.ScanType.SCAN_BAR_CODE)){

            oCurrentItemViewInfo =  oGlobalData.CurrentInboundViewInfo;
            Iter =  oGlobalData.iterater;

            oCurrentItemViewInfo.BarCode = oMsg.getKey01();
            setViewInfo(oCurrentItemViewInfo);

            oCurrectProductViewInfo = new cProductViewInfo();
            oCurrectProductViewInfo.ProductoSAPId = oCurrentItemViewInfo.ProductId;
            oCurrectProductViewInfo.CodigoBarra = oCurrentItemViewInfo.BarCode;

            AsyncTaskValidateProduct asyncTask=new AsyncTaskValidateProduct();
            asyncTask.execute("params");
        }

        fillDataUnits();
    }



    public void   onScanBarCode(View view) {

        getViewInfo(oCurrentItemViewInfo);
        oGlobalData.CurrentInboundViewInfo = oCurrentItemViewInfo;
        oGlobalData.iterater = Iter;


        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("PutAwayTargetBD",Scanner.ScanType.SCAN_BAR_CODE));
        startActivity(oIntent);

/*        AsyncTaskScan asyncTask=new AsyncTaskScan();
        asyncTask.execute("params");*/
    }

    private void setViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            txtTargetId.setText(pInboundViewInfo.TargetId);
            txtProductId.setText(pInboundViewInfo.ProductId);
            txtQtyId.setText(pInboundViewInfo.QtyDiff);
            lblOpenValueId.setText(pInboundViewInfo.Open + " " +  pInboundViewInfo.OpenUnit);
            txtBarCodeId.setText(pInboundViewInfo.BarCode);

        } catch (Exception e){

            String s = e.getMessage();
        }
    }


    private void getViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            pInboundViewInfo.TargetId = txtTargetId.getText().toString();
            pInboundViewInfo.BarCode = txtBarCodeId.getText().toString();
            pInboundViewInfo.QtyDiff = txtQtyId.getText().toString().trim();


/*            if(!txtQtyId.getText().toString().trim().isEmpty() || !oCurrentItemViewInfo.Qty.trim().isEmpty() ){

                int  QtyOther =   Integer.valueOf(txtQtyId.getText().toString().trim());
                int  Qty =   Integer.valueOf(oCurrentItemViewInfo.Qty.trim());
                int  RemaningQty = 0;

                if(QtyOther >  Qty){
                    RemaningQty = 0;
                }else{
                     RemaningQty =  Qty - QtyOther;
                }

                pInboundViewInfo.QtyDiff = String.valueOf(QtyOther);
                pInboundViewInfo.Qty = String.valueOf(RemaningQty);

            }*/

        } catch (Exception e){

            String s = e.getMessage();
        }
    }

    private void fillDataUnits (){
        ArrayAdapter<cSpinnerItem> adapter = new  ArrayAdapter<>(this,R.layout.spinner_item_filter,getInfoFilter());
        spinner.setAdapter(adapter);
    }


    public void   onClickConfirm(View spinner) {

            getViewInfo(oCurrentItemViewInfo);


        if(!txtQtyId.getText().toString().trim().isEmpty() || !oCurrentItemViewInfo.Qty.trim().isEmpty() ){

            int  QtyOther =   Integer.valueOf(txtQtyId.getText().toString().trim());
            int  Qty =   Integer.valueOf(oCurrentItemViewInfo.Qty.trim());
            int  RemaningQty = 0;

            if(QtyOther >  Qty){
                RemaningQty = 0;
            }else{
                RemaningQty =  Qty - QtyOther;
            }

            oCurrentItemViewInfo.QtyDiff = String.valueOf(QtyOther);
            oCurrentItemViewInfo.Qty = String.valueOf(RemaningQty);
        }


            Intent oIntent = new Intent(this, PickSourceBD.class);
            oIntent.putExtra("oMsg",new cActivityMessage("OtherTargetConfirmed",String.valueOf(Iter)));
            startActivity(oIntent);
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

        InfoFilter.add(new cSpinnerItem(1,"ZPZ"));
       // InfoFilter.add(new cSpinnerItem(1,"pick"));

        return  InfoFilter;
    }


    private class AsyncTaskValidateProduct extends AsyncTask<String, String,cProductResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PutAwayTargetBD.this);
            vProgressDialog.setMessage("Validando Producto...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected cProductResponse doInBackground(String... strings) {
            cProductResponse oResp = new  cProductResponse();

            try {

                cServices ocServices = new cServices();

                //oCurrectProductViewInfo.ProductoSAPId = "1";
                //    oCurrectProductViewInfo.Nombre = "productoPrueba3";
                //   oCurrectProductViewInfo.Descripcion = "productoPruebaDescripcion3";
                // oCurrectProductViewInfo.CodigoBarra = "12312312312";
                //      oCurrectProductViewInfo.Estado = "Activo";

                //oCurrectProductViewInfo.Usuario = "tcabrera";

                oResp = ocServices.PostProductAssignedDataService(oCurrectProductViewInfo);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return oResp;
        }

        @Override
        protected void onProgressUpdate(String... values){
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(cProductResponse lsData) {
            super.onPostExecute(lsData);

            if  (lsData != null){

                if(!lsData.ResponseId.equals("-1")){

                    if (lsData.Assigned){

                        Toast.makeText(getApplicationContext(),"Producto ASIGNADO" , Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(getApplicationContext(),"Producto NO ASIGNADO" , Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Error al intentar registrar el producto " , Toast.LENGTH_LONG).show();
                }
            }

            vProgressDialog.hide();
        }
    }


}

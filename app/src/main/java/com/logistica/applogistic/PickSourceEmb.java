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

    // Views
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

    // Data
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();
    ArrayList<cOutboundViewInfo>  lsOutbounItems;
    cOutboundViewInfo  oCurrentItemViewInfo;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;
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
        StartActivity();
    }


    private void Init (){

        countItems = 0;
        TotalItems = 0;
        consecutive = 0;
        Quantity = 1;
        iterater = 1;


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


        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        lsOutbounItems = ((cGlobalData)getApplication()).LsOutboudItems;
    }

    private  void StartActivity(){

        if ( oMsg.getMessage().equals("ConfirmedPartially") )
        {
            for ( cOutboundViewInfo e:lsOutbounItems){
                if (e.ProductId.equals(oMsg.getKey01())){

                    e.Confirmed = true;
                    e.ConfirmedPartially = true;
                    setViewInfo(e);
                    Toast.makeText(getApplicationContext(),"Product: " + e.ProductId + " Confirmed" , Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            Boolean  bfinishTask = true;
            for ( cOutboundViewInfo e:lsOutbounItems){
                if (!e.Confirmed){
                    bfinishTask = false;
                    break;
                }
            }

            if (  bfinishTask  ){
                AsyncTaskAllItemConfirmed asyncTask=new AsyncTaskAllItemConfirmed();
                asyncTask.execute("params");
            } else {

                for(int i = 0; i < lsOutbounItems.size();  i++ ){
                    oCurrentItemViewInfo = lsOutbounItems.get(i);
                    if(!oCurrentItemViewInfo.Confirmed){
                        setViewInfo(oCurrentItemViewInfo);
                        iterater = i + 1;
                        lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsOutbounItems.size()));
                        break;
                    }
                }
            }

        } else if (oMsg.getMessage().equals("StartTask") || oMsg.getMessage().equals("Reload") ){

            if ( lsOutbounItems.size() > 0){
                iterater = 1;
                oCurrentItemViewInfo = lsOutbounItems.get(iterater - 1);
                lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsOutbounItems.size()));
                setViewInfo(oCurrentItemViewInfo);
            }else{
                oCurrentItemViewInfo = new cOutboundViewInfo();
            }
        } else if (oMsg.getMessage().equals("NoConfirmedPartially")){

            if ( lsOutbounItems.size() > 0){
                iterater = Integer.valueOf(oMsg.getKey01());
                if ( iterater > 0 ){
                    oCurrentItemViewInfo = lsOutbounItems.get(iterater-1);
                    setViewInfo(oCurrentItemViewInfo);
                    lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsOutbounItems.size()));
                }
            }
        } else if ( oMsg.getMessage().equals(Scanner.ScanType.SCAN_PRODUCT_QTY)){


            oCurrentItemViewInfo =  ((cGlobalData)getApplication()).CurrentOutboundViewInfo;
          //  lsAreaInfoService =  ((cGlobalData)getApplication()).lsAreaInfoService;
          //  lsAreaInfoView =  ((cGlobalData)getApplication()).lsAreaInfoView;

            consecutive = ((cGlobalData)getApplication()).consecutive;
            Quantity = ((cGlobalData)getApplication()).Quantity;
            iterater = ((cGlobalData)getApplication()).iterater;
           // IterScan = ((cGlobalData)getApplication()).IterScan;

            lblCountItemsId.setText(((cGlobalData)getApplication()).lblCountItemsId);

            txtQtyId.setText("2");   // esta dato debe venir del Sscaneo del producto
           // txtIdentStockId.setText("30541");
            oCurrentItemViewInfo.Qty = txtQtyId.getText().toString();

            setViewInfo(oCurrentItemViewInfo);
        }

        fillDataUnits();
    }

    public void   onClickConfirm(View spinner) {

        if (  txtQtyId.getText().toString().isEmpty() ){

            Toast.makeText(getApplicationContext(),"ACTUAL field is required", Toast.LENGTH_SHORT).show();

        } else  if ( txtSourceId.getText().toString().isEmpty() ){

            Toast.makeText(getApplicationContext(),"SOURCE field is required", Toast.LENGTH_SHORT).show();

        }else{

            if (oCurrentItemViewInfo != null ){

                getViewInfo(oCurrentItemViewInfo);

                int  dif = 0;
                if (Integer.parseInt(oCurrentItemViewInfo.Open) < Integer.parseInt(oCurrentItemViewInfo.Qty)){
                    dif = 0;
                } else {

                    dif = Integer.parseInt(oCurrentItemViewInfo.Open) - Integer.parseInt(oCurrentItemViewInfo.Qty);
                }

                if(dif > 0){

                    Intent oIntent = new Intent(this, Remaining.class);
                    oIntent.putExtra("oMsg", new cActivityMessage("DeviationReason", String.valueOf(iterater)));
                    startActivity(oIntent);

                } else {
                    oCurrentItemViewInfo.Confirmed = true;
                    Toast.makeText(getApplicationContext(),"Product: " + oCurrentItemViewInfo.ProductId + " Confirmed" , Toast.LENGTH_SHORT).show();

                    Boolean  bfinishTask = true;
                    for ( cOutboundViewInfo e:lsOutbounItems){
                        if (!e.Confirmed){
                            bfinishTask = false;
                            break;
                        }
                    }

                    if (  bfinishTask  ){
                        AsyncTaskAllItemConfirmed asyncTask=new AsyncTaskAllItemConfirmed();
                        asyncTask.execute("params");
                    } else {

                        for(int i = 0; i < lsOutbounItems.size();  i++ ){
                            oCurrentItemViewInfo = lsOutbounItems.get(i);
                            if(!oCurrentItemViewInfo.Confirmed){
                                setViewInfo(oCurrentItemViewInfo);
                                iterater = i + 1;
                                lblCountItemsId.setText(String.valueOf(iterater) + " of " + String.valueOf(lsOutbounItems.size()));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void   onScanProduct(View view) {

        getViewInfo(oCurrentItemViewInfo);

        ((cGlobalData)getApplication()).CurrentOutboundViewInfo = oCurrentItemViewInfo;
//        ((cGlobalData)getApplication()).lsAreaInfoService = lsAreaInfoService;
//        ((cGlobalData)getApplication()).lsAreaInfoView = lsAreaInfoView;

        ((cGlobalData)getApplication()).consecutive = consecutive;
        ((cGlobalData)getApplication()).Quantity = Quantity;
        ((cGlobalData)getApplication()).iterater = iterater;
        //((cGlobalData)getApplication()).IterScan = IterScan;

        ((cGlobalData)getApplication()).lblCountItemsId = lblCountItemsId.getText().toString();


        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("PickSourceEmb",Scanner.ScanType.SCAN_PRODUCT_QTY));
        startActivity(oIntent);


//        AsyncTaskScanProduct asyncTask=new AsyncTaskScanProduct();
//        asyncTask.execute("params");

    }

    public void   onClickNext(View spinner) {

        if ( iterater < lsOutbounItems.size()){
            iterater = iterater+1;
            lblCountItemsId.setText(  iterater + " of " + lsOutbounItems.size());

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentItemViewInfo);

            // se obtiene el sigiente
            oCurrentItemViewInfo = lsOutbounItems.get(iterater - 1);
            setViewInfo(oCurrentItemViewInfo);
        }

        if ( lsOutbounItems.size() > 0 && iterater == lsOutbounItems.size()  ){

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentItemViewInfo);

            oCurrentItemViewInfo = lsOutbounItems.get(iterater-1);
            setViewInfo(oCurrentItemViewInfo);
        }

        //  Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {

        if ( iterater >  1){
            iterater = iterater-1;
            lblCountItemsId.setText(  iterater + " of " + lsOutbounItems.size());

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentItemViewInfo);

            oCurrentItemViewInfo = lsOutbounItems.get(iterater - 1);
            setViewInfo(oCurrentItemViewInfo);
        }

        if (  lsOutbounItems.size() == 1 ){

            // almacena  lo que hay en la vista
            getViewInfo(oCurrentItemViewInfo);

            oCurrentItemViewInfo = lsOutbounItems.get(0);
            setViewInfo(oCurrentItemViewInfo);
        }

        // Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        Intent oIntent = new Intent(this, OutBound.class);
        startActivity(oIntent);
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

    private List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"ea"));
        InfoFilter.add(new cSpinnerItem(1,"pick"));

        return  InfoFilter;
    }

    private class AsyncTaskScanProduct extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PickSourceEmb.this);
            vProgressDialog.setMessage("Scanning PRODUCT...");
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
            // se debe de validar que el producto escaneado sea igula con el  Currect Product
            txtQtyId.setText("2");   // esta dato debe venir del Sscaneo del producto
            txtIdentStockId.setText("30541");

            vProgressDialog.hide();
        }
    }

    private class AsyncTaskAllItemConfirmed extends AsyncTask<String, String,  String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(PickSourceEmb.this);
            vProgressDialog.setMessage("All items have already been confirmed");
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

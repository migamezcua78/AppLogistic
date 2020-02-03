package com.logistica.applogistic;

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


public class ShipmentConfirmation extends MainBaseActivity {


    // VIEWS
    private Spinner     spinner;
    private TableLayout tableLayout;
    private cDataGrid   oDataGrid;
    private EditText    txtImputFilterId;
    private TextView    lblTaskValueId;
    private TextView    lbOrderValueId;


    //  DATA
    private ArrayList <String[]> InfoData;
    private String[] InfoHeader;
    private List<cSpinnerItem>  InfoFilter;
    ArrayList<cInboundViewInfo>  lsInbounItems;

    public  String  OrderStatus;

    // Process
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;
    Boolean Scanned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_confirmation);

//        cGlobalData  oGlobalData=  (cGlobalData)getApplication();
//        oGlobalData.setGlobalVarValue("Hola");

        init();
        StartActivity();
    }


    private void init (){

        txtImputFilterId = findViewById(R.id.txtImputFilterId);
        lblTaskValueId = findViewById(R.id.lblTaskValueId);
        lbOrderValueId = findViewById(R.id.lbOrderValueId);

        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);
        OrderStatus = "";

        Scanned = false;

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");

        InfoData = new ArrayList <> ();
        InfoFilter = new ArrayList<>();
        lsInbounItems =  new  ArrayList<>();
    }


    private void StartActivity (){

        if( oMsg != null){
            if(oMsg.getMessage().equals(Scanner.ScanType.SCAN_TASK)){

                // txtImputFilterId.setText("");
                lblTaskValueId.setText("");
                lbOrderValueId.setText("");

                txtImputFilterId.setText(oMsg.getKey01());


                Scanned = true;

                // mig: se agrega con la validacion del estatus de la tarea
                AsyncTaskConfirm asyncTask=new AsyncTaskConfirm();
                asyncTask.execute("params");

                // mig: original sin la validacion del estatus de la tarea.
//                AsyncTaskExample asyncTask=new AsyncTaskExample();
//                asyncTask.execute("params");
            }
        }

        fillDataFilter();
        fillDataGrid();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void onScan(View view) {

        Intent oIntent = new Intent(this, Scanner.class);
        oIntent.putExtra("oMsg", new cActivityMessage("ShipmentConfirmation",Scanner.ScanType.SCAN_TASK));
        startActivity(oIntent);

/*        txtImputFilterId.setText("");
        AsyncTaskExample  asyncTask= new AsyncTaskExample();
        asyncTask.execute("params");*/

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

            if( lsInbounItems != null && lsInbounItems.size() > 0  &&  Scanned == true){

                for ( cInboundViewInfo e:lsInbounItems){
                    getParamInfo(e);
                }

                Intent oIntent = new Intent(this, PutAwayTarget.class);
                oIntent.putExtra("oMsg", getActivityMsg());
                startActivity(oIntent);

            }else {

                //  txtImputFilterId.setText("15");

                AsyncTaskConfirm asyncTaskConfirm=new AsyncTaskConfirm();
                asyncTaskConfirm.execute("params");

                //  Toast.makeText(getApplicationContext(),"There are NO items in task selected", Toast.LENGTH_SHORT).show();
            }

/*            Intent oIntent = new Intent(this, PutAwayTarget.class);
            cActivityMessage   oMssg = new  cActivityMessage();
            oIntent.putExtra("oMssg",oMssg );
            startActivity(oIntent);*/
        }



        // Intent oIntent = new Intent(this, PutAwayTarget.class);
        //oIntent.setExtrasClassLoader();
        //    startActivity(oIntent);


      /*
        //obtiene los datos de SQLite
        Cursor res= BD.getAllData();
        if (res.getCount()==0){

            showMessage("Datos","La consulta no devolvió información");
            return;

        }

        StringBuffer Buffer=new StringBuffer();
        while (res.moveToNext())
        {

            // Buffer.append("Id :" + getString(0)+"\n");
            //Buffer.append("Product :"+ getString(1)+"\n");
            //  Buffer.append("Planned_Quantity :"+ getString(2)+"\n");

            int cid =res.getInt(res.getColumnIndex(BD.Col1));
            String Product =res.getString(res.getColumnIndex(BD.Col2));
            String  Planned_Quantity =res.getString(res.getColumnIndex(BD.Col3));
            Buffer.append(cid+ "   " + Product + "   " + Planned_Quantity +" \n");

        }
        showMessage("Datos",Buffer.toString());
        //  Toast.makeText(getApplicationContext(),"Start Task", Toast.LENGTH_SHORT).show();*/

    }

    public void   onClickNext(View spinner) {
        // Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
        //Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();
        //finish();
    }

    private void  getParamInfo(cInboundViewInfo  pViewInfo) {

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

    private  List<cSpinnerItem> getInfoFilter(){


        InfoFilter = new ArrayList<>();
        InfoFilter.add(new cSpinnerItem(1,getString(R.string.TaskId), "TaskId"));
        // InfoFilter.add(new cSpinnerItem(2,getString(R.string.BarCodeId),"BarCodeId"));
        // InfoFilter.add(new cSpinnerItem(2,getString(R.string.LabelId),"LabelId"));
        // InfoFilter.add(new cSpinnerItem(3,getString(R.string.ReferenceId),"ReferenceId"));

        return  InfoFilter;
    }

    private  String[] getInfoHeader(){
        InfoHeader = new String[]{getString(R.string.Product),getString(R.string.PlannedQuantity)};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){
        return  InfoData;
    }

    private class AsyncTaskConfirm extends AsyncTask<String, String,  ArrayList<cPurchaseItem>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(ShipmentConfirmation.this);
            vProgressDialog.setMessage("Please wait...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected ArrayList<cPurchaseItem> doInBackground(String... strings) {
            ArrayList<cPurchaseItem> lsData = new ArrayList<>();
            ArrayList<cPurchaseOrder> lsDataOrder = new ArrayList<>();
            OrderStatus = "";
            try {

                cServices ocServices = new cServices();

               /* lsDataOrder =  ocServices.GetPurchaseOrderServiceData(cServices.PurchaseOrderFilterType.SelectionByID,  txtImputFilterId.getText().toString(), "1");

                for ( cPurchaseOrder  itemOrder :lsDataOrder) {
                     if (!itemOrder.ID.equals("")){
                       OrderStatus = itemOrder.TaskStatusId;
                       break;
                     }
                }
*/

                lsData = ocServices.GetPurchaseItemServiceData(cServices.PurchaseItemFilterType.ID, txtImputFilterId.getText().toString(), "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsData;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cPurchaseItem> lsData) {

            super.onPostExecute(lsData);
            lblTaskValueId.setText( txtImputFilterId.getText().toString().trim() + "  "  +  "Pick");
            //lbOrderValueId.setText("365");

            lsInbounItems = new  ArrayList<>();

            InfoData = new ArrayList <> ();
            cInboundViewInfo  oInboundViewInfo = new cInboundViewInfo();

            for (int i = 0; i < lsData.size(); i++) {
                cPurchaseItem oData = lsData.get(i);

                if( !oData.ID.trim().isEmpty()){

                    oInboundViewInfo = new cInboundViewInfo();
                    oInboundViewInfo.ProductId = oData.ID;
                    oInboundViewInfo.Open = oData.Quantity;
                    oInboundViewInfo.OpenUnit = oData.QuantityUnitCode;
                    oInboundViewInfo.PlanedQty =   String.valueOf(Math.round( Float.valueOf(oData.Quantity)));


                    String  sDescription= oData.Description.trim();

                    if( sDescription.length() > 30) {
                        oInboundViewInfo.ProductName = sDescription.substring(0,30) + " ..";
                    } else {
                        oInboundViewInfo.ProductName = sDescription;
                    }

                    lsInbounItems.add(oInboundViewInfo);
                    InfoData.add(new String[]{ oInboundViewInfo.ProductId, oInboundViewInfo.Open + "  " + oInboundViewInfo.OpenUnit });
                }
            }


            cGlobalData  oGlobalData=  (cGlobalData)getApplication();
            oGlobalData.LsIntboudIShipmentItems = lsInbounItems;

            oDataGrid.RemoveAllItems();
            fillDataGrid();

            if( lsInbounItems != null && lsInbounItems.size() > 0  ){

                for ( cInboundViewInfo e:lsInbounItems){
                    getParamInfo(e);
                }

/*                if (OrderStatus.equals("10")){

                    Toast.makeText(getApplicationContext(),"La orden ya ha sido confirmada", Toast.LENGTH_LONG).show();
                }else {

                    Intent oIntent = new Intent(Inbound.this, PutAwayTarget.class);
                    oIntent.putExtra("oMsg", getActivityMsg());
                    startActivity(oIntent);
                }*/

                Intent oIntent = new Intent(ShipmentConfirmation.this, ShipmentConfirmationDes.class);
                oIntent.putExtra("oMsg", getActivityMsg());
                startActivity(oIntent);

            }else {
                Toast.makeText(getApplicationContext(),"No hay elementos para la Tarea seleccionada", Toast.LENGTH_SHORT).show();
            }

            vProgressDialog.hide();
        }
    }
}

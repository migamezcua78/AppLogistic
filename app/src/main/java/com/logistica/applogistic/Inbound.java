package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Inbound extends AppCompatActivity {

    // VIEWS

    private TextView  txtFilter;
    private TextView  txtTaskId;
    private Spinner spinner;
    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList <String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    DatabaseHelper BD;

    InputStream is = null;
    ImageView imageView= null;
    ProgressDialog p;

    AlertDialog alert11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_inbound_bd);
        setContentView(R.layout.activity_inbound);

        //Se crea y se inserta en la tabla
        BD = new DatabaseHelper(this);

        intInbound();
        fillDataFilter();

        cGlobalData  oGlobalData=  (cGlobalData)getApplication();
        oGlobalData.setGlobalVarValue("Hola");

        AsyncTaskExample asyncTask=new AsyncTaskExample();
        asyncTask.execute("params");

//        String recuperamos_variable_string = getIntent().getStringExtra("variable_string");
//        String   s2  = recuperamos_variable_string;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

//    @Override
//    public void onBackPressed() {
//       // alert11.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG)
//      //  alert11.show();
//    }


    private void intInbound (){
        txtTaskId = findViewById(R.id.lbTaskId);
        txtFilter = findViewById(R.id.txtImputFilterId);
        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Inbound.this);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Inbound.super.onBackPressed();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        return;
                    }
                });

        alert11 = builder1.create();

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


    public void   onClickStartTask(View spinner) {

        Intent oIntent = new Intent(this, PutAwayTarget.class);
        //oIntent.setExtrasClassLoader();
        startActivity(oIntent);


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

    public void showMessage(String Title, String Message)
    {
        AlertDialog.Builder build =new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(Title);
        build.setMessage(Message);
        build.show();

    }


    public void   onClickNext(View spinner) {
        Toast.makeText(getApplicationContext(),"Next", Toast.LENGTH_SHORT).show();
    }

    public void   onClickPrevious(View spinner) {
        //Toast.makeText(getApplicationContext(),"Previous", Toast.LENGTH_SHORT).show();
        finish();
    }



    private  List<cSpinnerItem> getInfoFilter(){
        //  List<cSpinnerItem>  ItemsList = new ArrayList<>();
        InfoFilter = new ArrayList<>();

        InfoFilter.add(new cSpinnerItem(1,"Reference"));
        InfoFilter.add(new cSpinnerItem(1,"Label ID"));
        InfoFilter.add(new cSpinnerItem(1,"Task ID"));
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

/*


    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cStock>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cStock> doInBackground(String... strings) {
            ArrayList<cStock>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetStockServiceData(cServices.StockFilterType.CMATERIAL_UUID,"333","3");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsData;
        }

        @Override
        protected void onProgressUpdate(String... values){
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cStock> lsData) {
            super.onPostExecute(lsData);

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cStock  oData =lsData.get(i);
                InfoData.add(new String[]{oData.CMATERIAL_UUID, oData.CLOG_AREA_UUID});
            }

            fillDataGrid();
            p.hide();
        }
    }
*/


/*
    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cLogisticsArea>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cLogisticsArea> doInBackground(String... strings) {
            ArrayList<cLogisticsArea>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetLogisticAreaServiceData(cServices.LogisticAreaFilterType.SelectionByID,"*","3");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsData;
        }

        @Override
        protected void onProgressUpdate(String... values){
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cLogisticsArea> lsData) {
            super.onPostExecute(lsData);

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cLogisticsArea  oData  =lsData.get(i);
                InfoData.add(new String[]{oData.ID, oData.TypeCode});
            }

            fillDataGrid();
            p.hide();
        }
    }
    */

/*


    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cCustomer>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cCustomer> doInBackground(String... strings) {
            ArrayList<cCustomer>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetCustomersServ(cServices.CustomerFilterType.SelectionByInternalID,"*","5");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsData;
        }

        @Override
        protected void onProgressUpdate(String... values){
            //  Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cCustomer> lsData) {
            super.onPostExecute(lsData);

            for ( int i = 0; i <  lsData.size(); i ++  ){
                cCustomer  oCustomer  =lsData.get(i);
                InfoData.add(new String[]{oCustomer.InternalID, oCustomer.CategoryCode});
            }

            fillDataGrid();
            p.hide();
        }
    }

*/


    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cMaterial>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(Inbound.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cMaterial> doInBackground(String... strings) {
            ArrayList<cMaterial> lsData = new ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetMaterialsServ(cServices.MaterialFilterType.SelectionByInternalID, "*", "8");

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
        protected void onPostExecute(ArrayList<cMaterial> lsData) {
            super.onPostExecute(lsData);


            for (int i = 0; i < lsData.size(); i++) {
                cMaterial oMaterial = lsData.get(i);
                InfoData.add(new String[]{oMaterial.getProductCategoryID(), oMaterial.getQuantity() + " " + oMaterial.getQuantityUnitCode()});
            }

            fillDataGrid();
            AddData(lsData);
            p.hide();
        }
    }



    private void AddData(ArrayList<cMaterial> lsData) {
          /*  String Product="";
            String Planned_Quantity="";
            Product="MCFHITG-001";
            Planned_Quantity="5 ea";
*/
        for ( int i = 0; i <  lsData.size(); i ++  ){
            cMaterial  oMaterial  =lsData.get(i);
            //InfoData.add(new String[]{oMaterial.getProductCategoryID(), oMaterial.getQuantity() + " " +  oMaterial.getQuantityUnitCode() });


/*            boolean Inserted =BD.insertData(oMaterial.ProductCategoryID, oMaterial.Quantity);
            if (Inserted==true){
                Toast.makeText(Inbound.this,"Datos insertados correctamente...",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(Inbound.this,"Los datos no fueron Insertados...",Toast.LENGTH_LONG).show();
            }*/

        }
    }
}


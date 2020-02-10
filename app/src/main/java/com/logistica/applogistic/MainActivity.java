package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.net.Uri;

import android.provider.Settings;


public class MainActivity extends AppCompatActivity {

    // view
    EditText username;
    EditText password;


    // Proceso
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;

    cUserRequest  oUserReq;
    cUserResponse  oUserResp;

    public int CurrentCountClick;
    AlertDialog  Dialog;

    TextView  lblAppMode;




    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Mensaje");
        alertDialog.setMessage("Aplicacion No FUNCIONARA sin acceso a la camara");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //startInstalledAppDetailsActivity(MainActivity.this);

                    }
                });
        alertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult
            (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(MainActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }


    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (getFromPref(this, ALLOW_KEY)) {

                showSettingsAlert();

            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        } else {
            //openCamera();
        }
    }



  /*  @Override
    public boolean onCreateOptionsMenu(Menu manu) {
        getMenuInflater().inflate(R.menu.main,manu);
        return true;  // super.onCreateOptionsMenu(manu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        String s = "";

        Intent oIntent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(oIntent);

        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onBackPressed() {
        // no hacer nada
    }


    private void init() {

        username =  findViewById(R.id.username);
        password =  findViewById(R.id.password);
        lblAppMode =  findViewById(R.id.lblAppMode);

        oUserReq =  new cUserRequest();
        oUserResp =  new cUserResponse();

         CurrentCountClick = 1;
         if (((cGlobalData)getApplication()).AppMod != null ){

             if (((cGlobalData)getApplication()).AppMod.equals("DEV")){
                 lblAppMode.setVisibility(View.VISIBLE);
                 SetAppMode("DEV");

             } else {
                 SetAppMode("PROD");
                 lblAppMode.setVisibility(View.INVISIBLE);
             }
         }else {
             ((cGlobalData)getApplication()).AppMod = "";
             lblAppMode.setVisibility(View.INVISIBLE);
             SetAppMode("PROD");
         }

         BuildDialog ();
    }


    public void SetAppMode(String pAppMode){

        if (pAppMode.equals("DEV")){

            cGlobalData.AUTHORIZATION_REST  =  cGlobalData.AUTHORIZATION_REST_DEV;
            cGlobalData.AUTHORIZATION_SOAP  =  cGlobalData.AUTHORIZATION_SOAP_DEV;
            cGlobalData.NAME_SPACE_SOAP  =  cGlobalData.NAME_SPACE_SOAP_DEV;
            cGlobalData.NAME_SPACE_REST  =  cGlobalData.NAME_SPACE_REST_DEV;
            cGlobalData.NAME_RESOURCE_REST  =  cGlobalData.NAME_RESOURCE_REST_DEV;
            cGlobalData.END_POINT_PRODUCT_REST  =  cGlobalData.END_POINT_PRODUCT_REST_DEV;

            cGlobalData.PUT_CONFIRM_TASK  =  cGlobalData.PUT_CONFIRM_TASK_DEV;
            cGlobalData.PUT_MOVEMENT  =  cGlobalData.PUT_MOVEMENT_DEV;
            cGlobalData.PUT_INBOUND_DELIVERY  =  cGlobalData.PUT_INBOUND_DELIVERY_DEV;
            cGlobalData.GET_TASK  =  cGlobalData.GET_TASK_DEV;
            cGlobalData.GET_PURCHASE_ITEM  =  cGlobalData.GET_PURCHASE_ITEM_DEV;
            cGlobalData.GET_PURCHASE_ORDER  =  cGlobalData.GET_PURCHASE_ORDER_DEV;
            cGlobalData.GET_LOGISTIC_AREA  =  cGlobalData.GET_LOGISTIC_AREA_DEV;
            cGlobalData.GET_CUSTOMERS  =  cGlobalData.GET_CUSTOMERS_DEV;
            cGlobalData.GET_MATERIALS  =  cGlobalData.GET_MATERIALS_DEV;
            cGlobalData.GET_OUTBOUND_DELIVERY  =  cGlobalData.GET_OUTBOUND_DELIVERY_DEV;

        } else  if (pAppMode.equals("PROD")){

            cGlobalData.AUTHORIZATION_REST  =  cGlobalData.AUTHORIZATION_REST_PROD;
            cGlobalData.AUTHORIZATION_SOAP  =  cGlobalData.AUTHORIZATION_SOAP_PROD;
            cGlobalData.NAME_SPACE_SOAP  =  cGlobalData.NAME_SPACE_SOAP_PROD;
            cGlobalData.NAME_SPACE_REST  =  cGlobalData.NAME_SPACE_REST_PROD;
            cGlobalData.NAME_RESOURCE_REST  =  cGlobalData.NAME_RESOURCE_REST_PROD;
            cGlobalData.END_POINT_PRODUCT_REST  =  cGlobalData.END_POINT_PRODUCT_REST_PROD;

            cGlobalData.PUT_CONFIRM_TASK  =  cGlobalData.PUT_CONFIRM_TASK_PROD;
            cGlobalData.PUT_MOVEMENT  =  cGlobalData.PUT_MOVEMENT_PROD;
            cGlobalData.PUT_INBOUND_DELIVERY  =  cGlobalData.PUT_INBOUND_DELIVERY_PROD;
            cGlobalData.GET_TASK  =  cGlobalData.GET_TASK_PROD;
            cGlobalData.GET_PURCHASE_ITEM  =  cGlobalData.GET_PURCHASE_ITEM_PROD;
            cGlobalData.GET_PURCHASE_ORDER  =  cGlobalData.GET_PURCHASE_ORDER_PROD;
            cGlobalData.GET_LOGISTIC_AREA  =  cGlobalData.GET_LOGISTIC_AREA_PROD;
            cGlobalData.GET_CUSTOMERS  =  cGlobalData.GET_CUSTOMERS_PROD;
            cGlobalData.GET_MATERIALS  =  cGlobalData.GET_MATERIALS_PROD;
            cGlobalData.GET_OUTBOUND_DELIVERY  =  cGlobalData.GET_OUTBOUND_DELIVERY_RPOD;

        }
    }




    public void  onClickHome(View view){

       // finish();
      //  Intent oIntent = new Intent(MainActivity.this,MainActivity.class);
      //  startActivity(oIntent);
    }


    private void BuildDialog (){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if (((cGlobalData)getApplication()).AppMod.equals("DEV")){
                            ((cGlobalData)getApplication()).AppMod = "";
                            lblAppMode.setVisibility(View.INVISIBLE);
                            SetAppMode("PROD");
                        } else {

                            ((cGlobalData)getApplication()).AppMod = "DEV";
                            lblAppMode.setVisibility(View.VISIBLE);
                            SetAppMode("DEV");
                        }

//                        AsyncTaskFinishTask AsyncTask = new AsyncTaskFinishTask();
//                        AsyncTask.execute("params");
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

        Dialog = builder1.create();

    }


    public void  onClickImageView(View view){

        CurrentCountClick ++;
        String s = "";

        if (CurrentCountClick % 5 == 0){

            if (((cGlobalData)getApplication()).AppMod.equals("DEV")){
                Dialog.setMessage("¿Desea Salir del MODO DESARROLLO?.");
            } else {

                Dialog.setMessage("¿Desea usar la aplicación en MODO DESARROLLO?.");
            }

            Dialog.show();
        }


        // finish();
        //  Intent oIntent = new Intent(MainActivity.this,MainActivity.class);
        //  startActivity(oIntent);
    }


    public  void  goInicio (View view){
        // Intent oIntent = new Intent(this,Inbound.class);

        oUserReq.Aplicacion = "ApiLogistic";
        oUserReq.User = username.getText().toString().trim();
        oUserReq.Pwd = password.getText().toString().trim();

//        oUserReq.User = "Consultor";
//        oUserReq.Pwd = "Inicio01#";

        AsyncTaskLogin asyncTask=new AsyncTaskLogin();
        asyncTask.execute("params");

//        Intent oIntent = new Intent(this,Inicio.class);
//        startActivity(oIntent);
    }


    private class AsyncTaskLogin extends AsyncTask<String, String,cUserResponse> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vProgressDialog = new ProgressDialog(MainActivity.this);
            vProgressDialog.setMessage("Validando Acceso ...");
            vProgressDialog.setIndeterminate(false);
            vProgressDialog.setCancelable(true);
            vProgressDialog.show();
        }


        @Override
        protected cUserResponse  doInBackground(String... strings) {
            cUserResponse oResp = new  cUserResponse();

            try {

                cServices ocServices = new cServices();
                oResp = ocServices.PostLoginDataService(oUserReq);

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
        protected void onPostExecute(cUserResponse lsData) {
            super.onPostExecute(lsData);

            ((cGlobalData)getApplication()).CurrentUser =  oUserReq.User;

            // mig:  borra  este bloque solo es de prueba
          // Intent oIntent2 = new Intent(MainActivity.this,Inicio.class);
          // startActivity(oIntent2);
            // mig:   fin bloque


            if  (lsData != null){

                if(lsData.Error.isEmpty()){

                    if (lsData.Acceso){


                        ((cGlobalData)getApplication()).CurrentUser =  oUserReq.User;
                        Intent oIntent = new Intent(MainActivity.this,Inicio.class);
                        startActivity(oIntent);

                    } else {

                        Toast.makeText(getApplicationContext(),"El usuario no tiene prmisos de acceso a la aplicación", Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(),"Error al intentar registrar el producto " , Toast.LENGTH_LONG).show();
                    // setViewInfo(oCurrectProductViewInfo);
                }
            }

            vProgressDialog.hide();
        }
    }
}

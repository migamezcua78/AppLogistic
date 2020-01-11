package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // view
    EditText username;
    EditText password;


    // Proceso
    ProgressDialog vProgressDialog;
    cActivityMessage oMsg;

    cUserRequest  oUserReq;
    cUserResponse  oUserResp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
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

        oUserReq =  new cUserRequest();
        oUserResp =  new cUserResponse();
    }




    public void  onClickHome(View view){

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

            // mig:  borra  este bloque solo es de prueba
          //  Intent oIntent2 = new Intent(MainActivity.this,Inicio.class);
           // startActivity(oIntent2);
            // mig:   fin bloque


            if  (lsData != null){

                if(lsData.Error.isEmpty()){

                    if (lsData.Acceso){

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

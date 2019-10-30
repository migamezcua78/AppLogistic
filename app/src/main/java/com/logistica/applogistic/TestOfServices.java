package com.logistica.applogistic;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TestOfServices extends AppCompatActivity {

    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView= null;
    ProgressDialog p;

    TextView Msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_of_services);

        Msg = findViewById(R.id.lblProgressId);
        Button button=findViewById(R.id.asyncTask);
        imageView=findViewById(R.id.image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("https://www.tutorialspoint.com/images/tp-logo-diamond.png");
            }
        });
    }


    private class AsyncTaskExample extends AsyncTask<String, String,  ArrayList<cMaterial>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(TestOfServices.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }


        @Override
        protected ArrayList<cMaterial> doInBackground(String... strings) {
            ArrayList<cMaterial>  lsData = new  ArrayList<>();

            try {

                cServices ocServices = new cServices();
                lsData = ocServices.GetMaterialsServ(cServices.MaterialFilterType.SelectionByInternalID,"*","8");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsData;
        }

        @Override
        protected void onProgressUpdate(String... values){
            Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(ArrayList<cMaterial> lsData) {
            super.onPostExecute(lsData);
            if(imageView!=null) {
                p.hide();
               // Msg.setText(lsData.get(0));
                //imageView.setImageBitmap(lsData);
            }else {
                p.show();
            }
        }
    }



/*
    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(TestOfServices.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(true);
            p.show();
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                //publishProgress("Init");

               // cServices ocServices = new cServices();

                //   ocServices.GetServiceIfo();

                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmImg;
        }

        @Override
        protected void onProgressUpdate(String... values){
            Msg.setText(values[0]);
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }
        }
    }*/
}

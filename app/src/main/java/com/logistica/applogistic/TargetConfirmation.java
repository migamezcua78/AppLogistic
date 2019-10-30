package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TargetConfirmation extends AppCompatActivity {

    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    //  DATA
    private ArrayList<String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_confirmation);
        Init();
        fillDataGrid();
    }

    private void Init (){
        tableLayout = findViewById(R.id.tgProductos);
    }

    public void   onClickConfirm(View spinner) {
        Toast.makeText(getApplicationContext(),"Task 131 Confirmed", Toast.LENGTH_LONG).show();
        Intent oIntent = new Intent(this, Inbound.class);
        oIntent.putExtra("param_reset","1");
        startActivity(oIntent);
    }

    private void fillDataGrid (){
        oDataGrid = new cDataGrid(tableLayout,getApplicationContext());
        oDataGrid.addHeader(getInfoHeader());
        oDataGrid.addData(getInfoData());
    }


    private  String[] getInfoHeader(){
        InfoHeader = new String[]{"Product","Actual Qty"};
        return InfoHeader;
    }

    private ArrayList<String[]> getInfoData(){

        InfoData = new ArrayList <> ();

        InfoData.add(new String[]{"MCF-0001","5 ea"});
       /* InfoData.add(new String[]{"MCF-0002","6 ea"});
        InfoData.add(new String[]{"MCF-0003","7 ea"});
        InfoData.add(new String[]{"MCF-0004","8 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","12 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","12 ea"});*/


        return  InfoData;
    }
}

package com.logistica.applogistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InboundBD extends AppCompatActivity {

    // VIEWS

    private TextView  txtFilter;
    private TextView  txtTaskId;
    private Spinner spinner;
    private TableLayout tableLayout;
    private  cDataGrid  oDataGrid;

    private CheckBox cheDistinctLogAreas;

    //  DATA
    private ArrayList <String[]> InfoData = new ArrayList <> ();
    private String[] InfoHeader;
    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound_bd);
        Init();
        fillDataFilter();
        fillDataGrid();

        String recuperamos_variable_string = getIntent().getStringExtra("variable_string");
        String   s2  = recuperamos_variable_string;

    }

    private void Init (){
        txtTaskId = findViewById(R.id.lbTaskId);
        txtFilter = findViewById(R.id.txtImputFilterId);
        spinner = findViewById(R.id.spFilterId);
        tableLayout =(TableLayout)findViewById(R.id.tgProductos);
       // cheDistinctLogAreas = findViewById(R.id.cheDistinctLogAreaId);
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

/*        if ( cheDistinctLogAreas.isChecked())
        {
            // do something
        }else  {*/

            Intent oIntent = new Intent(this, PickSourceBD.class);
            //oIntent.setExtrasClassLoader();
            startActivity(oIntent);
     //   }


        //  Toast.makeText(getApplicationContext(),"Start Task", Toast.LENGTH_SHORT).show();
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

        InfoData = new ArrayList <> ();

        InfoData.add(new String[]{"MCF-0001","5 ea"});
        // InfoData.add(new String[]{"MCF-0002","6 ea"});
/*

        InfoData.add(new String[]{"MCF-0003","7 ea"});
        InfoData.add(new String[]{"MCF-0004","8 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","12 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","12 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","12 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","40 ea"});

        InfoData.add(new String[]{"MCF-0001","9 ea"});
        InfoData.add(new String[]{"MCF-0002","10 ea"});
        InfoData.add(new String[]{"MCF-0003","11 ea"});
        InfoData.add(new String[]{"MCF-0004","80 ea"});
*/

        return  InfoData;
    }
}
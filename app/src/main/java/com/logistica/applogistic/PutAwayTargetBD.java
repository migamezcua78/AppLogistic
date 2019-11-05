package com.logistica.applogistic;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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


public class PutAwayTargetBD extends AppCompatActivity {

    private Spinner spinner;

    EditText txtTargetId;
    EditText txtProductId;
    TextView lblOpenValueId;
    EditText txtQtyId;
    CheckBox cheRestrictedId;
    EditText txtLuId;
    EditText txtLuQtyId;
    EditText txtBarCodeId;

    private List<cSpinnerItem>  InfoFilter = new ArrayList<>();

    ArrayList<cInboundViewInfo>  lsInbounItems;
    cInboundViewInfo  oCurrentItemViewInfo;

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


    private void StartActivity (){

        if ( oMsg.getMessage().equals("AssignOtherArea") ){

            if ( lsInbounItems.size() > 0){
                Iter = Integer.valueOf(oMsg.getKey01());
                if ( Iter > 0 ){
                    oCurrentItemViewInfo = lsInbounItems.get(Iter-1);
                    setViewInfo(oCurrentItemViewInfo);
                }
            }
        }

        fillDataUnits();
    }


    private void Init (){
        spinner = findViewById(R.id.spiUnitId);

        txtTargetId = findViewById(R.id.txtTargetId);
        txtProductId = findViewById(R.id.txtProductId);
        lblOpenValueId = findViewById(R.id.lblOpenValueId);
        txtQtyId = findViewById(R.id.txtQtyId);
        cheRestrictedId = findViewById(R.id.cheRestrictedId);
        txtLuId = findViewById(R.id.txtLuId);
        txtLuQtyId = findViewById(R.id.txtLuQtyId);
        txtBarCodeId = findViewById(R.id.txtBarCodeId);

        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        lsInbounItems = ((cGlobalData)getApplication()).LsIntboudItems;
    }


    private void setViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            txtTargetId.setText(pInboundViewInfo.TargetId);
            txtProductId.setText(pInboundViewInfo.ProductId);
            txtQtyId.setText(pInboundViewInfo.QtyDiff);
            lblOpenValueId.setText(pInboundViewInfo.Open + " " +  pInboundViewInfo.OpenUnit);

        } catch (Exception e){

            String s = e.getMessage();
        }
    }


    private void getViewInfo(cInboundViewInfo pInboundViewInfo){

        try {

            pInboundViewInfo.TargetId = txtTargetId.getText().toString();

            if(!txtQtyId.getText().toString().trim().isEmpty() || !oCurrentItemViewInfo.Qty.trim().isEmpty()  ){

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
            }

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

        InfoFilter.add(new cSpinnerItem(1,"ea"));
        InfoFilter.add(new cSpinnerItem(1,"pick"));

        return  InfoFilter;
    }

}

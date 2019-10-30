package com.logistica.applogistic;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class TableDynamic {

    private TableLayout  tableLayout;
    private Context  context;
    private String[]  header;
    private ArrayList<String[]>  data;
    private TableRow   tableRow;
    private TextView txtCell;
    private int indexC;
    private int indexR;

    public TableDynamic(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    public void addHeader(String[]  header){
        this.header = header;
        createHeader();
    }

    public void addData(ArrayList<String[]>  data){
        this.data = data;
        createDataTable();
    }

    public void newRow(){
        this.tableRow = new TableRow(context);
    }

    public void newCell(){
        txtCell = new TextView(context);
        txtCell.setGravity(Gravity.LEFT);
        txtCell.setTextColor(Color.BLACK);
        txtCell.setTextSize(14);
    }

    public void createHeader(){
        indexC  = 0;
        newRow();
        while (indexC < header.length){
            newCell();
            txtCell.setText(header[indexC++]);
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    public  void addItem(String[] item ) {
        String info;
        data.add(item);
        indexC = 0;
        newRow();
        while (indexC < header.length ){
            newCell();
            info = (indexC < item.length)? item[indexC++]: "";
            txtCell.setText(info);
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow,data.size()-1);
    }


    public void createDataTable(){
        String info;
        for (indexR = 1; indexR <=data.size(); indexR++){
            newRow();
            for (indexC = 0; indexC < header.length; indexC++){
                newCell();
                String[] row = data.get(indexR-1);
                info = (indexC < row.length)? row[indexC]: "";
                txtCell.setText(info);
                tableRow.addView(txtCell,newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
    }


    private TableRow.LayoutParams  newTableRowParams(){

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight = 1;
        return  params;
    }


    public  void backgroundHeader(int color){
        indexC  = 0;
        while (indexC < header.length){
            txtCell = getCell(0,indexC++);
            txtCell.setBackgroundColor(Color.parseColor("#7C8BC7EE"));
            txtCell.setBackgroundResource(R.drawable.textview_border);

           // txtCell.setBackground(ContextCompat.getDrawable(context, R.drawable.textview_border));

        }
       // tableLayout.addView(tableRow);
    }

    private TableRow getRow(int index){
        return  (TableRow)tableLayout.getChildAt(index);
    }

    private TextView getCell(int rowIndex, int columnIndex){
        tableRow = getRow(rowIndex);
        return  (TextView)tableRow.getChildAt(columnIndex);
    }
}

package com.logistica.applogistic;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class cDataGrid {

    private TableLayout  tableLayout;
    private Context  context;
    public String[]  header;
    private ArrayList<String[]>  data;
    public TableRow   tableRow;
    private TextView txtCell;
    private int indexC;
    private int indexR;

    public cDataGrid(TableLayout tableLayout, Context context) {
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

    private void newRow(){
        this.tableRow = new TableRow(context);
    }

    private void newCell(){
        txtCell = new TextView(context);
        //txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextColor(Color.BLACK);
        txtCell.setTextSize(12);
        txtCell.setPadding(10,0,0,0);
        txtCell.setBackgroundResource(R.drawable.shape_cell_data);
    }


    private void newCellHeader(){
        txtCell = new TextView(context);
        //txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextColor(Color.BLACK);
        txtCell.setTextSize(14);
        txtCell.setPadding(10,0,0,0);
        txtCell.setBackgroundResource(R.drawable.shape_cell_header);
    }

    private void createHeader(){
        indexC  = 0;
        newRow();
        while (indexC < header.length){
            newCellHeader();
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


    private void createDataTable(){
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
        //params.setMargins(1,1,1,1);
        params.weight = 1;
        return  params;
    }


    private TableRow getRow(int index){
        return  (TableRow)tableLayout.getChildAt(index);
    }

    private TextView getCell(int rowIndex, int columnIndex){
        tableRow = getRow(rowIndex);
        return  (TextView)tableRow.getChildAt(columnIndex);
    }

    public  void  RemoveAllItems (){
        tableLayout.removeAllViews();
    }
}

package com.logistica.applogistic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    //public static final String Database_Name="AppLogisticaBD.db";
    //public static final String Table_Name="Inbound";
    //public static final String Col1="Id";
    // public static final String Col2="Product";
    //public static final String Col3="Planned_Quantity";

    private static final String DATABASE_NAME = "AppLogisticaBD2.db";    // Database Name
    private static final String TABLE_NAME = "Inbound";   // Table Name
    private static final int DATABASE_Version = 1;    // Database Version
    public static String Col1="Id";     // Column I (Primary Key)
    public static String  Col2 = "Product";    //Column II
    public static String Col3= "Planned_Quantity";    // Column III
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
            " ("+Col1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Col2+" VARCHAR(255) ,"+ Col3+" VARCHAR(225));";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;


    public DatabaseHelper(@Nullable Context context) {
        //super(context,DATABASE_NAME,null,DATABASE_Version);
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;

       // SQLiteDatabase db  = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL("Create Table " + Table_Name +"(Id Integer Primary Key AutoIncrement, Product Text, Planned_Quantity Tex )");
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
           // Message.message(context,""+e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        //onCreate(db);

        try {
           // Message.message(context,"OnUpgrade");
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
           // Message.message(context,""+e);
        }
    }
    public boolean insertData(String Product, String Planned_Quantity)
    {
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, Product);
        contentValues.put(Col3, Planned_Quantity);
        long result = db.insert(TABLE_NAME, null , contentValues);
        if (result== -1){
            return false;
        }
        else {
            return true;
        }
    }

   public Cursor getAllData(){

        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Id,Product,Planned_Quantity FROM "+ TABLE_NAME,null);
        return res;


    }
  /*  public  void dropTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DROP_TABLE);

    }
*/

}

package com.logistica.applogistic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.DialogFragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.MenuItemCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.content.ContentValues;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.logistica.zbar.BarcodeFormat;
import com.logistica.zbar.Result;
import com.logistica.zbar.ZBarScannerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;





public class Scanner extends BaseScannerActivity implements MessageDialogFragment.MessageDialogListener,
        ZBarScannerView.ResultHandler, FormatSelectorDialogFragment.FormatSelectorDialogListener,
        CameraSelectorDialogFragment.CameraSelectorDialogListener {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZBarScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = -1;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    // Mike
    private Class<?> mClassSource;
    cActivityMessage oMsg;



    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }

        setContentView(R.layout.activity_scanner);
        // setupToolbar();

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZBarScannerView(this);
        setupFormats();
        contentFrame.addView(mScannerView);

        init();
    }

    private void init() {
        oMsg = (cActivityMessage)(getIntent()).getSerializableExtra("oMsg");
        setRequestClass(oMsg.getMessage());
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem;


        if(mFlash) {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_flash, 0, R.string.flash_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);


        if(mAutoFocus) {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on);
        } else {
            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off);
        }
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);

        menuItem = menu.add(Menu.NONE, R.id.menu_formats, 0, R.string.formats);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);

        menuItem = menu.add(Menu.NONE, R.id.menu_camera_selector, 0, R.string.select_camera);
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_flash:
                mFlash = !mFlash;
                if(mFlash) {
                    item.setTitle(R.string.flash_on);
                } else {
                    item.setTitle(R.string.flash_off);
                }
                mScannerView.setFlash(mFlash);
                return true;
            case R.id.menu_auto_focus:
                mAutoFocus = !mAutoFocus;
                if(mAutoFocus) {
                    item.setTitle(R.string.auto_focus_on);
                } else {
                    item.setTitle(R.string.auto_focus_off);
                }
                mScannerView.setAutoFocus(mAutoFocus);
                return true;
            case R.id.menu_formats:
                DialogFragment fragment = FormatSelectorDialogFragment.newInstance(this, mSelectedIndices);
                fragment.show(getSupportFragmentManager(), "format_selector");
                return true;
            case R.id.menu_camera_selector:
                mScannerView.stopCamera();
                DialogFragment cFragment = CameraSelectorDialogFragment.newInstance(this, mCameraId);
                cFragment.show(getSupportFragmentManager(), "camera_selector");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
        showMessageDialog("Contents = " + rawResult.getContents() + ", Format = " + rawResult.getBarcodeFormat().getName(),rawResult.getContents());
    }

    public void showMessageDialog(String message,String Valor) {
        DialogFragment fragment = MessageDialogFragment.newInstance("Codigo Escaneado", message, this);
        fragment.show(getSupportFragmentManager(), "scan_results");

        // se agrega codigo para guardar codigo escaneado
        SQLiteDatabase myDB =
                openOrCreateDatabase("dbcodigobarras.db", MODE_PRIVATE, null);

        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS codigo (codigo VARCHAR(200), nombre VARCHAR(200), total INT, fechaInseta DATETIME)"
        );



        Cursor c = myDB.rawQuery("SELECT codigo, nombre, total FROM codigo where codigo='"+Valor+"' order by fechaInseta limit 1", null);

        String codigo = "";
        String nombre = "";
        int total = 0;
        if (c != null) {
            if(c.getCount() > 0) {
                c.moveToFirst();
                do {
                    //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                    codigo = c.getString(c.getColumnIndex("codigo"));
                    nombre = c.getString(c.getColumnIndex("nombre"));
                    total = c.getInt(c.getColumnIndex("total"));
                } while (c.moveToNext());

                myDB.execSQL("DELETE FROM codigo WHERE codigo='"+Valor+"'");


                ContentValues row1 = new ContentValues();
                row1.put("codigo", Valor);
                row1.put("nombre", nombre);
                row1.put("total", String.valueOf(total));
                row1.put("fechaInseta",getNow());
                myDB.insert("codigo", null, row1);

            }
            else
            {
                ContentValues row1 = new ContentValues();
                row1.put("codigo", Valor);
                row1.put("nombre", "");
                row1.put("total", 0);
                row1.put("fechaInseta",getNow());
                myDB.insert("codigo", null, row1);
            }
        }


        myDB.close();

        if ( oMsg != null ){

            if (oMsg.getKey01().equals(ScanType.SCAN_SOURCE)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_SOURCE, Valor);
            }

            if (oMsg.getKey01().equals(ScanType.SCAN_PRODUCT)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_PRODUCT, Valor);
            }

            if (oMsg.getKey01().equals(ScanType.SCAN_TARGET)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_TARGET, Valor);
            }

            if (oMsg.getKey01().equals(ScanType.SCAN_TASK)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_TASK, Valor);
            }

            if (oMsg.getKey01().equals(ScanType.SCAN_QTY)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_QTY, Valor);
            }

            if (oMsg.getKey01().equals(ScanType.SCAN_PRODUCT_QTY)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_PRODUCT_QTY, Valor);
            }

            if (oMsg.getKey01().equals(ScanType.SCAN_SERIAL_NUMBER)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_SERIAL_NUMBER, Valor);
            }
            if (oMsg.getKey01().equals(ScanType.SCAN_BAR_CODE)){

                oMsg =  new  cActivityMessage(Scanner.ScanType.SCAN_BAR_CODE, Valor);
            }
        }

        Intent intent = new Intent(this,this.mClassSource);
        intent.putExtra("oMsg", oMsg);
        startActivity(intent);

//        Intent intent = new Intent(this, MainScanner.class);
//        intent.putExtra("oMsg", new cActivityMessage(""));
//        startActivity(intent);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }


    private String getNow(){
        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Resume the camera
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onFormatsSaved(ArrayList<Integer> selectedIndices) {
        mSelectedIndices = selectedIndices;
        setupFormats();
    }

    @Override
    public void onCameraSelected(int cameraId) {
        mCameraId = cameraId;
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }


    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < BarcodeFormat.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(BarcodeFormat.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }


    private void setRequestClass (String pNameClassSorce ){

        switch (pNameClassSorce)
        {
            case "Goods_Movement_Source" :
                this.mClassSource = Goods_Movement_Source.class;
                break;
            case "GoodsMovementTarget" :
                this.mClassSource = GoodsMovementTarget.class;
                break;
            case "GoodMovement" :
                this.mClassSource = GoodMovement.class;
                break;
            case "LogisticAreaCount" :
                this.mClassSource = LogisticAreaCount.class;
                break;
            case "LogisticAreaCountDes" :
                this.mClassSource = LogisticAreaCountDes.class;
                break;

            case "OutBound" :
                this.mClassSource = OutBound.class;
                break;

            case "PickSourceEmb" :
                this.mClassSource = PickSourceEmb.class;
                break;

            case "Inbound" :
                this.mClassSource = Inbound.class;
                break;

            case "PutAwayTarget" :
                this.mClassSource = PutAwayTarget.class;
                break;

            case "PickSource" :
                this.mClassSource = PickSource.class;
                break;

            case "InboundBD" :
                this.mClassSource = InboundBD.class;
                break;

            case "PickSourceBD" :
                this.mClassSource = PickSourceBD.class;
                break;

            case "MappingProducts" :
                this.mClassSource = MappingProducts.class;
                break;

            case "PutAwayTargetBD" :
                this.mClassSource = PutAwayTargetBD.class;
                break;


            case "RegisterCodeBarProducts" :
                this.mClassSource = RegisterCodeBarProducts.class;
                break;

            case "RegisterProducts" :
                this.mClassSource = RegisterProducts.class;
                break;

            case "ShipmentConfirmation" :
                this.mClassSource = ShipmentConfirmation.class;
                break;

            case "ShipmentConfirmationDes" :
                this.mClassSource = ShipmentConfirmationDes.class;
                break;
        }
    }


    public static class ScanType {

        public static final String  SCAN_PRODUCT = "SCAN_PRODUCT";
        public static final String  SCAN_SOURCE = "SCAN_SOURCE";
        public static final String  SCAN_TARGET = "SCAN_TARGET";
        public static final String  SCAN_TASK = "SCAN_TASK";
        public static final String  SCAN_QTY = "SCAN_QTY";
        public static final String  SCAN_PRODUCT_QTY = "SCAN_PRODUCT_QTY";
        public static final String  SCAN_SERIAL_NUMBER = "SCAN_SERIAL_NUMBER";
        public static final String  SCAN_BAR_CODE = "SCAN_BAR_CODE";

    }
}



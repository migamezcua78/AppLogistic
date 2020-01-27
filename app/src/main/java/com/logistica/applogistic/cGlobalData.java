package com.logistica.applogistic;

import android.app.Application;

import java.util.ArrayList;

public class cGlobalData extends Application {

    private String mGlobalVarValue;

    public int consecutive;

    public int Quantity;

    public int iterater;

    public int IterScan;

    public String lblCountItemsId;

    public String CurrentArea;

    public String CurrentTaskId;

    public String ReferenceId;


    public cAreaInfoView  CurrenAreaInfoView;

    public cOutboundViewInfo  CurrentOutboundViewInfo;

    public cInboundViewInfo  CurrentInboundViewInfo;


    public cProductViewInfo  CurrentProductViewInfo;
    public   ArrayList<cProductViewInfo>   lsProductViewInfo;
    public   ArrayList<cProductViewInfo>    lsProductViewInfoFilter;


    public  ArrayList<cAreaInfoView> lsAreaInfoService;

    public  ArrayList<cAreaInfoView> lsAreaInfoView;

    public ArrayList<cOutboundViewInfo> LsOutboudItems;

    public ArrayList<cInboundViewInfo> LsIntboudItems;

    public cMovementViewInfo  CurrentMovementViewInfo;

    public cTaskResponse CurrentTaskResponse;


    public cSpinnerItem CurrentSelectedSpinnerItem;


    public ArrayList<String> LsCatalogLogisticAreas;
    public ArrayList<String> LsCatalogLogisticAreasS;  //  tiene el site concatenado con la area logistica


    public String  CurrentUser;

    public String  AppMod = "";


    public int CountClick;



    //  DATA
    public static final String   AUTHORIZATION_REST_DEV  = "Basic REVWOkluaWNpbzAy";
    public static final String   AUTHORIZATION_REST_PROD  = "Basic REVWOkluaWNpbzAy";
    public static  String AUTHORIZATION_REST  = "Basic REVWOkluaWNpbzAy";

    public static final String  AUTHORIZATION_SOAP_DEV = "Basic X0FQUElOVEVHUkFUOkluaWNpbzAx";
    public static final String  AUTHORIZATION_SOAP_PROD = "Basic X0FQUElOVEVHUkFUOkluaWNpbzAx";
    public static  String   AUTHORIZATION_SOAP = "Basic X0FQUElOVEVHUkFUOkluaWNpbzAx";


    public static final String   NAME_SPACE_SOAP_DEV  = "http://sap.com/xi/SAPGlobal20/Global";
    public static final String  NAME_SPACE_SOAP_PROD  = "http://sap.com/xi/SAPGlobal20/Global";
    public static  String  NAME_SPACE_SOAP  = "http://sap.com/xi/SAPGlobal20/Global";


    public static final String   NAME_SPACE_REST_DEV  =  "https://my346674.sapbydesign.com";
    public static final String   NAME_SPACE_REST_PROD  = "https://my343751.sapbydesign.com";
    public static String   NAME_SPACE_REST  = "https://my343751.sapbydesign.com";


    public static final String   NAME_RESOURCE_REST_DEV  = "/sap/byd/odata/ana_businessanalytics_analytics.svc/RPSCMINVV02_Q0001QueryResults";
    public static final String   NAME_RESOURCE_REST_PROD  = "/sap/byd/odata/ana_businessanalytics_analytics.svc/RPSCMINVV02_Q0001QueryResults";
    public static  String   NAME_RESOURCE_REST  = "/sap/byd/odata/ana_businessanalytics_analytics.svc/RPSCMINVV02_Q0001QueryResults";


    public static final String   END_POINT_PRODUCT_REST_DEV  =  "http://3.92.221.195";
    public static final String   END_POINT_PRODUCT_REST_PROD  =  "http://3.92.221.195";
    public static  String   END_POINT_PRODUCT_REST  =  "http://3.92.221.195";




    //  END POINTS
    public static final String   PUT_CONFIRM_TASK_DEV  = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/managesitelogisticstaskin?sap-vhost=my346674.sapbydesign.com";
    public static final String   PUT_CONFIRM_TASK_PROD  =  "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/managesitelogisticstaskin?sap-vhost=my343751.sapbydesign.com";
    public static  String   PUT_CONFIRM_TASK =  "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/managesitelogisticstaskin?sap-vhost=my343751.sapbydesign.com";


    public static final String    PUT_MOVEMENT_DEV  = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/inventoryprocessinggoodsandac2?sap-vhost=my346674.sapbydesign.com";
    public static final String   PUT_MOVEMENT_PROD  =  "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/inventoryprocessinggoodsandac2?sap-vhost=my343751.sapbydesign.com";
    public static  String   PUT_MOVEMENT =  "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/inventoryprocessinggoodsandac2?sap-vhost=my343751.sapbydesign.com";


    public static final String    PUT_INBOUND_DELIVERY_DEV  = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_z_inbounddelivery?sap-vhost=my346674.sapbydesign.com" ;
    public static final String   PUT_INBOUND_DELIVERY_PROD  = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_z_inbounddelivery?sap-vhost=my343751.sapbydesign.com" ;
    public static  String   PUT_INBOUND_DELIVERY = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_z_inbounddelivery?sap-vhost=my343751.sapbydesign.com";


    public static final String    GET_TASK_DEV  = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/querysitelogisticstaskin?sap-vhost=my346674.sapbydesign.com";
    public static final String   GET_TASK_PROD = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querysitelogisticstaskin?sap-vhost=my343751.sapbydesign.com";
    public static  String   GET_TASK = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querysitelogisticstaskin?sap-vhost=my343751.sapbydesign.com" ;


    public static final String     GET_PURCHASE_ITEM_DEV  = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my346674.sapbydesign.com" ;
    public static final String   GET_PURCHASE_ITEM_PROD = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my343751.sapbydesign.com";
    public static  String   GET_PURCHASE_ITEM = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my343751.sapbydesign.com";


    public static final String     GET_PURCHASE_ORDER_DEV  =  "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my346674.sapbydesign.com";
    public static final String   GET_PURCHASE_ORDER_PROD = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my343751.sapbydesign.com" ;
    public static  String   GET_PURCHASE_ORDER = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my343751.sapbydesign.com";


    public static final String     GET_LOGISTIC_AREA_DEV  =  "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_logisticarea?sap-vhost=my346674.sapbydesign.com";
    public static final String   GET_LOGISTIC_AREA_PROD = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_logisticarea?sap-vhost=my343751.sapbydesign.com";
    public static  String   GET_LOGISTIC_AREA = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_logisticarea?sap-vhost=my343751.sapbydesign.com";


    public static final String     GET_CUSTOMERS_DEV  = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerin1?sap-vhost=my346674.sapbydesign.com";
    public static final String   GET_CUSTOMERS_PROD = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerin1?sap-vhost=my343751.sapbydesign.com";
    public static  String   GET_CUSTOMERS = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerin1?sap-vhost=my343751.sapbydesign.com" ;


    public static final String     GET_MATERIALS_DEV  = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/querymaterialin?sap-vhost=my346674.sapbydesign.com";
    public static final String   GET_MATERIALS_PROD = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querymaterialin?sap-vhost=my343751.sapbydesign.com";
    public static  String   GET_MATERIALS = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querymaterialin?sap-vhost=my343751.sapbydesign.com";


    public ArrayList<String>  LsFilterStock;

    public String getGlobalVarValue() {
        return mGlobalVarValue;
    }

    public void setGlobalVarValue(String str) {
        mGlobalVarValue = str;
    }

}

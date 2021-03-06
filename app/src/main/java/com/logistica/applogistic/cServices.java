package com.logistica.applogistic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public class cServices {


    public  String END_POINT_REST = "";
    public  String END_POINT_REST_C = "";


    public  String NAME_SPACE_SOAP = "";
    public  String NAME_SPACE_REST = "";
    public  String NAME_RESOURCE_REST = "";


    public  int  CONNECT_TIMEOUT_REST = 0;
    public  int  WRITE_TIMEOUT_REST = 0;
    public  int  READ_TIMEOUT_REST = 0;


    public  int  CONNECT_TIMEOUT_SOAP = 0;
    public  int  READ_TIMEOUT_SOAP = 0;

    public  String  AUTHORIZATION_REST_VALUE = "";
    public  String  AUTHORIZATION_SOAP_VALUE = "";


    public cServices() {
        Init();
    }

    private void Init() {

        // secs
        CONNECT_TIMEOUT_REST = 60;
        WRITE_TIMEOUT_REST = 300;
        READ_TIMEOUT_REST = 600;

        // milisec
        CONNECT_TIMEOUT_SOAP = 120000;
        READ_TIMEOUT_SOAP = 300000;

        NAME_SPACE_SOAP = getNameSpaceSoap();
        NAME_SPACE_REST = getNameSpaceRest();
        NAME_RESOURCE_REST = getNameResourceRest();
        AUTHORIZATION_REST_VALUE = getAuthorizationRest();
        AUTHORIZATION_SOAP_VALUE = getAuthorizationSoap();
        END_POINT_REST = getEndPointProductRest();
        END_POINT_REST_C = getEndPointProductRest_C();
    }

/*    private String getAuthorizationRest (){
        String  sResult = "";
        sResult = "Basic REVWOkluaWNpbzAy";
        return sResult;
    }*/

    private String getAuthorizationRest (){
        String  sResult = "";
        sResult = cGlobalData.AUTHORIZATION_REST;
        return sResult;
    }

/*    private String getAuthorizationSoap (){
        String  sResult = "";
        sResult = "Basic X0FQUElOVEVHUkFUOkluaWNpbzAx";
        return sResult;
    }*/

    private String getAuthorizationSoap (){
        String  sResult = "";
        sResult = cGlobalData.AUTHORIZATION_SOAP;
        return sResult;
    }


/*    private String getNameSpaceSoap (){
        String  sResult = "";
        sResult = "http://sap.com/xi/SAPGlobal20/Global";
        return sResult;
    }*/

    private String getNameSpaceSoap (){
        String  sResult = "";
        sResult =  cGlobalData.NAME_SPACE_SOAP;
        return sResult;
    }


    /*    private String getNameSpaceRest (){
        String  sResult = "";
        sResult = "https://my346674.sapbydesign.com";
        return sResult;
    }*/


    private String getNameSpaceRest (){
        String  sResult = "";
        sResult = cGlobalData.NAME_SPACE_REST;
        return sResult;
    }


    //  DEV
/*    private String getNameSpaceRest (){
        String  sResult = "";
        sResult = "https://my346674.sapbydesign.com";
        return sResult;
    }*/


    // PROD
//    private String getNameSpaceRest (){
//        String  sResult = "";
//        sResult = "https://my343751.sapbydesign.com";
//        return sResult;
//    }


/*    private String getNameResourceRest (){
        String  sResult = "";
        sResult = "/sap/byd/odata/ana_businessanalytics_analytics.svc/RPSCMINVV02_Q0001QueryResults";
        return sResult;
    }*/


    private String getNameResourceRest (){
        String  sResult = "";
        sResult = cGlobalData.NAME_RESOURCE_REST;
        return sResult;
    }


/*    private String getEndPointProductRest (){
        String  sResult = "";
        sResult = "http://3.92.221.195";
        return sResult;
    }*/

    private String getEndPointProductRest (){
        String  sResult = "";
        sResult = cGlobalData.END_POINT_PRODUCT_REST;
        return sResult;
    }

    private String getEndPointProductRest_C (){
        String  sResult = "";
        sResult = cGlobalData.END_POINT_PRODUCT_REST_C;
        return sResult;
    }

    public cUserResponse PostLoginDataService(cUserRequest pObj){

        String Resource = "/ws_ApiLogistic/api/LoginAplicacion";
        cUserResponse oResponse = new  cUserResponse();
        Response response;
        String  sResult  = "";


        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();


                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
                jRequest.put("Aplicacion",pObj.Aplicacion);
                jRequest.put("User",pObj.User);
                jRequest.put("Password",pObj.Pwd);


                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("Mensaje").toString().equals("null"))
                    {
                        if (jr.get("Mensaje").toString().equals("acceso")){

                            oResponse.login =  jr.get("login").toString();
                            oResponse.Mensaje =  jr.get("Mensaje").toString();
                            oResponse.Error =  "";
                            if( oResponse.login.equals("verdadero")){

                                oResponse.Acceso = true;
                            }

                        } else {

                            oResponse.login = "falso";
                            oResponse.Acceso =  false;
                            oResponse.Error =  "";
                        }

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Error = oError.get("ErrorCodigo").toString();
                        oResponse.login = "falso";
                        oResponse.Acceso =  false;
                    }
                }
            }

        } catch (Exception e) {

            oResponse.Error = e.getMessage();
            oResponse.login = "falso";
            oResponse.Acceso =  false;

            return oResponse;
        }

        return oResponse;
    }




    public cProductResponse PostConsultProductDataService(cProductViewInfo  pObj){

        String Resource = "/ws_ApiLogistic/api/obtenerproducto";
        cProductResponse oResponse = new  cProductResponse();
        Response response;
        String  sResult  = "";


        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();


                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
                jRequest.put("idProductoSAP",pObj.ProductoSAPId);
                jRequest.put("CodigoBarra",pObj.CodigoBarra);


                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("mensaje").toString().equals("null"))
                    {
                        if (jr.get("mensaje").toString().equals("Asignado")){

                            oResponse.ResponseId =  jr.get("idProductoSAP").toString();
                            oResponse.CodigoBarra =  jr.get("CodigoBarra").toString();
                            oResponse.Nombre =  jr.get("Nombre").toString();
                            oResponse.Descripcion =  jr.get("Descripcion").toString();
                            oResponse.Estado =  jr.get("Estado").toString();
                            oResponse.mensaje =  jr.get("mensaje").toString();
                            oResponse.Assigned =  true;

                        } else {

                            oResponse.ResponseId = "0";
                            oResponse.Assigned =  false;
                        }

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Msg = oError.get("ErrorDescripcion").toString();
                        oResponse.ResponseId= "-1";
                        oResponse.Assigned =  false;
                    }
                }
            }

        } catch (Exception e) {

            oResponse.ResponseId= "0";
            oResponse.Msg = e.getMessage();
            return oResponse;
        }

        return oResponse;
    }

    public cProductResponse PostConsultProductDataService_C(cProductViewInfo  pObj){

        String Resource = "/ws_ApiLogistic/api/ObtenerProductoCaja";
        cProductResponse oResponse = new  cProductResponse();
        Response response;
        String  sResult  = "";


        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();


                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
               // jRequest.put("idProductoSAP",pObj.ProductoSAPId);
                jRequest.put("idproductoDetalle",pObj.ProductoSAPId);
                jRequest.put("CodigoBarra",pObj.CodigoBarra);


                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST_C + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("mensaje").toString().equals("null"))
                    {
                        if (jr.get("mensaje").toString().equals("Asignado")){

                            oResponse.ResponseId =  jr.get("idProductoSAP").toString();
                            oResponse.CodigoBarra =  jr.get("CodigoBarra").toString();
                            oResponse.Nombre =  jr.get("Nombre").toString();
                            oResponse.Descripcion =  jr.get("Descripcion").toString();
                            oResponse.Estado =  jr.get("Estado").toString();
                            oResponse.mensaje =  jr.get("mensaje").toString();
                            oResponse.CodigoCaja =  jr.get("codigoCaja").toString();
                            oResponse.CantidadCaja =  jr.get("cantidadCaja").toString();

                            oResponse.Assigned =  true;

                        } else {

                            oResponse.ResponseId = "0";
                            oResponse.Assigned =  false;
                        }

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Msg = oError.get("ErrorDescripcion").toString();
                        oResponse.ResponseId= "-1";
                        oResponse.Assigned =  false;
                    }
                }
            }

        } catch (Exception e) {

            oResponse.ResponseId= "0";
            oResponse.Msg = e.getMessage();
            return oResponse;
        }

        return oResponse;
    }



    public cProductResponse PostProductAssignedDataService(cProductViewInfo  pObj){

        String Resource = "/ws_ApiLogistic/api/ValidarProducto";
        cProductResponse oResponse = new  cProductResponse();
        Response response;
        String  sResult  = "";


        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();


                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
                jRequest.put("idProductoSAP",pObj.ProductoSAPId);
                jRequest.put("CodigoBarra",pObj.CodigoBarra);


                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("mensaje").toString().equals("null"))
                    {
                        if (jr.get("mensaje").toString().equals("Asignado")){

                            oResponse.ResponseId =  jr.get("idProductoSAP").toString();
                            oResponse.CodigoBarra =  jr.get("CodigoBarra").toString();
                            oResponse.Msg =  jr.get("mensaje").toString();
                            oResponse.Assigned =  true;
                        } else {

                            oResponse.ResponseId = "0";
                            oResponse.Assigned =  false;
                        }

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Msg = oError.get("ErrorDescripcion").toString();
                        oResponse.ResponseId= "-1";
                        oResponse.Assigned =  false;
                    }
                }
            }

        } catch (Exception e) {

            oResponse.ResponseId= "0";
            oResponse.Msg = e.getMessage();
            return oResponse;
        }

        return oResponse;
    }


    public cProductResponse PostProductAssignedDataService_C_Desembarque(cProductViewInfo  pObj, String SerialNumberGetS){

        String Resource = "/ws_ApiLogistic/api/ValidarProductoCaja";
        cProductResponse oResponse = new  cProductResponse();
        Response response;
        String  sResult  = "";


        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();


                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
                // jRequest.put("idProductoSAP",pObj.ProductoSAPId);
                if(SerialNumberGetS != "" ) {

                    jRequest.put("idproductoDetalle","");
                    jRequest.put("CodigoBarra",SerialNumberGetS);

                } else {

                    jRequest.put("idproductoDetalle",pObj.ProductoSAPId);
                    jRequest.put("CodigoBarra","");

                }





                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST_C + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("mensaje").toString().equals("null"))
                    {
                        if (jr.get("mensaje").toString().equals("Asignado")){

                            oResponse.ResponseId =  jr.get("idProductoSAP").toString();
                            oResponse.CodigoBarra =  jr.get("CodigoBarra").toString();
                            oResponse.CodigoCaja =  jr.get("codigoCaja").toString();
                            oResponse.CantidadCaja =  jr.get("cantidadCaja").toString();
                            oResponse.Msg =  jr.get("mensaje").toString();
                            oResponse.Assigned =  true;
                        } else {

                            oResponse.ResponseId = "0";
                            oResponse.Assigned =  false;
                        }

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Msg = oError.get("ErrorDescripcion").toString();
                        oResponse.ResponseId= "-1";
                        oResponse.Assigned =  false;
                    }
                }
            }

        } catch (Exception e) {

            oResponse.ResponseId= "0";
            oResponse.Msg = e.getMessage();
            return oResponse;
        }

        return oResponse;
    }

    public cProductResponse PostProductAssignedDataService_C(cProductViewInfo  pObj){

        String Resource = "/ws_ApiLogistic/api/ValidarProductoCaja";
        cProductResponse oResponse = new  cProductResponse();
        Response response;
        String  sResult  = "";


        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();


                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
               // jRequest.put("idProductoSAP",pObj.ProductoSAPId);
                jRequest.put("idproductoDetalle",pObj.ProductoSAPId);
                jRequest.put("CodigoBarra",pObj.CodigoBarra);


                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST_C + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("mensaje").toString().equals("null"))
                    {
                        if (jr.get("mensaje").toString().equals("Asignado")){

                            oResponse.ResponseId =  jr.get("idProductoSAP").toString();
                            oResponse.CodigoBarra =  jr.get("CodigoBarra").toString();
                            oResponse.CodigoCaja =  jr.get("codigoCaja").toString();
                            oResponse.CantidadCaja =  jr.get("cantidadCaja").toString();
                            oResponse.Msg =  jr.get("mensaje").toString();
                            oResponse.Assigned =  true;
                        } else {

                            oResponse.ResponseId = "0";
                            oResponse.Assigned =  false;
                        }

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Msg = oError.get("ErrorDescripcion").toString();
                        oResponse.ResponseId= "-1";
                        oResponse.Assigned =  false;
                    }
                }
            }

        } catch (Exception e) {

            oResponse.ResponseId= "0";
            oResponse.Msg = e.getMessage();
            return oResponse;
        }

        return oResponse;
    }



    public cProductResponse PostProductDataService(cProductViewInfo  pObj){

        String Resource = "/ws_ApiLogistic/api/RegistrarProducto";
        cProductResponse oResponse = new  cProductResponse();
        Response response;
        String  sResult  = "";


        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();


                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
                jRequest.put("idProductoSAP",pObj.ProductoSAPId);
                jRequest.put("Nombre",pObj.Nombre);
                jRequest.put("Descripcion",pObj.Descripcion);
                jRequest.put("CodigoBarra",pObj.CodigoBarra);
                jRequest.put("Estado",pObj.Estado);
                jRequest.put("Usuario",pObj.Usuario);


                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("idProductoSAP").toString().equals("null"))
                    {
                        oResponse.ResponseId =  jr.get("idProductoSAP").toString();

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Msg = oError.get("ErrorDescripcion").toString();
                        oResponse.ResponseId= "0";
                    }
                }
            }

        } catch (Exception e) {

            oResponse.ResponseId= "0";
            oResponse.Msg = e.getMessage();
            return oResponse;
        }

        return oResponse;
    }

    public cProductResponse PostProductDataService_C(cProductViewInfo  pObj){

        String Resource = "/ws_ApiLogistic/api/RegistrarProductoCaja";
        cProductResponse oResponse = new  cProductResponse();
        Response response;
        String  sResult  = "";

        try {

            if (pObj != null){

                // se construye  el httpCliente
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                        .build();

                String ID =  Integer.toString(pObj.ID);

                // se crea el json de request
                JSONObject   jRequest = new JSONObject();
                jRequest.put("idproductoDetalle",ID);
                jRequest.put("idProductoSAP",pObj.ProductoSAPId);
                jRequest.put("Nombre",pObj.Nombre);
                jRequest.put("Descripcion",pObj.Descripcion);
                jRequest.put("CodigoBarra",pObj.CodigoBarra);
                jRequest.put("Estado",pObj.Estado);
                jRequest.put("Usuario",pObj.Usuario);
                jRequest.put("codigoCaja",pObj.CodigoCaja);
                jRequest.put("cantidadCaja",pObj.CantidadCaja);


                // Se crea el request
                HttpUrl route = HttpUrl.parse(END_POINT_REST_C + Resource);
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jRequest.toString());
                Request request = new Request.Builder()
                        .url(route)
                        .post(body)
                        .build();


                // se invoca el servicio
                response = client.newCall(request).execute();


                // se guarda la respuesta
                if ( response.isSuccessful() ){
                    sResult =  response.body().string();
                    JSONObject  jr = new JSONObject(sResult);

                    if (!jr.get("idProductoSAP").toString().equals("null"))
                    {
                        oResponse.ResponseId =  jr.get("idProductoSAP").toString();

                    } else {

                        JSONObject  oError =  jr.getJSONObject("Error");
                        oResponse.Msg = oError.get("ErrorDescripcion").toString();
                        oResponse.ResponseId= "0";
                    }
                }
            }

        } catch (Exception e) {

            oResponse.ResponseId= "0";
            oResponse.Msg = e.getMessage();
            return oResponse;
        }

        return oResponse;
    }





    public ArrayList<cStock> GetStockServiceData(String  pFilterType, String pFilterValue, String pMaximumNumberValue){
        String ErrorMsg = "";
        ArrayList<cStock> lsData = new  ArrayList<>();

        Response response;
        String  sResult  = "";
        String  sFiltervalueTemp  =  "(" + pFilterType + " eq " + "'" +  pFilterValue + "'" + ")";

        try {


            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT_REST, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT_REST, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT_REST, TimeUnit.SECONDS)
                    .build();


          //  HttpUrl.Builder urlBuilder = HttpUrl.parse("https://my346674.sapbydesign.com/sap/byd/odata/ana_businessanalytics_analytics.svc/RPSCMINVV02_Q0001QueryResults").newBuilder();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(NAME_SPACE_REST +  NAME_RESOURCE_REST).newBuilder();
            urlBuilder.addQueryParameter("$select", "CMATERIAL_UUID,TLOG_AREA_UUID,CLOG_AREA_UUID,CON_HAND_STOCK_UOM,TSITE_UUID,CSITE_UUID,KCON_HAND_STOCK");
            urlBuilder.addQueryParameter("$filter", sFiltervalueTemp);
            urlBuilder.addQueryParameter("$format", "json");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                  //  .addHeader("Authorization", "Basic REVWOkluaWNpbzAy")
                    .addHeader("Authorization", AUTHORIZATION_REST_VALUE)
                    .build();

            response = client.newCall(request).execute();

            if ( response.isSuccessful() ){
                sResult =  response.body().string();
                lsData = getStockData( new JSONObject(sResult));
            }

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private ArrayList<cStock> getStockData (JSONObject  pJsonData){

        JSONObject oJsonD = null;
        JSONArray lsJsonArray = null;
        cStock  oData = new cStock();
        ArrayList<cStock>  lsData = new  ArrayList<>();

        try {

            oJsonD =  pJsonData.getJSONObject("d");
            lsJsonArray = oJsonD.getJSONArray("results");

            for (int i = 0; i < lsJsonArray.length(); i++) {
                oData = new cStock();
                JSONObject object = lsJsonArray.getJSONObject(i);

                oData.CLOG_AREA_UUID  = object.getString("CLOG_AREA_UUID");
                oData.CMATERIAL_UUID  = object.getString("CMATERIAL_UUID");
                oData.CON_HAND_STOCK_UOM  = object.getString("CON_HAND_STOCK_UOM");
                oData.CSITE_UUID  = object.getString("CSITE_UUID");
                oData.KCON_HAND_STOCK  = object.getString("KCON_HAND_STOCK");
                oData.TLOG_AREA_UUID  = object.getString("TLOG_AREA_UUID");
                oData.TSITE_UUID  = object.getString("TSITE_UUID");

                lsData.add(oData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lsData;
    }


    // Confirm outbound items task
    public  cConfirmTaskResponse PutConfirmOutboundTaskServiceData(cConfirmOutboundItemsTask pObj){

        // SOAP
        String Soap_Action = "Create";
        String url =  cGlobalData.POST_OUTBOUND_DELIVERY_CONFIRM_QUANTITY;
        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        cConfirmTaskResponse oResponse = new  cConfirmTaskResponse();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_ConfirmOutboundTask(pObj));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);

            if ((envelope.getResponse().getClass()).getName().equals("java.util.Vector")){
                Vector  vec =   (Vector)envelope.getResponse();

                if (vec.size() > 0){
                    SoapObject    so  =  (SoapObject)vec.get(0);
                    String   sServiceCode =  ((SoapPrimitive)so.getProperty("SAP_UUID")).getValue().toString().trim();
                    oResponse.SiteLogisticsTaskSeverityCode = sServiceCode;
                    oResponse.Msg = "";
                }
            }

        } catch (Exception e) {

            ErrorMsg = e.getMessage();

            oResponse.SiteLogisticsTaskSeverityCode = "E" ;
            oResponse.Msg = ErrorMsg;

            return  oResponse;
        }

        return oResponse;
    }

    private  SoapObject  getBodySoapObjectByFilterType_ConfirmOutboundTask(cConfirmOutboundItemsTask pObj){

        SoapObject soNI =  new SoapObject();
        SoapPrimitive sopNQ =  null;

        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "Z_ConfirmacionCantidadCreateRequest_sync");
        SoapObject soN1 =  new SoapObject("", "Z_ConfirmacionCantidad");
        soN1.addProperty("outboundDeliveryID", pObj.outboundDeliveryID);
        soN1.addProperty("date", pObj.date);
        soN1.addProperty("confirmationStatus", pObj.confirmationStatus);

        for ( cConfirmOutboundItem item:pObj.lsItems){
            soNI =  new SoapObject("", "Item");
            soNI.addProperty("productID", item.productID);

            sopNQ = new SoapPrimitive("", "confirmedQuantity", item.confirmedQuantity);
            sopNQ.addAttribute("unitCode", item.confirmedQuantityUnitCode);

            soNI.addProperty("confirmedQuantity",sopNQ);

            soN1.addSoapObject(soNI);
        }

        oSoapObjectResult.addSoapObject(soN1);

        return oSoapObjectResult;
    }



    // Confirm task
    public  cConfirmTaskResponse PutConfirmTaskServiceData(cTaskResponse pObj){

        // SOAP
        String Soap_Action = "MaintainBundle_V1";
       // String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/managesitelogisticstaskin?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/managesitelogisticstaskin?sap-vhost=my343751.sapbydesign.com";  // PROD
        String url =  cGlobalData.PUT_CONFIRM_TASK;
        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        cConfirmTaskResponse oResponse = new  cConfirmTaskResponse();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_ConfirmTask(pObj));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);

            if ((envelope.getResponse().getClass()).getName().equals("org.ksoap2.serialization.SoapObject")){
                SoapObject  so =   (SoapObject)envelope.getResponse();
                SoapObject    soResp  =  (SoapObject)so.getProperty("SiteLogisticsTaskLog");

                String   sServiceCode =  ((SoapPrimitive)soResp.getProperty("SiteLogisticsTaskSeverityCode")).getValue().toString().trim();

                oResponse.SiteLogisticsTaskSeverityCode = sServiceCode;
                oResponse.Msg = "";

               // ((SoapPrimitive)((SoapObject)so.getProperty("SiteLogisticsTaskLog")).getProperty("SiteLogisticsTaskSeverityCode")).getValue().toString().trim();
            }

        } catch (Exception e) {

            ErrorMsg = e.getMessage();

            oResponse.SiteLogisticsTaskSeverityCode = "E" ;
            oResponse.Msg = ErrorMsg;

            return  oResponse;
        }

        return oResponse;
    }

    private  SoapObject  getBodySoapObjectByFilterType_ConfirmTask(cTaskResponse pObj){

        SoapObject soNI =  new SoapObject();
        SoapPrimitive sopNQ =  null;



        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "SiteLogisticsTaskBundleMaintainRequest_sync_V1");
        SoapObject soN1 =  new SoapObject("", "BasicMessageHeader");
        soN1.addProperty("ID", "123");

        SoapObject soN2 =  new SoapObject("", "SiteLogisticsTask");
        soN2.addProperty("SiteLogisticTaskID", pObj.SiteLogisticTaskID);
        soN2.addProperty("SiteLogisticTaskUUID", pObj.SiteLogisticTaskUUID);

        SoapObject soN3 =  new SoapObject("", "ReferenceObject");
        soN3.addProperty("ReferenceObjectUUID", pObj.ReferenceObjectUUID);

        SoapObject soN4 =  new SoapObject("", "OperationActivity");
        soN4.addProperty("OperationActivityUUID", pObj.SiteLogisticsLotOperationActivityUUID);

        for ( cMaterialSimpleData item:pObj.MaterialsInput){
            soNI =  new SoapObject("", "MaterialInput");
            soNI.addProperty("MaterialInputUUID", item.SiteLogisticsLotMaterialInputUUID);
            soNI.addProperty("ProductID", item.ProductID);

            sopNQ = new SoapPrimitive("", "ActualQuantity", item.ActualQuantity);
            sopNQ.addAttribute("unitCode", item.PlanQuantityUnitCode);
            soNI.addProperty("ActualQuantity",sopNQ);

            //soNI.addProperty("ActualQuantity", item.ActualQuantity);
            if(!item.SourceLogisticsAreaID.trim().equals("")){
                soNI.addProperty("SourceLogisticsAreaID", item.SourceLogisticsAreaID);
            }
            soN4.addSoapObject(soNI);
        }

        for ( cMaterialSimpleData item:pObj.MaterialsOutput){
            soNI =  new SoapObject("", "MaterialOutput");
            soNI.addProperty("MaterialOutputUUID", item.SiteLogisticsLotMaterialOutputUUID);
            soNI.addProperty("ProductID", item.ProductID);

            sopNQ = new SoapPrimitive("", "ActualQuantity", item.ActualQuantity);
            sopNQ.addAttribute("unitCode", item.PlanQuantityUnitCode);
            soNI.addProperty("ActualQuantity",sopNQ);

           // soNI.addProperty("ActualQuantity", item.ActualQuantity);
            if(!item.TargetLogisticsAreaID.trim().equals("")){
                soNI.addProperty("TargetLogisticsAreaID", item.TargetLogisticsAreaID);
            }
            soN4.addSoapObject(soNI);
        }

        soN3.addSoapObject(soN4);
        soN2.addSoapObject(soN3);

        oSoapObjectResult.addSoapObject(soN1);
        oSoapObjectResult.addSoapObject(soN2);


/*        SoapObject soN3 =  new SoapObject("", "InventoryItemChangeQuantity");
        SoapPrimitive  spN = new SoapPrimitive("", "Quantity", pObj.Quantity);
        spN.addAttribute("unitCode", pObj.QuantityUnitCode);
        soN3.addProperty("Quantity",spN);
        soN2.addSoapObject(soN3);*/


        return oSoapObjectResult;
    }





    // Send Movement

    public  cMovementResponse PutMovementServiceData(cMovementRequest pObj){

        // SOAP
        String Soap_Action = "DoGoodsMovementGoodsAndActivityConfirmation";

        // String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/inventoryprocessinggoodsandac2?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/inventoryprocessinggoodsandac2?sap-vhost=my343751.sapbydesign.com";  // PROD
         String url = cGlobalData.PUT_MOVEMENT;


        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        cMovementResponse oResponse = new  cMovementResponse();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_Movement(pObj));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);

            if ((envelope.getResponse().getClass()).getName().equals("org.ksoap2.serialization.SoapObject")){
               SoapObject  so =   (SoapObject)envelope.getResponse();
               //so.getProperty("");

                oResponse.GACID = ((SoapPrimitive)so.getProperty("GACID")).getValue().toString();
                oResponse.MSG = "";
            }


        } catch (Exception e) {

            ErrorMsg = e.getMessage();
            oResponse.GACID = "0";
            oResponse.MSG = "No fue posible realizar la operación: Favor de validar que los datos enviados son correctos";
            return  oResponse;

        }

        return oResponse;
    }

    private  SoapObject  getBodySoapObjectByFilterType_Movement(cMovementRequest pObj){

        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "GoodsAndActivityConfirmationGoodsMovement");
        SoapObject soN1 =  new SoapObject("", "GoodsAndActivityConfirmation");
        soN1.addProperty("ExternalID", pObj.ExternalID);
        soN1.addProperty("SiteID", pObj.SiteID);

        SoapObject soN2 =  new SoapObject("", "InventoryChangeItemGoodsMovement");
        soN2.addProperty("ExternalItemID", pObj.ExternalItemID);
        soN2.addProperty("MaterialInternalID", pObj.MaterialInternalID);
        soN2.addProperty("OwnerPartyInternalID", pObj.OwnerPartyInternalID);
        soN2.addProperty("InventoryRestrictedUseIndicator", pObj.InventoryRestrictedUseIndicator);

        if (!pObj.IdentifiedStockID.isEmpty()){
            soN2.addProperty("IdentifiedStockID", pObj.IdentifiedStockID);
        }

        soN2.addProperty("SourceLogisticsAreaID", pObj.SourceLogisticsAreaID);
        soN2.addProperty("TargetLogisticsAreaID", pObj.TargetLogisticsAreaID);


        SoapObject soN3 =  new SoapObject("", "InventoryItemChangeQuantity");
        SoapPrimitive  spN = new SoapPrimitive("", "Quantity", pObj.Quantity);
        spN.addAttribute("unitCode", pObj.QuantityUnitCode);
        soN3.addProperty("Quantity",spN);

        soN2.addSoapObject(soN3);

        if (!pObj.SerialID.isEmpty()){

            SoapObject soN4 = new SoapObject("", "InventoryItemChangeSerialNumber");
            soN4.addProperty("SerialID", pObj.SerialID);
            soN2.addSoapObject(soN4);
        }

        soN1.addSoapObject(soN2);

        oSoapObjectResult.addSoapObject(soN1);

        return oSoapObjectResult;
    }

    private ArrayList<cMovementResponse> getMovementResponseData (Vector  pVector){

        cMovementResponse  oResponse = new cMovementResponse();
        ArrayList<cMovementResponse>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {

            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){

                oResponse =  new cMovementResponse();

                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetMovementResponseProperty(oResponse,  oPropertyInfo);

//                if (!oResponse.TypeID.isEmpty()){
//                    lsData.add(oResponse);
//                }
            }
        }

        return lsData;
    }

    private void SetMovementResponseProperty(cMovementResponse oObj, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "GACDetails":
                oObj.GACID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("GACID")).getValue().toString().trim();

            default:break;
        }
    }



   //  Send Inbound
    public ArrayList<cInboundDeliveryResponse> PutInboundDeliveryServiceData(cInboundDelivery  pInboundDelivery){

        // SOAP
        String Soap_Action = "Create";
        //String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_z_inbounddelivery?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_z_inbounddelivery?sap-vhost=my343751.sapbydesign.com"; // PROD
        String url = cGlobalData.PUT_INBOUND_DELIVERY;

        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cInboundDeliveryResponse> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_InboundDelivery(pInboundDelivery));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);
            vResponse = (Vector)envelope.getResponse();
            lsData = getInboundDeliveryResponseData(vResponse);

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_InboundDelivery(cInboundDelivery pInboundDelivery){

        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "Z_InboundDeliveryCreateRequest_sync");
        SoapObject soN1 =  new SoapObject("", "Z_InboundDelivery");
        soN1.addProperty("ID", pInboundDelivery.ID );

        SoapObject soN2 = new SoapObject("", "item");
        soN2.addProperty("ID", pInboundDelivery.oInboundDeliveryItem.ID);


        SoapPrimitive  spN = new SoapPrimitive("", "CantidadConfirmada", pInboundDelivery.oInboundDeliveryItem.CantidadConfirmada);
        spN.addAttribute("unitCode", "ZPZ");
        soN2.addProperty("CantidadConfirmada",spN);

      //  soN2.addProperty("CantidadConfirmada", pInboundDelivery.oInboundDeliveryItem.CantidadConfirmada);

        if (!pInboundDelivery.oInboundDeliveryItem.IDAreaLogistica.trim().isEmpty()){
            soN2.addProperty("IDAreaLogistica", pInboundDelivery.oInboundDeliveryItem.IDAreaLogistica);
        }

        if (!pInboundDelivery.oInboundDeliveryItem.IDStockIdentificado.trim().isEmpty()){
            soN2.addProperty("IDStockIdentificado", pInboundDelivery.oInboundDeliveryItem.IDStockIdentificado);
        }

        soN1.addSoapObject(soN2);

        oSoapObjectResult.addSoapObject(soN1);

        return oSoapObjectResult;
    }

    private ArrayList<cInboundDeliveryResponse> getInboundDeliveryResponseData (Vector  pVector){

        cInboundDeliveryResponse  oInboundDeliveryResponse = new cInboundDeliveryResponse();
        ArrayList<cInboundDeliveryResponse>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {

            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){

                oInboundDeliveryResponse =  new cInboundDeliveryResponse();

                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetInboundDeliveryResponseProperty(oInboundDeliveryResponse,  oPropertyInfo);

                if (!oInboundDeliveryResponse.TypeID.isEmpty()){
                    lsData.add(oInboundDeliveryResponse);
                }
            }
        }

        return lsData;
    }

    private void SetInboundDeliveryResponseProperty(cInboundDeliveryResponse oObj, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "Log":
                oObj.TypeID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("MaximumLogItemSeverityCode")).getValue().toString().trim();

            default:break;
        }
    }




    //  get Task
    public ArrayList<cTaskResponse> GetTaskServiceData(String FilterType, String FilterValue, String  MaximumNumberValue){

        // SOAP
        String Soap_Action = "FindByElements";
        //String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/querysitelogisticstaskin?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querysitelogisticstaskin?sap-vhost=my343751.sapbydesign.com"; // PROD
        String url = cGlobalData.GET_TASK;

                HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cTaskResponse> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization",AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_GetTask(FilterType,FilterValue,MaximumNumberValue));


            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);

            vResponse = (Vector)envelope.getResponse();

            lsData = getTaskData(vResponse);


        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_GetTask(String FilterType, String FilterValue, String  MaximumNumberValue){
        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "SiteLogisticsTaskByElementsQuery_sync");
        SoapObject soN1 =  new SoapObject("", "SiteLogisticsTaskSelectionByElements");

        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundarySiteLogisticsTaskID", FilterValue);


        soN1.addSoapObject(soN2);
        oSoapObjectResult.addSoapObject(soN1);

        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }

        return oSoapObjectResult;
    }

    private ArrayList<cTaskResponse> getTaskData (Vector  pVector){

        cTaskResponse  oTaskResponse = new cTaskResponse();
        ArrayList<cTaskResponse>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {
            oTaskResponse =  new cTaskResponse();
            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){
                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetTaskDataProperty(oTaskResponse,  oPropertyInfo);
            }

            lsData.add(oTaskResponse);
        }

        return lsData;
    }

    private void SetTaskDataProperty(cTaskResponse oTaskResponse, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "SiteLogisticsTaskID": oTaskResponse.SiteLogisticTaskID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "SiteLogisticsTaskUUID": oTaskResponse.SiteLogisticTaskUUID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "SiteLogisticsTaskReferencedObject":
                oTaskResponse.ReferenceObjectUUID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("ReferencedObjectUUID")).getValue().toString().trim();

                SoapObject    soTemp =  (SoapObject)((SoapObject)oPropertyInfo.getValue()).getProperty("SiteLogisticsLotOperationActivity");
                for( int j= 0; j < soTemp.getPropertyCount(); j++ ){
                    PropertyInfo oPropertyInfoTemp = soTemp.getPropertyInfo(j);

                    if (oPropertyInfoTemp.getName().equals("SiteLogisticsLotOperationActivityUUID")){
                        oTaskResponse.SiteLogisticsLotOperationActivityUUID =  ((SoapPrimitive)oPropertyInfoTemp.getValue()).getValue().toString().trim();

                    } else if(oPropertyInfoTemp.getName().equals("MaterialInput")){
                            cMaterialSimpleData oData =  new cMaterialSimpleData();

                            oData.ProductID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("ProductID")).getValue().toString().trim();
                            oData.PlanQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("PlanQuantity")).getValue().toString().trim();
                            oData.PlanQuantityUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("PlanQuantity")).getAttribute("unitCode").toString().trim();

                            oData.OpenQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("OpenQuantity")).getValue().toString().trim();
                            oData.OpenQuantityUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("OpenQuantity")).getAttribute("unitCode").toString().trim();

                            oData.TotalConfirmedQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("TotalConfirmedQuantity")).getValue().toString().trim();
                            oData.TotalConfirmedQuantityUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("TotalConfirmedQuantity")).getAttribute("unitCode").toString().trim();

                            oData.SiteLogisticsLotMaterialInputUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("SiteLogisticsLotMaterialInputUUID")).getValue().toString().trim();
                            oData.SourceLogisticsAreaID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("SourceLogisticsAreaID")).getValue().toString().trim();

                        oTaskResponse.MaterialsInput.add(oData);

                    } else if(oPropertyInfoTemp.getName().equals("MaterialOutput")){
                        cMaterialSimpleData oData =  new cMaterialSimpleData();

                        oData.ProductID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("ProductID")).getValue().toString().trim();
                        oData.PlanQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("PlanQuantity")).getValue().toString().trim();
                        oData.PlanQuantityUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("PlanQuantity")).getAttribute("unitCode").toString().trim();

                        oData.OpenQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("OpenQuantity")).getValue().toString().trim();
                        oData.OpenQuantityUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("OpenQuantity")).getAttribute("unitCode").toString().trim();

                        oData.TotalConfirmedQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("TotalConfirmedQuantity")).getValue().toString().trim();
                        oData.TotalConfirmedQuantityUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("TotalConfirmedQuantity")).getAttribute("unitCode").toString().trim();

                        oData.SiteLogisticsLotMaterialOutputUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("SiteLogisticsLotMaterialOutputUUID")).getValue().toString().trim();
                        oTaskResponse.MaterialsOutput.add(oData);
                    }
                }


/*                SoapObject soTemp = ((SoapObject)oPropertyInfo.getValue());
                for( int j= 0; j < soTemp.getPropertyCount(); j++ ){
                    cSupplyPlanning oSupplyPlanning =  new cSupplyPlanning();
                    PropertyInfo oPropertyInfoTemp = soTemp.getPropertyInfo(j);

                    oSupplyPlanning.SupplyPlanningAreaID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("SupplyPlanningAreaID")).getValue().toString().trim();
                    oSupplyPlanning.LifeCycleStatusCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                    oSupplyPlanning.ProcurementTypeCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("ProcurementTypeCode")).getValue().toString().trim();
                    oSupplyPlanning.PlanningProcedureCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("PlanningProcedureCode")).getValue().toString().trim();
                    oSupplyPlanning.LotSizeProcedureCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("LotSizeProcedureCode")).getValue().toString().trim();
                    oSupplyPlanning.GoodsReceiptProcessingDuration  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("GoodsReceiptProcessingDuration")).getValue().toString().trim();
                    oMaterial.getPlanning().add(oSupplyPlanning);
                }*/


                break;

/*
            case  "InternalID": oMaterial.InternalID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "UUID": oMaterial.UUID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "SystemAdministrativeData":
                oMaterial.CreationDateTime  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CreationDateTime")).getValue().toString().trim();
                oMaterial.CreationIdentityUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CreationIdentityUUID")).getValue().toString().trim();
                oMaterial.LastChangeDateTime  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LastChangeDateTime")).getValue().toString().trim();
                oMaterial.LastChangeIdentityUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LastChangeIdentityUUID")).getValue().toString().trim();
                break;

            case  "ProductCategoryID": oMaterial.ProductCategoryID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "BaseMeasureUnitCode": oMaterial.BaseMeasureUnitCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "InventoryValuationMeasureUnitCode": oMaterial.InventoryValuationMeasureUnitCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "PlanningMeasureUnitCode": oMaterial.PlanningMeasureUnitCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "Description":
                oMaterial.Description  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Description")).getValue().toString().trim();
                oMaterial.DescriptionlanguageCode =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Description")).getAttribute("languageCode").toString().trim();
                break;
            case  "QuantityConversion":
                oMaterial.Quantity  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Quantity")).getValue().toString().trim();
                oMaterial.QuantityUnitCode =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Quantity")).getAttribute("unitCode").toString().trim();
                oMaterial.CorrespondingQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CorrespondingQuantity")).getValue().toString().trim();
                oMaterial.CorrespondingQuantityUnitCode =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CorrespondingQuantity")).getAttribute("unitCode").toString().trim();
                break;
            case  "Purchasing":
                oMaterial.LifeCycleStatusCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oMaterial.PurchasingMeasureUnitCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("PurchasingMeasureUnitCode")).getValue().toString().trim();
                break;
            case  "Planning":
                SoapObject soTemp = ((SoapObject)oPropertyInfo.getValue());
                for( int j= 0; j < soTemp.getPropertyCount(); j++ ){
                    cSupplyPlanning oSupplyPlanning =  new cSupplyPlanning();
                    PropertyInfo oPropertyInfoTemp = soTemp.getPropertyInfo(j);

                    oSupplyPlanning.SupplyPlanningAreaID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("SupplyPlanningAreaID")).getValue().toString().trim();
                    oSupplyPlanning.LifeCycleStatusCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                    oSupplyPlanning.ProcurementTypeCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("ProcurementTypeCode")).getValue().toString().trim();
                    oSupplyPlanning.PlanningProcedureCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("PlanningProcedureCode")).getValue().toString().trim();
                    oSupplyPlanning.LotSizeProcedureCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("LotSizeProcedureCode")).getValue().toString().trim();
                    oSupplyPlanning.GoodsReceiptProcessingDuration  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("GoodsReceiptProcessingDuration")).getValue().toString().trim();
                    oMaterial.getPlanning().add(oSupplyPlanning);
                }
                break;


            case  "AvailabilityConfirmation":

                cAvailabilityConfirmation oAvailabilityConfirmation = new cAvailabilityConfirmation();

                oAvailabilityConfirmation.PlanningAreaID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("PlanningAreaID")).getValue().toString().trim();
                oAvailabilityConfirmation.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oAvailabilityConfirmation.AvailabilityCheckScopeCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("AvailabilityCheckScopeCode")).getValue().toString().trim();
                oAvailabilityConfirmation.GoodsIssueProcessingDuration = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("GoodsIssueProcessingDuration")).getValue().toString().trim();

                oMaterial.AvailabilityConfirmation.add(oAvailabilityConfirmation);

                break;


            case  "Sales":

                cSales  oSales = new cSales();

                oSales.SalesOrganisationID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("SalesOrganisationID")).getValue().toString().trim();
                oSales.DistributionChannelCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("DistributionChannelCode")).getValue().toString().trim();
                oSales.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oSales.SalesMeasureUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("SalesMeasureUnitCode")).getValue().toString().trim();
                oSales.ItemGroupCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("ItemGroupCode")).getValue().toString().trim();

                oMaterial.Sales.add(oSales);

                break;

            case  "Logistics":

                cLogistics oLogistics = new cLogistics();

                oLogistics.SiteID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("SiteID")).getValue().toString().trim();
                oLogistics.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oLogistics.CycleCountPlannedDuration = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CycleCountPlannedDuration")).getValue().toString().trim();

                oMaterial.Logistics.add(oLogistics);

                break;

            case  "DeviantTaxClassification":
                oMaterial.CountryCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CountryCode")).getValue().toString().trim();
                oMaterial.RegionCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("RegionCode")).getValue().toString().trim();
                oMaterial.RegionCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("RegionCode")).getAttribute("listID").toString().trim();
                oMaterial.TaxTypeCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxTypeCode")).getValue().toString().trim();
                oMaterial.TaxTypeCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxTypeCode")).getAttribute("listID").toString().trim();
                oMaterial.TaxRateTypeCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxRateTypeCode")).getValue().toString().trim();
                oMaterial.TaxRateTypeCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxRateTypeCode")).getAttribute("listID").toString().trim();
                oMaterial.TaxExemptionReasonCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxExemptionReasonCode")).getValue().toString().trim();
                oMaterial.TaxExemptionReasonCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxExemptionReasonCode")).getAttribute("listID").toString().trim();

                break;

            case  "Valuation":

                cValuation  oValuation = new cValuation();

                oValuation.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oValuation.CompanyID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CompanyID")).getValue().toString().trim();
                oValuation.BusinessResidenceID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("BusinessResidenceID")).getValue().toString().trim();

                oMaterial.Valuation.add(oValuation);

                break;
                */
            default:break;
        }
    }


    //mig: new
    public ArrayList<cOutboundOrderItem> GetOutboundOrderItemsServiceData(String  pFilterType, String pFilterValue, String pMaximumNumberValue){

        // SOAP
        String Soap_Action = "Read";
        String url = cGlobalData.GET_OUTBOUND_DELIVERY;

        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cOutboundOrderItem> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_OutboundOrderItems(pFilterType,pFilterValue,pMaximumNumberValue));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);
            vResponse = (Vector)envelope.getResponse();
            lsData = getOutboundOrderItemData(vResponse);

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_OutboundOrderItems(String FilterType, String FilterValue, String  MaximumNumberValue){

        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "OutboundDeliveryReadByIDQuery_sync");
        SoapObject soN1 =  new SoapObject("", "OutboundDelivery");
        soN1.addProperty(FilterType, FilterValue);


/*        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundaryID", FilterValue);*/
        // soN1.addSoapObject(soN2);

        oSoapObjectResult.addSoapObject(soN1);

/*        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }*/

        return oSoapObjectResult;
    }

    private ArrayList<cOutboundOrderItem> getOutboundOrderItemData (Vector  pVector){

        cOutboundOrderItem  oOutboundOrderItem = new cOutboundOrderItem();
        ArrayList<cOutboundOrderItem>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {

            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){

                oOutboundOrderItem =  new cOutboundOrderItem();

                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetInboundOrerItemProperty(oOutboundOrderItem,  oPropertyInfo);

                if (!oOutboundOrderItem.ID.isEmpty()){
                    lsData.add(oOutboundOrderItem);
                }
            }
        }

        return lsData;
    }

    private void SetInboundOrerItemProperty(cOutboundOrderItem oObj, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "Item":
                SoapObject  so = (SoapObject)oPropertyInfo.getValue();

                // ProductID
                SoapObject  soProduct = (SoapObject)so.getProperty("Product");
                SoapObject  soProductKey = (SoapObject)soProduct.getProperty("ProductKey");

                //  Description
                SoapObject  soMaterial = (SoapObject)soProduct.getProperty("Material");
                SoapObject  soDescription = (SoapObject)soMaterial.getProperty("Description");

                //  Quantity
                SoapObject  soDeliveryQuantity = (SoapObject)so.getProperty("DeliveryQuantity");

                // read data
                oObj.ID = ((SoapPrimitive)so.getProperty("ID")).getValue().toString().trim();
                oObj.ProductID = ((SoapPrimitive)soProductKey.getProperty("ProductID")).getValue().toString().trim();
                oObj.Description = ((SoapPrimitive)soDescription.getProperty("Description")).getValue().toString().trim();

                oObj.Quantity = ((SoapPrimitive)soDeliveryQuantity.getProperty("Quantity")).getValue().toString().trim();
                oObj.QuantityUnitCode =  ((SoapPrimitive)soDeliveryQuantity.getProperty("Quantity")).getAttribute("unitCode").toString().trim();

                break;

            default:break;
        }
    }






    public ArrayList<cPurchaseItem> GetPurchaseItemServiceData(String  pFilterType, String pFilterValue, String pMaximumNumberValue){

        // SOAP
        String Soap_Action = "Read";
       // String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my343751.sapbydesign.com";  // PROD
        String url = cGlobalData.GET_PURCHASE_ITEM;

        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cPurchaseItem> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_PurchaseItem(pFilterType,pFilterValue,pMaximumNumberValue));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);
            vResponse = (Vector)envelope.getResponse();
            lsData = getPurchaseItemData(vResponse);

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_PurchaseItem(String FilterType, String FilterValue, String  MaximumNumberValue){

        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "PurchaseOrderReadByIDQuery_sync");
        SoapObject soN1 =  new SoapObject("", "PurchaseOrder");
        soN1.addProperty(FilterType, FilterValue);


/*        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundaryID", FilterValue);*/
       // soN1.addSoapObject(soN2);

        oSoapObjectResult.addSoapObject(soN1);

/*        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }*/

        return oSoapObjectResult;
    }

    private ArrayList<cPurchaseItem> getPurchaseItemData (Vector  pVector){

        cPurchaseItem  oPurchaseItem = new cPurchaseItem();
        ArrayList<cPurchaseItem>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {

            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){

                oPurchaseItem =  new cPurchaseItem();

                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetPurchaseItemProperty(oPurchaseItem,  oPropertyInfo);

                if (!oPurchaseItem.ID.isEmpty()){
                    lsData.add(oPurchaseItem);
                }
            }
        }

        return lsData;
    }

    private void SetPurchaseItemProperty(cPurchaseItem oObj, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "Item":

                oObj.ID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("ID")).getValue().toString().trim();
                oObj.Description = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Description")).getValue().toString().trim();
                oObj.Quantity = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Quantity")).getValue().toString().trim();
                oObj.QuantityUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Quantity")).getAttribute("unitCode").toString().trim();

                break;

            default:break;
        }
    }





//  mig: new
    public ArrayList<cOutboundOrder> GetOutboundOrderServiceData(String  pFilterType, String pFilterValue, String pMaximumNumberValue){

        // SOAP
        String Soap_Action = "QueryByElements";
        String url = cGlobalData.GET_OUTBOUND_DELIVERY;

        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cOutboundOrder> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_OutboundOrder(pFilterType,pFilterValue,pMaximumNumberValue));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);
            vResponse = (Vector)envelope.getResponse();
            lsData = getOutboundOrderData(vResponse);

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_OutboundOrder(String FilterType, String FilterValue, String  MaximumNumberValue){

        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "OutboundDeliveryQueryByElementsSimpleByRequest_sync");
        SoapObject soN1 =  new SoapObject("", "OutboundDeliverySimpleSelectionBy");

        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundaryID", FilterValue);

        soN1.addSoapObject(soN2);
        oSoapObjectResult.addSoapObject(soN1);

        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }

        return oSoapObjectResult;
    }

    private ArrayList<cOutboundOrder> getOutboundOrderData (Vector  pVector){

        cOutboundOrder  oOutboundOrder = new cOutboundOrder();
        ArrayList<cOutboundOrder>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {
            oOutboundOrder =  new cOutboundOrder();
            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){
                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetOutboundOrderProperty(oOutboundOrder,  oPropertyInfo);
            }

            if (!oOutboundOrder.ID.equals("")) {
                lsData.add(oOutboundOrder);
            }
        }

        return lsData;
    }

    private void SetOutboundOrderProperty(cOutboundOrder oObj, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "ID": oObj.ID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "UUID": oObj.UUID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "Status":
                SoapObject  so = (SoapObject)oPropertyInfo.getValue();
                oObj.DeliveryProcessingStatusCode = ((SoapPrimitive)so.getProperty("DeliveryProcessingStatusCode")).getValue().toString().trim();
                break;

            default:break;
        }
    }





    public ArrayList<cPurchaseOrder> GetPurchaseOrderServiceData(String  pFilterType, String pFilterValue, String pMaximumNumberValue){

        // SOAP
        String Soap_Action = "QueryByElements";
        //String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my346674.sapbydesign.com";
        // String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_purchaseorder?sap-vhost=my343751.sapbydesign.com";  //  PROD
        String url = cGlobalData.GET_PURCHASE_ORDER;

                HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cPurchaseOrder> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_PurchaseOrder(pFilterType,pFilterValue,pMaximumNumberValue));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);
            vResponse = (Vector)envelope.getResponse();
            lsData = getPurchaseOrderData(vResponse);

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_PurchaseOrder(String FilterType, String FilterValue, String  MaximumNumberValue){

        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "PurchaseOrderQueryByElementsSimpleByRequest_sync");
        SoapObject soN1 =  new SoapObject("", "PurchaseOrderSimpleSelectionBy");

        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundaryID", FilterValue);

        soN1.addSoapObject(soN2);
        oSoapObjectResult.addSoapObject(soN1);

        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }

        return oSoapObjectResult;
    }

    private ArrayList<cPurchaseOrder> getPurchaseOrderData (Vector  pVector){

        cPurchaseOrder  cPurchaseOrder = new cPurchaseOrder();
        ArrayList<cPurchaseOrder>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {
            cPurchaseOrder =  new cPurchaseOrder();
            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){
                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetPurchaseOrderProperty(cPurchaseOrder,  oPropertyInfo);
            }

            lsData.add(cPurchaseOrder);
        }

        return lsData;
    }

    private void SetPurchaseOrderProperty(cPurchaseOrder oObj, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "ID": oObj.ID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "Status":
                oObj.TaskStatusId = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("PurchaseOrderLifeCycleStatusCode")).getValue().toString().trim();
                oObj.TaskStatusName = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("PurchaseOrderLifeCycleStatusName")).getValue().toString().trim();
                break;

            default:break;
        }
    }






    public ArrayList<cLogisticsArea> GetLogisticAreaServiceData(String  pFilterType, String pFilterValue, String pMaximumNumberValue){

        // SOAP
        String Soap_Action = "QueryByElements";
      //  String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_logisticarea?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/yypf5fcnxy_logisticarea?sap-vhost=my343751.sapbydesign.com";   // prod
        String url =  cGlobalData.GET_LOGISTIC_AREA;



        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cLogisticsArea> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_LogisticArea(pFilterType,pFilterValue,pMaximumNumberValue));

            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);
            vResponse = (Vector)envelope.getResponse();
            lsData = getLogisticAreaData(vResponse);

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_LogisticArea(String FilterType, String FilterValue, String  MaximumNumberValue){
        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "LogisticsAreaQueryByElementsSimpleByRequest_sync");
        SoapObject soN1 =  new SoapObject("", "LogisticsAreaSimpleSelectionBy");

        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundaryID", FilterValue);

        soN1.addSoapObject(soN2);
        oSoapObjectResult.addSoapObject(soN1);

        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }

       return oSoapObjectResult;
    }

    private ArrayList<cLogisticsArea> getLogisticAreaData (Vector  pVector){

        cLogisticsArea  ocLogisticsArea = new cLogisticsArea();
        ArrayList<cLogisticsArea>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {
            ocLogisticsArea =  new cLogisticsArea();
            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){
                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetLogisticAreaProperty(ocLogisticsArea,  oPropertyInfo);
            }

            lsData.add(ocLogisticsArea);
        }

        return lsData;
    }

    private void SetLogisticAreaProperty(cLogisticsArea oLogisticsArea, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "ID": oLogisticsArea.ID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "UUID": oLogisticsArea.UUID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "InventoryManagedLocationID": oLogisticsArea.InventoryManagedLocationID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "InventoryManagedLocationUUID": oLogisticsArea.InventoryManagedLocationUUID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "SiteID": oLogisticsArea.SiteID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "SiteUUID": oLogisticsArea.SiteUUID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "SystemAdministrativeData":
                oLogisticsArea.CreationDateTime  =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("CreationDateTime")).getValue().toString().trim();
                oLogisticsArea.CreationIdentityUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("CreationIdentityUUID")).getValue().toString().trim();
                oLogisticsArea.LastChangeDateTime  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("LastChangeDateTime")).getValue().toString().trim();
                oLogisticsArea.LastChangeIdentityUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("LastChangeIdentityUUID")).getValue().toString().trim();
                break;
            case  "TypeCode": oLogisticsArea.TypeCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "TypeName": oLogisticsArea.TypeName = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "Status":
                oLogisticsArea.LifeCycleStatusCode  =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("LifeCycleStatusCode")).getValue().toString().trim();
                oLogisticsArea.LifeCycleStatusName  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("LifeCycleStatusName")).getValue().toString().trim();
                break;

            case  "Description":
                oLogisticsArea.Description  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Description")).getValue().toString().trim();
                oLogisticsArea.DescriptionlanguageCode =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Description")).getAttribute("languageCode").toString().trim();
                break;

            default:break;
        }
    }



    public ArrayList<cCustomer> GetCustomersServiceData(String FilterType, String FilterValue, String  MaximumNumberValue){

        // SOAP
        String Soap_Action = "FindByElements";
       // String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerin1?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querycustomerin1?sap-vhost=my343751.sapbydesign.com";  // PROD
        String url = cGlobalData.GET_CUSTOMERS;

        HttpTransportSE transporte;

        SoapObject soCustomerByElementsQuery_sync;
        SoapObject soN1;
        SoapObject soN2;

        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cCustomer> lsData = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization", AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_Customer(FilterType,FilterValue,MaximumNumberValue));


            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);
            vResponse = (Vector)envelope.getResponse();
            lsData = getCustomerData(vResponse);

        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsData;
    }

    private  SoapObject  getBodySoapObjectByFilterType_Customer(String FilterType, String FilterValue, String  MaximumNumberValue){
        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "CustomerByElementsQuery_sync");
        SoapObject soN1 =  new SoapObject("", "SelectionByInternalID");

        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundaryInternalID", FilterValue);

        soN1.addSoapObject(soN2);
        oSoapObjectResult.addSoapObject(soN1);

        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }

        return oSoapObjectResult;
    }

    private ArrayList<cCustomer> getCustomerData (Vector  pVector){

        cCustomer  oCustomer = new cCustomer();
        ArrayList<cCustomer>  lsData = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {
            oCustomer =  new cCustomer();
            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){
                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetCustomerProperty(oCustomer,  oPropertyInfo);
            }

            lsData.add(oCustomer);
        }

        return lsData;
    }

    private void SetCustomerProperty(cCustomer oCustomer, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "InternalID": oCustomer.InternalID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "CategoryCode": oCustomer.CategoryCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "LifeCycleStatusCode": oCustomer.LifeCycleStatusCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;

            case  "Person":
                oCustomer.GivenName  =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("GivenName")).getValue().toString().trim();
                oCustomer.FamilyName  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getPropertySafely("FamilyName")).getValue().toString().trim();
                break;

            default:break;
        }
    }



    public ArrayList<cMaterial> GetMaterialsServiceData(String FilterType, String FilterValue, String  MaximumNumberValue){

        // SOAP
        String Soap_Action = "FindByElements";
        //String url = "https://my346674.sapbydesign.com/sap/bc/srt/scs/sap/querymaterialin?sap-vhost=my346674.sapbydesign.com";
        //  String url = "https://my343751.sapbydesign.com/sap/bc/srt/scs/sap/querymaterialin?sap-vhost=my343751.sapbydesign.com";  // PROD
        String url = cGlobalData.GET_MATERIALS;


        HttpTransportSE transporte;
        SoapSerializationEnvelope envelope;

        // Data
        String  ErrorMsg = "";
        Vector vResponse = new  Vector();
        ArrayList<cMaterial> lsMaterials = new  ArrayList<>();

        try {

            List<HeaderProperty> headerPropertieList = new ArrayList<HeaderProperty>();
            headerPropertieList.add(new HeaderProperty("Authorization",AUTHORIZATION_SOAP_VALUE));

            envelope = new SAPSerializationEnvelope(110,NAME_SPACE_SOAP);
            envelope.dotNet = false;
            envelope.setOutputSoapObject(getBodySoapObjectByFilterType_Material(FilterType,FilterValue,MaximumNumberValue));


            transporte = new HttpTransportSE(url,CONNECT_TIMEOUT_SOAP);
            transporte.debug = true;

            transporte.setReadTimeout(READ_TIMEOUT_SOAP);

            transporte.call(Soap_Action, envelope,headerPropertieList);

            vResponse = (Vector)envelope.getResponse();

            lsMaterials = getMaterialData(vResponse);


        } catch (Exception e) {

            ErrorMsg = e.getMessage();
        }

        return lsMaterials;
    }

    private  SoapObject  getBodySoapObjectByFilterType_Material(String FilterType, String FilterValue, String  MaximumNumberValue){
        SoapObject  oSoapObjectResult = new SoapObject(NAME_SPACE_SOAP, "MaterialByElementsQuery_sync");
        SoapObject soN1 =  new SoapObject("", "MaterialSelectionByElements");

        SoapObject soN2 = new SoapObject("", FilterType);
        soN2.addProperty("InclusionExclusionCode", "I");
        soN2.addProperty("IntervalBoundaryTypeCode", "1");
        soN2.addProperty("LowerBoundaryInternalID", FilterValue);

        soN1.addSoapObject(soN2);
        oSoapObjectResult.addSoapObject(soN1);

        if ( MaximumNumberValue != null  &  MaximumNumberValue.trim() != ""  )
        {
            soN1 = new SoapObject("", "ProcessingConditions");
            soN1.addProperty("QueryHitsMaximumNumberValue", MaximumNumberValue);
            soN1.addProperty("QueryHitsUnlimitedIndicator", "false");

            oSoapObjectResult.addSoapObject(soN1);
        }

        return oSoapObjectResult;
    }

    private ArrayList<cMaterial> getMaterialData (Vector  pVector){

        cMaterial  oMaterial = new cMaterial();
        ArrayList<cMaterial>  lsMaterials = new  ArrayList<>();
        SoapObject   oSoap = new  SoapObject();
        PropertyInfo oPropertyInfo =  new PropertyInfo();

        for (int i = 0; i < pVector.size() ; i++) {
            oMaterial =  new cMaterial();
            oSoap = (SoapObject) pVector.get(i);

            for( int j= 0; j < oSoap.getPropertyCount(); j++ ){
                oPropertyInfo = oSoap.getPropertyInfo(j);
                SetMatrialProperty(oMaterial,  oPropertyInfo);
            }

            lsMaterials.add(oMaterial);
        }

        return lsMaterials;
    }

    private void SetMatrialProperty(cMaterial oMaterial, PropertyInfo oPropertyInfo) {
        switch (oPropertyInfo.getName()){

            case  "ChangeStateID": oMaterial.ChangeStateID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "InternalID": oMaterial.InternalID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "UUID": oMaterial.UUID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "SystemAdministrativeData":
                oMaterial.CreationDateTime  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CreationDateTime")).getValue().toString().trim();
                oMaterial.CreationIdentityUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CreationIdentityUUID")).getValue().toString().trim();
                oMaterial.LastChangeDateTime  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LastChangeDateTime")).getValue().toString().trim();
                oMaterial.LastChangeIdentityUUID  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LastChangeIdentityUUID")).getValue().toString().trim();
                break;

            case  "ProductCategoryID": oMaterial.ProductCategoryID = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "BaseMeasureUnitCode": oMaterial.BaseMeasureUnitCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "InventoryValuationMeasureUnitCode": oMaterial.InventoryValuationMeasureUnitCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "PlanningMeasureUnitCode": oMaterial.PlanningMeasureUnitCode = ((SoapPrimitive)oPropertyInfo.getValue()).getValue().toString().trim();
                break;
            case  "Description":
                oMaterial.Description  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Description")).getValue().toString().trim();
                oMaterial.DescriptionlanguageCode =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Description")).getAttribute("languageCode").toString().trim();
                break;
            case  "QuantityConversion":
                oMaterial.Quantity  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Quantity")).getValue().toString().trim();
                oMaterial.QuantityUnitCode =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("Quantity")).getAttribute("unitCode").toString().trim();
                oMaterial.CorrespondingQuantity  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CorrespondingQuantity")).getValue().toString().trim();
                oMaterial.CorrespondingQuantityUnitCode =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CorrespondingQuantity")).getAttribute("unitCode").toString().trim();
                break;
            case  "Purchasing":
                oMaterial.LifeCycleStatusCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oMaterial.PurchasingMeasureUnitCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("PurchasingMeasureUnitCode")).getValue().toString().trim();
                break;

/*            case  "Planning":
                SoapObject soTemp = ((SoapObject)oPropertyInfo.getValue());
                for( int j= 0; j < soTemp.getPropertyCount(); j++ ){
                    cSupplyPlanning oSupplyPlanning =  new cSupplyPlanning();
                    PropertyInfo oPropertyInfoTemp = soTemp.getPropertyInfo(j);

                    oSupplyPlanning.SupplyPlanningAreaID  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("SupplyPlanningAreaID")).getValue().toString().trim();
                    oSupplyPlanning.LifeCycleStatusCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                    oSupplyPlanning.ProcurementTypeCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("ProcurementTypeCode")).getValue().toString().trim();
                    oSupplyPlanning.PlanningProcedureCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("PlanningProcedureCode")).getValue().toString().trim();
                    oSupplyPlanning.LotSizeProcedureCode  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("LotSizeProcedureCode")).getValue().toString().trim();
                    oSupplyPlanning.GoodsReceiptProcessingDuration  = ((SoapPrimitive)((SoapObject)oPropertyInfoTemp.getValue()).getProperty("GoodsReceiptProcessingDuration")).getValue().toString().trim();
                    oMaterial.getPlanning().add(oSupplyPlanning);
                }
                break;*/


/*            case  "AvailabilityConfirmation":

                cAvailabilityConfirmation oAvailabilityConfirmation = new cAvailabilityConfirmation();

                oAvailabilityConfirmation.PlanningAreaID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("PlanningAreaID")).getValue().toString().trim();
                oAvailabilityConfirmation.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oAvailabilityConfirmation.AvailabilityCheckScopeCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("AvailabilityCheckScopeCode")).getValue().toString().trim();
                oAvailabilityConfirmation.GoodsIssueProcessingDuration = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("GoodsIssueProcessingDuration")).getValue().toString().trim();

                oMaterial.AvailabilityConfirmation.add(oAvailabilityConfirmation);

                break;*/


/*            case  "Sales":

                cSales  oSales = new cSales();

                oSales.SalesOrganisationID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("SalesOrganisationID")).getValue().toString().trim();
                oSales.DistributionChannelCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("DistributionChannelCode")).getValue().toString().trim();
                oSales.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oSales.SalesMeasureUnitCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("SalesMeasureUnitCode")).getValue().toString().trim();
                oSales.ItemGroupCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("ItemGroupCode")).getValue().toString().trim();

                oMaterial.Sales.add(oSales);

                break;*/

/*            case  "Logistics":

                cLogistics oLogistics = new cLogistics();

                oLogistics.SiteID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("SiteID")).getValue().toString().trim();
                oLogistics.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oLogistics.CycleCountPlannedDuration = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CycleCountPlannedDuration")).getValue().toString().trim();

                oMaterial.Logistics.add(oLogistics);

                break;*/

/*            case  "DeviantTaxClassification":
                oMaterial.CountryCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CountryCode")).getValue().toString().trim();
                oMaterial.RegionCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("RegionCode")).getValue().toString().trim();
                oMaterial.RegionCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("RegionCode")).getAttribute("listID").toString().trim();
                oMaterial.TaxTypeCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxTypeCode")).getValue().toString().trim();
                oMaterial.TaxTypeCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxTypeCode")).getAttribute("listID").toString().trim();
                oMaterial.TaxRateTypeCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxRateTypeCode")).getValue().toString().trim();
                oMaterial.TaxRateTypeCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxRateTypeCode")).getAttribute("listID").toString().trim();
                oMaterial.TaxExemptionReasonCode  = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxExemptionReasonCode")).getValue().toString().trim();
                oMaterial.TaxExemptionReasonCodelistID =  ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("TaxExemptionReasonCode")).getAttribute("listID").toString().trim();

                break;*/

/*            case  "Valuation":

                cValuation  oValuation = new cValuation();

                oValuation.LifeCycleStatusCode = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("LifeCycleStatusCode")).getValue().toString().trim();
                oValuation.CompanyID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("CompanyID")).getValue().toString().trim();
                oValuation.BusinessResidenceID = ((SoapPrimitive)((SoapObject)oPropertyInfo.getValue()).getProperty("BusinessResidenceID")).getValue().toString().trim();

                oMaterial.Valuation.add(oValuation);

                break;*/

                default:break;
        }
    }



    public static class SAPSerializationEnvelope extends SoapSerializationEnvelope {

        public String namespace;
        public SAPSerializationEnvelope(int version, String namespace) {
            super(version);
            this.namespace= namespace;
        }

        @Override
        public void write(XmlSerializer writer) throws IOException {

            writer.setPrefix("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
            writer.setPrefix("glob", namespace);
            writer.setPrefix("soapenv", env);

            writer.startTag(env, "Envelope");
            writer.startTag(env, "Header");
            writeHeader(writer);
            writer.endTag(env, "Header");
            writer.startTag(env, "Body");
            writeBody(writer);
            writer.endTag(env, "Body");
            writer.endTag(env, "Envelope");

        }

        @Override
        public Object getResponse() throws SoapFault {
            return super.getResponse();
        }
    }


    public  static  class  CustomerFilterType {
        public static final String SelectionByInternalID = "SelectionByInternalID";
    }

    public  static  class  LogisticAreaFilterType {
        public static final String SelectionByID = "SelectionByID";
    }

    public  static  class  PurchaseOrderFilterType {
        public static final String SelectionByID = "SelectionByID";
        public static final String SelectionByItemStatusPurchaseOrderItemLifeCycleStatusCode = "SelectionByItemStatusPurchaseOrderItemLifeCycleStatusCode";
    }

    public  static  class  PurchaseItemFilterType {
        public static final String ID = "ID";

    }

    public  static  class  OutBoundDeliveryFilterType {
        public static final String SelectionByID = "SelectionByID";
        public static final String UUID = "UUID";

    }



    public  static  class  MaterialFilterType {
        public static final String SelectionByInternalID = "SelectionByInternalID";
    }

    public  static  class  StockFilterType {
        public static final String CMATERIAL_UUID = "CMATERIAL_UUID";
        public static final String CLOG_AREA_UUID = "CLOG_AREA_UUID";
        public static final String CSITE_UUID  = "CSITE_UUID";
    }

    public  static  class  GetTaskkFilterType {
        public static final String SelectionBySiteLogisticsTaskID = "SelectionBySiteLogisticsTaskID";
        public static final String SelectionBySiteID = "SelectionBySiteID";
    }
}

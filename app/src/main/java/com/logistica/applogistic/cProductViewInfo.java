package com.logistica.applogistic;

public class cProductViewInfo {

    public   cProductViewInfo (){

        ID = 0;
        ProductoSAPId = "";
        Nombre = "";
        Descripcion = "";
        CodigoBarra = "";
        Estado = "";
        Usuario = "";
        NombreShort = "";
        Qty = "0";
        Caja = "1";
        LogisticAreaId = "";
        SedeId = "";
        Activo = "";
        Confirmed = false;
    }

    public  int ID;
    public  String ProductoSAPId;
    public  String Nombre;
    public  String NombreShort;
    public  String Descripcion;
    public  String CodigoBarra;
    public  String Estado;
    public  String Usuario;
    public  String Qty;
    public  String Caja;
    public  String LogisticAreaId;
    public  String SedeId;
    public  String Activo;
    public  Boolean Confirmed;
}

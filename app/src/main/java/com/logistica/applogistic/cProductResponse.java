package com.logistica.applogistic;

public class cProductResponse {

    public cProductResponse(){

        ProductId = "";
        ResponseId = "";
        CodigoBarra = "";
        Msg = "";
        Assigned = false;
        Nombre = "";
        Descripcion = "";
        Usuario = "";
        mensaje = "";
        Estado = "";
    }

    public  String ResponseId;
    public  String ProductId;
    public  String CodigoBarra;
    public  String Msg;
    public  Boolean Assigned;
    public String  Nombre;
    public String  Descripcion;
    public String  Estado;
    public String  Usuario;
    public String  mensaje;

    public  String Error;

}

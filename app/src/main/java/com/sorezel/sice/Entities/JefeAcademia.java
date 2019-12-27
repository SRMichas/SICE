package com.sorezel.sice.Entities;

import java.io.Serializable;

public class JefeAcademia implements Serializable {

    private int ID;
    private String Nombre,ApePat,ApeMat, Password;

    public JefeAcademia() {
    }

    public JefeAcademia(int ID, String nombre, String apePat, String apeMat, String pass) {
        this.ID = ID;
        Nombre = nombre;
        ApePat = apePat;
        ApeMat = apeMat;
        Password = pass;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApePat() {
        return ApePat;
    }

    public void setApePat(String apePat) {
        ApePat = apePat;
    }

    public String getApeMat() {
        return ApeMat;
    }

    public void setApeMat(String apeMat) {
        ApeMat = apeMat;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String nombreCompleto(){
        String output = "";
        if( ApeMat != null)
            output = ApePat+" "+ApeMat+" "+Nombre;
        else
            output = ApePat+" "+Nombre;
        return output;
    }
}

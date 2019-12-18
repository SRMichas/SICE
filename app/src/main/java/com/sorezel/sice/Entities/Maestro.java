package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Maestro implements Serializable {

    private int ID;
    private String Nombre,ApePat,ApeMat,Contraseña;

    public Maestro() {
    }

    public Maestro(int ID, String nombre, String apePat, String apeMat, String contraseña) {
        this.ID = ID;
        Nombre = nombre;
        ApePat = apePat;
        ApeMat = apeMat;
        Contraseña = contraseña;
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

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
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

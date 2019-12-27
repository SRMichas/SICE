package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Instituto implements Serializable {

    private int ID;
    private String Nombre,Correo;
    private char[] Telefono;

    public Instituto() {
    }

    public Instituto(int ID, String nombre, String correo, char[] telefono) {
        this.ID = ID;
        Nombre = nombre;
        Correo = correo;
        Telefono = telefono;
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

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public char[] getTelefono() {
        return Telefono;
    }

    public void setTelefono(char[] telefono) {
        Telefono = telefono;
    }
}


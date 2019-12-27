package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Materia implements Serializable {

    private int ID;
    private String Nombre;
    private short Creditos,Calificacion;

    public Materia() {
    }

    public Materia(int ID, String nombre, short creditos, short calificacion) {
        this.ID = ID;
        Nombre = nombre;
        Creditos = creditos;
        Calificacion = calificacion;
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

    public short getCreditos() {
        return Creditos;
    }

    public void setCreditos(short creditos) {
        Creditos = creditos;
    }

    public short getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(short calificacion) {
        Calificacion = calificacion;
    }
}

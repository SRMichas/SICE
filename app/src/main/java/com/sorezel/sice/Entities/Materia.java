package com.sorezel.sice.Entities;

public class Materia {

    int ID;
    String Nombre;
    short Creditos;

    public Materia() {
    }

    public Materia(int ID, String nombre, short creditos) {
        this.ID = ID;
        Nombre = nombre;
        Creditos = creditos;
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
}

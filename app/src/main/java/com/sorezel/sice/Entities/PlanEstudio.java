package com.sorezel.sice.Entities;

public class PlanEstudio {

    short ID;
    String nombre;

    public PlanEstudio() {
    }

    public PlanEstudio(short ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

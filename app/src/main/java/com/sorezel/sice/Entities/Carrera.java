package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Carrera implements Serializable {

    int ID;
    String Nombre;
    Coordinador coord;

    public Carrera() { }

    public Carrera(int ID, String nombre, Coordinador coord) {
        this.ID = ID;
        Nombre = nombre;
        this.coord = coord;
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

    public Coordinador getCoord() {
        return coord;
    }

    public void setCoord(Coordinador coord) {
        this.coord = coord;
    }
}

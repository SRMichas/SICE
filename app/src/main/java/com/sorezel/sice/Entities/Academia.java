package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Academia implements Serializable {

    private int ID;
    private String Nombre;
    private JefeAcademia jaca;

    public Academia() {
    }

    public Academia(int ID, String nombre, JefeAcademia jaca) {
        this.ID = ID;
        Nombre = nombre;
        this.jaca = jaca;
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

    public JefeAcademia getJaca() {
        return jaca;
    }

    public void setJaca(JefeAcademia jaca) {
        this.jaca = jaca;
    }
}

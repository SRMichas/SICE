package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Kardex implements Serializable {

    Materia mat;
    short Calificacion,Creaditos;

    public Kardex() {
    }

    public Kardex(Materia mat, short calificacion, short creaditos) {
        this.mat = mat;
        Calificacion = calificacion;
        Creaditos = creaditos;
    }

    public Materia getMat() {
        return mat;
    }

    public void setMat(Materia mat) {
        this.mat = mat;
    }

    public short getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(short calificacion) {
        Calificacion = calificacion;
    }

    public short getCreaditos() {
        return Creaditos;
    }

    public void setCreaditos(short creaditos) {
        Creaditos = creaditos;
    }
}

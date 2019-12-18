package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Solicitud implements Serializable {

    int ID;
    String fchSolicitada,fchRespuesta;
    Alumno al;
    Carrera sol;
    Status status;

    public Solicitud() {
    }

    public Solicitud(int ID, String fchSolicitada, String fchRespuesta, Alumno al,Carrera sol, Status status) {
        this.ID = ID;
        this.fchSolicitada = fchSolicitada;
        this.fchRespuesta = fchRespuesta;
        this.sol = sol;
        this.status = status;
        this.al=al;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Alumno getAl() {
        return al;
    }

    public void setAl(Alumno al) {
        this.al = al;
    }

    public String getFchSolicitada() {
        return fchSolicitada;
    }

    public void setFchSolicitada(String fchSolicitada) {
        this.fchSolicitada = fchSolicitada;
    }

    public String getFchRespuesta() {
        return fchRespuesta;
    }

    public void setFchRespuesta(String fchRespuesta) {
        this.fchRespuesta = fchRespuesta;
    }

    public Carrera getSol() {
        return sol;
    }

    public void setSol(Carrera sol) {
        this.sol = sol;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

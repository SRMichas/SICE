package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Solicitud implements Serializable {

    int ID,Matricula;
    String fchSolicitada,fchRespuesta;
    Carrera sol;
    char status;

    public Solicitud() {
    }

    public Solicitud(int ID, int matricula, String fchSolicitada, String fchRespuesta, Carrera sol, char status) {
        this.ID = ID;
        Matricula = matricula;
        this.fchSolicitada = fchSolicitada;
        this.fchRespuesta = fchRespuesta;
        this.sol = sol;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMatricula() {
        return Matricula;
    }

    public void setMatricula(int matricula) {
        Matricula = matricula;
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}

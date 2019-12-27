package com.sorezel.sice.Entities;

import java.io.Serializable;

public class Alumno implements Serializable {

    private int Matricula;
    private String Nombre,ApePat,ApeMat, Password;
    private short Semestre;
    private Carrera carr;

    public Alumno() { }

    public Alumno(int matricula, String nombre, String apePat, String apeMat, String password, short semestre, Carrera carr) {
        Matricula = matricula;
        Nombre = nombre;
        ApePat = apePat;
        ApeMat = apeMat;
        Password = password;
        Semestre = semestre;
        this.carr = carr;
    }

    public int getMatricula() {
        return Matricula;
    }

    public void setMatricula(int matricula) {
        Matricula = matricula;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public short getSemestre() {
        return Semestre;
    }

    public void setSemestre(short semestre) {
        Semestre = semestre;
    }

    public Carrera getCarr() {
        return carr;
    }

    public void setCarr(Carrera carr) {
        this.carr = carr;
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

package com.sorezel.sice;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.sorezel.sice.BD.LocalHelper;
import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.Entities.Escolares;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.JefeDepartamento;
import com.sorezel.sice.Entities.Maestro;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    LocalHelper mDBHelper;
    LocalHelper2.Consultas consultas;
    private SQLiteDatabase mDb;
    String us,ps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        LocalHelper2 helper = new LocalHelper2(this);
        helper.openDataBase();
        consultas = helper.new Consultas();


    }
    private boolean checkText(){
        EditText u=findViewById(R.id.login_user),p=findViewById(R.id.login_pass);
        us = u.getText().toString();
        ps = p.getText().toString();

        if( us.equals(""))
            return false;
        if( ps.equals(""))
            return false;

        return true;
    }
    public void singIn(View v){
        if( checkText() ) {
            Object obj = consultas.retUser(new String[]{us, ps});
            String d = "";
            if (obj instanceof Alumno) {
                d = "Alumno";
            }else if(obj instanceof Coordinador){
                d = "Coordinador";
            }else if(obj instanceof Maestro){
                d = "Maestro";
            }else if(obj instanceof JefeAcademia){
                d = "JefeAcademia";
            }else if(obj instanceof JefeDepartamento){
                d = "Jefe Departamento";
            }else if(obj instanceof Escolares){
                d = "Escolares";
            }else{
                Snackbar.make(findViewById(R.id.login_ingresar),
                        "Usuario Invalido"
                        ,Snackbar.LENGTH_SHORT).show();
                return;
            }
            Snackbar.make(findViewById(R.id.login_ingresar),
                    "Ingreso: "+d
                    ,Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(findViewById(R.id.login_ingresar),
                    "llene los campos faltantes"
                    ,Snackbar.LENGTH_SHORT).show();
        }
    }
    public void inizializateDataBase(){
        mDBHelper = new LocalHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
}

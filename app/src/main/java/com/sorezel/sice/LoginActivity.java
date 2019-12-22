package com.sorezel.sice;

import android.content.Context;
import android.content.Intent;
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
    private EditText u,p;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        u=findViewById(R.id.login_user);
        p=findViewById(R.id.login_pass);

        LocalHelper2 helper = new LocalHelper2(this);
        helper.openDataBase();
        consultas = helper.new Consultas();


    }
    private boolean checkText(){
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
            Intent intent = new Intent();
            Bundle b = new Bundle();
            if (obj instanceof Alumno) {
                d = "Alumno";
                intent.setClass(this,AlumnoActivity.class);
                intent.putExtra("user",(Alumno)obj);
            }else if(obj instanceof Coordinador){
                d = "Coordinador";
                intent.setClass(this,CoordinadorActivity.class);
                intent.putExtra("user",(Coordinador)obj);
            }else if(obj instanceof Maestro){
                d = "Maestro";
                intent.setClass(this,JefeDivisionesActivity.class);
                intent.putExtra("user",(Maestro)obj);
            }else if(obj instanceof JefeAcademia){
                d = "JefeAcademia";
                intent.setClass(this,AcademiaActivity.class);
                intent.putExtra("user",(JefeAcademia)obj);
            }else if(obj instanceof JefeDepartamento){
                d = "Jefe Departamento";
                intent.setClass(this,JefeDivisionesActivity.class);
                intent.putExtra("user",(JefeDepartamento)obj);
            }else if(obj instanceof Escolares){
                d = "Escolares";
                intent.setClass(this,JefeDivisionesActivity.class);
                intent.putExtra("user",(Escolares)obj);
            }else{
                Snackbar.make(findViewById(R.id.login_ingresar),
                        "Usuario Invalido"
                        ,Snackbar.LENGTH_SHORT).show();
                return;
            }
            /*Snackbar.make(findViewById(R.id.login_ingresar),
                    "Ingreso: "+d
                    ,Snackbar.LENGTH_SHORT).show();*/
            startActivity(intent);
            //clearTxt();
        }else{
            Snackbar.make(findViewById(R.id.login_ingresar),
                    "llene los campos faltantes"
                    ,Snackbar.LENGTH_SHORT).show();
        }
    }
    private void clearTxt(){
        u.setText("");
        p.setText("");
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

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        clearTxt();
    }
}

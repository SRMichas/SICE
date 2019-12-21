package com.sorezel.sice;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.sorezel.sice.Adapters.AnexoAdapter;
import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.Maestro;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.Entities.Solicitud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Anexo6Activity extends AppCompatActivity{

    private LocalHelper2.Insersiones inserts;
    private LocalHelper2.Consultas selects;
    private LocalHelper2.Actualizaciones updates;
    private Alumno al;
    private Solicitud sol;
    private Object user;

    private TextView txvI1,txvN1,txvNP1,txvCP1,txvIT1,txvNP2,txvCP2,txvIT2;
    private EditText edtA,edtJA,edtJD;
    private Spinner spGen;
    private RecyclerView table;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anexovi);

        LocalHelper2 helper = new LocalHelper2(this);
        helper.openDataBase();
        inserts = helper.new Insersiones();
        selects = helper.new Consultas();
        updates = helper.new Actualizaciones();

        //al = (Alumno) getIntent().getSerializableExtra("student");
        sol = (Solicitud) getIntent().getSerializableExtra("sol");
        al = sol.getAl();
        user = getIntent().getSerializableExtra("user");

        init();
        fillData();
        fillTable();

    }

    private void init(){
        txvI1 = findViewById(R.id.txt_alum_inst6);
        txvN1 = findViewById(R.id.txt_alum_nom6);
        txvNP1 = findViewById(R.id.txt_plan_d6);
        txvCP1 = findViewById(R.id.txt_clave_d6);
        txvIT1 = findViewById(R.id.txt_inst_d6);
        txvNP2 = findViewById(R.id.txt_plan_a6);
        txvCP2 = findViewById(R.id.txt_clave_a6);
        txvIT2 = findViewById(R.id.txt_inst_a6);
        edtA = findViewById(R.id.edt_firma_analizado);
        edtJA = findViewById(R.id.edt_firma_jefe_academia);
        edtJD = findViewById(R.id.JefeDepa);
        spGen = findViewById(R.id.sp_gen);
        table = findViewById(R.id.recy_mat);
    }
    private void fillData(){
        txvI1.setText("Tecnologico de Culiacan");
        txvN1.setText(sol.getAl().nombreCompleto());
        txvNP1.setText("2015");
        txvCP1.setText("1");
        txvIT1.setText("Culiacan");
        txvNP2.setText("2015");
        txvCP2.setText("1");
        txvIT2.setText("Culiacan");

    }
    private void fillTable(){
        //analizada -> maestro
        String[] data = {""+sol.getID(),""+sol.getAl().getMatricula(),"6"};
        Map<Character,Object> map = selects.retMateriasC(data);

        AnexoAdapter adp = new AnexoAdapter(map,user);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        table.setLayoutManager(llm);
        table.setAdapter(adp);
        Maestro ma = selects.retDosId(data);
        edtA.setText(ma.nombreCompleto());
        edtA.setEnabled(false);
        if( user instanceof JefeAcademia) {
            edtJA.setText(((JefeAcademia) user).nombreCompleto());
            edtJD.setEnabled(false);
        }else {
            edtJA.setText("");
        }
        edtJD.setText("");

    }
    public void makeChangeA(View v){
        //short index = (short)spOption.getSelectedItemPosition();
        //if( index > 0){
            String[] newData = {"7",""+sol.getID(),""+sol.getAl().getMatricula(),"7"};
            updates.updateSol(newData);
            /*String[] newData2 = {""+academys.get(index).getJaca().getID(),""+sol.getID()};
            inserts.insertAsig(newData2);*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            onBackPressed();
        /*}else{
            Snackbar.make(spOption,"No ha selecciona una academia",Snackbar.LENGTH_SHORT).show();
        }*/
    }
    private void algo3(){

    }
}

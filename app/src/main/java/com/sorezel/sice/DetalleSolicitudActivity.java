package com.sorezel.sice;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Academia;
import com.sorezel.sice.Entities.Solicitud;

import java.util.ArrayList;

public class DetalleSolicitudActivity extends AppCompatActivity {

    private TextView txvN,txvI,txvNC,txvS,txvC1,txvC1C,txvC2,txvC2C,txvF,txvFch;
    private Spinner spOption;
    private Solicitud sol;
    private LocalHelper2.Actualizaciones updates;
    private LocalHelper2.Consultas selects;
    private LocalHelper2.Insersiones inserts;
    ArrayList<Academia> academys;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soli_detalle);

        LocalHelper2 helper = new LocalHelper2(this);
        helper.openDataBase();
        updates = helper.new Actualizaciones();
        selects = helper.new Consultas();
        inserts = helper.new Insersiones();

        sol = (Solicitud) getIntent().getSerializableExtra("sol");

        Toolbar tb = findViewById(R.id.detsol_tool);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        fillData();
    }

    private void init(){
        txvN = findViewById(R.id.tvAlumNom);
        txvI = findViewById(R.id.tvAlumInst);
        txvNC = findViewById(R.id.tvAlumControl);
        txvS = findViewById(R.id.tvAlumSem);
        txvC1 = findViewById(R.id.tvAlumCarr1);
        txvC1C = findViewById(R.id.tvAlumCarr1Cve);
        txvC2 = findViewById(R.id.tvAlumCarr2);
        txvC2C = findViewById(R.id.tvAlumCarr2Cve);
        txvF = findViewById(R.id.tvalumFirma);
        txvFch = findViewById(R.id.fch);
        spOption = findViewById(R.id.sp_acade);
    }
    private void fillData(){
        txvN.setText(sol.getAl().nombreCompleto());
        txvI.setText("Tecnologico de Culiacán");
        txvNC.setText(""+sol.getAl().getMatricula());
        txvS.setText(""+sol.getAl().getSemestre());
        txvC1.setText(sol.getAl().getCarr().getNombre());
        txvC1C.setText(""+sol.getAl().getCarr().getID());
        txvC2.setText(sol.getCarrera().getNombre());
        txvC2C.setText(""+sol.getCarrera().getID());
        txvF.setText(sol.getAl().nombreCompleto());
        txvFch.setText(sol.getFchSolicitada());
        fillSpinner();
    }
    private void fillSpinner(){
        ArrayList<String> data=new ArrayList<>();
        academys = selects.retAcademias();
        data.add("--Seleccione una Cadema");
        for (Academia a: academys){
            data.add(a.getNombre());
        }
        ArrayAdapter<String> spadapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                data
        );
        spOption.setAdapter(spadapter);

    }
    public void reject(View v){
        String[] newData = {"3",""+sol.getID(),""+sol.getAl().getMatricula(),"1"};
        updates.updateSol(newData);
        onBackPressed();
    }
    public void accept(View v){
        short index = (short)spOption.getSelectedItemPosition();
        if( index > 0){
            String[] newData = {"4",""+sol.getID(),""+sol.getAl().getMatricula(),"1"};
            updates.updateSol(newData);
            String[] newData2 = {""+academys.get(index).getJaca().getID(),""+sol.getID()};
            inserts.insertAsig(newData2);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            onBackPressed();
        }else{
            Snackbar.make(spOption,"No ha selecciona una academia",Snackbar.LENGTH_SHORT).show();
        }

    }
}

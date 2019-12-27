package com.sorezel.sice;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import com.sorezel.sice.BD.LocalHelper;
import com.sorezel.sice.Entities.Academia;
import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.Maestro;
import com.sorezel.sice.Entities.Solicitud;

import java.util.ArrayList;

public class DetalleSolicitudActivity extends AppCompatActivity {

    private TextView txvN,txvI,txvNC,txvS,txvC1,txvC1C,txvC2,txvC2C,txvF,txvFch;
    private Spinner spOption;
    private Button btnR;
    private Solicitud sol;
    private LocalHelper.Actualizaciones updates;
    private LocalHelper.Consultas selects;
    private LocalHelper.Insersiones inserts;
    private ArrayList<Academia> academys;
    private ArrayList<Maestro> maestros;
    private Object user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soli_detalle);

        LocalHelper helper = new LocalHelper(this);
        helper.openDataBase();
        updates = helper.new Actualizaciones();
        selects = helper.new Consultas();
        inserts = helper.new Insersiones();

        sol = (Solicitud) getIntent().getSerializableExtra("sol");
        user = getIntent().getSerializableExtra("user");

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
        btnR = findViewById(R.id.btnReSol);
        Button btnA = findViewById(R.id.btnAceSol);
    }
    private void fillData(){
        txvN.setText(sol.getAl().nombreCompleto());
        txvI.setText(R.string.institute);
        txvNC.setText(String.valueOf(sol.getAl().getMatricula()));
        txvS.setText(String.valueOf(sol.getAl().getSemestre()));
        txvC1.setText(sol.getAl().getCarr().getNombre());
        txvC1C.setText(String.valueOf(sol.getAl().getCarr().getID()));
        txvC2.setText(sol.getCarrera().getNombre());
        txvC2C.setText(String.valueOf(sol.getCarrera().getID()));
        txvF.setText(sol.getAl().nombreCompleto());
        txvFch.setText(sol.getFchSolicitada());
        if( user instanceof JefeAcademia)
            btnR.setVisibility(View.GONE);
        fillSpinner();
    }
    private void fillSpinner(){
        ArrayList<String> data=new ArrayList<>();
        if( user instanceof Coordinador){
            academys = selects.retAcademias();
            data.add("--Seleccione una Academia");
            for (Academia a: academys){
                data.add(a.getNombre());
            }
        }else{
            maestros = selects.retMaestrosPerAcade(((JefeAcademia)user).getID());
            data.add("--Seleccione un(a) Maestro(a)");
            for (Maestro a: maestros){
                data.add(a.nombreCompleto());
            }
        }

        ArrayAdapter<String> spadapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                data
        );
        spOption.setAdapter(spadapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void reject(View v){
        String[] newData = {"3",""+sol.getID(),""+sol.getAl().getMatricula(),"1"};
        updates.updateSol(newData);
        onBackPressed();
    }
    public void accept(View v){
        short index = (short)spOption.getSelectedItemPosition();
        if( index > 0){
            index--;
            if( user instanceof Coordinador){
                String[] newData = {"4",""+sol.getID(),""+sol.getAl().getMatricula(),"1"};
                updates.updateSol(newData);
                String[] newData2 = {""+academys.get(index).getJaca().getID(),""+sol.getID()};
                inserts.insertAsig(newData2);
            }else{
                JefeAcademia ja = (JefeAcademia) user;
                String[] newData = {"5",""+sol.getID(),""+sol.getAl().getMatricula(),"4"};
                updates.updateSol(newData);
                int acaid=selects.getAcadByBoss(ja.getID());
                String[] newData2 = {""+maestros.get(index).getID(),""+acaid,""+sol.getID()};
                updates.updateAsig(newData2);
            }
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

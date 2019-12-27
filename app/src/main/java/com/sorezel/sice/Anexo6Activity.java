package com.sorezel.sice;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sorezel.sice.Adapters.AnexoAdapter;
import com.sorezel.sice.BD.LocalHelper;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.Maestro;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.Entities.Solicitud;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Anexo6Activity extends AppCompatActivity{

    private LocalHelper.Insersiones inserts;
    private LocalHelper.Consultas selects;
    private LocalHelper.Actualizaciones updates;
    private Solicitud sol;
    private Object user;
    private TextView txvI1,txvN1,txvNP1,txvCP1,txvIT1,txvNP2,txvCP2,txvIT2;
    private EditText edtA,edtJA,edtJD;
    //private Spinner spGen;
    private RecyclerView table;
    private Map<Character,Object> map;
    boolean test = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anexovi);

        Toolbar tb = findViewById(R.id.anexo6_tool);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LocalHelper helper = new LocalHelper(this);
        helper.openDataBase();
        inserts = helper.new Insersiones();
        selects = helper.new Consultas();
        updates = helper.new Actualizaciones();

        //al = (Alumno) getIntent().getSerializableExtra("student");
        sol = (Solicitud) getIntent().getSerializableExtra("sol");
        Alumno al = sol.getAl();
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
        Spinner spGen = findViewById(R.id.sp_gen);
        table = findViewById(R.id.recy_mat);
    }
    private void fillData(){
        txvI1.setText(R.string.institute);
        txvN1.setText(sol.getAl().nombreCompleto());
        txvNP1.setText(R.string.plan_date_sample);
        txvCP1.setText("1");
        txvIT1.setText(R.string.institute_name_sample);
        txvNP2.setText(R.string.year_sample);
        txvCP2.setText("1");
        txvIT2.setText(R.string.institute_name_sample);

    }
    private void fillTable(){
        //analizada -> maestro
        String sta = (user instanceof JefeAcademia) ? "6" : "7";
        String[] data = {""+sol.getID(),""+sol.getAl().getMatricula(),sta};
        map = selects.retMateriasC(data);
        if (test)
            return;
        map.put('5',true);
        map.put('6',R.layout.modelo_anexo);
        AnexoAdapter adp = new AnexoAdapter(map,user);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        table.setLayoutManager(llm);
        table.setAdapter(adp);
        Maestro ma = selects.retDosId(data);
        edtA.setText(ma.nombreCompleto());
        edtA.setEnabled(false);
        edtJD.setText(R.string.jefeDepa);
        edtJD.setEnabled(false);
        if( user instanceof JefeAcademia) {
            edtJA.setText(((JefeAcademia) user).nombreCompleto());
        }else {
            String name = selects.retName(data);
            edtJA.setText(name);
            edtJA.setEnabled(false);
        }
        //edtJD.setText("");

    }
    public void makeChangeA(View v){
        //short index = (short)spOption.getSelectedItemPosition();
        //if( index > 0){

            String d,sta,psta,cdr = null;
            if( user instanceof JefeAcademia) {
                sta = "7";
                psta="6";
                JefeAcademia ja = (JefeAcademia) user;
                d = "" +ja.getID();
                String[] newData2 = {d,retFch()};
                inserts.insertConva(newData2);
                String[] nD3 = new String[5];
                ArrayList<Materia> mat=(ArrayList<Materia>) map.get('1'),
                        matCo=(ArrayList<Materia>) map.get('2');
                ArrayList<Boolean> bl=(ArrayList<Boolean>) map.get('3');;
                ArrayList<Integer> perc=(ArrayList<Integer>) map.get('4');;
                d = ""+selects.retLastConva();
                int cal;
                for (int i = 0; i < mat.size(); i++) {
                    if( bl.get(i)) {
                        cal=matCo.get(i).getCalificacion();
                        nD3[0] = d;
                        nD3[1] = ""+sol.getID();
                        nD3[2] = ""+mat.get(i).getID();
                        nD3[3] = "prueba";
                        if( cal >= 70){
                            cdr=selects.retCred(matCo.get(i).getID());
                        }
                        nD3[4] = cdr;
                        inserts.inserDetConva(nD3);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }else {
                d = "";
                sta="8";
                psta="7";
            }
            String[] newData = {sta,""+sol.getID(),""+sol.getAl().getMatricula(),psta};
            updates.updateSol(newData);
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
    private String retFch(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(currentTime);
        return formattedDate;
    }
}

package com.sorezel.sice;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sorezel.sice.Adapters.AnexoAdapter;
import com.sorezel.sice.BD.LocalHelper;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.Escolares;
import com.sorezel.sice.Entities.JefeDepartamento;
import com.sorezel.sice.Entities.Solicitud;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Anexo7Activity extends AppCompatActivity {

    private LocalHelper.Insersiones inserts;
    private LocalHelper.Consultas selects;
    private LocalHelper.Actualizaciones updates;
    //private Alumno al;
    private Solicitud sol;
    private Object user;
    private TextView txvI1,txvN1,txvNP1,txvCP1,txvIT1,txvNP2,txvCP2,txvIT2,txvTC;
    private EditText edtA;
    private RecyclerView table;
    private Map<Character,Object> map;
    boolean test = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anexovii);

        Toolbar tb = findViewById(R.id.anexo7_tool);
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
        txvI1 = findViewById(R.id.txt_alum_inst7);
        txvN1 = findViewById(R.id.txt_alum_nom7);
        txvNP1 = findViewById(R.id.txt_plan_d7);
        txvCP1 = findViewById(R.id.txt_clave_d7);
        txvIT1 = findViewById(R.id.txt_inst_d7);
        txvNP2 = findViewById(R.id.txt_plan_a7);
        txvCP2 = findViewById(R.id.txt_clave_a7);
        txvIT2 = findViewById(R.id.txt_inst_a7);
        txvTC = findViewById(R.id.txt_total_creditos);
        edtA = findViewById(R.id.firma_jefe_div);
        table = findViewById(R.id.recy_mat2);
    }
    private void fillData() {
        txvI1.setText(R.string.institute);
        txvN1.setText(sol.getAl().nombreCompleto());
        txvNP1.setText(R.string.year_sample);
        txvCP1.setText("1");
        txvIT1.setText(R.string.institute_name_sample);
        txvNP2.setText(R.string.year_sample);
        txvCP2.setText("1");
        txvIT2.setText(R.string.institute_name_sample);
    }
    private void fillTable(){
        //analizada -> maestro
        String sta = (user instanceof JefeDepartamento) ? "8" : "9";
        String[] data = {""+sol.getID(),""+sol.getAl().getMatricula(),sta};
        map = selects.retMateriasC2(data);
        if (test)
            return;
        map.put('5',false);
        map.put('6',R.layout.modelo_anexo_7);
        AnexoAdapter adp = new AnexoAdapter(map,user);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        table.setLayoutManager(llm);
        table.setAdapter(adp);

        if( user instanceof JefeDepartamento) {
            JefeDepartamento jd = (JefeDepartamento) user;
            edtA.setText(jd.nombreCompleto());

        }else {
            String name = selects.retName2(data);
            edtA.setText(name);
            edtA.setEnabled(false);
        }
        //edtJD.setText("");

    }
    public void act(View v){
        String d,sta,psta;
        String[] nD3 = null;
        String status = (user instanceof JefeDepartamento) ? "8" : "9";
        String[] data = {""+sol.getID(),""+sol.getAl().getMatricula(),status};
        if( user instanceof JefeDepartamento){
            JefeDepartamento jd = (JefeDepartamento) user;
            sta = "9";
            psta="8";
            nD3 = new String[3];
            nD3[0] = String.valueOf(jd.getID());
            nD3[1] = retFch();
            nD3[2] = String.valueOf(selects.retJefeAca2(data));
            //Log.d("hhhh",nD3.toString());
            updates.updateConva(nD3,true);
        }else{
            Escolares esc = (Escolares) user;
            d = "";
            sta = "2";
            psta="9";
            nD3 = new String[4];
            nD3[0] = String.valueOf(esc.getID());
            nD3[1] = retFch();
            nD3[2] = String.valueOf(selects.retJefeDiv(data));
            nD3[3] = String.valueOf(selects.retJefeAca2(data));
            //Log.d("HHHHHHH","algo");
            //return;
            updates.updateConva(nD3,false);
        }

        String[] newData = {sta,""+sol.getID(),""+sol.getAl().getMatricula(),psta};
        updates.updateSol(newData);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onBackPressed();

    }
    public void setTot(int num){
        txvTC.setText(String.valueOf(num));
    }
    private String retFch(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(currentTime);
        return formattedDate;
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
}

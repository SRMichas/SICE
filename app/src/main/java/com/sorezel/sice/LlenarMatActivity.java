package com.sorezel.sice;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.sorezel.sice.Adapters.ListAdapter;
import com.sorezel.sice.Adapters.ListKardexAdapter;
import com.sorezel.sice.BD.LocalHelper;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.Maestro;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.Entities.Solicitud;
import java.util.ArrayList;
import java.util.Map;

public class LlenarMatActivity extends AppCompatActivity {

    private Solicitud sol;
    private Maestro user;
    private LocalHelper.Consultas selects;
    private LocalHelper.Actualizaciones updates;
    private LocalHelper.Insersiones inserts;
    private RecyclerView rv,rc;
    private TextView txvN,txvC,txvM1C,txvM1N,txvM1CA;
    private EditText edtM2C,edtM2N,edtM2CA,edtP;
    private Spinner sp;
    private ListKardexAdapter lka;
    //private Button btnADD,btnREM,btnFIN;
    private ArrayList<Materia> materias,materiasFirst,primer;
    private ArrayList<Boolean> aceps;
    private ArrayList<Integer> porcen;
    private Materia mat,matFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llenar_anexo_vi);

        Toolbar tb = findViewById(R.id.anex_tool);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sol = (Solicitud) getIntent().getSerializableExtra("sol");
        user = (Maestro) getIntent().getSerializableExtra("user");

        LocalHelper helper = new LocalHelper(this);
        helper.openDataBase();
        selects = helper.new Consultas();
        updates = helper.new Actualizaciones();
        inserts = helper.new Insersiones();

        materias = new ArrayList<>();
        materiasFirst = new ArrayList<>();
        aceps = new ArrayList<>();
        porcen = new ArrayList<>();
        init();

        rv = findViewById(R.id.rc_mate);
        primer = selects.retMaterias(sol.getAl().getMatricula());
        ListAdapter la = new ListAdapter(primer, sol, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Materia item) {
                Toast.makeText(LlenarMatActivity.this, "Item Clicked: "+item.getNombre(), Toast.LENGTH_LONG).show();
                txvM1C.setText(String.valueOf(item.getID()));
                txvM1N.setText(String.valueOf(item.getNombre()));
                txvM1CA.setText(String.valueOf(item.getCalificacion()));
                matFirst = item;
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(la);
        Alumno al = sol.getAl();

        txvC.setText(al.getCarr().getNombre());
        txvN.setText(al.nombreCompleto());

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

    private void init(){
        txvN = findViewById(R.id.txt_nomAl);
        txvC = findViewById(R.id.txt_nomCar);
        txvM1C = findViewById(R.id.cveMat1);
        txvM1N = findViewById(R.id.nomMat1);
        txvM1CA = findViewById(R.id.calMat1);
        edtM2C = findViewById(R.id.cveMat2);
        edtM2N = findViewById(R.id.nomMat2);
        edtM2CA = findViewById(R.id.calMat2);
        edtP = findViewById(R.id.porcen);
        sp = findViewById(R.id.aceptado);
        rc = findViewById(R.id.llena_recy);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lka = new ListKardexAdapter(materias);
        rc.setLayoutManager(llm);
        rc.setAdapter(lka);
        Button btnADD,btnREM,btnFIN;
        btnADD = findViewById(R.id.agr);
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        btnREM = findViewById(R.id.borr);
        btnREM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove();
            }
        });
        btnFIN = findViewById(R.id.ter);
        btnFIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAll();
            }
        });
    }
    public void fill(Map<String,Object> map){
        Materia ma = (Materia) map.get("mat");
        Alumno al = (Alumno) map.get("al");

        txvC.setText(al.getCarr().getNombre());
        txvN.setText(al.nombreCompleto());
        txvM1C.setText(String.valueOf(ma.getID()));
        txvM1N.setText(ma.getNombre());
        txvM1CA.setText(String.valueOf(ma.getCalificacion()));
    }
    private void add(){
        short idx = (short) sp.getSelectedItemPosition();
        if( idx > 0){
            /*boolean c = (idx == 1);
            Log.d("HHHH",""+((idx == 1) ? true : false));*/
            int id=Integer.parseInt(edtM2C.getText().toString()),
                por = Integer.parseInt(edtP.getText().toString());
            String n=edtM2N.getText().toString();
            short cr=0,
                    ca=Short.parseShort(edtM2CA.getText().toString());
            if( ca >= 70 )
                cr = 5;
            mat = new Materia(id,n,cr,ca);
            materias.add(mat);
            materiasFirst.add(matFirst);
            int pos = materias.size()-1;
            lka.notifyItemInserted(pos);
            edtM2C.setText(String.valueOf((id+1)));
            edtM2CA.setText(String.valueOf((id+1)));
            aceps.add((idx == 1));
            porcen.add(por);
        }
    }
    private void remove(){
        int position = materias.size() - 1;
        if( position >= 0){
            materias.remove(position);
            materiasFirst.remove(position);
            aceps.remove(position);
            //rc.removeViewAt(position);
            rc.getAdapter().notifyItemRemoved(position);
            rc.getAdapter().notifyItemRangeChanged(position, materias.size());
            rc.getAdapter().notifyDataSetChanged();
        }

    }
    private void insertAll() {

        if( materias.size() > 0){
            String[] newD = {"6",""+sol.getID(),""+sol.getAl().getMatricula(),"5"};
            updates.updateSol(newD);
            for(int i = 0; i < materiasFirst.size(); i++){
                String sid=String.valueOf(sol.getID()),
                maid=String.valueOf(materiasFirst.get(i).getID()),
                mid=String.valueOf(user.getID()),
                ma2id=String.valueOf(materias.get(i).getID()),
                c=String.valueOf(materias.get(i).getCalificacion()),
                p=String.valueOf(porcen.get(i)),
                a=(aceps.get(i)? "1" : "0");
                String[] newD2 = {sid,maid,mid,ma2id,c,p,a};
                inserts.insertDetSol(newD2);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onBackPressed();
            }
        }else{
            Snackbar.make(rc,"No hay materias",Snackbar.LENGTH_SHORT).show();
        }
    }
}

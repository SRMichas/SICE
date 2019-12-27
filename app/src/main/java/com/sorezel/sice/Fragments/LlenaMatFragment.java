package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sorezel.sice.Adapters.ListKardexAdapter;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.R;
import java.util.ArrayList;
import java.util.Map;

public class LlenaMatFragment extends Fragment {

    private View v;
    private TextView txvN,txvC,txvM1C,txvM1N,txvM1CA;
    private EditText edtM2C,edtM2N,edtM2CA,edtP;
    private Spinner sp;
    private RecyclerView rc;
    private ListKardexAdapter lka;
    private ArrayList<Materia> materias;
    private Materia mat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_llena_mat,container,false);
        if( getArguments() != null){
            Bundle b = getArguments();
        }
        materias = new ArrayList<>();
        init();
        return v;
    }

    private void init(){
        txvN = v.findViewById(R.id.txt_nomAl);
        txvC = v.findViewById(R.id.txt_nomCar);
        txvM1C = v.findViewById(R.id.cveMat1);
        txvM1N = v.findViewById(R.id.nomMat1);
        txvM1CA = v.findViewById(R.id.calMat1);
        edtM2C = v.findViewById(R.id.cveMat2);
        edtM2N = v.findViewById(R.id.nomMat2);
        edtM2CA = v.findViewById(R.id.calMat2);
        edtP = v.findViewById(R.id.porcen);
        sp = v.findViewById(R.id.aceptado);
        rc = v.findViewById(R.id.llena_recy);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        lka = new ListKardexAdapter(materias);
        rc.setLayoutManager(llm);
        rc.setAdapter(lka);
        Button btnADD,btnREM,btnFIN;
        btnADD = v.findViewById(R.id.agr);
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        btnREM = v.findViewById(R.id.borr);
        btnREM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove();
            }
        });
        btnFIN = v.findViewById(R.id.ter);
        btnFIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAll();
            }
        });

    }
    private void fillSpinner(){

    }
    public void fill(Map<String,Object> map){
        Materia ma = (Materia) map.get("mat");
        Alumno al = (Alumno) map.get("al");

        txvC.setText(al.getCarr().getNombre());
        txvN.setText(al.nombreCompleto());
        txvM1C.setText(""+ma.getID());
        txvM1N.setText(ma.getNombre());
        txvM1CA.setText(""+ma.getCalificacion());
    }

    private void add(){
        /*int id=Integer.parseInt(edtM2C.getText().toString());
        String n=edtM2N.getText().toString();
        short cr=0,
                ca=Short.parseShort(edtM2CA.getText().toString());
        if( ca >= 70 )
            cr = 5;
        mat = new Materia(id,n,cr,ca);
        materias.add(mat);
        lka = new ListKardexAdapter(materias);
        rc.notify();*/
        int id=Integer.parseInt(edtM2C.getText().toString());
        String n=edtM2N.getText().toString();
        short cr=0,
                ca=Short.parseShort(edtM2CA.getText().toString());
        if( ca >= 70 )
            cr = 5;
        mat = new Materia(id,n,cr,ca);
        materias.add(mat);
        int pos = materias.size()-1;
        lka.notifyItemInserted(pos);
        edtM2C.setText(""+(id+1));
        edtM2CA.setText(""+(id+1));
    }
    private void remove(){
        int position = materias.size() - 1;
        if( position >= 0){
            materias.remove(position);
            rc.removeViewAt(position);
            rc.getAdapter().notifyItemRemoved(position);
            rc.getAdapter().notifyItemRangeChanged(position, materias.size());
            rc.getAdapter().notifyDataSetChanged();
        }

    }
    private void insertAll(){

    }
}

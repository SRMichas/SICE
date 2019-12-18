package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.R;

public class AlmunoStartFragment extends Fragment {

    TextView edtN,edtNC,edtS,edtC,edtI;
    View v;
    Alumno al;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_alumno_start,container,false);

        if (getArguments() != null){
            init();
            Bundle b = getArguments();
            al = (Alumno) b.getSerializable("user");
            edtN.setText(al.nombreCompleto());
            edtNC.setText(""+al.getMatricula());
            edtS.setText(""+al.getSemestre());
            edtC.setText(al.getCarr().getNombre());
            edtI.setText("Tecnologico de Culiacan");
        }

        return v;
    }
    private void init(){
        edtN = v.findViewById(R.id.alum_st_nom);
        edtNC = v.findViewById(R.id.alum_st_ncon);
        edtS = v.findViewById(R.id.alum_st_sem);
        edtC = v.findViewById(R.id.alum_st_carr);
        edtI = v.findViewById(R.id.alum_st_inst);
    }
}

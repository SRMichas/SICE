package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.R;

import java.io.Serializable;

public class WorkerStartFragment extends Fragment {

    View v;
    TextView txvN,edtNC,edtS,edtC,edtI;
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_worker_start,container,false);

        if( getArguments() != null){
            init();
            Bundle b = getArguments();
            Serializable s = b.getSerializable("user");
            if( s instanceof Coordinador){
                Coordinador coo = (Coordinador) s;
                txvN.setText(coo.nombreCompleto());
                edtNC.setText(""+coo.getID());
                edtC.setText("");
                edtI.setText("Tecnologico de Culiacan");
            }

        }

        return v;
    }

    private void init(){
        txvN = v.findViewById(R.id.work_st_nom);
        edtNC = v.findViewById(R.id.work_st_nwork);
        edtC = v.findViewById(R.id.work_st_carr);
        edtI = v.findViewById(R.id.work_st_inst);
        linearLayout = v.findViewById(R.id.work_st_lay);
    }
}
package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.Entities.Escolares;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.JefeDepartamento;
import com.sorezel.sice.Entities.Maestro;
import com.sorezel.sice.R;
import java.io.Serializable;

public class WorkerStartFragment extends Fragment {

    private View v;
    private TextView txvN,edtNC,edtC,edtI;

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
                edtNC.setText(String.valueOf(coo.getID()));
                edtC.setText("");
                edtI.setText(R.string.institute);
            }else if ( s instanceof Maestro ){
                Maestro tch = (Maestro) s;
                txvN.setText(tch.nombreCompleto());
                edtNC.setText(String.valueOf(tch.getID()));
                edtC.setText("");
                edtI.setText(R.string.institute);
            }else if( s instanceof JefeAcademia){
                JefeAcademia acadB = (JefeAcademia) s;
                txvN.setText(acadB.nombreCompleto());
                edtNC.setText(String.valueOf(acadB.getID()));
                edtC.setText("");
                edtI.setText(R.string.institute);
            }else if( s instanceof JefeDepartamento){
                JefeDepartamento depB = (JefeDepartamento) s;
                txvN.setText(depB.nombreCompleto());
                edtNC.setText(String.valueOf(depB.getID()));
                edtC.setText("");
                edtI.setText(R.string.institute);
            }else if( s instanceof Escolares){
                Escolares esc = (Escolares) s;
                txvN.setText(esc.nombreCompleto());
                edtNC.setText(String.valueOf(esc.getID()));
                edtC.setText("");
                edtI.setText(R.string.institute);
            }
        }
        return v;
    }

    private void init(){
        txvN = v.findViewById(R.id.work_st_nom);
        edtNC = v.findViewById(R.id.work_st_nwork);
        edtC = v.findViewById(R.id.work_st_carr);
        edtI = v.findViewById(R.id.work_st_inst);
        //linearLayout = v.findViewById(R.id.work_st_lay);
    }
}

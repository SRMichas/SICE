package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.Carrera;
import com.sorezel.sice.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {

    private EditText edtN,edtI,edtNC,edtS,edtC;
    private TextView txvCl;
    private Spinner spCarr;
    private View v;

    LocalHelper2.Insersiones inserts;
    LocalHelper2.Consultas selects;

    Alumno al;
    ArrayList<Carrera> carreras;
    ArrayList<String> info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_form,container,false);

        if (getArguments() != null){
            LocalHelper2 helper = new LocalHelper2(getContext());
            helper.openDataBase();
            selects = helper.new Consultas();
            inserts = helper.new Insersiones();
            init();
            Bundle b = getArguments();
            al = (Alumno) b.getSerializable("user");
            edtN.setText(al.nombreCompleto());
            edtI.setText("Tecnologico de Cualican");
            edtNC.setText(""+al.getMatricula());
            edtS.setText(""+al.getSemestre());
            edtC.setText(al.getCarr().getNombre());
        }

        return v;
    }

    private void init(){
        edtN = v.findViewById(R.id.et_est);
        edtNC = v.findViewById(R.id.et_ncontrol);
        edtS = v.findViewById(R.id.et_sem);
        edtC = v.findViewById(R.id.et_car_cur);
        edtI = v.findViewById(R.id.et_inst);
        txvCl = v.findViewById(R.id.tv_cve_CA);
        spCarr = v.findViewById(R.id.sp_car);
        populateSpinner();
        Button b = v.findViewById(R.id.btn_aceptar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertForm();
            }
        });
    }
    private void populateSpinner(){
        carreras = selects.retCarreras();
        info = new ArrayList<>();
        info.add("--Seleccione ");
        for (Carrera ca: carreras) {
            info.add(ca.getNombre());
        }
        ArrayAdapter<String> spadapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                info
        );
        spCarr.setAdapter(spadapter);
    }
    private void insertForm(){

        if( chkData() ){
            v.findViewById(R.id.btn_aceptar).setClickable(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(v.findViewById(R.id.btn_aceptar),"Se inserto!!!",Snackbar.LENGTH_SHORT).show();
                            Date currentTime = Calendar.getInstance().getTime();

                            String[] data = {""+4,currentTime.toString(),"",""+al.getMatricula(),""+carreras.get(spCarr.getSelectedItemPosition()-1).getID(),""+1};
                            inserts.insertSol(data);
                            getActivity().onBackPressed();
                        }
                    });
                }
            }).start();

        }else{
            Snackbar.make(v.findViewById(R.id.btn_aceptar),"Complete los datos faltantes",Snackbar.LENGTH_SHORT).show();
        }

    }
    private boolean chkData(){
        if( edtN.getText().toString().equals("") )
            return false;
        else if( edtN.getText().toString().equals("") )
            return false;
        else if( edtC.getText().toString().equals("") )
            return false;
        else if( edtNC.getText().toString().equals("") )
            return false;
        else if( edtI.getText().toString().equals("") )
            return false;
        else if( edtS.getText().toString().equals("") )
            return false;
        else if( spCarr.getSelectedItemPosition() == 0)
            return false;
        return true;
    }
}
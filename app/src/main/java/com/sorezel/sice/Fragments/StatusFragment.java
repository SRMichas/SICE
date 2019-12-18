package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.R;

public class StatusFragment extends Fragment {

    private View v;
    private Fragment fragment;
    private LocalHelper2.Consultas selects;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_edo_sol,container,false);
        if( getArguments() != null){
            Bundle b = getArguments();
            Solicitud sol = (Solicitud) b.getSerializable("sol");
            boolean edo = b.getBoolean("edo");
            if( edo ){
                short type = sol.getStatus().getID();
                switch (type){
                    case 2:
                        fragment = new AcceptedFragment();
                        b.putString("msg",sol.getAl().getNombre());
                        break;
                    case 3:
                        fragment = new RejectedFragment();
                        b.putString("msg",sol.getAl().getNombre());
                        break;
                    default:
                        fragment = new ProcessFragment();
                        b.putString("msg",sol.getStatus().getDesc());
                        break;
                }
            }else{

            }
            fragment.setArguments(b);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.alum_edo_container,fragment).commit();
        }
        return v;
    }
}

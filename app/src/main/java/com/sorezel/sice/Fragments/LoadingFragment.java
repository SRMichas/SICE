package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.R;

import java.util.ArrayList;

public class LoadingFragment extends Fragment {

    LocalHelper2.Consultas selects;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_load,container,false);

        if( getArguments() != null){
            final Bundle b = getArguments();
            char control = b.getChar("key");
            LocalHelper2 helper = new LocalHelper2(getActivity());
            helper.openDataBase();
            selects = helper.new Consultas();
            final Fragment fragment = null;
            final Bundle b2 = new Bundle();
            switch (control){
                case '1':
                    break;
                case '2':
                    break;
                case '3':
                    break;
                case '4': //kardex 1 pg

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    algo(fragment,b,b2);
                                }
                            });
                        }
                    }).start();
                    break;
                case '5': //kardex 2 pg
                    break;
                case '6':
                    break;
                case '7':
                    break;
                case '8':
                    break;
                case '9':
                    break;
                case '0':
                    break;
                case 'a':
                    break;
                case 'b':
                    break;
                case 'c':
                    break;
            }
            //getFragmentManager().beginTransaction().replace(R.id.alumno_container,fragment);
        }
        return v;
    }

    private void algo(Fragment fragment,Bundle b,Bundle b2){
        ArrayList<Materia> kdx = selects.retMaterias(b.getInt("uid"));
        fragment = new kdxFragment();
        b2.putSerializable("kar",kdx);
        fragment.setArguments(b2);
        ((KardexFragment)getParentFragment()).refresh(fragment,0);
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.alumno_container,fragment).commit();
    }
}

package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sorezel.sice.Adapters.ListKardexAdapter;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.R;

import java.util.ArrayList;

public class kdxFragment extends Fragment {

    private View v;
    private ArrayList<Materia> kdx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_kdx,container,false);

        if( getArguments() != null){
            Bundle b = getArguments();
            kdx = (ArrayList<Materia>) b.getSerializable("kar");
            RecyclerView rv = v.findViewById(R.id.alum_recy_kdx);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            ListKardexAdapter lka = new ListKardexAdapter(kdx);
            rv.setLayoutManager(llm);
            rv.setAdapter(lka);
        }

        return v;
    }
}

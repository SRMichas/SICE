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
import com.sorezel.sice.Adapters.ListSolAdapter;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.R;
import java.util.ArrayList;

public class WorkerListFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_sol,container,false);

        if( getArguments() != null){
            Bundle b = getArguments();
            ArrayList<Solicitud> sols = (ArrayList<Solicitud>) b.getSerializable("sols");
            Object user = b.getSerializable("user");
            char ch = b.getChar("type");
            RecyclerView list = v.findViewById(R.id.work_list_sol);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            ListSolAdapter lsa = new ListSolAdapter(sols,ch,user);
            list.setLayoutManager(llm);
            list.setAdapter(lsa);
        }
        return v;
    }
}

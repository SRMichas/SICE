package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.R;

public class MultiFragment extends Fragment{

    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_empty,container,false);
        if( getArguments() != null){
            Bundle b = getArguments();
            ImageView icon = v.findViewById(R.id.empty_fg_icon);
            TextView txv = v.findViewById(R.id.empty_fg_msg);
            icon.setImageResource(b.getInt("img"));
            txv.setText(b.getString("msg"));
        }
        return v;
    }
}

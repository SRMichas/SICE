package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sorezel.sice.R;

public class AcceptedFragment extends Fragment {

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_solicitud__aceptada, container, false);
        if (getArguments() != null) {
            Bundle b = getArguments();
            TextView txvM = v.findViewById(R.id.alum_txvgf_ace);
            txvM.setText(b.getString("msg"));
        }
        return v;
    }
}

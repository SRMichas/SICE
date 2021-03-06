package com.sorezel.sice.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.R;

import java.util.ArrayList;

public class ListKardexAdapter extends RecyclerView.Adapter<ListKardexAdapter.VH> {

    private ArrayList<Materia> lista;

    public ListKardexAdapter(ArrayList<Materia> l){
        lista = l;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_materia_3,parent,false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        Materia mat = lista.get(i);
        vh.indice = i;
        vh.txvC.setText(String.valueOf(mat.getID()));
        vh.txvN.setText(mat.getNombre());
        vh.txvCa.setText(String.valueOf(mat.getCalificacion()));

        vh.txvT.setText((mat.getCalificacion() >= 70)? R.string.kdx_subj_pass:R.string.kdx_subj_noapro);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class VH extends RecyclerView.ViewHolder{

        int indice;
        TextView txvC,txvN,txvT,txvCa;

        private VH(@NonNull View itemView) {
            super(itemView);
            txvC = itemView.findViewById(R.id.txt_cveMat);
            txvN = itemView.findViewById(R.id.txt_nomMat);
            txvT = itemView.findViewById(R.id.txt_tipoMat);
            txvCa = itemView.findViewById(R.id.txt_calMat);
        }
    }
}

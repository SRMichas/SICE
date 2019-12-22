package com.sorezel.sice.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sorezel.sice.Anexo7Activity;
import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.Entities.Escolares;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.JefeDepartamento;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.R;

import java.util.ArrayList;
import java.util.Map;

public class AnexoAdapter extends RecyclerView.Adapter<AnexoAdapter.VH> {

    Context c;
    Object user;
    ArrayList<Materia> mat,matCo;
    ArrayList<Boolean> bl;
    private ArrayList<Integer> perc;
    boolean res;
    private static int reso=0;
    private int totCre=0;

    public AnexoAdapter(Map<Character,Object> map, Object us){
        mat = (ArrayList<Materia>) map.get('1');
        matCo = (ArrayList<Materia>) map.get('2');
        bl = (ArrayList<Boolean>) map.get('3');
        perc= (ArrayList<Integer>) map.get('4');
        user = us;
        res=(boolean)map.get('5');
        reso=(int)map.get('6');
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        if (res)
            v = LayoutInflater.from(parent.getContext()).inflate(reso,parent,false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(reso,parent,false);

        c = parent.getContext();
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        Materia m1 = mat.get(i),m2=matCo.get(i);
        boolean accept = false;
        int perce =0;
        try {
             accept = bl.get(i);
             perce = perc.get(i);
        }catch (IndexOutOfBoundsException e){}


        vh.indice = i;
        if( user instanceof Coordinador){
            vh.txvNO.setText(""+(i+1));
            vh.txvN1.setText(m1.getNombre());
            vh.txvC1.setText(""+m1.getID());
            vh.txvCal.setText(""+m1.getCalificacion());
            vh.txvN2.setText(m2.getNombre());
            vh.txvC2.setText(""+m2.getID());
            vh.txvP.setText(""+perce);
            vh.txvA.setText(bl.get(i)?"Si":"No");
        }else if( user instanceof JefeAcademia){
            vh.txvNO.setText(""+(i+1));
            vh.txvN1.setText(m1.getNombre());
            vh.txvC1.setText(""+m1.getID());
            vh.txvCal.setText(""+m1.getCalificacion());
            vh.txvN2.setText(m2.getNombre());
            vh.txvC2.setText(""+m2.getID());
            vh.txvP.setText(""+perce);
            vh.txvA.setText(bl.get(i)?"Si":"No");
        }else if( user instanceof JefeDepartamento){
            vh.txvNO.setText(String.valueOf((i+1)));
            vh.txvN1.setText(m1.getNombre());
            vh.txvCal.setText(String.valueOf(m2.getCalificacion()));
            vh.txvN2.setText(m2.getNombre());
            vh.txvC2.setText(String.valueOf(m2.getID()));
            vh.txvCR.setText(String.valueOf(m2.getCreditos()));
            totCre += m2.getCreditos();
            ((Anexo7Activity)c).setTot(totCre);
        }else if( user instanceof Escolares){
            vh.txvNO.setText(String.valueOf((i+1)));
            vh.txvN1.setText(m1.getNombre());
            vh.txvCal.setText(String.valueOf(m2.getCalificacion()));
            vh.txvN2.setText(m2.getNombre());
            vh.txvC2.setText(String.valueOf(m2.getID()));
            vh.txvCR.setText(String.valueOf(m2.getCreditos()));
            totCre += m2.getCreditos();
            ((Anexo7Activity)c).setTot(totCre);
        }
    }
    public int getCredits(){
        return totCre;
    }

    @Override
    public int getItemCount() {
        return mat.size();
    }

    class VH extends RecyclerView.ViewHolder{

        int indice;
        TextView txvNO,txvN1,txvC1,txvCal,txvN2,txvC2,txvP,txvA,txvCR;

        public VH(@NonNull View itemView) {
            super(itemView);
            if( res ){
                txvNO = itemView.findViewById(R.id.mo_anx_no);
                txvN1 = itemView.findViewById(R.id.mo_anx_n1);
                txvC1 = itemView.findViewById(R.id.mo_anx_c1);
                txvCal = itemView.findViewById(R.id.mo_anx_cal);
                txvN2 = itemView.findViewById(R.id.mo_anx_n2);
                txvC2 = itemView.findViewById(R.id.mo_anx_c2);
                txvP = itemView.findViewById(R.id.mo_anx_p);
                txvA = itemView.findViewById(R.id.mo_anx_a);
            }else{
                txvNO = itemView.findViewById(R.id.mo_anx_no);
                txvN1 = itemView.findViewById(R.id.mo_anx_n1);
                txvCal = itemView.findViewById(R.id.mo_anx_cal);
                txvN2 = itemView.findViewById(R.id.mo_anx_n2);
                txvC2 = itemView.findViewById(R.id.mo_anx_c2);
                txvCR = itemView.findViewById(R.id.mo_anx_cr);
            }


        }
    }
}

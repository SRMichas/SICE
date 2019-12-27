package com.sorezel.sice.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sorezel.sice.Anexo7Activity;
import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.Entities.Escolares;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.JefeDepartamento;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.R;
import java.util.ArrayList;
import java.util.Map;

public class AnexoAdapter extends RecyclerView.Adapter<AnexoAdapter.VH> {

    private Context c;
    private Object user;
    private ArrayList<Materia> mat,matCo;
    private ArrayList<Boolean> bl;
    private ArrayList<Integer> perc;
    private boolean res;
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
        View v;
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
        }catch (IndexOutOfBoundsException e){ e.printStackTrace();}
        
        vh.indice = i;
        vh.txvNO.setText(String.valueOf(i+1));
        vh.txvN1.setText(m1.getNombre());
        vh.txvCal.setText(String.valueOf(m1.getCalificacion()));
        vh.txvN2.setText(m2.getNombre());
        vh.txvC2.setText(String.valueOf(m2.getID()));

        if( user instanceof Coordinador || user instanceof JefeAcademia){
            vh.txvC1.setText(String.valueOf(m1.getID()));
            vh.txvP.setText(String.valueOf(perce));
            vh.txvA.setText(accept ?"Si":"No");
        }else if( user instanceof JefeDepartamento || user instanceof Escolares){
            vh.txvCR.setText(String.valueOf(m2.getCreditos()));
            totCre += m2.getCreditos();
            ((Anexo7Activity)c).setTot(totCre);
        }
    }
    /*public int getCredits(){
        return totCre;
    }*/

    @Override
    public int getItemCount() {
        return mat.size();
    }

    class VH extends RecyclerView.ViewHolder{

        int indice;
        TextView txvNO,txvN1,txvC1,txvCal,txvN2,txvC2,txvP,txvA,txvCR;

        private VH(@NonNull View itemView) {
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

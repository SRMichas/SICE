package com.sorezel.sice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sorezel.sice.Anexo6Activity;
import com.sorezel.sice.Anexo7Activity;
import com.sorezel.sice.DetalleSolicitudActivity;
import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.Entities.Escolares;
import com.sorezel.sice.Entities.JefeAcademia;
import com.sorezel.sice.Entities.JefeDepartamento;
import com.sorezel.sice.Entities.Maestro;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.LlenarMatActivity;
import com.sorezel.sice.R;
import java.util.ArrayList;

public class ListSolAdapter extends RecyclerView.Adapter<ListSolAdapter.VH> {

    private ArrayList<Solicitud> lista;
    private Context c;
    private char type;
    private Object user;

    public ListSolAdapter(ArrayList<Solicitud> l,char t,Object us){
        lista = l;
        type = t;
        user = us;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_solicitud,parent,false);
        c = parent.getContext();
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        Solicitud sol = lista.get(i);
        vh.indice = i;

        vh.txvC.setText("Ing. en "+sol.getAl().getCarr().getNombre());
        vh.txvN.setText(sol.getAl().nombreCompleto());
        vh.txvNC.setText(String.valueOf(sol.getAl().getMatricula()));

        if( user instanceof Coordinador){
            if( type == '1')
                vh.txvF.setText(sol.getFchSolicitada());
            else
                vh.txvF.setText(sol.getFchRespuesta());
        }else
            vh.txvF.setText(sol.getFchRespuesta());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        int indice;
        TextView txvNC,txvN,txvF,txvC;
        ImageView img;

        private VH(@NonNull View itemView) {
            super(itemView);
            txvN = itemView.findViewById(R.id.nomAlum);
            txvNC = itemView.findViewById(R.id.nconAlum);
            txvF = itemView.findViewById(R.id.fecha);
            txvC = itemView.findViewById(R.id.carrAlum);
            img = itemView.findViewById(R.id.sol_icon);
            img.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            if( user instanceof Coordinador){
                if( type == '1'){
                    intent.setClass(c, DetalleSolicitudActivity.class);
                    intent.putExtra("sol",lista.get(indice));
                    intent.putExtra("user",(Coordinador)user);
                }else{
                    intent.setClass(c, Anexo6Activity.class);
                    intent.putExtra("sol",lista.get(indice));
                    intent.putExtra("user",(Coordinador)user);
                    //intent.putExtra("");
                }
            }else if( user instanceof JefeAcademia){
                if( type == '1'){
                    intent.setClass(c,DetalleSolicitudActivity.class);
                    intent.putExtra("sol",lista.get(indice));
                    intent.putExtra("user",(JefeAcademia)user);
                }else{
                    intent.setClass(c,Anexo6Activity.class);
                    intent.putExtra("sol",lista.get(indice));
                    intent.putExtra("user",(JefeAcademia)user);
                }
            }else if( user instanceof JefeDepartamento){
                intent.setClass(c, Anexo7Activity.class);
                intent.putExtra("sol",lista.get(indice));
                intent.putExtra("user",(JefeDepartamento)user);
            }else if( user instanceof Escolares){
                intent.setClass(c, Anexo7Activity.class);
                intent.putExtra("sol",lista.get(indice));
                intent.putExtra("user",(Escolares)user);
            }else if( user instanceof Maestro){
                intent.setClass(c, LlenarMatActivity.class);
                intent.putExtra("sol",lista.get(indice));
                intent.putExtra("user",(Maestro)user);
            }
            c.startActivity(intent);
        }
    }
}

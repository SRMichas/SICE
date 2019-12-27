
package com.sorezel.sice.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.R;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(Materia item);
    }

    private ArrayList<Materia> lista;
    //private Context c;
    //private LlenaMatFragment fragment;

    private final OnItemClickListener listener;

    public ListAdapter(ArrayList<Materia> l,Solicitud s, OnItemClickListener listener){
        lista = l;
        Solicitud sol = s;
        this.listener = listener;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_materia_2,parent,false);
        //c = parent.getContext();
        //fragment = (LlenaMatFragment) getFragment(R.id.fr_maestro);
        return new VH(v);
    }

    /*private Fragment getFragment(int id){
        return ((LlenarMatActivity) c).getSupportFragmentManager().findFragmentById(id);
    }*/

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        Materia mat = lista.get(i);
        vh.indice = i;
        vh.txvC.setText(String.valueOf(mat.getID()));
        vh.txvN.setText(mat.getNombre());
        vh.bind(mat,listener);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class VH extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        int indice;
        TextView txvC,txvN;

        private VH(@NonNull View itemView) {
            super(itemView);
            txvC = itemView.findViewById(R.id.cve_mat);
            txvN = itemView.findViewById(R.id.nmb_mat);
            //itemView.findViewById(R.id.cont_mat).setOnClickListener(this);
        }

        private void bind(final Materia item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
        /*@Override
        public void onClick(View view) {
            Map<String,Object> mapa = new HashMap<>();
            mapa.put("mat",lista.get(indice));
            mapa.put("al",sol.getAl());
            //fragment.fill(mapa);

        }*/


    }
}
//ListAdapter
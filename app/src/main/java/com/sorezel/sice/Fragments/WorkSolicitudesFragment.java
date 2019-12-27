package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.sorezel.sice.Adapters.WorkerAdapter;
import com.sorezel.sice.R;

public class WorkSolicitudesFragment extends Fragment {

    //private View v;
    private int uid;
    //private TabLayout tbl;
    private ViewPager vp;
    private WorkerAdapter am;
    private char ty1,ty2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_coord_m,container,false);

        if( getArguments() != null){
            Bundle b = getArguments();
            uid = b.getInt("uid");
            ty1 = b.getChar("swv1");
            ty2 = b.getChar("swv2");
            TabLayout tbl = v.findViewById(R.id.tablay_coord);
            vp = v.findViewById(R.id.work_pager);

            load();
            tbl.setupWithViewPager(vp);
        }

        return v;
    }

    private void load(){
        am = new WorkerAdapter(getChildFragmentManager(),0,new String[]{"Pendientes","Convalidar"});
        Bundle b = new Bundle(),b2=new Bundle();
        LoadingFragment ld1 = new LoadingFragment(),
                ld2 = new LoadingFragment();

        b.putChar("key",ty1);
        b.putInt("uid",uid);
        b.putSerializable("user",getArguments().getSerializable("user"));
        ld1.setArguments(b);

        b2.putChar("key",ty2);
        b2.putInt("uid",uid);
        b2.putSerializable("user",getArguments().getSerializable("user"));
        ld2.setArguments(b2);

        am.addFrag(ld1);
        am.addFrag(ld2);
        vp.setAdapter(am);
    }

    public void refresh(Fragment fragment, int i) {
            am.replaceFrag(fragment,i);
    }
}

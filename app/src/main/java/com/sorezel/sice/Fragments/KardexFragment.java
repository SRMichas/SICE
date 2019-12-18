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
import com.sorezel.sice.Adapters.KardexAdapter;
import com.sorezel.sice.R;

public class KardexFragment extends Fragment {

    private KardexAdapter am;
    private int uid;
    TabLayout tbl;
    ViewPager vp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kardex,container,false);

        if (getArguments() != null){
            Bundle b = getArguments();
            uid = b.getInt("uid");
            tbl = v.findViewById(R.id.alum_tab);
            vp = v.findViewById(R.id.alum_pager);

            load();
            tbl.setupWithViewPager(vp);
        }

        return v;
    }

    private void init(){

    }
    public void refresh(Fragment frag,int pos){
        am.replaceFrag(frag,pos);
    }
    public void remove(){

    }
    private void load(){
        am = new KardexAdapter(getChildFragmentManager(),0,new String[]{"Kardex 1","Kardex 2"});
        Bundle b = new Bundle();
        LoadingFragment ld1 = new LoadingFragment(),
                ld2 = new LoadingFragment();

        b.putChar("key",'4');
        b.putInt("uid",uid);
        ld1.setArguments(b);

        am.addFrag(ld1);
        am.addFrag(ld2);
        vp.setAdapter(am);
    }
}

package com.sorezel.sice.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class WorkerAdapter extends FragmentStatePagerAdapter {


    ArrayList<Fragment> pesta= new ArrayList<>();
    Context con;
    String[] names;

    public WorkerAdapter(@NonNull FragmentManager fm, int behavior, String[] n) {
        super(fm, behavior);
        names = n;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        return pesta.get(i);
    }

    @Override
    public int getCount() {
        return pesta.size();
    }
    public void addFrag(Fragment fr){
        pesta.add(fr);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void replaceFrag(Fragment fg, int i){
        pesta.remove(i);
        pesta.add(i,fg);
        notifyDataSetChanged();

    }
    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }
}

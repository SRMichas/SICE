package com.sorezel.sice.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sorezel.sice.BD.LocalHelper;
import com.sorezel.sice.Entities.Escolares;
import com.sorezel.sice.Entities.JefeDepartamento;
import com.sorezel.sice.Entities.Maestro;
import com.sorezel.sice.Entities.Materia;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.R;
import java.util.ArrayList;

public class LoadingFragment extends Fragment {

    private LocalHelper.Consultas selects;
    private Fragment fragment = null;
    private ArrayList<Solicitud> solicituds;
    private Object user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_load,container,false);

        if( getArguments() != null){
            final Bundle b = getArguments();
            char control = b.getChar("key");
            LocalHelper helper = new LocalHelper(getActivity());
            helper.openDataBase();
            selects = helper.new Consultas();

            final Bundle b2 = new Bundle();
            final Handler handler = new Handler();
            switch (control){
                case '1':
                    break;
                case '2':
                    break;
                case '3':
                    break;
                case '4': //kardex 1 pg

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    algo(fragment,b,b2);
                                }
                            });
                        }
                    }).start();
                    break;
                case '5': //kardex 2 pg
                    break;
                case '6':
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Solicitud sol = selects.retSolbyAlumn(new String[]{""+b.getInt("uid")});
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if( sol != null){
                                        fragment = new StatusFragment();
                                        b2.putSerializable("sol",sol);
                                        b2.putBoolean("edo",true);
                                    }else{
                                        //No solicitud
                                        fragment = new MultiFragment();
                                        b2.putInt("img",R.drawable.ic_accessibility);
                                        b2.putString("msg","No tienes solicitudes activas por el momento!!");
                                    }
                                    fragment.setArguments(b2);
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.alumno_container,fragment).addToBackStack("EDO").commit();
                                }
                            });
                        }
                    }).start();
                    break;
                case '7': //list of request
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            solicituds = selects.retSolsbyAlum(b.getInt("uid"),'1');
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    algo2(fragment,b,0,'1');
                                }
                            });
                        }
                    }).start();
                    break;
                case '8': //list of convalidate
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            solicituds = selects.retSolsbyAlum(b.getInt("uid"),'7');
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    /*if (solicituds != null){
                                        fragment = new WorkerListFragment();
                                        b.putSerializable("sols",solicituds);
                                    }else{
                                        //no solicitud
                                        fragment = new MultiFragment();
                                        b2.putInt("img",R.drawable.ic_mail_outline);
                                        b2.putString("msg","No hay Solictudes Pendientes");
                                    }
                                    fragment.setArguments(b);*/
                                    algo2(fragment,b,1,'2');
                                    //switchFrag(R.id.work_container,fragment,"S");
                                }
                            });
                        }
                    }).start();
                    break;
                case '9':
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            solicituds = selects.retSolsbyAlum2(b.getInt("uid"),'4');
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    algo2(fragment,b,0,'1');
                                }
                            });
                        }
                    }).start();
                    break;
                case '0':
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            solicituds = selects.retSolsbyAlum2(b.getInt("uid"),'6');
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    algo2(fragment,b,1,'2');
                                }
                            });
                        }
                    }).start();
                    break;
                case 'a'://Teacher list
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            user = b.getSerializable("user");
                            solicituds = selects.retSolsbyAlum2(b.getInt("uid"),'5');
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if( solicituds != null){
                                        fragment = new WorkerListFragment();
                                        b2.putSerializable("sols",solicituds);
                                        b2.putSerializable("user",(Maestro)user);
                                        b2.putChar("type",'1');
                                    }else{
                                        //No solicitud
                                        fragment = new MultiFragment();
                                        b2.putInt("img",R.drawable.ic_accessibility);
                                        b2.putString("msg","No tienes solicitudes activas por el momento!!");
                                    }
                                    fragment.setArguments(b2);
                                    switchFrag(R.id.work2_container,fragment,"LST");
                                }
                            });
                        }
                    }).start();
                    break;
                case 'b'://
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            user = b.getSerializable("user");
                            solicituds = selects.retSolsbyAlum2(b.getInt("uid"),'8');
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if( solicituds != null){
                                        fragment = new WorkerListFragment();
                                        b2.putSerializable("sols",solicituds);
                                        b2.putSerializable("user",(JefeDepartamento)user);
                                        b2.putChar("type",'1');
                                    }else{
                                        //No solicitud
                                        fragment = new MultiFragment();
                                        b2.putInt("img",R.drawable.ic_accessibility);
                                        b2.putString("msg","No tienes solicitudes activas por el momento!!");
                                    }
                                    fragment.setArguments(b2);
                                    switchFrag(R.id.work2_container,fragment,"LST");
                                }
                            });
                        }
                    }).start();
                    break;
                case 'c':
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            user = b.getSerializable("user");
                            solicituds = selects.retSolsbyAlum2(b.getInt("uid"),'9');
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if( solicituds != null){
                                        fragment = new WorkerListFragment();
                                        b2.putSerializable("sols",solicituds);
                                        b2.putSerializable("user",(Escolares)user);
                                        b2.putChar("type",'1');
                                    }else{
                                        //No solicitud
                                        fragment = new MultiFragment();
                                        b2.putInt("img",R.drawable.ic_accessibility);
                                        b2.putString("msg","No tienes solicitudes activas por el momento!!");
                                    }
                                    fragment.setArguments(b2);
                                    switchFrag(R.id.work2_container,fragment,"LST");
                                }
                            });
                        }
                    }).start();
                    break;
            }
            //getFragmentManager().beginTransaction().replace(R.id.alumno_container,fragment);
        }
        return v;
    }

    private void algo(Fragment fragment,Bundle b,Bundle b2){
        ArrayList<Materia> kdx = selects.retMaterias(b.getInt("uid"));
        fragment = new kdxFragment();
        b2.putSerializable("kar",kdx);
        fragment.setArguments(b2);
        ((KardexFragment)getParentFragment()).refresh(fragment,0);
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.alumno_container,fragment).commit();
    }
    private void algo2(Fragment fragment,Bundle b,int idx,char type){

        if (solicituds != null){
            fragment = new WorkerListFragment();
            b.putSerializable("sols",solicituds);
            b.putChar("type",type);
        }else{
            //no solicitud
            fragment = new MultiFragment();
            b.putInt("img",R.drawable.ic_mail_outline);
            b.putString("msg","No hay Solictudes Pendientes");
        }
        fragment.setArguments(b);


        ((WorkSolicitudesFragment)getParentFragment()).refresh(fragment,idx);
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.alumno_container,fragment).commit();
    }
    private void algo3(Fragment fragment,Bundle b){

        if (solicituds != null){
            fragment = new WorkerListFragment();
            b.putSerializable("sols",solicituds);
            b.putChar("type",'1');
        }else{
            //no solicitud
            fragment = new MultiFragment();
            b.putInt("img",R.drawable.ic_mail_outline);
            b.putString("msg","No hay Solictudes Pendientes");
        }
        fragment.setArguments(b);


        //((WorkSolicitudesFragment)getParentFragment()).refresh(fragment,idx);
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.alumno_container,fragment).commit();
    }
    private void switchFrag(int container,Fragment fragment,String msg){
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(container,fragment).addToBackStack(msg).commit();
    }

}

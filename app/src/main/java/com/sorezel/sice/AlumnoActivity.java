package com.sorezel.sice;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.sorezel.sice.BD.LocalHelper;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.Solicitud;
import com.sorezel.sice.Fragments.AlmunoStartFragment;
import com.sorezel.sice.Fragments.FormFragment;
import com.sorezel.sice.Fragments.KardexFragment;
import com.sorezel.sice.Fragments.LoadingFragment;
import com.sorezel.sice.Fragments.StatusFragment;

public class AlumnoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout DWL;
    Alumno al;
    FragmentManager fragM;
    LocalHelper.Eliminaciones deletes;
    LocalHelper.Consultas selects;
    LocalHelper.Actualizaciones updates;
    LocalHelper.Insersiones inserts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_2);

        Toolbar tb = findViewById(R.id.alumno_tool);
        setSupportActionBar(tb);
        LocalHelper helper = new LocalHelper(this);
        helper.openDataBase();
        deletes = helper.new Eliminaciones();
        selects = helper.new Consultas();
        updates = helper.new Actualizaciones();
        inserts = helper.new Insersiones();



        al = (Alumno) getIntent().getSerializableExtra("user");

        DWL = findViewById(R.id.alum_drawer);
        NavigationView NGV = findViewById(R.id.alumno_nav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,DWL,tb,R.string.open,R.string.close);
        DWL.addDrawerListener(toggle);
        toggle.syncState();
        NGV.setNavigationItemSelectedListener(this);

        fragM = getSupportFragmentManager();
        showStart();

    }

    private void showStart(){
        Bundle b = new Bundle();
        AlmunoStartFragment alsf = new AlmunoStartFragment();
        b.putSerializable("user",al);
        alsf.setArguments(b);
        fragM.beginTransaction().add(R.id.alumno_container,alsf).addToBackStack("HOME").commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(this,"Seleccionaste "+item.getTitle(), Toast.LENGTH_SHORT).show();
        //SharedPreferences sh = getSharedPreferences("Usuario",0);
        Fragment fragment;
        Bundle b = new Bundle();
        Solicitud sol;
        switch (id){
            case R.id.alum_home:
                fragM.popBackStack("HOME",0);
                break;
            case R.id.alum_form:

                sol = selects.retSolbyAlumn(new String[]{""+al.getMatricula()});

                if( sol == null ){
                    fragment = new FormFragment();
                    b.putSerializable("user",al);
                    fragment.setArguments(b);
                    fragM.beginTransaction().replace(R.id.alumno_container,fragment)
                            .addToBackStack("FORM").commit();
                }else{
                    Snackbar.make(DWL,"Ya tienes una solicitud activa, revisala",Snackbar.LENGTH_SHORT).show();
                }

                break;
            case R.id.alum_edo:
                fragment = new LoadingFragment();
                b.putChar("key",'6');
                b.putInt("uid",al.getMatricula());
                fragment.setArguments(b);
                fragM.beginTransaction().replace(R.id.alumno_container,fragment)
                        .addToBackStack("LOAD").commit();
                break;
            case R.id.alum_kardex:
                fragment = new KardexFragment();
                b.putInt("uid",al.getMatricula());
                fragment.setArguments(b);
                fragM.beginTransaction().replace(R.id.alumno_container,fragment).
                        addToBackStack("KDX").commit();
                break;
            case R.id.reset:
                reset();
                break;
            case R.id.alum_salir:
                finish();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.alum_drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void reset(){
        deletes.deleteSol(4);
    }
    @Override
    public void onBackPressed() {

        Fragment frag = fragM.findFragmentById(R.id.alumno_container);
        if( frag instanceof FormFragment){
            fragM.popBackStack("HOME",0);
        }else if( frag instanceof KardexFragment){
            fragM.popBackStack("HOME",0);
        }else if( frag instanceof StatusFragment){
            fragM.popBackStack("HOME",0);
        }else if( frag instanceof AlmunoStartFragment){
            //super.onBackPressed();
            finish();
        }

    }
}

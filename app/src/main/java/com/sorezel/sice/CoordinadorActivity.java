package com.sorezel.sice;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.sorezel.sice.BD.LocalHelper2;
import com.sorezel.sice.Entities.Alumno;
import com.sorezel.sice.Entities.Coordinador;
import com.sorezel.sice.Fragments.AlmunoStartFragment;
import com.sorezel.sice.Fragments.FormFragment;
import com.sorezel.sice.Fragments.KardexFragment;
import com.sorezel.sice.Fragments.StatusFragment;
import com.sorezel.sice.Fragments.WorkSolicitudesFragment;
import com.sorezel.sice.Fragments.WorkerStartFragment;

public class CoordinadorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout DWL;
    private NavigationView NGV;
    Coordinador coord;
    FragmentManager fragM;
    LocalHelper2.Eliminaciones deletes;
    LocalHelper2.Consultas selects;
    LocalHelper2.Actualizaciones updates;
    LocalHelper2.Insersiones inserts;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinador_2);

        Toolbar tb = findViewById(R.id.work_tool);
        setSupportActionBar(tb);
        LocalHelper2 helper = new LocalHelper2(this);
        helper.openDataBase();
        deletes = helper.new Eliminaciones();
        selects = helper.new Consultas();
        updates = helper.new Actualizaciones();
        inserts = helper.new Insersiones();



        coord = (Coordinador) getIntent().getSerializableExtra("user");

        DWL = findViewById(R.id.work_drawer);
        NGV = findViewById(R.id.work_nav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,DWL,tb,R.string.open,R.string.close);
        DWL.addDrawerListener(toggle);
        toggle.syncState();
        NGV.setNavigationItemSelectedListener(this);

        fragM = getSupportFragmentManager();
        showStart();
    }

    private void showStart(){
        Bundle b = new Bundle();
        WorkerStartFragment wsf = new WorkerStartFragment();
        b.putSerializable("user",coord);
        wsf.setArguments(b);
        fragM.beginTransaction().replace(R.id.work_container,wsf)
                .addToBackStack("HOMEW").commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toast.makeText(this,"Seleccionaste "+item.getTitle(), Toast.LENGTH_SHORT).show();
        //SharedPreferences sh = getSharedPreferences("Usuario",0);
        Fragment fragment = null;
        Bundle b = new Bundle();
        switch (id){
            case R.id.work_home:
                fragM.popBackStack("HOMEW",0);
                break;
            case R.id.work_sol:
                fragment = new WorkSolicitudesFragment();
                b.putInt("uid",coord.getID());
                b.putSerializable("user",coord);
                b.putChar("swv1",'7');
                b.putChar("swv2",'8');
                fragment.setArguments(b);
                fragM.beginTransaction().replace(R.id.work_container,fragment).
                        addToBackStack("WK").commit();
                break;
            case R.id.work_salir:
                finish();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.work_drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        Fragment frag = fragM.findFragmentById(R.id.work_container);
        if( frag instanceof WorkerStartFragment){
            finish();
        }else if( frag instanceof WorkSolicitudesFragment){
            //super.onBackPressed();
            fragM.popBackStack("HOMEW",0);
        }
    }
}

package com.example.corpsarens2.proyecto3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Main3Activity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener,FragmentTareas.OnFragmentInteractionListener,FragmentConfiguracion.OnFragmentInteractionListener, Fragment_VerTareas.OnFragmentInteractionListener {
    TextView Texto1;
    ImageView Flecha;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usuario1=getIntent().getExtras().getString("Usuario");
        Texto1 = (TextView) findViewById(R.id.Empezar1);
        Flecha = (ImageView) findViewById(R.id.Flecha1);

        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView usuario = (TextView) headerView.findViewById(R.id.nombreusuario);
        usuario.setText(usuario1);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String token=getIntent().getExtras().getString("token");
        String usuario1=getIntent().getExtras().getString("Usuario");
        // Handle navigation view item clicks here.
        int id = item.getItemId();
            Boolean FragmentTransaction=false;
        Fragment fragment=null;
        if (id == R.id.nav_tareas) {
            fragment= new FragmentTareas();
            Bundle bundle=new Bundle();
            bundle.putString("Token",token);
            fragment.setArguments(bundle);
            FragmentTransaction=true;
        }  else if (id == R.id.nav_configuracion) {

            fragment= new FragmentConfiguracion();
            Bundle bundle=new Bundle();
            bundle.putString("Token",token);
            fragment.setArguments(bundle);
            FragmentTransaction=true;
        } else if (id==R.id.nav_vertareas){
            fragment= new Fragment_VerTareas();
            Bundle bundle=new Bundle();
            bundle.putString("token",token);
            bundle.putString("Usuario",usuario1);
            fragment.setArguments(bundle);
            FragmentTransaction=true;
        }else if (id == R.id.nav_cerrarsesion){

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Cerrando Sesion...");
            progressDialog.show();
            Intent Registrarse= new Intent(this,MainActivity.class);
            startActivity(Registrarse);
            System.out.println("finalizado");
    }


    if(FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main3,fragment).
                    commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

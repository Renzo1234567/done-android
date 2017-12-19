package com.example.corpsarens2.proyecto3;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;


public class Main3Activity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener,FragmentTareas.OnFragmentInteractionListener,FragmentAyuda.OnFragmentInteractionListener,FragmentConfiguracion.OnFragmentInteractionListener, Fragment_VerTareas.OnFragmentInteractionListener {
    TextView Texto1;
    ImageView Flecha;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Texto1=(TextView) findViewById(R.id.Empezar1);
        Flecha=(ImageView) findViewById(R.id.Flecha1);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
            Boolean FragmentTransaction=false;
        Fragment fragment=null;

        if (id == R.id.nav_tareas) {
            fragment= new FragmentTareas();
            FragmentTransaction=true;
        } else if (id == R.id.nav_ayuda) {
            fragment= new FragmentAyuda();
            FragmentTransaction=true;
        } else if (id == R.id.nav_configuracion) {
            fragment= new FragmentConfiguracion();
            FragmentTransaction=true;
        } else if (id==R.id.nav_vertareas){
            fragment= new Fragment_VerTareas();
            FragmentTransaction=true;
        }else if (id == R.id.nav_cerrarsesion){

    }


    if(FragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main3,fragment)
                    .commit();

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

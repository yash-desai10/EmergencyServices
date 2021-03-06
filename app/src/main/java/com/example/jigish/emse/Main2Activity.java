package com.example.jigish.emse;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,home.OnFragmentInteractionListener,hospital.OnFragmentInteractionListener,Fire.OnFragmentInteractionListener ,police.OnFragmentInteractionListener,feedback.OnFragmentInteractionListener,SafetyDocs.OnFragmentInteractionListener{

    static Context con;
    //  static android.app.FragmentManager frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction().replace(R.id.main_content,new home()).addToBackStack(null).commit();


        con=getApplicationContext();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(getFragmentManager().getBackStackEntryCount() == 1) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Check out")
                    .setMessage("want to do check out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            closeApp();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
//            moveTaskToBack(false);

        }
        else {
            super.onBackPressed();
        }
    }

    private void closeApp() {
    super.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

                SharedPreferences sharedPrefer=this.getSharedPreferences("saveinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sharedPrefer.edit();
                //edit.putString("firstname",sfna);
                //edit.putString("lastname",lastname);
                edit.putString("email","");
                edit.putString("password","");
                edit.apply();

            Intent i=new Intent(getApplicationContext(),Main1Activity.class);
            startActivity(i);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;

        FragmentManager ff=getFragmentManager();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            ff.beginTransaction().replace(R.id.main_content,new home()).addToBackStack(null).commit();

        } else if (id == R.id.nav_gallery) {
            //FragmentManager ff=getFragmentManager();
            //fragment =new hospital();
           // FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            //ft.replace(R.id.main_content,fragment);
           // ft.commit();
            ff.beginTransaction().replace(R.id.main_content,new hospital()).addToBackStack(null).commit();

           // Toast.makeText(getApplicationContext(),"gallery",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_slideshow) {
            ff.beginTransaction().replace(R.id.main_content,new Fire()).addToBackStack(null).commit();

        } else if (id == R.id.nav_manage) {
            ff.beginTransaction().replace(R.id.main_content,new police()).addToBackStack(null).commit();

        } else if (id == R.id.feedback) {
            ff.beginTransaction().replace(R.id.main_content,new feedback()).addToBackStack(null).commit();

       }
        else if (id == R.id.nav_docs) {
            ff.beginTransaction().replace(R.id.main_content,new SafetyDocs()).addToBackStack(null).commit();
        }
        //else if (id == R.id.nav_send) {

        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

package com.smartly.parked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    private Fragment fragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configurationNavigationView();

        //Open home fragment on starting the app
        Fragment fragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();

    }

    private void configureToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

    }

    private void configurationNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setCheckedItem(R.id.nav_home);
    }

    private void configureDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            //Opening home fragment
            case R.id.nav_home:

                if (this.fragmentHome == null) this.fragmentHome = HomeFragment.newInstance();
                this.startTransactionFragment(this.fragmentHome);

                break;

            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("RestrictedApi")
    public boolean onCreateOptionsMenu(Menu menu){

        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();
        }}

    @Override

    public void onBackPressed(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wanna exit the app?")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                        Toast.makeText(getApplicationContext(),"Leaving the page!",
                                Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Toast.makeText(getApplicationContext(),"Permission Denied!",
                                Toast.LENGTH_SHORT).show();

                    }
                });
        builder.setIcon(android.R.drawable.btn_dialog);
        builder.setTitle("Leaving !!!");
        AlertDialog alert = builder.create();
        alert.show();
    }
}
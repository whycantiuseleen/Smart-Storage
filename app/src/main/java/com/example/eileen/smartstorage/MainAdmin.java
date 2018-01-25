package com.example.eileen.smartstorage;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class MainAdmin extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        mDrawerLayout = findViewById(R.id.drawerLayout_admin);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        NavigationView nvDrawer = findViewById(R.id.navidrawer_admin);

        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem){

        Fragment myFragment = null;
        Class fragmentClass = null;

        switch (menuItem.getItemId()){
            case R.id.nav_aAccount:
                fragmentClass = AccountAdmin.class;
                break;
            case R.id.nav_BookingHistory:
                fragmentClass = BookingHistory.class;
                break;
            case R.id.nav_Statistics:
                fragmentClass = Statistics.class;
                break;
            case R.id.nav_MainMenu:
                fragmentClass = mainmenu.class;
                break;
            case R.id.nav_Reports:
                fragmentClass = Reports.class;
                break;
        }
        try{
            myFragment  = (Fragment) fragmentClass.newInstance();
            Log.i("Eileen","Fragment:  "+ myFragment);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame2,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }
    private void setupDrawerContent (NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                selectItemDrawer(item);
                return true;
            }
        });


    }

}

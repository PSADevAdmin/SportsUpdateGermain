package com.prasaurus.app.psa_b2c_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class PlayerProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        Toolbar nav_toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(nav_toolbar);
        getSupportActionBar().setTitle("Profile");

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, nav_toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BasicInfo()).commit();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BasicInfo()).commit();
                break;
            case R.id.nav_create_match:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateMatch()).commit();
                break;
            case R.id.nav_logout:
                preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
                preferenceConfig.writeLoginStatus(false);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void presentNumberPicker(NumberPickerValueAdapter numberPickerValueAdapter) {
        NumberPickerDialogFragment pickerFragment = new NumberPickerDialogFragment();
        pickerFragment.numberPickerValueAdapter = numberPickerValueAdapter;
        pickerFragment.show(getSupportFragmentManager(), "time picker");
    }
}

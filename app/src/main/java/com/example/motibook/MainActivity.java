package com.example.motibook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        FragmentView(1); // 1: Statistics, 2: Note, 3: Subscribe
    }

    private void FragmentView(int fragmentNumber) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch(fragmentNumber) {
            case 1:
                StatisticsFragment  statisticsFragment = new StatisticsFragment();
                transaction.replace(R.id.Screen, statisticsFragment);
                transaction.commit();
                break;
            case 2:
                NoteFragment noteFragment = new NoteFragment();
                transaction.replace(R.id.Screen, noteFragment);
                transaction.commit();
                break;
            case 3:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_menu_home) {
            FragmentView(1);
        } else if (id == R.id.nav_menu_note) {
            FragmentView(2);
        } else if (id == R.id.nav_menu_event) {
            //TODO
        }

        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    public void onAddBookFragment() {
        AddBookFragment addBookFragment = new AddBookFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.Screen, addBookFragment);
        transaction.commit();
    }
}
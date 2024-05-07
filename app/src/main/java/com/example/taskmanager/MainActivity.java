package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.taskmanager.ui.CompletedTaskFragment;
import com.example.taskmanager.ui.CurrentTasksFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static int x = 0;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);

        ImageButton drawerBtnToggle = findViewById(R.id.drawerBtnToggle);
        drawerBtnToggle.setOnClickListener(v -> drawerLayout.open());

        NavigationView navView = findViewById(R.id.navView);
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CurrentTasksFragment()).commit();
        }
    }

    public void createNewTask(View v){
        Intent i = new Intent(this,CreateTaskActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int itemID = menuItem.getItemId();
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        if(itemID == R.id.navCurrent) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CurrentTasksFragment()).commit();
        }else if(itemID == R.id.navCompleted) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompletedTaskFragment()).commit();
        }else if(itemID == R.id.navSettings) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
package com.hra.hourregistrationapp;

import android.os.Bundle;

import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

/*
    This activity class is making sure that the menu is loaded and all the menu items are inserted.
 */

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.navigation_toolbar_menu);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_navigation_menu);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calendar_menu_item, R.id.nav_hours, R.id.nav_project,
                R.id.nav_settings, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.navigation_fragment_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*
            The addOnDestinationChangedListener looks at which item is selected in the menu and gives a toast with the corresponding text
            for the page.
         */
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.nav_calendar_menu_item) {
                Toast.makeText(NavigationActivity.this, "home", Toast.LENGTH_LONG).show();
            }
            if(destination.getId() == R.id.nav_hours) {
                Toast.makeText(NavigationActivity.this, "hours", Toast.LENGTH_LONG).show();
            }
            if(destination.getId() == R.id.nav_project) {
                Toast.makeText(NavigationActivity.this, "project", Toast.LENGTH_LONG).show();
            }
            if(destination.getId() == R.id.nav_settings) {
                Toast.makeText(NavigationActivity.this, "settings", Toast.LENGTH_LONG).show();
            }
            if(destination.getId() == R.id.nav_logout) {
                Toast.makeText(NavigationActivity.this, "logout", Toast.LENGTH_LONG).show();
            }
        });

    }

    /*
        Initialize the contents of the Activity's standard options menu.
        This is only called once, the first time the options menu is displayed
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO R.menu fixen
        //getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    /*
        This method is called whenever the user chooses to navigate Up within your application's activity hierarchy from the action bar.
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.navigation_fragment_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*
        This hook is called whenever an item in your options menu is selected.
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.navigation_fragment_menu);
        return super.onOptionsItemSelected(item);
    }
}

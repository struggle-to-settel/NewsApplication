package com.learnandroid.myapplication;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private String param = "&category=technology";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setUpToolbar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.purple_500));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean b;
                String title = "Technology";
                boolean doIt = true;
                int id = item.getItemId();
                Menu m = navigationView.getMenu();
                switch (item.getItemId()) {
                    case R.id.country_menu:
                        b = !m.findItem(R.id.country_us).isVisible();
                        viewCountries(m, b);
                        viewCategories(m,false);
                        doIt = false;
                        break;
                    case R.id.category_menu:
                        b = !m.findItem(R.id.cat_top).isVisible();
                        viewCategories(m, b);
                        viewCountries(m,false);
                        doIt = false;
                        break;
                    case R.id.country_in:
                        param = "&country=in";
                        title = "India";
                        break;
                    case R.id.country_jp:
                        param = "&country=jp";
                        title = "Japan";
                        break;
                    case R.id.country_us:
                        param = "&country=us";
                        title= "USA";
                        break;

                    case R.id.cat_business:
                        param = "&category=business";
                        title = "Business";
                        break;
                    case R.id.cat_entertain:
                        param = "&category=entertainment";
                        title = "Entertainment";
                        break;
                    case R.id.cat_science:
                        param = "&category=science";
                        title = "Science";
                        break;
                    case R.id.cat_top:
                        param = "&category=top";
                        title = "Top";
                        break;
                        
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                if (doIt) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    setToolBarTitle(toolbar,title);
                    HomeFragment homeFragment = HomeFragment.newInstance(param);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
                }
                return true;
            }
        });
        openHome();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    private void setUpToolbar(Toolbar toolbar) {
        toolbar.setTitle("Technology");
        toolbar.setBackgroundColor(getResources().getColor(R.color.purple_500));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void setToolBarTitle(Toolbar toolbar,String title){
         toolbar.setTitle(title);
    }

    private void openHome() {
        HomeFragment homeFragment = HomeFragment.newInstance(param);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
    }

    private void viewCountries(Menu m, boolean b) {
        m.findItem(R.id.country_us).setVisible(b);
        m.findItem(R.id.country_in).setVisible(b);
        m.findItem(R.id.country_jp).setVisible(b);
    }

    private void viewCategories(Menu m, boolean b) {
        m.findItem(R.id.cat_top).setVisible(b);
        m.findItem(R.id.cat_business).setVisible(b);
        m.findItem(R.id.cat_entertain).setVisible(b);
        m.findItem(R.id.cat_science).setVisible(b);
    }
}
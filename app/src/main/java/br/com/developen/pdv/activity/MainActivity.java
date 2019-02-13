package br.com.developen.pdv.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import br.com.developen.pdv.R;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.widget.MainPagerAdapter;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private SharedPreferences preferences;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.activity_main_navigator);

        navigationView.setNavigationItemSelectedListener(this);

        TextView userNameTextView = navigationView.getHeaderView(0).findViewById(R.id.activity_main_navigator_header_name);

        TextView userLoginTextView = navigationView.getHeaderView(0).findViewById(R.id.activity_main_navigator_header_login);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.activity_main_viewpager);

        viewPager.setAdapter(mainPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.activity_main_tab);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        userNameTextView.setText(preferences.getString(Constants.USER_NAME_PROPERTY,"Anônimo"));

        userLoginTextView.setText(preferences.getString(Constants.USER_LOGIN_PROPERTY,""));

    }


    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();

        }

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

        switch (item.getItemId()){

            case R.id.activity_main_menu_home:

                break;

            case R.id.activity_main_menu_catalog:

                drawer.closeDrawers();

                Intent saleIntent = new Intent(MainActivity.this, CatalogActivity.class);

                startActivity(saleIntent);

                break;

            case R.id.activity_main_menu_cash:

                drawer.closeDrawer(GravityCompat.START);

                Intent cashIntent = new Intent(MainActivity.this, CashActivity.class);

                startActivity(cashIntent);

                break;

            case R.id.activity_main_menu_logout:

                SharedPreferences.Editor editor = preferences.edit();

                editor.remove(Constants.USER_IDENTIFIER_PROPERTY);

                editor.remove(Constants.USER_NAME_PROPERTY);

                editor.remove(Constants.USER_LOGIN_PROPERTY);

                editor.apply();

                drawer.closeDrawer(GravityCompat.START);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);

                finish();

                break;

        }

        return true;

    }

}
package br.com.developen.pdv.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.CashRepository;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.widget.MainPagerAdapter;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private static final int CASH = 0;

    private static final int REPORTS = 1;

    private static final int SALES = 2;


    private SharedPreferences preferences;

    private Snackbar cashClosedSnackbar;

    private Boolean cashIsOpen = false;


    //private Disposable networkDisposable;

    //private Disposable internetDisposable;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);

        toolbar.setTitle(R.string.home);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.home, R.string.home);

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

        CashRepository cashRepository = ViewModelProviders.of(this).get(CashRepository.class);

        cashRepository.isOpen().observe(this, isOpen -> {

            cashIsOpen = isOpen;

            if (!cashIsOpen)

                getCashClosedSnackbar().show();

            else

                getCashClosedSnackbar().dismiss();

            supportInvalidateOptionsMenu();

        });

    }


    protected void onResume() {

        super.onResume();

/*        InternetObservingSettings settings = InternetObservingSettings.builder()
                .initialInterval(5*1000)
                .interval(5*1000)
                .host("192.168.0.108")
                .port(8080)
                .timeout(30*1000)
                .httpResponse(HttpURLConnection.HTTP_OK)
                .errorHandler((exception, message) -> {

                    Log.d("DIOGO", message +":"+exception.getMessage());

                }).build();

        internetDisposable = ReactiveNetwork.
                observeInternetConnectivity(settings)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> Log.d("DIOGO", isConnectedToInternet ? "conectado" : "desconectado")); */

    }


    protected void onPause() {

        super.onPause();

        //safelyDispose(internetDisposable);

    }


    /*private void safelyDispose(Disposable... disposables) {

        for (Disposable subscription : disposables) {

            if (subscription != null && !subscription.isDisposed()) {

                subscription.dispose();

            }

        }

    }*/


    public boolean onPrepareOptionsMenu (Menu menu) {

        menu.findItem(R.id.activity_main_menu_newsale).setVisible(cashIsOpen);

        return true;

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.activity_main_menu_newsale:

                if (cashIsOpen)

                    openCatalogActivity();

                else

                    showCashClosedAlertDialog();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

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

            case R.id.activity_main_drawer_menu_home:

                break;

            case R.id.activity_main_drawer_menu_sale:

                drawer.closeDrawers();

                openSaleActivity();

                break;

            case R.id.activity_main_drawer_menu_cash:

                drawer.closeDrawer(GravityCompat.START);

                openCashActivity();

                break;

            case R.id.activity_main_drawer_menu_report:

                drawer.closeDrawer(GravityCompat.START);

                openReportActivity();

                break;

            case R.id.activity_main_drawer_menu_logout:

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


    private Snackbar getCashClosedSnackbar(){

        if (cashClosedSnackbar == null){

            cashClosedSnackbar = Snackbar.make(findViewById(R.id.activity_main_drawer), "O caixa encontra-se fechado", Snackbar.LENGTH_INDEFINITE);

            cashClosedSnackbar.setActionTextColor(Color.WHITE);

            cashClosedSnackbar.getView().setBackgroundResource(R.color.colorRedDark);

            cashClosedSnackbar.setAction("ABRIR", new View.OnClickListener() {

                public void onClick(View view) {

                    openCashActivity();

                }

            });

        }

        return cashClosedSnackbar;

    }


    public void showCashClosedAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

        builder.setMessage(Html.fromHtml("Deseja abrir agora?"));

        builder.setCancelable(true);

        builder.setTitle(R.string.dlg_title_cash_closed);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                openCashActivity();

            }

        });

        builder.setNegativeButton(R.string.no, null);

        AlertDialog alert = builder.create();

        alert.setCanceledOnTouchOutside(false);

        alert.show();

    }

    private void openSaleActivity(){

        Intent saleIntent = new Intent(MainActivity.this, ConfirmPasswordActivity.class);

        saleIntent.putExtra(ConfirmPasswordActivity.USER_IDENTIFIER,
                preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

        saleIntent.putExtra(ConfirmPasswordActivity.USER_NAME,
                preferences.getString(Constants.USER_NAME_PROPERTY, "Desconhecido"));

        saleIntent.putExtra(ConfirmPasswordActivity.MINIMUM_LEVEL, Constants.MANAGER_SUBJECT_LEVEL);

        startActivityForResult(saleIntent, SALES);

    }

    private void openCatalogActivity(){

        Intent catalogIntent = new Intent(MainActivity.this, CatalogActivity.class);

        startActivity(catalogIntent);

    }

    private void openCashActivity(){

        Intent cashIntent = new Intent(MainActivity.this, ConfirmPasswordActivity.class);

        cashIntent.putExtra(ConfirmPasswordActivity.USER_IDENTIFIER,
                preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

        cashIntent.putExtra(ConfirmPasswordActivity.USER_NAME,
                preferences.getString(Constants.USER_NAME_PROPERTY, "Desconhecido"));

        cashIntent.putExtra(ConfirmPasswordActivity.MINIMUM_LEVEL, Constants.MANAGER_SUBJECT_LEVEL);

        startActivityForResult(cashIntent, CASH);

    }

    private void openReportActivity(){

        Intent reportIntent = new Intent(MainActivity.this, ConfirmPasswordActivity.class);

        reportIntent.putExtra(ConfirmPasswordActivity.USER_IDENTIFIER,
                preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

        reportIntent.putExtra(ConfirmPasswordActivity.USER_NAME,
                preferences.getString(Constants.USER_NAME_PROPERTY, "Desconhecido"));

        reportIntent.putExtra(ConfirmPasswordActivity.MINIMUM_LEVEL, Constants.MANAGER_SUBJECT_LEVEL);

        startActivityForResult(reportIntent, REPORTS);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {

            case (CASH) : {

                if (resultCode == ConfirmPasswordActivity.RESULT_OK) {

                    Intent cashIntent = new Intent(MainActivity.this, CashActivity.class);

                    startActivity(cashIntent);

                }

                break;

            }

            case (REPORTS) : {

                if (resultCode == ConfirmPasswordActivity.RESULT_OK) {

                    Intent reportIntent = new Intent(MainActivity.this, ReportActivity.class);

                    startActivity(reportIntent);

                }

                break;

            }

            case (SALES) : {

                if (resultCode == ConfirmPasswordActivity.RESULT_OK) {

                    Intent salesIntent = new Intent(MainActivity.this, SaleActivity.class);

                    startActivity(salesIntent);

                }

                break;

            }

        }

    }

}
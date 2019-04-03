package br.com.developen.pdv.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Observable;
import java.util.Observer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import br.com.developen.pdv.R;
import br.com.developen.pdv.report.Report;
import br.com.developen.pdv.report.ReportName;
import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.repository.CatalogRepository;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.room.MeasureUnitGroup;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.task.CreateSaleFromCatalogAsyncTask;
import br.com.developen.pdv.task.UpdateSaleFromCatalogAsyncTask;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.widget.CatalogItemFragment;
import br.com.developen.pdv.widget.CatalogPagerAdapter;
import br.com.developen.pdv.widget.SaleFragment;

public class CatalogActivity extends AppCompatActivity
        implements PrintListener, Observer,
        CatalogItemFragment.Listener,
        SaleFragment.Listener,
        CreateSaleFromCatalogAsyncTask.Listener,
        UpdateSaleFromCatalogAsyncTask.Listener{


    private ViewPager viewPager;

    private CatalogRepository catalogRepository;

    private CatalogPagerAdapter catalogPagerAdapter;

    private FloatingActionButton floatingActionButton;

    private ProgressDialog progressDialog;

    private SharedPreferences preferences;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_catalog);

        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Toolbar toolbar = findViewById(R.id.activity_catalog_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.new_sale);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        catalogPagerAdapter = new CatalogPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.activity_catalog_viewpager);

        viewPager.setAdapter(catalogPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.activity_catalog_tab);

        tabLayout.setupWithViewPager(viewPager);

        catalogRepository = CatalogRepository.getInstance();

        catalogRepository.addObserver(this);

        floatingActionButton = findViewById(R.id.activity_catalog_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (CatalogRepository.getInstance().getSale() == null)

                    new CreateSaleFromCatalogAsyncTask<>(CatalogActivity.this).
                            execute();

                else

                    new UpdateSaleFromCatalogAsyncTask<>(CatalogActivity.this).
                            execute();

            }

        });

        floatingActionButton.setVisibility(
                catalogRepository.hasItemSelected() ? View.VISIBLE : View.GONE);

        catalogPagerAdapter.setCatalogs(
                CatalogRepository.
                        getInstance().
                        getCatalogs());

        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

        preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_catalog_menu, menu);

        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.activity_catalog_menu_reset:

                CatalogRepository.getInstance().reset();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    public boolean onSupportNavigateUp() {

        onBackPressed();

        return true;

    }


    public void onCatalogItemClick(CatalogItemModel catalogItem) {

        Double quantity = catalogItem.getQuantity() + 1;

        catalogItem.setQuantity(quantity);

        catalogItem.setTotal(quantity * catalogItem.getSaleable().getPrice());

        CatalogRepository.getInstance().updateCatalogItem(catalogItem);

    }


    public void onCatalogItemLongClick(final CatalogItemModel catalogItem) {

        boolean withDecimal = catalogItem.
                getSaleable().
                getMeasureUnit().
                getGroup().
                equals(MeasureUnitGroup.LENGHT.ordinal()) ||
                catalogItem.
                        getSaleable().
                        getMeasureUnit().
                        getGroup().
                        equals(MeasureUnitGroup.MASS.ordinal()) ||
                catalogItem.
                        getSaleable().
                        getMeasureUnit().
                        getGroup().
                        equals(MeasureUnitGroup.MEASURE.ordinal());

        NumberPickerBuilder npb = new NumberPickerBuilder();

        npb.setFragmentManager(getSupportFragmentManager());

        npb.setStyleResId(R.style.BetterPickersDialogFragment);

        npb.setPlusMinusVisibility(View.INVISIBLE);

        npb.setMaxNumber(BigDecimal.valueOf(999));

        npb.setDecimalVisibility(withDecimal ? View.VISIBLE : View.INVISIBLE);

        npb.setLabelText(catalogItem.getSaleable().getMeasureUnit().getDenomination() + "(S)");

        npb.addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

            public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                Double quantity = fullNumber.doubleValue();

                catalogItem.setQuantity(quantity);

                catalogItem.setTotal(quantity * catalogItem.getSaleable().getPrice());

                CatalogRepository.getInstance().updateCatalogItem(catalogItem);

            }

        });

        if (withDecimal)

            npb.setCurrentNumber(catalogItem.getQuantity() == null
                    ? BigDecimal.valueOf(0) : BigDecimal.valueOf(catalogItem.getQuantity()));

        else

            npb.setCurrentNumber(catalogItem.getQuantity() == null ? 0 : catalogItem.getQuantity().intValue());

        npb.show();

    }


    public void onCreateSaleSuccess(SaleModel saleModel) {

        CatalogRepository.getInstance().setSale(saleModel.getIdentifier());

        showSaleFragment();

    }


    public void onCreateSaleFailure(Messaging messaging) {}


    public void onUpdateSaleSuccess() {

        showSaleFragment();

    }


    public void onUpdateSaleFailure(Messaging messaging) {}


    public void onSaleFinalized() {

        printTicketsOfSale();

    }

    public void showSaleFragment(){

        BottomSheetDialogFragment bottomSheetDialogFragment = SaleFragment.
                newInstance(CatalogRepository.getInstance().getSale());

        bottomSheetDialogFragment.show(
                getSupportFragmentManager(),
                bottomSheetDialogFragment.getTag());

    }

    //////////////////////////////////////////////////////////
    //IMPRESSAO DOS TICKETS///////////////////////////////////
    //////////////////////////////////////////////////////////

    public void printTicketsOfSale(){

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        String userName = preferences.getString(Constants.USER_NAME_PROPERTY, "");

        String note = "PROIBIDA A VENDA E ENTREGA DE BEBIDAS ALCOOLICAS PARA MENORES DE 18 ANOS.";

        String footer = "WWW.DEVELOPEN.COM.BR";

        try {

            Report report = (Report) Class.
                    forName("br.com.developen.pdv.report.PT7003Report").
                    newInstance();

            report.printTicketsOfSale(
                    this,
                    CatalogRepository.
                            getInstance().
                            getSale(),
                    title,
                    subtitle,
                    deviceAlias,
                    userName,
                    note,
                    footer);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void showPrinterAlertDialog(Messaging messaging, DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

        builder.setMessage(Html.fromHtml(TextUtils.join("\n", messaging.getMessages())));

        builder.setCancelable(false);

        builder.setTitle(R.string.dlg_title_print_failure);

        builder.setPositiveButton(R.string.try_again, listener);

        AlertDialog alert = builder.create();

        alert.setCanceledOnTouchOutside(false);

        alert.show();

    }


    public void onPrintPreExecute(ReportName report) {

        progressDialog.setCancelable(false);

        progressDialog.setTitle("Aguarde");

        progressDialog.setMessage("Imprimindo cupons...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();

    }


    public void onPrintProgressInitialize(ReportName report, int progress, int max) {

        progressDialog.setProgress(progress);

        progressDialog.setMax(max);

    }


    public void onPrintProgressUpdate(ReportName report, int status) {

        progressDialog.incrementProgressBy(status);

    }


    public void onPrintSuccess(ReportName report) {

        CatalogRepository.
                getInstance().
                reset();

        progressDialog.hide();

        switch (report){

            case SALE_ITEM_COUPON:

                Toast.makeText(getBaseContext(), getResources().getString(R.string.success_sale_finished), Toast.LENGTH_SHORT).show();

                break;

        }

    }


    public void onPrintFailure(ReportName report, Messaging message) {

        progressDialog.hide();

        showPrinterAlertDialog(message, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                printTicketsOfSale();

            }

        });

    }


    public void onPrintCancelled(ReportName report) {

        progressDialog.hide();

    }


    public void update(Observable o, Object arg) {

        if (o instanceof CatalogRepository)

            floatingActionButton.setVisibility(
                    catalogRepository.hasItemSelected() ? View.VISIBLE : View.GONE);

    }


}
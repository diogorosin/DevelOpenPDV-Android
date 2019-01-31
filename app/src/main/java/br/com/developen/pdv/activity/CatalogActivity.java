package br.com.developen.pdv.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Observable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.CatalogItemRepository;
import br.com.developen.pdv.repository.CatalogRepository;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.room.CatalogModel;
import br.com.developen.pdv.room.MeasureUnitGroup;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.task.NewSaleFromCatalogAsyncTask;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.widget.SaleFragment;
import br.com.developen.pdv.widget.CatalogItemFragment;
import br.com.developen.pdv.widget.CatalogPagerAdapter;

public class CatalogActivity extends AppCompatActivity
        implements CatalogItemFragment.CatalogItemFragmentListener,
        SaleFragment.SaleFragmentListener,
        NewSaleFromCatalogAsyncTask.Listener,
        java.util.Observer {


    private ViewPager viewPager;

    private CatalogRepository catalogRepository;

    private CatalogItemRepository catalogItemRepository;

    private CatalogPagerAdapter catalogPagerAdapter;

    private FloatingActionButton floatingActionButton;

    private SharedPreferences sharedPreferences;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_catalog);

        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Toolbar toolbar = findViewById(R.id.activity_catalog_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.catalog);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        catalogPagerAdapter = new CatalogPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.activity_catalog_viewpager);

        viewPager.setAdapter(catalogPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.activity_catalog_tab);

        tabLayout.setupWithViewPager(viewPager);

        floatingActionButton = findViewById(R.id.activity_catalog_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                new NewSaleFromCatalogAsyncTask<>(CatalogActivity.this).
                        execute();

            }

        });

        catalogRepository = ViewModelProviders.of(this).get(CatalogRepository.class);

        catalogRepository.getCatalogs().observe(CatalogActivity.this, new Observer<List<CatalogModel>>() {

            public void onChanged(@Nullable List<CatalogModel> catalogs) {

                catalogPagerAdapter.setCatalogs(catalogs);

            }

        });

        catalogItemRepository = CatalogItemRepository.getInstance();

        catalogItemRepository.addObserver(this);

        sharedPreferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected void onDestroy() {

        super.onDestroy();

        catalogItemRepository.deleteObserver(this);

    }


    public void update(Observable o, Object arg) {

        if (o instanceof CatalogItemRepository) {}

    }


    public void onCatalogItemClick(CatalogItemModel catalogItem) {

        Double quantity = catalogItem.getQuantity() + 1;

        catalogItem.setQuantity(quantity);

        catalogItem.setTotal(quantity * catalogItem.getSaleable().getPrice());

        catalogItemRepository.updateCatalogItem(catalogItem);

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

                catalogItemRepository.updateCatalogItem(catalogItem);

            }

        });

        if (withDecimal)

            npb.setCurrentNumber(catalogItem.getQuantity() == null
                    ? BigDecimal.valueOf(0) : BigDecimal.valueOf(catalogItem.getQuantity()));

        else

            npb.setCurrentNumber(catalogItem.getQuantity() == null ? 0 : catalogItem.getQuantity().intValue());

        npb.show();

    }


    public void onFinalizeSale() {}


    public void onNewSaleCreateSuccess(SaleModel saleModel) {

        BottomSheetDialogFragment bottomSheetDialogFragment = SaleFragment.
                newInstance(saleModel.getIdentifier());

        bottomSheetDialogFragment.show(
                getSupportFragmentManager(),
                bottomSheetDialogFragment.getTag());

    }


    public void onNewSaleCreateFailure(Messaging messaging) {}


}
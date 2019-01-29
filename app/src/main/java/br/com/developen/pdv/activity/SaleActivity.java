package br.com.developen.pdv.activity;

import android.app.ProgressDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Observable;

import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.CatalogRepository;
import br.com.developen.pdv.repository.SaleItemRepository;
import br.com.developen.pdv.room.CatalogModel;
import br.com.developen.pdv.room.MeasureUnitGroup;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.widget.CatalogPagerAdapter;
import br.com.developen.pdv.widget.SaleItemFragment;

public class SaleActivity extends AppCompatActivity
        implements SaleItemFragment.SaleItemFragmentListener,
        java.util.Observer {


    private ViewPager viewPager;

    private CatalogRepository catalogRepository;

    private CatalogPagerAdapter catalogPagerAdapter;

    private FloatingActionButton floatingActionButton;

    private SharedPreferences preferences;

    private ProgressDialog progressDialog;

    private SaleItemRepository saleItemRepository;



    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sale);


        preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        progressDialog = new ProgressDialog(this);


        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));


        Toolbar toolbar = findViewById(R.id.activity_sale_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.sale);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        catalogPagerAdapter = new CatalogPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.activity_sale_viewpager);

        viewPager.setAdapter(catalogPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.activity_sale_tab);

        tabLayout.setupWithViewPager(viewPager);


        floatingActionButton = findViewById(R.id.activity_sale_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                /*BottomSheetDialogFragment bottomSheetDialogFragment = CatalogCartFragment.
                        newInstance();

                bottomSheetDialogFragment.show(
                        getSupportFragmentManager(),
                        bottomSheetDialogFragment.getTag());*/

            }

        });


        catalogRepository = ViewModelProviders.of(this).get(CatalogRepository.class);

        catalogRepository.getCatalogs().observe(SaleActivity.this, new Observer<List<CatalogModel>>() {

            public void onChanged(@Nullable List<CatalogModel> catalogs) {

                catalogPagerAdapter.setCatalogs(catalogs);

            }

        });


        saleItemRepository = SaleItemRepository.getInstance();

        saleItemRepository.addObserver(this);


    }

    protected void onDestroy() {

        super.onDestroy();

        saleItemRepository.deleteObserver(this);

    }

    public void onIncrementSaleItemQuantity(SaleItemModel saleItem) {

        saleItem.setQuantity(saleItem.getQuantity() + 1);

        saleItemRepository.updateSaleItem(saleItem);

    }

    public void onEditSaleItem(final SaleItemModel saleItemModel) {

        boolean withDecimal = saleItemModel.
                getMeasureUnit().
                getGroup().
                equals(MeasureUnitGroup.LENGHT.ordinal()) ||
                saleItemModel.
                        getMeasureUnit().
                        getGroup().
                        equals(MeasureUnitGroup.MASS.ordinal()) ||
                saleItemModel.
                        getMeasureUnit().
                        getGroup().
                        equals(MeasureUnitGroup.MEASURE.ordinal());

        NumberPickerBuilder npb = new NumberPickerBuilder();

        npb.setFragmentManager(getSupportFragmentManager());

        npb.setStyleResId(R.style.BetterPickersDialogFragment);

        npb.setPlusMinusVisibility(View.INVISIBLE);

        npb.setMaxNumber(BigDecimal.valueOf(999));

        npb.setDecimalVisibility(withDecimal ? View.VISIBLE : View.INVISIBLE);

        npb.setLabelText(saleItemModel.getMeasureUnit().getDenomination() + "(S)");

        npb.addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

            public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                saleItemModel.setQuantity(fullNumber.doubleValue());

                saleItemRepository.updateSaleItem(saleItemModel);

            }

        });

        if (withDecimal)

            npb.setCurrentNumber(saleItemModel.getQuantity() == null
                    ? BigDecimal.valueOf(0) : BigDecimal.valueOf(saleItemModel.getQuantity()));

        else

            npb.setCurrentNumber(saleItemModel.getQuantity() == null ? 0 : saleItemModel.getQuantity().intValue());

        npb.show();

    }

    public void update(Observable o, Object arg) {

        if (o instanceof SaleItemRepository) {

            //           SaleItemRepository saleItemRepository = (SaleItemRepository) o;
//            mTextViewUserAge.setText(String.valueOf(userDataRepository.getAge()));
//            mTextViewUserFullName.setText(userDataRepository.getFullName());

        }

    }


}
package br.com.developen.pdv.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.report.Report;
import br.com.developen.pdv.report.ReportName;
import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.repository.CashRepository;
import br.com.developen.pdv.room.CashModel;
import br.com.developen.pdv.task.CloseCashAsyncTask;
import br.com.developen.pdv.task.OpenCashAsyncTask;
import br.com.developen.pdv.task.RemovalCashAsyncTask;
import br.com.developen.pdv.task.SummaryCashReportAsyncTask;
import br.com.developen.pdv.task.SupplyCashAsyncTask;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.StringUtils;
import br.com.developen.pdv.widget.CashEntryRecyclerViewAdapter;
import br.com.developen.pdv.widget.CashSummaryRecyclerViewAdapter;
import br.com.developen.pdv.widget.CloseCashDialogFragment;

public class CashActivity extends AppCompatActivity implements
        OpenCashAsyncTask.Listener,
        CloseCashAsyncTask.Listener,
        CloseCashDialogFragment.Listener,
        SupplyCashAsyncTask.Listener,
        RemovalCashAsyncTask.Listener,
        PrintListener,
        SummaryCashReportAsyncTask.Listener,
        View.OnClickListener {

    private FloatingActionButton openCloseFAB;

    private CashSummaryRecyclerViewAdapter cashSummaryRecyclerViewAdapter;

    private CashEntryRecyclerViewAdapter cashEntryRecyclerViewAdapter;

    private FloatingActionsMenu menuFAM;

    private SharedPreferences preferences;

    private ProgressDialog progressDialog;

    private Boolean cashOpen = false;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cash);


        preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);


        progressDialog = new ProgressDialog(this);

        openCloseFAB = findViewById(R.id.activity_cash_fab);

        openCloseFAB.setOnClickListener(CashActivity.this);

        menuFAM = findViewById(R.id.activity_cash_fam);

        com.getbase.floatingactionbutton.FloatingActionButton supplyFAB = findViewById(R.id.activity_cash_supply_fab);

        supplyFAB.setOnClickListener(this);

        com.getbase.floatingactionbutton.FloatingActionButton removalFAB = findViewById(R.id.activity_cash_removal_fab);

        removalFAB.setOnClickListener(this);


        Window window = getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(Color.TRANSPARENT);


        Toolbar toolbar = findViewById(R.id.activity_cash_toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final AppBarLayout barLayout = findViewById(R.id.activity_cash_bar_layout);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.activity_cash_toolbar_layout);


        CashRepository cashRepository = ViewModelProviders.of(this).get(CashRepository.class);

        cashRepository.isOpen().observe(CashActivity.this, new Observer<Boolean>() {

            public void onChanged(Boolean isOpen) {

                cashOpen = isOpen;

                if (menuFAM.isExpanded())

                    menuFAM.collapseImmediately();

                menuFAM.setVisibility(cashOpen ? View.VISIBLE : View.GONE);

                barLayout.setBackgroundColor(

                        cashOpen ? getResources().getColor(R.color.colorCashOpened) : getResources().getColor(R.color.colorCashClosed)

                );

                collapsingToolbarLayout.setTitle(

                        cashOpen ? getResources().getString(R.string.cash_opened) : getResources().getString(R.string.cash_closed)

                );

                collapsingToolbarLayout.setStatusBarScrimColor(

                        cashOpen ? getResources().getColor(R.color.colorCashOpened) : getResources().getColor(R.color.colorCashClosed)

                );

                collapsingToolbarLayout.setContentScrimColor(

                        cashOpen ? getResources().getColor(R.color.colorCashOpened) : getResources().getColor(R.color.colorCashClosed)

                );

                openCloseFAB.setBackgroundTintList(

                        cashOpen ? ContextCompat.getColorStateList(CashActivity.this, R.color.colorBlackMedium) : ContextCompat.getColorStateList(CashActivity.this, R.color.colorBlackMedium)

                );

                openCloseFAB.setImageDrawable(

                        cashOpen ? ContextCompat.getDrawable(CashActivity.this, R.drawable.lock_24) : ContextCompat.getDrawable(CashActivity.this, R.drawable.unlock_24)

                );

            }

        });

        cashRepository.value().observe(CashActivity.this, new Observer<Double>() {

            public void onChanged(final Double money) {

               // int color = ContextCompat.getColor(App.getContext(),
               //         money >= 0 ?  R.color.colorBlueLight : R.color.colorRedLight);

                TextView moneyTextView = findViewById(R.id.activity_cash_money_textview);

                //moneyTextView.setTextColor(color);

                moneyTextView.setText(StringUtils.formatCurrencyWithSymbol(money));

                TextView totalMoneyTextView = findViewById(R.id.activity_cash_summary_value_textview);

                //totalMoneyTextView.setTextColor(color);

                totalMoneyTextView.setText(StringUtils.formatCurrencyWithSymbol(money));

            }

        });

        RecyclerView cashSummaryRecyclerView = findViewById(R.id.activity_cash_summary_recyclerview);

        cashSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        cashSummaryRecyclerViewAdapter = new CashSummaryRecyclerViewAdapter(new ArrayList<CashModel>());

        cashSummaryRecyclerView.setAdapter(cashSummaryRecyclerViewAdapter);

        cashRepository.cashSummary().observe(CashActivity.this, new Observer<List<CashModel>>() {

            public void onChanged(@Nullable List<CashModel> cashModelList) {

                cashSummaryRecyclerViewAdapter.setCashSummary(cashModelList);

            }

        });


        RecyclerView cashEntryRecyclerView = findViewById(R.id.activity_cash_entry_recyclerview);

        cashEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        cashEntryRecyclerViewAdapter = new CashEntryRecyclerViewAdapter(cashEntryRecyclerView, new ArrayList<CashModel>());

        cashEntryRecyclerView.setAdapter(cashEntryRecyclerViewAdapter);

        cashRepository.cashEntry().observe(CashActivity.this, new Observer<List<CashModel>>() {

            public void onChanged(@Nullable List<CashModel> cashModelList) {

                cashEntryRecyclerViewAdapter.setCashEntries(cashModelList);

            }

        });

    }

    public boolean onSupportNavigateUp() {

        onBackPressed();

        return true;

    }

    private void showAlertDialog(Messaging messaging){

        AlertDialog.Builder builder = new AlertDialog.Builder(CashActivity.this.getBaseContext());

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setCancelable(true);

        builder.setTitle(R.string.dlg_title_request_failure);

        builder.setPositiveButton(android.R.string.ok,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                });

        builder.create().show();

    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_cash_fab:

                if (cashOpen){

                    DialogFragment dialog = new CloseCashDialogFragment();

                    dialog.show(CashActivity.this.getSupportFragmentManager(), "CloseCashDialogFragment");

                } else {

                    NumberPickerBuilder openCashNPB = new NumberPickerBuilder()
                            .setFragmentManager(CashActivity.this.getSupportFragmentManager())
                            .setStyleResId(R.style.BetterPickersDialogFragment)
                            .setPlusMinusVisibility(View.INVISIBLE)
                            .setMaxNumber(BigDecimal.valueOf(99999))
                            .setDecimalVisibility(View.VISIBLE)
                            .setLabelText("R$")
                            .addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

                                public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                                    new OpenCashAsyncTask(CashActivity.this).
                                            execute(fullNumber.doubleValue(), preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

                                }

                            });

                    openCashNPB.show();

                }

                break;

            case R.id.activity_cash_supply_fab:

                menuFAM.collapse();

                NumberPickerBuilder supplyCashNPB = new NumberPickerBuilder()
                        .setFragmentManager(CashActivity.this.getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment)
                        .setPlusMinusVisibility(View.INVISIBLE)
                        .setMaxNumber(BigDecimal.valueOf(99999))
                        .setDecimalVisibility(View.VISIBLE)
                        .setLabelText("R$")
                        .addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

                            public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                                new SupplyCashAsyncTask(CashActivity.this).
                                        execute(fullNumber.doubleValue(), preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

                            }

                        });

                supplyCashNPB.show();

                break;

            case R.id.activity_cash_removal_fab:

                menuFAM.collapse();

                NumberPickerBuilder removalCashNPB = new NumberPickerBuilder()
                        .setFragmentManager(CashActivity.this.getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment)
                        .setPlusMinusVisibility(View.INVISIBLE)
                        .setMaxNumber(BigDecimal.valueOf(99999))
                        .setDecimalVisibility(View.VISIBLE)
                        .setLabelText("R$")
                        .addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

                            public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                                new RemovalCashAsyncTask(CashActivity.this).
                                        execute(fullNumber.doubleValue(), preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

                            }

                        });

                removalCashNPB.show();


                break;

        }

    }

    public void onOpenCashSuccess(final CashModel cashModel){

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = cashModel.getDateTime();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            Report report = (Report) Class.
                    forName("br.com.developen.pdv.report.PT7003Report").
                    newInstance();

            report.printOpenCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    cashModel);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onOpenCashFailure(Messaging messaging){

        showAlertDialog(messaging);

    }


    public void onSupplyCashSuccess(CashModel cashModel) {

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = new Date();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            Report report = (Report) Class.
                    forName("br.com.developen.pdv.report.PT7003Report").
                    newInstance();

            report.printSupplyCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    cashModel);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onSupplyCashFailure(Messaging messaging) {

        showAlertDialog(messaging);

    }


    public void onRemoveCashSuccess(CashModel cashModel) {

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = cashModel.getDateTime();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            Report report = (Report) Class.
                    forName("br.com.developen.pdv.report.PT7003Report").
                    newInstance();

            report.printRemoveCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    cashModel);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onRemoveCashFailure(Messaging messaging) {

        showAlertDialog(messaging);

    }


    public void onCloseCash(Double value) {

        new CloseCashAsyncTask(this).
                execute(value, preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

    }


    public void onCloseCashSuccess(CashModel cashModel) {

        new SummaryCashReportAsyncTask<>(this).execute();

    }


    public void onCloseCashFailure(Messaging messaging) {

        showAlertDialog(messaging);

    }


    public void onPrintPreExecute(ReportName report) {

        progressDialog.setCancelable(false);

        progressDialog.setTitle("Aguarde");

        progressDialog.setMessage("Imprimindo comprovante...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();

    }

    public void onPrintProgressInitialize(ReportName report, int progress, int max) {

        progressDialog.setProgress(progress);

        progressDialog.setMax(max);

    }

    public void onPrintSuccess(ReportName report) {

        progressDialog.hide();

        switch (report){

            case OPEN_CASH_COUPON:

                Toast.makeText(getBaseContext(), getResources().getString(R.string.success_cash_opened), Toast.LENGTH_SHORT).show();

                break;

            case SUPPLY_CASH_COUPON:

                Toast.makeText(getBaseContext(), R.string.success_cash_supplied, Toast.LENGTH_SHORT).show();

                break;

            case REMOVE_CASH_COUPON:

                Toast.makeText(getBaseContext(), R.string.success_cash_removed, Toast.LENGTH_SHORT).show();

                break;

            case CLOSE_CASH_COUPON:

                Toast.makeText(getBaseContext(), getResources().getString(R.string.success_cash_closed), Toast.LENGTH_SHORT).show();

                break;

        }

    }


    public void onPrintFailure(ReportName report, Messaging message) {}


    public void onPrintCancelled(ReportName report) {

        progressDialog.hide();

    }


    public void onPrintProgressUpdate(ReportName report, int status) {

        progressDialog.incrementProgressBy(status);

    }


    public void onSummaryReportSuccess(List<CashModel> cashModelList) {

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = new Date();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        try {

            CashModel[] list = new CashModel[cashModelList.size()];

            list = cashModelList.toArray(list);

            Report report = (Report) Class.
                    forName("br.com.developen.pdv.report.PT7003Report").
                    newInstance();

            report.printCloseCashCoupon(
                    this,
                    title,
                    subtitle,
                    dateTime,
                    deviceAlias,
                    list);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public void onSummaryReportFailure(Messaging messaging) {}


}
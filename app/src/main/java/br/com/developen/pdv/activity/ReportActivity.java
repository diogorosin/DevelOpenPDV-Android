package br.com.developen.pdv.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import java.util.Date;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.report.Report;
import br.com.developen.pdv.report.ReportName;
import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.widget.ReportFilterDialogFragment;
import br.com.developen.pdv.widget.ReportRecyclerViewAdapter;

public class ReportActivity extends AppCompatActivity implements PrintListener {

    private SharedPreferences preferences;

    private ProgressDialog progressDialog;

    private View.OnClickListener salesByProgenyOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            ReportFilterDialogFragment dialog = ReportFilterDialogFragment.
                    newInstance(ReportName.SALES_BY_PROGENY, R.string.sales_by_progeny);

            dialog.show(ReportActivity.this.getSupportFragmentManager(), "ReportFilterDialogFragment");

        }
    };

    private View.OnClickListener salesByCatalogOnClickListener = new View.OnClickListener() {

        public void onClick(View v) {

            ReportFilterDialogFragment dialog = ReportFilterDialogFragment.
                    newInstance(ReportName.SALES_BY_CATALOG, R.string.sales_by_catalog);

            dialog.show(ReportActivity.this.getSupportFragmentManager(), "ReportFilterDialogFragment");

        }

    };

    private View.OnClickListener salesByUserOnClickListener = new View.OnClickListener() {

        public void onClick(View v) {

            ReportFilterDialogFragment dialog = ReportFilterDialogFragment.
                    newInstance(ReportName.SALES_BY_USER, R.string.sales_by_user);

            dialog.show(ReportActivity.this.getSupportFragmentManager(), "ReportFilterDialogFragment");

        }

    };

    private View.OnClickListener saleablesOnClickListener = new View.OnClickListener() {

        public void onClick(View v) {

            ReportActivity.this.onExecuteReport(ReportName.SALEABLES, null);

        }

    };

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        Toolbar toolbar = findViewById(R.id.activity_report_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.reports);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.activity_report_recyclerview);

        ReportRecyclerViewAdapter adapter = new ReportRecyclerViewAdapter(
                salesByProgenyOnClickListener,
                salesByCatalogOnClickListener,
                salesByUserOnClickListener,
                saleablesOnClickListener);

        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, layoutManager);

        layoutManager.setSpanSizeLookup(lookup);

        recyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(this);

        preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

    }

    public void onPrintPreExecute(ReportName report) {

        progressDialog.setCancelable(false);

        progressDialog.setTitle("Aguarde");

        progressDialog.setMessage("Imprimindo relatório...");

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

        progressDialog.hide();

        Toast.makeText(
                getBaseContext(),
                getResources().getString(R.string.success_print), Toast.LENGTH_SHORT).show();

    }

    public void onPrintFailure(ReportName report, Messaging message) {}

    public void onPrintCancelled(ReportName report) {

        progressDialog.hide();

    }

    public void onExecuteReport(ReportName reportName, Map<Integer, Object> parameters){

        String title = preferences.getString(Constants.COUPON_TITLE_PROPERTY,"");

        String subtitle = preferences.getString(Constants.COUPON_SUBTITLE_PROPERTY,"");

        Date dateTime = new Date();

        String deviceAlias = preferences.getString(Constants.DEVICE_ALIAS_PROPERTY,"");

        switch (reportName){

            case SALES_BY_PROGENY:

                try {

                    Report report = (Report) Class.
                            forName("br.com.developen.pdv.report.PT7003Report").
                            newInstance();

                    report.printSalesByProgeny(
                            this,
                            title,
                            subtitle,
                            dateTime,
                            deviceAlias, parameters);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;

            case SALES_BY_USER:

                try {

                    Report report = (Report) Class.
                            forName("br.com.developen.pdv.report.PT7003Report").
                            newInstance();

                    report.printSalesByUser(
                            this,
                            title,
                            subtitle,
                            dateTime,
                            deviceAlias, parameters);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;

            case SALES_BY_CATALOG:

                try {

                    Report report = (Report) Class.
                            forName("br.com.developen.pdv.report.PT7003Report").
                            newInstance();

                    report.printSalesByCatalog(
                            this,
                            title,
                            subtitle,
                            dateTime,
                            deviceAlias, parameters);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;

            case SALEABLES:

                try {

                    Report report = (Report) Class.
                            forName("br.com.developen.pdv.report.PT7003Report").
                            newInstance();

                    report.printSaleables(
                            this,
                            title,
                            subtitle,
                            dateTime,
                            deviceAlias, parameters);

                } catch (Exception e) {

                    e.printStackTrace();

                }

                break;

        }

    }

}
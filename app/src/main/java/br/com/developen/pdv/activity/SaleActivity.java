package br.com.developen.pdv.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.CashRepository;
import br.com.developen.pdv.repository.SaleRepository;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.task.CancelSaleAsyncTask;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.widget.RecyclerClickListener;
import br.com.developen.pdv.widget.RecyclerTouchListener;
import br.com.developen.pdv.widget.SaleRecyclerViewPagerAdapter;
import br.com.developen.pdv.widget.SaleToolbarActionModeCallback;

public class SaleActivity extends AppCompatActivity implements CancelSaleAsyncTask.Listener {


    private ActionMode actionMode;

    private RecyclerView recyclerView;

    private Snackbar cashClosedSnackbar;

    private SaleRepository saleRepository;

    private ProgressDialog progressDialog;

    private FloatingActionButton newSaleFAB;

    private SaleRecyclerViewPagerAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sale);

        Toolbar toolbar = findViewById(R.id.activity_sale_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.sales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.activity_sale_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        recyclerView.setHasFixedSize(true);

        adapter = new SaleRecyclerViewPagerAdapter();

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerClickListener() {

            public void onClick(View view, int position) {

                if (actionMode != null)

                    onListItemSelect(position);

            }

            public void onLongClick(View view, int position) {

                onListItemSelect(position);

            }

        }));

        newSaleFAB = findViewById(R.id.activity_sale_new);

        newSaleFAB.setOnClickListener(view -> {

            Intent saleIntent = new Intent(SaleActivity.this, CatalogActivity.class);

            startActivity(saleIntent);

        });

        saleRepository = ViewModelProviders.of(this).get(SaleRepository.class);

        saleRepository.getSalesPaged().observe(this, saleModels -> {

            if (saleModels != null)

                adapter.submitList(saleModels);

        });

        CashRepository cashRepository = ViewModelProviders.of(this).get(CashRepository.class);

        cashRepository.isOpen().observe(this, isOpen -> {

            newSaleFAB.setVisibility(isOpen ? View.VISIBLE : View.GONE);

            if (!isOpen)

                getCashClosedSnackbar().show();

            else

                getCashClosedSnackbar().dismiss();

        });

        progressDialog = new ProgressDialog(this);

    }


    public void onResume(){

        super.onResume();

        new Handler().postDelayed(new Runnable() {

            public void run() {

                recyclerView.scrollToPosition(0);

            }

        }, 500);

    }


    private void onListItemSelect(int position) {

        adapter.toggleSelection(position);

        boolean hasCheckedItems = adapter.getSelectedItemsCount() > 0;

        if (hasCheckedItems && actionMode == null)

            actionMode = this.startSupportActionMode(new SaleToolbarActionModeCallback(adapter, this));

        else if (!hasCheckedItems && actionMode != null)

            actionMode.finish();

        if (actionMode != null) {

            int count = adapter.getSelectedItemsCount();

            actionMode.setTitle(String.valueOf(count) + " venda" + (count > 1 ? "s" : "") + " selecionada" + (count > 1 ? "s" : ""));

        }

    }


    public void setNullToActionMode() {

        if (actionMode != null)

            actionMode = null;

    }


    public void cancelSales() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

        builder.setMessage("Deseja realmente cancelar a(s) venda(s) selecionada(s)?");

        builder.setCancelable(true);

        builder.setTitle(R.string.confirm);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> {

            List<SaleModel> saleModelList = new ArrayList<>();

            SparseBooleanArray selected = adapter.getSelectedItems();

            for (int i = (selected.size() - 1); i >= 0; i--)

                saleModelList.add(Objects.requireNonNull(adapter.getCurrentList()).get(selected.keyAt(i)));

            new CancelSaleAsyncTask<>(SaleActivity.this).execute(saleModelList.toArray(new SaleModel[0]));

            actionMode.finish();

        });

        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog alert = builder.create();

        alert.setCanceledOnTouchOutside(false);

        alert.show();

    }


    public void onCancelSalePreExecute() {

        progressDialog.setCancelable(false);

        progressDialog.setTitle("Aguarde");

        progressDialog.setMessage("Cancelando venda(s)...");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();

    }


    public void onCancelSaleProgressInitialize(int progress, int max) {

        progressDialog.setProgress(progress);

        progressDialog.setMax(max);

    }


    public void onCancelSaleProgressUpdate(int status) {

        progressDialog.incrementProgressBy(status);

    }


    public void onCancelSaleSuccess() {

        progressDialog.hide();

        Toast.makeText(
                getBaseContext(),
                getResources().
                        getString(R.string.success_sale_cancel),
                Toast.LENGTH_SHORT).show();

    }


    public void onCancelSaleFailure(Messaging messaging) {

        showAlertDialog(messaging);

    }


    public void onCancelSaleCancelled() {

        progressDialog.hide();

    }


    private void showAlertDialog(Messaging messaging){

        if (progressDialog.isShowing())

            progressDialog.hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(SaleActivity.this, R.style.AppCompatAlertDialogStyle);

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setCancelable(true);

        builder.setTitle(R.string.error);

        builder.setPositiveButton(android.R.string.ok,

                (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder.create();

        alert.setCanceledOnTouchOutside(false);

        alert.show();

    }


    private Snackbar getCashClosedSnackbar(){

        if (cashClosedSnackbar == null){

            cashClosedSnackbar = Snackbar.make(findViewById(R.id.activity_sale_layout), "O caixa encontra-se fechado", Snackbar.LENGTH_INDEFINITE);

            cashClosedSnackbar.setActionTextColor(Color.WHITE);

            cashClosedSnackbar.setAction("ABRIR", new View.OnClickListener() {

                public void onClick(View view) {}

            });

            cashClosedSnackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {

                                               public void onShown(Snackbar transientBottomBar) {

                                                   super.onShown(transientBottomBar);

                                                   transientBottomBar.getView().findViewById(R.id.snackbar_action).setOnClickListener(new View.OnClickListener() {

                                                       public void onClick(View v) {

                                                           openCashActivity();

                                                       }

                                                   });

                                               }

                                           });

            cashClosedSnackbar.getView().setBackgroundResource(R.color.colorRedDark);

            }

            return cashClosedSnackbar;

        }


        private void openCashActivity(){

            Intent cashIntent = new Intent(SaleActivity.this, CashActivity.class);

            startActivity(cashIntent);

        }


    }
package br.com.developen.pdv.activity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
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

    private SaleRecyclerViewPagerAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sale);

        Toolbar toolbar = findViewById(R.id.activity_sale_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.sales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.activity_sale_recyclerview);

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

        SaleRepository saleRepository = ViewModelProviders.of(this).get(SaleRepository.class);

        saleRepository.getSalesPaged().observe(this, new Observer<PagedList<SaleModel>>() {

            public void onChanged(PagedList<SaleModel> saleModels) {

                if (saleModels!=null)

                    adapter.submitList(saleModels);

            }

        });

        FloatingActionButton fab = findViewById(R.id.activity_sale_new);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


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

            actionMode.setTitle(String.valueOf(count) + " venda" + (count>1?"s":"") + " selecionada" + (count>1?"s":""));


        }

    }

    public void setNullToActionMode() {

        if (actionMode != null)

            actionMode = null;

    }

    public void cancelSales() {

        SparseBooleanArray selected = adapter.getSelectedItems();

        List<SaleModel> saleModelList = new ArrayList<>();

        for (int i = (selected.size() - 1); i >= 0; i--)

            if (selected.valueAt(i))

                saleModelList.add(adapter.getCurrentList().get(i));

        new CancelSaleAsyncTask<>(this).execute(saleModelList.toArray(new SaleModel[0]));

        actionMode.finish();

    }

    public void onCancelSaleSuccess() {}

    public void onCancelSaleFailure(Messaging messaging) {}

}
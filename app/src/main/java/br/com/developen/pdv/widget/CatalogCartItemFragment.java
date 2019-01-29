package br.com.developen.pdv.widget;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.CatalogItemRepository;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.utils.StringUtils;


public class CatalogCartItemFragment extends Fragment implements Observer {


    private CatalogCartItemRecyclerViewAdapter recyclerViewAdapter;

    private TextView subtotalTextView;

    private TextView payTextView;


    public static CatalogCartItemFragment newInstance() {

        CatalogCartItemFragment fragment = new CatalogCartItemFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_catalog_cart_item_view, container, false);

        subtotalTextView = view.findViewById(R.id.activity_catalog_cart_subtotal);

        payTextView = view.findViewById(R.id.activity_catalog_cart_total);



        RecyclerView recyclerView = view.findViewById(R.id.activity_catalog_cart_item_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewAdapter = new CatalogCartItemRecyclerViewAdapter(new ArrayList<CatalogItemModel>());

        recyclerViewAdapter.setHasStableIds(true);

        recyclerView.setAdapter(recyclerViewAdapter);



        CatalogItemRepository catalogItemRepository = CatalogItemRepository.getInstance();

        catalogItemRepository.addObserver(this);

        setCatalogItems(catalogItemRepository.getCatalogItems());


        return view;

    }

    public void update(Observable o, Object arg) {

        if (o instanceof CatalogItemRepository) {

            CatalogItemRepository catalogItemRepository = (CatalogItemRepository) o;

            setCatalogItems(catalogItemRepository.getCatalogItems());

        }

    }

    private void setCatalogItems(List<CatalogItemModel> catalogItems){

        Double subTotal = 0.0;

        Double pay = 0.0;

        List<CatalogItemModel> newCatalogItems = new ArrayList<>();

        for (CatalogItemModel catalogItem: catalogItems) {

            if (catalogItem.getQuantity() > 0) {

                newCatalogItems.add(catalogItem);

                subTotal += catalogItem.getTotal();

                pay += catalogItem.getTotal();

            }

        }

        recyclerViewAdapter.setCatalogItems(newCatalogItems);

        subtotalTextView.setText(StringUtils.formatCurrencyWithSymbol(subTotal));

        payTextView.setText(StringUtils.formatCurrencyWithSymbol(pay));

    }

}
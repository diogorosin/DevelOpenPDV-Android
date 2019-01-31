package br.com.developen.pdv.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.SaleItemRepository;
import br.com.developen.pdv.repository.SaleRepository;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.utils.StringUtils;


public class SaleItemFragment extends Fragment {


    private static final String ARG_SALE = "ARG_SALE";


    private SaleItemRecyclerViewAdapter recyclerViewAdapter;

    private SaleRepository saleRepository;

    private SaleItemRepository saleItemRepository;


    private TextView subtotalTextView;

    private TextView payTextView;


    public static SaleItemFragment newInstance(Integer sale) {

        SaleItemFragment fragment = new SaleItemFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_SALE, sale);

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sale_item_view, container, false);

        subtotalTextView = view.findViewById(R.id.fragment_sale_subtotal);

        payTextView = view.findViewById(R.id.fragment_sale_total);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_sale_item_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewAdapter = new SaleItemRecyclerViewAdapter(new ArrayList<SaleItemModel>());

        recyclerViewAdapter.setHasStableIds(true);

        recyclerView.setAdapter(recyclerViewAdapter);

        saleRepository = ViewModelProviders.of(this).get(SaleRepository.class);

        saleRepository.getSubtotal(getArguments().getInt(ARG_SALE)).
                observe(SaleItemFragment.this, new Observer<Double>() {

                    public void onChanged(@Nullable Double subtotal) {

                        subtotalTextView.setText(StringUtils.formatCurrencyWithSymbol(subtotal));

                    }

                });

        saleRepository.getTotal(getArguments().getInt(ARG_SALE)).
                observe(SaleItemFragment.this, new Observer<Double>() {

                    public void onChanged(@Nullable Double total) {

                        payTextView.setText(StringUtils.formatCurrencyWithSymbol(total));

                    }

                });

        saleItemRepository = ViewModelProviders.of(this).get(SaleItemRepository.class);

        saleItemRepository.getItems(getArguments().getInt(ARG_SALE)).
                observe(SaleItemFragment.this, new Observer<List<SaleItemModel>>() {

                    public void onChanged(@Nullable List<SaleItemModel> saleItems) {

                        recyclerViewAdapter.setSaleItems(saleItems);

                    }

                });

        return view;

    }


}
package br.com.developen.pdv.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.SaleItemRepository;
import br.com.developen.pdv.room.SaleItemModel;

public class SaleItemFragment extends Fragment implements Observer {


    private static final String ARG_COLUMNS = "COLUMNS";

    private static final String ARG_CATALOG_POSITION = "CATALOG_POSITION";


    private SaleItemRecyclerViewAdapter recyclerViewAdapter;

    private SaleItemFragmentListener fragmentListener;

    private SaleItemRepository saleItemRepository;


    private int columns = 0;

    private int catalogPosition = 0;


    public SaleItemFragment() {}


    public static SaleItemFragment newInstance(int catalogPosition) {

        SaleItemFragment fragment = new SaleItemFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_COLUMNS, 3);

        args.putInt(ARG_CATALOG_POSITION, catalogPosition);

        fragment.setArguments(args);

        return fragment;

    }


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            columns = getArguments().getInt(ARG_COLUMNS);

            catalogPosition = getArguments().getInt(ARG_CATALOG_POSITION);

        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_sale_item, container, false);

        if (view instanceof RecyclerView) {

            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;

            if (columns <= 1) {

                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {

                recyclerView.setLayoutManager(new GridLayoutManager(context, columns));

            }

            saleItemRepository = SaleItemRepository.getInstance();

            saleItemRepository.addObserver(this);

            recyclerViewAdapter = new SaleItemRecyclerViewAdapter(new ArrayList<SaleItemModel>(), fragmentListener);

            recyclerView.setAdapter(recyclerViewAdapter);

            setSaleItems(saleItemRepository.getSaleItems());

        }

        return view;

    }


    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof SaleItemFragmentListener)

            fragmentListener = (SaleItemFragmentListener) context;

        else

            throw new RuntimeException(context.toString()
                    + " must implement SaleItemFragmentListener");

    }


    public void onDetach() {

        super.onDetach();

        fragmentListener = null;

    }


    public void onDestroy() {

        super.onDestroy();

        saleItemRepository.deleteObserver(this);

    }


    public void update(Observable o, Object arg) {

        if (o instanceof SaleItemRepository) {

            SaleItemRepository saleItemRepository = (SaleItemRepository) o;

            setSaleItems(saleItemRepository.getSaleItems());

        }

    }


    public interface SaleItemFragmentListener {

        void onIncrementSaleItemQuantity(SaleItemModel saleItemModel);

        void onEditSaleItem(SaleItemModel saleItemModel);

    }


    private void setSaleItems(List<SaleItemModel> saleItems){

        List<SaleItemModel> newSaleItems = new ArrayList<>();

        for (SaleItemModel saleItem: saleItems)

            if (saleItem.getSaleable().getCatalog().getPosition().equals(catalogPosition))

                newSaleItems.add(saleItem);

        recyclerViewAdapter.setSaleItems(newSaleItems);

    }


}
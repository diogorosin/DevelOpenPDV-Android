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
import br.com.developen.pdv.repository.CatalogRepository;
import br.com.developen.pdv.room.CatalogItemModel;

public class CatalogItemFragment extends Fragment implements Observer {


    private static final String ARG_COLUMNS = "COLUMNS";

    private static final String ARG_CATALOG_POSITION = "CATALOG_POSITION";


    private CatalogItemRecyclerViewAdapter recyclerViewAdapter;

    private CatalogItemFragmentListener fragmentListener;

    private CatalogRepository catalogRepository;


    private int columns = 0;

    private int catalogPosition = 0;


    public CatalogItemFragment() {}


    public static CatalogItemFragment newInstance(int catalogPosition) {

        CatalogItemFragment fragment = new CatalogItemFragment();

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

        View view = inflater.inflate(R.layout.activity_catalog_item, container, false);

        if (view instanceof RecyclerView) {

            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;

            if (columns <= 1) {

                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {

                recyclerView.setLayoutManager(new GridLayoutManager(context, columns));

            }

            catalogRepository = CatalogRepository.getInstance();

            catalogRepository.addObserver(this);

            recyclerViewAdapter = new CatalogItemRecyclerViewAdapter(new ArrayList<CatalogItemModel>(), fragmentListener);

            recyclerView.setAdapter(recyclerViewAdapter);

            setCatalogItems(catalogRepository.getCatalogItems());

        }

        return view;

    }


    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof CatalogItemFragmentListener)

            fragmentListener = (CatalogItemFragmentListener) context;

        else

            throw new RuntimeException(context.toString()
                    + " must implement CatalogItemFragmentListener");

    }


    public void onDetach() {

        super.onDetach();

        fragmentListener = null;

    }


    public void onDestroy() {

        super.onDestroy();

        catalogRepository.deleteObserver(this);

    }


    public void update(Observable o, Object arg) {

        if (o instanceof CatalogRepository) {

            CatalogRepository catalogRepository = (CatalogRepository) o;

            setCatalogItems(catalogRepository.getCatalogItems());

        }

    }


    public interface CatalogItemFragmentListener {

        void onCatalogItemClick(CatalogItemModel catalogItem);

        void onCatalogItemLongClick(CatalogItemModel catalogItem);

    }


    private void setCatalogItems(List<CatalogItemModel> catalogItems){

        List<CatalogItemModel> newCatalogItems = new ArrayList<>();

        for (CatalogItemModel catalogItem: catalogItems)

            if (catalogItem.getSaleable().getCatalog().getPosition().equals(catalogPosition))

                newCatalogItems.add(catalogItem);

        recyclerViewAdapter.setCatalogItems(newCatalogItems);

    }


}
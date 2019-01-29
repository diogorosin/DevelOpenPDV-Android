package br.com.developen.pdv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.utils.Hashids;
import br.com.developen.pdv.utils.StringUtils;

public class CatalogCartItemRecyclerViewAdapter extends RecyclerView.Adapter<CatalogCartItemRecyclerViewAdapter.CatalogCartItemViewHolder> {


    private List<CatalogItemModel> catalogItems;


    public CatalogCartItemRecyclerViewAdapter(List<CatalogItemModel> catalogItems) {

        this.catalogItems = catalogItems;

    }


    public CatalogCartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_catalog_cart_item_row, parent, false);

        return new CatalogCartItemViewHolder(view);

    }


    public void onBindViewHolder(final CatalogCartItemViewHolder holder, int position) {

        holder.catalogItemModel = catalogItems.get(position);

        holder.title.setText(catalogItems.get(position).getSaleable().getLabel());

        holder.quantity.setText(StringUtils.formatQuantityWithMinimumFractionDigit(catalogItems.get(position).getQuantity()));

        holder.measureUnit.setText(catalogItems.get(position).getSaleable().getMeasureUnit().getAcronym().toUpperCase());

        holder.price.setText(StringUtils.formatCurrency(catalogItems.get(position).getSaleable().getPrice()));

        holder.total.setText(StringUtils.formatCurrency(catalogItems.get(position).getTotal()));

    }


    public int getItemCount() {

        return catalogItems.size();

    }


    public long getItemId(int position){

        return new Hashids().encode(
                catalogItems.get(position).getSaleable().getCatalog().getPosition(),
                catalogItems.get(position).getSaleable().getPosition());

    }


    public void setCatalogItems(List<CatalogItemModel> catalogItems){

        this.catalogItems = catalogItems;

        notifyDataSetChanged();

    }


    public class CatalogCartItemViewHolder extends RecyclerView.ViewHolder {

        public CatalogItemModel catalogItemModel;

        public TextView title;

        public TextView quantity;

        public TextView measureUnit;

        public TextView price;

        public TextView total;

        public CatalogCartItemViewHolder(View view) {

            super(view);

            title = view.findViewById(R.id.activity_catalog_cart_item_row_title);

            quantity = view.findViewById(R.id.activity_catalog_cart_item_row_quantity);

            measureUnit = view.findViewById(R.id.activity_catalog_cart_item_row_measureunit);

            price = view.findViewById(R.id.activity_catalog_cart_item_row_price);

            total = view.findViewById(R.id.activity_catalog_cart_item_row_total);

        }

    }


}
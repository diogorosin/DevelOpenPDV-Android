package br.com.developen.pdv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.utils.Hashids;
import br.com.developen.pdv.utils.StringUtils;

public class SaleItemRecyclerViewAdapter extends RecyclerView.Adapter<SaleItemRecyclerViewAdapter.SaleItemViewHolder> {


    private List<SaleItemModel> saleItems;


    public SaleItemRecyclerViewAdapter(List<SaleItemModel> saleItems) {

        this.saleItems = saleItems;

    }


    public SaleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sale_item_row, parent, false);

        return new SaleItemViewHolder(view);

    }


    public void onBindViewHolder(final SaleItemViewHolder holder, int position) {

        holder.saleItemModel = saleItems.get(position);

        holder.title.setText(saleItems.get(position).getSaleable().getLabel());

        holder.measureUnit.setText(saleItems.get(position).getMeasureUnit().getAcronym().toUpperCase());

        holder.quantity.setText(StringUtils.formatQuantityWithMinimumFractionDigit(saleItems.get(position).getQuantity()));

        holder.price.setText(StringUtils.formatCurrency(saleItems.get(position).getPrice()));

        holder.total.setText(StringUtils.formatCurrency(saleItems.get(position).getTotal()));

    }


    public int getItemCount() {

        return saleItems.size();

    }


    public long getItemId(int position){

        return new Hashids().encode(saleItems.get(position).getItem());

    }


    public void setSaleItems(List<SaleItemModel> saleItems){

        this.saleItems = saleItems;

        notifyDataSetChanged();

    }


    public class SaleItemViewHolder extends RecyclerView.ViewHolder {

        public SaleItemModel saleItemModel;

        public TextView title;

        public TextView quantity;

        public TextView measureUnit;

        public TextView price;

        public TextView total;

        public SaleItemViewHolder(View view) {

            super(view);

            title = view.findViewById(R.id.fragment_sale_item_row_title);

            quantity = view.findViewById(R.id.fragment_sale_item_row_quantity);

            measureUnit = view.findViewById(R.id.fragment_sale_item_row_measureunit);

            price = view.findViewById(R.id.fragment_sale_item_row_price);

            total = view.findViewById(R.id.fragment_sale_item_row_total);

        }

    }


}
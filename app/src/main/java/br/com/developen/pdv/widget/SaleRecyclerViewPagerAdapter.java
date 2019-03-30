package br.com.developen.pdv.widget;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.StringUtils;

public class SaleRecyclerViewPagerAdapter extends PagedListAdapter<SaleModel, SaleRecyclerViewPagerAdapter.SaleViewHolder> {


    private SparseBooleanArray selectedItems;


    public SaleRecyclerViewPagerAdapter() {

        super(new SaleDiffCallback());

        selectedItems = new SparseBooleanArray();

    }


    public SaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sale_row, parent, false);

        return new SaleViewHolder(view);

    }


    public void onBindViewHolder(@NonNull SaleViewHolder holder, int position) {

        holder.saleModel = getItem(position);

        holder.number.setText(String.valueOf(Objects.requireNonNull(holder.saleModel).getNumber()));

        holder.dateTime.setText(StringUtils.formatDateTime(holder.saleModel.getDateTime()));

        holder.status.setText(StringUtils.getDenominationOfSaleStatus(holder.saleModel.getStatus()));

        holder.total.setText(StringUtils.formatCurrency(holder.saleModel.getTotal()));

        holder.itemView.setBackgroundColor(selectedItems.get(position) ?
                ContextCompat.getColor(App.getContext(), R.color.colorBlackMedium) :
                Color.TRANSPARENT);

    }


    public class SaleViewHolder extends RecyclerView.ViewHolder {

        public SaleModel saleModel;

        public TextView number;

        public TextView dateTime;

        public TextView status;

        public TextView total;

        public SaleViewHolder(View view) {

            super(view);

            number = view.findViewById(R.id.activity_sale_row_number_textview);

            dateTime = view.findViewById(R.id.activity_sale_row_datetime_textview);

            status = view.findViewById(R.id.activity_sale_row_status_textview);

            total = view.findViewById(R.id.activity_sale_row_total_textview);

        }

    }


    public void toggleSelection(int position) {

        selectView(position, !selectedItems.get(position));

    }


    public void removeSelection() {

        selectedItems = new SparseBooleanArray();

        notifyDataSetChanged();

    }


    public void selectView(int position, boolean value) {

        if (value)

            selectedItems.put(position, value);

        else

            selectedItems.delete(position);

        notifyDataSetChanged();

    }


    public int getSelectedItemsCount() {

        return selectedItems.size();

    }


    public SparseBooleanArray getSelectedItems() {

        return selectedItems;

    }


}
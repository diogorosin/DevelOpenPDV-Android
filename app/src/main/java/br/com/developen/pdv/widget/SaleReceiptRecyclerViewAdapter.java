package br.com.developen.pdv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.CatalogReceiptModel;
import br.com.developen.pdv.utils.StringUtils;

public class SaleReceiptRecyclerViewAdapter
        extends RecyclerView.Adapter<SaleReceiptRecyclerViewAdapter.CatalogCartReceiptViewHolder>{


    private List<CatalogReceiptModel> catalogReceipts;


    public SaleReceiptRecyclerViewAdapter(List<CatalogReceiptModel> catalogReceipts) {

        this.catalogReceipts = catalogReceipts;

    }


    public CatalogCartReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sale_receipt_row, parent, false);

        return new CatalogCartReceiptViewHolder(view);

    }


    public void onBindViewHolder(@NonNull CatalogCartReceiptViewHolder holder, int position) {

        holder.catalogReceipt = catalogReceipts.get(position);

        holder.method.setText(catalogReceipts.get(position).getMethod().getDenomination());

        holder.value.setText(StringUtils.formatCurrency(catalogReceipts.get(position).getValue()));

    }


    public int getItemCount() {

        return catalogReceipts.size();

    }


    public long getItemId(int position){

        return catalogReceipts.get(position).getIdentifier();

    }


    public void setCatalogReceipts(List<CatalogReceiptModel> catalogReceipts){

        this.catalogReceipts = catalogReceipts;

        notifyDataSetChanged();

    }


    public class CatalogCartReceiptViewHolder extends RecyclerView.ViewHolder {

        public CatalogReceiptModel catalogReceipt;

        public TextView method;

        public TextView value;

        public CatalogCartReceiptViewHolder(View view) {

            super(view);

            method = view.findViewById(R.id.fragment_sale_receipt_row_method);

            value = view.findViewById(R.id.fragment_sale_receipt_row_value);

        }

    }

}
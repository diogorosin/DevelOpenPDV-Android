package br.com.developen.pdv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.SaleReceiptModel;
import br.com.developen.pdv.task.DeleteSaleReceiptAsyncTask;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.StringUtils;

public class SaleReceiptRecyclerViewAdapter
        extends RecyclerView.Adapter<SaleReceiptRecyclerViewAdapter.SaleReceiptViewHolder> implements DeleteSaleReceiptAsyncTask.Listener{


    private List<SaleReceiptModel> saleReceipts;


    public SaleReceiptRecyclerViewAdapter(List<SaleReceiptModel> saleReceipts) {

        this.saleReceipts = saleReceipts;

    }


    public SaleReceiptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sale_receipt_row, parent, false);

        return new SaleReceiptViewHolder(view);

    }


    public void onBindViewHolder(@NonNull final SaleReceiptViewHolder holder, int position) {

        holder.saleReceipt = saleReceipts.get(position);

        holder.method.setText(saleReceipts.get(position).getReceiptMethod().getDenomination());

        holder.value.setText(StringUtils.formatCurrency(saleReceipts.get(position).getValue()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new DeleteSaleReceiptAsyncTask<>(SaleReceiptRecyclerViewAdapter.this).execute(
                        holder.saleReceipt.getSale().getIdentifier(),
                        holder.saleReceipt.getReceipt());

            }
        });

    }


    public int getItemCount() {

        return saleReceipts.size();

    }


    public long getItemId(int position){

        return saleReceipts.get(position).getReceipt();

    }


    public void setSaleReceipts(List<SaleReceiptModel> saleReceipts){

        this.saleReceipts = saleReceipts;

        notifyDataSetChanged();

    }


    public class SaleReceiptViewHolder extends RecyclerView.ViewHolder {

        public SaleReceiptModel saleReceipt;

        public TextView method;

        public TextView value;

        public ImageButton delete;

        public SaleReceiptViewHolder(View view) {

            super(view);

            method = view.findViewById(R.id.fragment_sale_receipt_row_method);

            value = view.findViewById(R.id.fragment_sale_receipt_row_value);

            delete = view.findViewById(R.id.fragment_sale_receipt_row_delete);

        }

    }


    public void onDeleteSaleReceiptFailure(Messaging messaging) {}


}
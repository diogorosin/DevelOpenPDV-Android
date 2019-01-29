package br.com.developen.pdv.widget;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.developen.pdv.R;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.utils.Hashids;
import br.com.developen.pdv.utils.StringUtils;

public class SaleItemRecyclerViewAdapter extends RecyclerView.Adapter<SaleItemRecyclerViewAdapter.SaleItemViewHolder> {


    private List<SaleItemModel> saleItems;

    private SaleItemFragment.SaleItemFragmentListener fragmentListener;


    public SaleItemRecyclerViewAdapter(List<SaleItemModel> saleItems, SaleItemFragment.SaleItemFragmentListener listener) {

        this.saleItems = saleItems;

        this.fragmentListener = listener;

        setHasStableIds(true);

    }


    public SaleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sale_item_row, parent, false);

        return new SaleItemViewHolder(view);

    }


    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(final SaleItemViewHolder holder, int position) {

        holder.catalogItemModel = saleItems.get(position);

        holder.title.setText(saleItems.get(position).getSaleable().getLabel());

        holder.quantity.setText("x" +
                StringUtils.formatQuantity(saleItems.get(position).getQuantity()) + " " +
                saleItems.get(position).getMeasureUnit().getAcronym()

        );

        holder.quantity.setVisibility(saleItems.get(position).getQuantity() > 0 ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (fragmentListener != null)

                    fragmentListener.onIncrementSaleItemQuantity(holder.catalogItemModel);

            }

        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {

                if (fragmentListener != null)

                    fragmentListener.onEditSaleItem(holder.catalogItemModel);

                return false;

            }

        });

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

        public SaleItemModel catalogItemModel;

        public TextView title;

        public TextView quantity;

        public SaleItemViewHolder(View view) {

            super(view);

            quantity = view.findViewById(R.id.activity_sale_item_quantity_textview);

            title = view.findViewById(R.id.activity_sale_item_title_textview);

        }

    }


}
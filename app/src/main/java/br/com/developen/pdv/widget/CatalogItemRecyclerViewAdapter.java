package br.com.developen.pdv.widget;

import android.annotation.SuppressLint;
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

public class CatalogItemRecyclerViewAdapter extends RecyclerView.Adapter<CatalogItemRecyclerViewAdapter.CatalogItemViewHolder> {


    private List<CatalogItemModel> catalogItems;

    private CatalogItemFragment.CatalogItemFragmentListener fragmentListener;


    public CatalogItemRecyclerViewAdapter(List<CatalogItemModel> catalogItems, CatalogItemFragment.CatalogItemFragmentListener listener) {

        this.catalogItems = catalogItems;

        this.fragmentListener = listener;

        setHasStableIds(true);

    }


    public CatalogItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_catalog_item_row, parent, false);

        return new CatalogItemViewHolder(view);

    }


    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(final CatalogItemViewHolder holder, int position) {

        holder.catalogItemModel = catalogItems.get(position);

        holder.title.setText(catalogItems.get(position).getSaleable().getLabel());

        holder.quantity.setText("x" +
                StringUtils.formatQuantity(catalogItems.get(position).getQuantity()) + " " +
                catalogItems.get(position).getSaleable().getMeasureUnit().getAcronym()

        );

        holder.quantity.setVisibility(catalogItems.get(position).getQuantity() > 0 ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (fragmentListener != null)

                    fragmentListener.onCatalogItemClick(holder.catalogItemModel);

            }

        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {

                if (fragmentListener != null)

                    fragmentListener.onCatalogItemLongClick(holder.catalogItemModel);

                return false;

            }

        });

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


    public class CatalogItemViewHolder extends RecyclerView.ViewHolder {

        public CatalogItemModel catalogItemModel;

        public TextView title;

        public TextView quantity;

        public CatalogItemViewHolder(View view) {

            super(view);

            quantity = view.findViewById(R.id.activity_catalog_item_quantity_textview);

            title = view.findViewById(R.id.activity_catalog_item_title_textview);

        }

    }


}
package br.com.developen.pdv.widget;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import br.com.developen.pdv.room.SaleModel;

public class SaleDiffCallback extends DiffUtil.ItemCallback<SaleModel> {

    public boolean areItemsTheSame(@NonNull SaleModel oldItem, @NonNull SaleModel newItem) {

        return oldItem.equals(newItem);

    }

    public boolean areContentsTheSame(@NonNull SaleModel oldItem, @NonNull SaleModel newItem) {

        return oldItem.equals(newItem);

    }

}

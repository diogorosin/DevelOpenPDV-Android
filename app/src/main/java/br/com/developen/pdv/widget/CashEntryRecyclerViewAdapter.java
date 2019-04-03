package br.com.developen.pdv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.CashModel;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.StringUtils;

public class CashEntryRecyclerViewAdapter extends RecyclerView.Adapter<CashEntryRecyclerViewAdapter.CashEntryViewHolder>{


    private static final int UNSELECTED = -1;

    private int selectedItem = UNSELECTED;

    private RecyclerView recyclerView;

    private List<CashModel> cashModelList;


    public CashEntryRecyclerViewAdapter(RecyclerView recyclerView, List<CashModel> cashModelList) {

        this.recyclerView = recyclerView;

        this.cashModelList = cashModelList;

    }


    @NonNull
    public CashEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cash_entry_row, parent, false);

        return new CashEntryViewHolder(view);

    }


    public void onBindViewHolder(@NonNull CashEntryViewHolder holder, int position) {

        holder.cashModel = cashModelList.get(position);

        Double total = holder.cashModel.getValue();

        holder.operation.setText(StringUtils.getDenominationOfCashOperation(holder.cashModel.getOperation()));

        holder.dateTime.setText(StringUtils.formatShortDateTime(holder.cashModel.getDateTime()));

        //int color = ContextCompat.getColor(App.getContext(),
//                holder.cashModel.getType().equals(Constants.OUT_CASH_TYPE) ?  R.color.colorRedLight : R.color.colorBlueLight);

//        holder.value.setTextColor(color);

        holder.value.setText(StringUtils.formatCurrencyWithSymbol(holder.cashModel.getType().equals(Constants.OUT_CASH_TYPE) ? total * -1 : total));

        holder.type.setText(StringUtils.getDenominationOfCashType(holder.cashModel.getType()));

        holder.userName.setText(holder.cashModel.getUser().getName());

        holder.note.setText(holder.cashModel.getNote());

        boolean isSelected = position == selectedItem;

        holder.rowLayout.setSelected(isSelected);

        holder.expandableLayout.setExpanded(isSelected, false);

    }


    public int getItemCount() {

        return cashModelList.size();

    }


    public void setCashEntries(List<CashModel> cashModelList){

        this.cashModelList = cashModelList;

        notifyDataSetChanged();

    }


    public class CashEntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener{


        CashModel cashModel;

        LinearLayout rowLayout;

        ExpandableLayout expandableLayout;


        public TextView operation;

        public TextView dateTime;

        public TextView value;

        public TextView type;

        public TextView userName;

        public TextView note;


        CashEntryViewHolder(View view) {

            super(view);

            operation = view.findViewById(R.id.activity_cash_entry_row_operation_textview);

            dateTime = view.findViewById(R.id.activity_cash_entry_row_datetime_textview);

            value = view.findViewById(R.id.activity_cash_entry_row_value_textview);

            type = view.findViewById(R.id.activity_cash_entry_row_type_textview);

            userName = view.findViewById(R.id.activity_cash_entry_row_username_textview);

            note = view.findViewById(R.id.activity_cash_entry_row_note_textview);

            rowLayout = view.findViewById(R.id.activity_cash_entry_row);

            rowLayout.setOnClickListener(this);

            expandableLayout = itemView.findViewById(R.id.activity_cash_entry_row_detail_layout);

            expandableLayout.setInterpolator(new OvershootInterpolator());

            expandableLayout.setOnExpansionUpdateListener(this);

        }


        public void onClick(View v) {

            CashEntryViewHolder holder = (CashEntryViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);

            if (holder != null) {

                holder.rowLayout.setSelected(false);

                holder.expandableLayout.collapse();

            }

            int position = getAdapterPosition();

            if (position == selectedItem) {

                selectedItem = UNSELECTED;

            } else {

                rowLayout.setSelected(true);

                expandableLayout.expand();

                selectedItem = position;

            }

        }

        public void onExpansionUpdate(float expansionFraction, int state) {

            if (state == ExpandableLayout.State.EXPANDING)

                recyclerView.smoothScrollToPosition(getAdapterPosition());

        }

    }


}
package br.com.developen.pdv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.CashModel;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.StringUtils;

public class CashSummaryRecyclerViewAdapter extends RecyclerView.Adapter<CashSummaryRecyclerViewAdapter.CashSummaryViewHolder>{


    private List<CashModel> cashModelList;


    public CashSummaryRecyclerViewAdapter(List<CashModel> cashModelList) {

        this.cashModelList = cashModelList;

    }


    @NonNull
    public CashSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cash_summary_row, parent, false);

        return new CashSummaryViewHolder(view);

    }


    public void onBindViewHolder(@NonNull final CashSummaryViewHolder holder, int position) {

        holder.cashModel = cashModelList.get(position);

        String type = holder.cashModel.getType();

        Double value = holder.cashModel.getValue();

        holder.operation.setText(StringUtils.getDenominationOfCashOperation(holder.cashModel.getOperation()));

        //int color = ContextCompat.getColor(App.getContext(),
//                holder.cashModel.getType().equals(Constants.OUT_CASH_TYPE) ?  R.color.colorRedLight : R.color.colorBlueLight);

//        holder.value.setTextColor(color);

        holder.value.setText(StringUtils.formatCurrencyWithSymbol(type.equals(Constants.OUT_CASH_TYPE) ? value * -1 : value));

    }


    public int getItemCount() {

        return cashModelList.size();

    }


    public void setCashSummary(List<CashModel> cashModelList){

        this.cashModelList = cashModelList;

        notifyDataSetChanged();

    }


    public List<CashModel> getCashSummary(){

        return this.cashModelList;

    }


    public class CashSummaryViewHolder extends RecyclerView.ViewHolder {

        CashModel cashModel;

        public TextView operation;

        public TextView value;

        CashSummaryViewHolder(View view) {

            super(view);

            operation = view.findViewById(R.id.activity_cash_summary_row_operation_textview);

            value = view.findViewById(R.id.activity_cash_summary_row_value_textview);

        }

    }


}
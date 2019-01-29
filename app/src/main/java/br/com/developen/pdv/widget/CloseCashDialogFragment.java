package br.com.developen.pdv.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.CashRepository;
import br.com.developen.pdv.room.CashModel;
import br.com.developen.pdv.utils.StringUtils;

public class CloseCashDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener{


    private Listener listener;

    private CashSummaryRecyclerViewAdapter recyclerViewAdapter;

    private TextView valueTextView;

    private Double value = 0.0;


    public void onAttach(Context context) {

        super.onAttach(context);

        try {

            listener = (Listener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " must implement CloseCashDialogFragment.Listener");

        }

    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams")
        final View rootView = inflater.inflate(R.layout.activity_cash_close_dialog, null);

        builder.setTitle(R.string.dlg_title_cash_close);

        builder.setView(rootView)
                .setPositiveButton(android.R.string.ok,  this)
                .setNegativeButton(android.R.string.cancel, this);

        return builder.create();

    }


    public void onClick(DialogInterface dialog, int which) {

        switch (which){

            case DialogInterface.BUTTON_POSITIVE:

                listener.onCloseCash(value);

                dialog.dismiss();

                break;

        }

    }


    public void onResume() {

        super.onResume();

        valueTextView = getDialog().findViewById(R.id.activity_cash_summary_value_textview);

        RecyclerView recyclerView = getDialog().findViewById(R.id.activity_cash_summary_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext()));

        recyclerViewAdapter = new CashSummaryRecyclerViewAdapter(new ArrayList<CashModel>());

        recyclerView.setAdapter(recyclerViewAdapter);

        CashRepository cashViewModel = ViewModelProviders.of(getActivity()).get(CashRepository.class);

        cashViewModel.cashSummary().observe(getActivity(), new Observer<List<CashModel>>() {

            public void onChanged(@Nullable List<CashModel> cashModelList) {

                recyclerViewAdapter.setCashSummary(cashModelList);

            }

        });

        cashViewModel.value().observe(getActivity(), new Observer<Double>() {

            public void onChanged(final Double value) {

                valueTextView.setText(StringUtils.formatCurrencyWithSymbol(value));

                CloseCashDialogFragment.this.value = value;

            }

        });

    }


    public interface Listener {

        void onCloseCash(Double value);
        
    }


}
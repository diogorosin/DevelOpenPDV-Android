package br.com.developen.pdv.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.ReceiptMethodRepository;
import br.com.developen.pdv.repository.SaleReceiptRepository;
import br.com.developen.pdv.repository.SaleRepository;
import br.com.developen.pdv.room.ReceiptMethodModel;
import br.com.developen.pdv.room.SaleReceiptModel;
import br.com.developen.pdv.task.UpdateMoneyReceiptAsyncTask;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.StringUtils;


public class SaleReceiptFragment extends Fragment implements UpdateMoneyReceiptAsyncTask.Listener{


    private static final String ARG_SALE = "ARG_SALE";


    private Double toReceive = 0.0;

    private Double received = 0.0;

    private Double total = 0.0;


    private TextView totalTextView;

    private TextView receivedTextView;

    private TextView toReceiveTextView;

    private TextView toReceiveTitleTextView;

    private LinearLayout toReceiveLayout;


    private SaleReceiptRecyclerViewAdapter saleReceiptRecyclerViewAdapter;

    private SaleReceiptMethodRecyclerViewAdapter saleReceiptMethodRecyclerViewAdapter;


    private View.OnClickListener moneyOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            NumberPickerBuilder npb = new NumberPickerBuilder()
                    .setFragmentManager(SaleReceiptFragment.this.getFragmentManager())
                    .setStyleResId(R.style.BetterPickersDialogFragment)
                    .setPlusMinusVisibility(View.INVISIBLE)
                    .setMaxNumber(BigDecimal.valueOf(99999))
                    .setDecimalVisibility(View.VISIBLE)
                    .setLabelText("R$")
                    .addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {

                        public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {

                            new UpdateMoneyReceiptAsyncTask<>(SaleReceiptFragment.this).execute(
                                    getArguments().getInt(ARG_SALE),
                                    fullNumber.doubleValue());

                        }

                    });

            npb.show();

        }
    };


    private View.OnClickListener creditCardOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };


    private View.OnClickListener debitCardOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };


    private View.OnClickListener checkOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };


    public static SaleReceiptFragment newInstance(Integer sale) {

        SaleReceiptFragment fragment = new SaleReceiptFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_SALE, sale);

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sale_receipt, container, false);

        totalTextView = view.findViewById(R.id.fragment_sale_receipt_total);

        receivedTextView = view.findViewById(R.id.fragment_sale_receipt_received);

        toReceiveTextView = view.findViewById(R.id.fragment_sale_receipt_toreceive);

        toReceiveTitleTextView = view.findViewById(R.id.fragment_sale_receipt_toreceive_title);

        toReceiveLayout = view.findViewById(R.id.fragment_sale_receipt_toreceive_layout);

        RecyclerView receiptMethodRecyclerView = view.findViewById(R.id.fragment_sale_receipt_method_recyclerview);

        receiptMethodRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        receiptMethodRecyclerView.addItemDecoration(new GridLayoutSpaceItemDecoration(10));

        saleReceiptMethodRecyclerViewAdapter = new SaleReceiptMethodRecyclerViewAdapter(
                new ArrayList<ReceiptMethodModel>(),
                moneyOnClickListener,
                creditCardOnClickListener,
                debitCardOnClickListener,
                checkOnClickListener);

        saleReceiptMethodRecyclerViewAdapter.setHasStableIds(true);

        receiptMethodRecyclerView.setAdapter(saleReceiptMethodRecyclerViewAdapter);

        ReceiptMethodRepository receiptMethodRepository = ViewModelProviders.of(this).get(ReceiptMethodRepository.class);

        receiptMethodRepository.getReceiptMethods().observe(SaleReceiptFragment.this, new Observer<List<ReceiptMethodModel>>() {

            public void onChanged(@Nullable List<ReceiptMethodModel> receiptMethods) {

                saleReceiptMethodRecyclerViewAdapter.setReceiptMethods(receiptMethods);

            }

        });


        RecyclerView saleReceiptRecyclerView = view.findViewById(R.id.fragment_sale_receipt_recyclerview);

        saleReceiptRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        saleReceiptRecyclerViewAdapter = new SaleReceiptRecyclerViewAdapter(new ArrayList<SaleReceiptModel>());

        saleReceiptRecyclerViewAdapter.setHasStableIds(true);

        saleReceiptRecyclerView.setAdapter(saleReceiptRecyclerViewAdapter);

        SaleReceiptRepository saleReceiptRepository = ViewModelProviders.of(this).get(SaleReceiptRepository.class);

        saleReceiptRepository.getReceipts(getArguments().getInt(ARG_SALE)).observe(SaleReceiptFragment.this, new Observer<List<SaleReceiptModel>>() {

            public void onChanged(@Nullable List<SaleReceiptModel> saleReceipts) {

                saleReceiptRecyclerViewAdapter.setSaleReceipts(saleReceipts);

            }

        });

        SaleRepository saleRepository = ViewModelProviders.of(this).get(SaleRepository.class);

        saleRepository.getTotal(getArguments().getInt(ARG_SALE)).observe(SaleReceiptFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double total) {

                SaleReceiptFragment.this.total = total;

                updateView();

            }

        });

        saleRepository.getReceived(getArguments().getInt(ARG_SALE)).observe(SaleReceiptFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double received) {

                SaleReceiptFragment.this.received = received;

                updateView();

            }

        });

        saleRepository.getToReceive(getArguments().getInt(ARG_SALE)).observe(SaleReceiptFragment.this, new Observer<Double>() {

            public void onChanged(@Nullable Double toReceive) {

                SaleReceiptFragment.this.toReceive = toReceive;

                updateView();

            }

        });

        return view;


    }

    private void updateView(){

        receivedTextView.setText(StringUtils.formatCurrencyWithSymbol(received));

        totalTextView.setText(StringUtils.formatCurrencyWithSymbol(total));

        toReceive = total - received;

        if (toReceive > 0){

            toReceiveTitleTextView.setText(R.string.to_receive);

            toReceiveTextView.setText(StringUtils.formatCurrencyWithSymbol(toReceive));

            toReceiveLayout.setBackgroundResource(R.color.colorRedDark);

        } else {

            if (toReceive == 0.00) {

                toReceiveTitleTextView.setText(R.string.to_receive);

                toReceiveTextView.setText(StringUtils.formatCurrencyWithSymbol(0.00));

                toReceiveLayout.setBackgroundResource(R.color.colorBlackMedium);

            } else {

                toReceiveTitleTextView.setText(R.string.change);

                toReceiveTextView.setText(StringUtils.formatCurrencyWithSymbol(toReceive * -1));

                toReceiveLayout.setBackgroundResource(R.color.colorBlueDark);

            }

        }

    }

    public void onUpdateMoneyReceiptItemFailure(Messaging messaging) {}

}
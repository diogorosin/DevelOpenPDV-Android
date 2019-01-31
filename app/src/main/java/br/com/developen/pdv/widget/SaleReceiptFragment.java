package br.com.developen.pdv.widget;

import android.annotation.SuppressLint;
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
import java.util.Observable;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.CatalogItemRepository;
import br.com.developen.pdv.repository.CatalogReceiptRepository;
import br.com.developen.pdv.repository.ReceiptMethodRepository;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.room.CatalogReceiptModel;
import br.com.developen.pdv.room.ReceiptMethodModel;
import br.com.developen.pdv.utils.StringUtils;


public class SaleReceiptFragment extends Fragment implements java.util.Observer {


    private static final String ARG_SALE = "ARG_SALE";


    private Double total = 0.0;

    private Double received = 0.0;

    private Double toReceive = 0.0;


    private TextView totalTextView;

    private TextView receivedTextView;

    private TextView toReceiveTextView;

    private TextView toReceiveTitleTextView;

    private LinearLayout toReceiveLayout;


    private ReceiptMethodRepository receiptMethodViewModel;

    private CatalogItemRepository catalogItemRepository;

    private CatalogReceiptRepository catalogReceiptRepository;

    private SaleReceiptMethodRecyclerViewAdapter receiptMethodRecyclerViewAdapter;

    private SaleReceiptRecyclerViewAdapter receiptRecyclerViewAdapter;


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

                            catalogReceiptRepository.updateMoneyReceiptValue(fullNumber.doubleValue());

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

        View view = inflater.inflate(R.layout.fragment_sale_receipt_view, container, false);

        totalTextView = view.findViewById(R.id.fragment_sale_receipt_total);

        receivedTextView = view.findViewById(R.id.fragment_sale_receipt_received);

        toReceiveTextView = view.findViewById(R.id.fragment_sale_receipt_toreceive);

        toReceiveTitleTextView = view.findViewById(R.id.fragment_sale_receipt_toreceive_title);

        toReceiveLayout = view.findViewById(R.id.fragment_sale_receipt_toreceive_layout);

        RecyclerView receiptMethodRecyclerView = view.findViewById(R.id.fragment_sale_receipt_method_recyclerview);

        receiptMethodRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        receiptMethodRecyclerView.addItemDecoration(new GridLayoutSpaceItemDecoration(10));

        receiptMethodRecyclerViewAdapter = new SaleReceiptMethodRecyclerViewAdapter(
                new ArrayList<ReceiptMethodModel>(),
                moneyOnClickListener,
                creditCardOnClickListener,
                debitCardOnClickListener,
                checkOnClickListener);

        receiptMethodRecyclerViewAdapter.setHasStableIds(true);

        receiptMethodRecyclerView.setAdapter(receiptMethodRecyclerViewAdapter);

        receiptMethodViewModel = ViewModelProviders.of(this).get(ReceiptMethodRepository.class);

        receiptMethodViewModel.getReceiptMethods().observe(SaleReceiptFragment.this, new Observer<List<ReceiptMethodModel>>() {

            public void onChanged(@Nullable List<ReceiptMethodModel> receiptMethods) {

                receiptMethodRecyclerViewAdapter.setReceiptMethods(receiptMethods);

            }

        });


        RecyclerView receiptRecyclerView = view.findViewById(R.id.fragment_sale_receipt_recyclerview);

        receiptRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        receiptRecyclerViewAdapter = new SaleReceiptRecyclerViewAdapter(new ArrayList<CatalogReceiptModel>());

        receiptRecyclerViewAdapter.setHasStableIds(true);

        receiptRecyclerView.setAdapter(receiptRecyclerViewAdapter);


        catalogItemRepository = CatalogItemRepository.getInstance();

        catalogItemRepository.addObserver(this);


        catalogReceiptRepository = CatalogReceiptRepository.getInstance();

        catalogReceiptRepository.addObserver(this);


        setCatalogItems(catalogItemRepository.getCatalogItems());

        setCatalogReceipts(catalogReceiptRepository.getCatalogReceipts());


        return view;

    }


    public void update(Observable o, Object arg) {

        if (o instanceof CatalogReceiptRepository) {

            CatalogReceiptRepository catalogReceiptRepository = (CatalogReceiptRepository) o;

            setCatalogReceipts(catalogReceiptRepository.getCatalogReceipts());

        }

    }


    private void setCatalogReceipts(List<CatalogReceiptModel> catalogReceipts){

        received = 0.0;

        List<CatalogReceiptModel> newCatalogReceipts = new ArrayList<>();

        for (CatalogReceiptModel catalogReceipt: catalogReceipts) {

            if (catalogReceipt.getValue() > 0) {

                newCatalogReceipts.add(catalogReceipt);

                received += catalogReceipt.getValue();

            }

        }

        receiptRecyclerViewAdapter.setCatalogReceipts(newCatalogReceipts);

        receivedTextView.setText(StringUtils.formatCurrencyWithSymbol(received));

        updateToReceive();

    }


    private void setCatalogItems(List<CatalogItemModel> catalogItems){

        total = 0.0;

        List<CatalogItemModel> newCatalogItems = new ArrayList<>();

        for (CatalogItemModel catalogItem: catalogItems) {

            if (catalogItem.getQuantity() > 0) {

                newCatalogItems.add(catalogItem);

                total += catalogItem.getTotal();

            }

        }

        totalTextView.setText(StringUtils.formatCurrencyWithSymbol(total));

        updateToReceive();

    }


    @SuppressLint("ResourceAsColor")
    private void updateToReceive(){

        toReceive = total - received;

        if (toReceive >= 0){

            toReceiveTitleTextView.setText(R.string.to_receive);

            toReceiveTextView.setText(StringUtils.formatCurrencyWithSymbol(toReceive));

            toReceiveLayout.setBackgroundResource(R.color.colorRedDark);

        } else {

            toReceiveTitleTextView.setText(R.string.change);

            toReceiveTextView.setText(StringUtils.formatCurrencyWithSymbol(toReceive*-1));

            toReceiveLayout.setBackgroundResource(R.color.colorBlueDark);

        }

    }


}
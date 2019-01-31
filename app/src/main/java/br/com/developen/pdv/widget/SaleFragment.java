package br.com.developen.pdv.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import br.com.developen.pdv.R;


public class SaleFragment
        extends BottomSheetDialogFragment
        implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private static final String ARG_SALE = "ARG_SALE";

    private static final int STEPS_COUNT = 2;

    private static final int ITEMS_STEP = 0;

    private static final int RECEIPT_STEP = 1;


    private LinearLayout dotsLayout;

    private HeightWrappingViewPager viewPager;

    private Button previewButton, nextButton;

    private SaleFragmentListener fragmentListener;


    public static SaleFragment newInstance(Integer sale) {

        SaleFragment fragment = new SaleFragment();

        Bundle args = new Bundle();

        args.putInt(ARG_SALE, sale);

        fragment.setArguments(args);

        return fragment;

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            public void onShow(DialogInterface dialog) {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);

                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);

            }

        });

        return dialog;

    }


    public void onResume() {

        super.onResume();

        SalePagerAdapter catalogPagerAdapter = new SalePagerAdapter(
                getChildFragmentManager(),
                getArguments().getInt(ARG_SALE,0));

        viewPager = getView().findViewById(R.id.fragment_sale_viewpager);

        viewPager.setAdapter(catalogPagerAdapter);

        viewPager.addOnPageChangeListener(this);

        dotsLayout = getView().findViewById(R.id.fragment_sale_layout_dots);

        previewButton = getView().findViewById(R.id.fragment_sale_prev_button);

        previewButton.setOnClickListener(this);

        nextButton = getView().findViewById(R.id.fragment_sale_next_button);

        nextButton.setOnClickListener(this);

        addBottomDots(0);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sale_view, container, false);

        return view;

    }


    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}


    public void onPageSelected(int position) {

        addBottomDots(position);

        TextView title = getView().findViewById(R.id.fragment_sale_title);

        switch (position) {

            case 0:

                title.setText(R.string.shopping_cart);

                break;

            case 1:

                title.setText(R.string.receipt);

                break;

        }

        previewButton.setVisibility(position == ITEMS_STEP ? View.INVISIBLE : View.VISIBLE);

        nextButton.setText(position == STEPS_COUNT - 1 ? R.string.finish : R.string.next);

    }


    public void onPageScrollStateChanged(int state) {}


    private void addBottomDots(int currentPage) {

        TextView[] dotsTextView = new TextView[STEPS_COUNT];

        dotsLayout.removeAllViews();

        for (int i = 0; i < dotsTextView.length; i++) {

            dotsTextView[i] = new TextView(getContext());

            dotsTextView[i].setText(Html.fromHtml("&#8226;"));

            dotsTextView[i].setTextSize(35);

            dotsTextView[i].setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlackMedium));

            dotsLayout.addView(dotsTextView[i]);

        }

        int selectedDot;

        switch (currentPage){

            case ITEMS_STEP:

                selectedDot = 0;
                break;

            case RECEIPT_STEP:

                selectedDot = 1;
                break;

            default:

                selectedDot = currentPage;
                break;

        }

        dotsTextView[selectedDot].setTextColor(
                ContextCompat.getColor(getContext(),
                        R.color.colorWhite));

    }


    public void onClick(View v) {

        switch (v.getId()){

            case R.id.fragment_sale_next_button:

                if (viewPager.getCurrentItem() == (viewPager.getChildCount()-1)) {

                    dismiss();

                    fragmentListener.onFinalizeSale();

                } else {

                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                }

                break;

            case R.id.fragment_sale_prev_button:

                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

                break;

        }

    }


    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof SaleFragmentListener)

            fragmentListener = (SaleFragmentListener) context;

        else

            throw new RuntimeException(context.toString()
                    + " must implement SaleFragmentListener");

    }


    public void onDetach() {

        super.onDetach();

        fragmentListener = null;

    }


    public interface SaleFragmentListener {

        void onFinalizeSale();

    }


}
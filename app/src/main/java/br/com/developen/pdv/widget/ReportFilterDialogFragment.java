package br.com.developen.pdv.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.fragment.app.DialogFragment;
import br.com.developen.pdv.R;
import br.com.developen.pdv.activity.ReportActivity;
import br.com.developen.pdv.report.Report;
import br.com.developen.pdv.report.ReportName;

public class ReportFilterDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener{


    private static final String REPORT_ARG = "REPORT_ARG";

    private static final String TITLE_ARG = "TITLE_ARG";


    private PeriodDatePicker periodDatePicker;


    public static ReportFilterDialogFragment newInstance(ReportName reportName, int title){

        ReportFilterDialogFragment frag = new ReportFilterDialogFragment();

        Bundle args = new Bundle();

        args.putSerializable(REPORT_ARG, reportName);

        args.putInt(TITLE_ARG, title);

        frag.setArguments(args);

        return frag;

    }


    public ReportFilterDialogFragment() {}


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_report_filter, null);

        periodDatePicker = view.findViewById(R.id.fragment_report_filter_period);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);

        builder.setTitle(getArguments().getInt(TITLE_ARG, 0));

        builder.setView(view).setPositiveButton(android.R.string.ok,  this);

        return builder.create();

    }


    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_POSITIVE) {

            Map<Integer, Object> parameters = new HashMap<Integer, Object>();

            parameters.put(Report.PERIOD_START_PARAM, periodDatePicker.getStartDateTime());

            parameters.put(Report.PERIOD_END_PARAM, periodDatePicker.getFinishDateTime());

            ((ReportActivity) Objects.requireNonNull(getActivity())).onExecuteReport((ReportName) getArguments().getSerializable(REPORT_ARG), parameters);

        }

    }



}
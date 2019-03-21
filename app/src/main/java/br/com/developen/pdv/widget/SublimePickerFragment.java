package br.com.developen.pdv.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

import androidx.fragment.app.DialogFragment;
import br.com.developen.pdv.R;

public class SublimePickerFragment extends DialogFragment {


    private DateFormat mDateFormatter, mTimeFormatter;

    private SublimePicker mSublimePicker;

    private Callback mCallback;


    SublimeListenerAdapter mListener = new SublimeListenerAdapter() {

        public void onCancelled() {

            if (mCallback != null)

                mCallback.onCancelled();

            dismiss();

        }

        public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker,
                                            SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            if (mCallback != null)

                mCallback.onDateTimeRecurrenceSet(
                        selectedDate,
                        hourOfDay,
                        minute,
                        recurrenceOption,
                        recurrenceRule);

            dismiss();

        }

    };


    public SublimePickerFragment() {

        mDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

        mTimeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());

        mTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));

    }


    public void setCallback(Callback callback) {

        mCallback = callback;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSublimePicker = (SublimePicker) getActivity().getLayoutInflater().
                inflate(R.layout.fragment_sublime_picker, container);

        Bundle arguments = getArguments();

        SublimeOptions options = null;

        if (arguments != null)

            options = arguments.getParcelable("SUBLIME_OPTIONS");

        mSublimePicker.initializePicker(options, mListener);

        return mSublimePicker;

    }


    public interface Callback {

        void onCancelled();

        void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                     int hourOfDay, int minute,
                                     SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                     String recurrenceRule);

    }


}
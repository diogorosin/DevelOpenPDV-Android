// https://medium.com/@otoloye/creating-custom-components-in-android-3d24a2bdaebd

package br.com.developen.pdv.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import br.com.developen.pdv.R;
import br.com.developen.pdv.utils.StringUtils;

public class PeriodDatePicker extends LinearLayout {


    private Date startDateTime, finishDateTime;

    private TextView startTextView, finishTextView;


    private SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {

        public void onCancelled() {}

        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            if (selectedDate.getType() == SelectedDate.Type.SINGLE){

                setStartDateTime(selectedDate.getFirstDate().getTime());

                setFinishDateTime(selectedDate.getSecondDate().getTime());

            } else {

                setStartDateTime(selectedDate.getStartDate().getTime());

                setFinishDateTime(selectedDate.getEndDate().getTime());

            }

        }

    };


    public PeriodDatePicker(Context context) {

        super(context);

        init();

    }


    public PeriodDatePicker(Context context, AttributeSet attributeSet) {

        super(context, attributeSet);

        init();

    }


    public PeriodDatePicker(Context context, AttributeSet attributeSet, int defStyleAttr) {

        super(context, attributeSet, defStyleAttr);

        init();

    }


    private void init(){

        View view = inflate(getContext(), R.layout.period_date_picker, this);

        startTextView = view.findViewById(R.id.period_date_picker_start);

        finishTextView = view.findViewById(R.id.period_date_picker_finish);

        this.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                SublimePickerFragment pickerFrag = new SublimePickerFragment();

                pickerFrag.setCallback(mFragmentCallback);

                Pair<Boolean, SublimeOptions> optionsPair = getOptions();

                Bundle bundle = new Bundle();

                bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);

                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                pickerFrag.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "SUBLIME_PICKER");

            }

        });

    }


    private Pair<Boolean, SublimeOptions> getOptions() {

        SublimeOptions options = new SublimeOptions();

        int displayOptions = 0;

        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;

        //displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;

        //displayOptions |= SublimeOptions.ACTIVATE_RECURRENCE_PICKER;

        /* if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        } else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
        } */

        options.setDisplayOptions(displayOptions);

        options.setCanPickDateRange(true);

        Calendar todayCalendar = Calendar.getInstance();

        todayCalendar.setTime(new Date());

        Calendar startCal = Calendar.getInstance();
        startCal.set(todayCalendar.get(Calendar.YEAR),
                todayCalendar.get(Calendar.MONTH),
                todayCalendar.get(Calendar.DAY_OF_MONTH));

        Calendar endCal = Calendar.getInstance();
        endCal.set(todayCalendar.get(Calendar.YEAR),
                todayCalendar.get(Calendar.MONTH),
                todayCalendar.get(Calendar.DAY_OF_MONTH));

        options.setDateParams(startCal, endCal);

        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);

    }


    public Date getStartDateTime() {

        return startDateTime;

    }


    public void setStartDateTime(Date startDateTime) {

        this.startDateTime = startDateTime;

        if (this.startDateTime != null)

            startTextView.setText(StringUtils.formatDate(this.startDateTime));

        else

            startTextView.setText("");

    }


    public Date getFinishDateTime() {

        return finishDateTime;

    }


    public void setFinishDateTime(Date finishDateTime) {

        this.finishDateTime = finishDateTime;

        if (this.finishDateTime != null)

            finishTextView.setText(StringUtils.formatDate(this.finishDateTime));

        else

            finishTextView.setText("");

    }


}
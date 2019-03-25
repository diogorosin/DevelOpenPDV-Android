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


    private Date startDateTime, endDateTime;

    private TextView startTextView, endTextView;


    private SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {

        public void onCancelled() {}

        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            if (selectedDate.getType() == SelectedDate.Type.SINGLE){

                setStartDateTime(selectedDate.getFirstDate().getTime());

                setEndDateTime(selectedDate.getSecondDate().getTime());

            } else {

                setStartDateTime(selectedDate.getStartDate().getTime());

                setEndDateTime(selectedDate.getEndDate().getTime());

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

        endTextView = view.findViewById(R.id.period_date_picker_end);

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

        options.setDisplayOptions(displayOptions);

        options.setCanPickDateRange(true);


        Calendar startCalendar = Calendar.getInstance();

        startCalendar.setTime(getStartDateTime());

        startCalendar.set(Calendar.MILLISECOND, 0);

        startCalendar.set(Calendar.SECOND, 0);

        startCalendar.set(Calendar.MINUTE, 0);

        startCalendar.set(Calendar.HOUR_OF_DAY, 0);


        Calendar endCalendar = Calendar.getInstance();

        endCalendar.setTime(getEndDateTime());

        endCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.SECOND, 0);

        endCalendar.set(Calendar.MINUTE, 0);

        endCalendar.set(Calendar.HOUR_OF_DAY, 0);


        options.setDateParams(startCalendar, endCalendar);

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


    public Date getEndDateTime() {

        return endDateTime;

    }


    public void setEndDateTime(Date finishDateTime) {

        this.endDateTime = finishDateTime;

        if (this.endDateTime != null)

            endTextView.setText(StringUtils.formatDate(this.endDateTime));

        else

            endTextView.setText("");

    }


}
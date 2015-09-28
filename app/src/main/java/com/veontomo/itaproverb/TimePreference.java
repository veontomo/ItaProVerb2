package com.veontomo.itaproverb;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.veontomo.itaproverb.api.Config;

import java.util.Calendar;
import java.util.Date;

/**
 * Dialog to select a time of a day.
 * http://www.vogella.com/tutorials/AndroidFileBasedPersistence/article.html
 */
public class TimePreference extends DialogPreference {
    private Calendar calendar;
    private TimePicker picker = null;

    public TimePreference(Context ctxt) {
        this(ctxt, null);
    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        this(ctxt, attrs, android.R.attr.dialogPreferenceStyle);
    }

    public TimePreference(Context ctxt, AttributeSet attrs, int defStyle) {
        super(ctxt, attrs, defStyle);

        setPositiveButtonText("confirm");
        setNegativeButtonText("cancel");
        calendar = Calendar.getInstance();
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        return picker;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        picker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        int hour, minute;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            hour = picker.getCurrentHour();
            minute = picker.getCurrentMinute();
        } else{
            hour = picker.getHour();
            minute = picker.getMinute();
        }
        Log.i(Config.APP_NAME, "hour: " + picker.getCurrentHour() + ", minutes: " + picker.getCurrentMinute());
        if (positiveResult) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            Log.i(Config.APP_NAME, "positive");
            setSummary(getSummary());
            if (callChangeListener(calendar.getTimeInMillis())) {
                Log.i(Config.APP_NAME, "calendar millis: " + calendar.getTimeInMillis());
                persistLong(calendar.getTimeInMillis());
                notifyChanged();
            }
        } else {
            Log.i(Config.APP_NAME, "negative");
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
            if (defaultValue == null) {
                calendar.setTimeInMillis(System.currentTimeMillis());
            } else {
                calendar.setTimeInMillis(Long.parseLong(getPersistedString((String) defaultValue)));
            }
        } else {
            if (defaultValue == null) {
                calendar.setTimeInMillis(System.currentTimeMillis());
            } else {
                calendar.setTimeInMillis(Long.parseLong((String) defaultValue));
            }
        }
        setSummary(getSummary());
    }

    @Override
    public CharSequence getSummary() {

        if (calendar == null) {
            return null;
        }
        String s = DateFormat.getTimeFormat(getContext()).format(new Date(calendar.getTimeInMillis()));
        return s;
    }
}
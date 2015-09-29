package com.veontomo.itaproverb;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
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
    private final Calendar calendar;
    /**
     * Unix time at which the notifications should be sent.
     * <p/>
     * Though this parameter defines a single moment on a timeline, only hour and minute
     * of a day corresponding that moment are taken into consideration and then repeated
     * over all days. In other words, time is defined modulo number of milliseconds in a day
     * (i.e. 24 * 60 * 60 * 1000)
     */
    private long time = -1;


    private TimePicker picker = null;

    public TimePreference(Context context) {
        this(context, null);
        Log.i(Config.APP_NAME, "TimePreference: single arg constructor " + Thread.currentThread().getStackTrace()[2].getMethodName());

    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        this(ctxt, attrs, android.R.attr.dialogPreferenceStyle);
        Log.i(Config.APP_NAME, "TimePreference: two arg constructor " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    public TimePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.i(Config.APP_NAME, "TimePreference: three arg constructor " + Thread.currentThread().getStackTrace()[2].getMethodName());
        setPositiveButtonText("confirm");
        setNegativeButtonText("cancel");
        calendar = Calendar.getInstance();

    }

    @Override
    protected View onCreateDialogView() {
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName());
        picker = new TimePicker(getContext());
        picker.setIs24HourView(true);
        return picker;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        Log.i(Config.APP_NAME, "TimePreference onBindDialogView");
        time = getPersistedLong(-1);
        Log.i(Config.APP_NAME, "preference time: " + time);
        if (time != -1) {
            calendar.setTime(new Date(time));
            picker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            picker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName());
        int hour, minute;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            hour = picker.getCurrentHour();
            minute = picker.getCurrentMinute();
        } else {
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
                long t = calendar.getTimeInMillis();
                long t0 = getPersistedLong(-2);
                boolean b = persistLong(t);
                Log.i(Config.APP_NAME, "persisting long " + t + " vs " + t0 + ", result: " + b);
                notifyChanged();
            }
        } else {
            Log.i(Config.APP_NAME, "negative");
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName());
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (restoreValue) {
            time = getPersistedLong(-1);
            if (time == -1){
                time = System.currentTimeMillis();
            }
            calendar.setTimeInMillis(time);
        }
        setSummary(getSummary());
    }

    @Override
    public CharSequence getSummary() {
        Log.i(Config.APP_NAME, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (calendar == null) {
            return null;
        }
        return calendar.getTime().toString();
    }
}
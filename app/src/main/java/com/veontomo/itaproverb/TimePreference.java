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

    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        this(ctxt, attrs, android.R.attr.dialogPreferenceStyle);
    }

    public TimePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPositiveButtonText(context.getString(R.string.confirm));
        setNegativeButtonText(context.getString(R.string.cancel));
        calendar = Calendar.getInstance();

    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        picker.setIs24HourView(true);
        return picker;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        time = getPersistedLong(-1);
        if (time != -1) {
            calendar.setTimeInMillis(time);
            picker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            picker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        int hour, minute;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            hour = picker.getCurrentHour();
            minute = picker.getCurrentMinute();
        } else {
            hour = picker.getHour();
            minute = picker.getMinute();
        }
        if (positiveResult) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            setSummary(getSummary());
            if (callChangeListener(calendar.getTimeInMillis())) {
                long t = calendar.getTimeInMillis();
                persistLong(t);
                notifyChanged();
            }
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
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }
}
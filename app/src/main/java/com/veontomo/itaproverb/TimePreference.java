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
import com.veontomo.itaproverb.api.Notificator;

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

    }

    public TimePreference(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.dialogPreferenceStyle);
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
        calendar.setTimeInMillis(time);
        picker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        picker.setCurrentMinute(calendar.get(Calendar.MINUTE));
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
            long t = calendar.getTimeInMillis();
            if (callChangeListener(t)) {
                persistLong(t);
                notifyChanged();
                Notificator.start(getContext());
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
            time = getPersistedLong(System.currentTimeMillis());
            calendar.setTimeInMillis(time);
        }
        setSummary(getSummary());
    }

    @Override
    public CharSequence getSummary() {
        if (calendar == null) {
            return null;
        }
        return DateFormat.getTimeFormat(getContext()).format(new Date(time));
    }
}
package com.veontomo.itaproverb.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mario Rossi on 23/09/2015 at 12:25.
 *
 * @author veontomo@gmail.com
 * @since xx.xx
 */
public class FragShowSingleProverbDay extends FragShowSingle {
    /**
     * Format in which it is represented time in today's proverb table.
     */
    private static final String dateFormat = "dd MMM";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(Config.APP_NAME, "FragShowSingleProverbDay: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        return inflater.inflate(R.layout.frag_show_single_proverb_day, container, false);
    }


    @Override
    public void updateView() {
        super.updateView();
        String date = mProverb.date;
        if (date != null){
            loadDate(date);
        }
    }

    private void loadDate(String date){
        String dateNew = reformatDate(date, Config.DATE_FORMAT_STORAGE, dateFormat);
        if (dateNew != null) {
            TextView mTextView = (TextView) getActivity().findViewById(R.id.frag_show_single_date);
            mTextView.setText(dateNew);
        }

    }



    /**
     * Converts a date represented as a string from one format into another.
     * @param dateOrig  date
     * @param formatOrig  format in which the date is represented
     * @param formatNew   new format
     * @return string
     */
    private String reformatDate(String dateOrig, String formatOrig, String formatNew){
        DateFormat df = new SimpleDateFormat(formatOrig);
        Date date;
        try {
            date = df.parse(dateOrig);
        } catch (ParseException e) {
            Log.i(Config.APP_NAME, "failed to parse date " + dateOrig);
            return null;
        }
        df = new SimpleDateFormat(formatNew);
        return df.format(date);
    }
}

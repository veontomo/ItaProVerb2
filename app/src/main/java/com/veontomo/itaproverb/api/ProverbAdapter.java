package com.veontomo.itaproverb.api;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.veontomo.itaproverb.R;

import java.util.List;

/**
 * Created by Mario Rossi on 09/09/2015 at 21:28.
 *
 * @author veontomo@gmail.com
 * @since 0
 */
public class ProverbAdapter extends BaseAdapter {

    /**
     * Current context
     */
    private final Context mContext;
    /**
     * List of proverbs
     */
    private List<Proverb> mItems;

    @Override
    public int getCount() {
        if (this.mItems == null) {
            return 0;
        }
        return this.mItems.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int index) {
        return index % 3 == 0 ? 0 : 1;
    }



    public ProverbAdapter(Context context, List<Proverb> proverbs) {
        this.mContext = context;
        this.mItems = proverbs;
    }

    @Override
    public Proverb getItem(int position) {
        return this.mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(Config.APP_NAME, "position = " + position + " convertView is " + (convertView == null ? "NULL" : "NOT null"));
        View row = convertView;
        if (position %3 == 0) {
            if (row == null) {
                AdHolder adHolder = new AdHolder();
                row = new TextView(mContext);
                adHolder.text = (TextView) row;
                Log.i(Config.APP_NAME, "set tag of type AdHolder at position " + position);
                row.setTag(adHolder);
            }
            AdHolder holder = (AdHolder) row.getTag();
            holder.text.setText(String.valueOf(position));

        } else {
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.proverb_row, parent, false);
                ProverbHolder proverbHolder = new ProverbHolder();
                proverbHolder.text = (TextView) row.findViewById(R.id.layout_proverb_row_text);
                Log.i(Config.APP_NAME, "set tag of type ProverbHolder at position " + position);
                row.setTag(proverbHolder);
            }
            ProverbHolder holder = (ProverbHolder) row.getTag();
            holder.text.setText(this.getItem(position).text);

        }
        return row;
    }

    /**
     * Loads proverbs.
     *
     * @param proverbs
     */
    public void load(List<Proverb> proverbs) {
        this.mItems = proverbs;
        Log.i(Config.APP_NAME, "loading items: " + proverbs.size());
    }

    private static class ProverbHolder {
        public TextView text;
    }

    private static class AdHolder {
        public TextView text;
    }
}

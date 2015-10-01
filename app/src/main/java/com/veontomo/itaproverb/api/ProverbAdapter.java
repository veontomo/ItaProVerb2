package com.veontomo.itaproverb.api;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.veontomo.itaproverb.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mario Rossi on 09/09/2015 at 21:28.
 */
public class ProverbAdapter extends BaseAdapter {

    /**
     * Current context
     */
    private final Context mContext;

    /**
     * Fraction with which the proverbs are mixed with ads.
     */
    private final float fraction;

    /**
     * List of proverbs
     */
    private List<Proverb> mItems;


    /**
     * Mapping from proverb-ad mix into proverb numbers.
     * Non-negative values correspond to proverb ids, -1 - to ads.
     * <p>
     * For example, array [0, 1, 2, -1, 3, 4, -1] means that there are 3 proverbs
     * (0, 1 and 2) followed by an ad, then other two proverbs followed by another ad.
     * </p><p>
     * {@link #mapping} is null if {@link #mItems} is null or is an empty list.</p>
     */
    private int[] mapping;

    /**
     * Constant to distinguish a proverb (from an ad)
     */
    private final static int TYPE_PROVERB = 0;
    /**
     * Constant to distinguish an ad (from a proverb)
     */
    private final static int TYPE_AD = 1;

    @Override
    public int getCount() {
        if (this.mapping == null) {
            return 0;
        }
        return this.mapping.length;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (this.mapping != null) {
            return this.mapping[position] == -1 ? TYPE_AD : TYPE_PROVERB;
        }
        return TYPE_PROVERB;
    }


    public ProverbAdapter(Context context, List<Proverb> proverbs, float fraction) {
        this.mContext = context;
        this.mItems = proverbs;
        this.fraction = fraction;
        initMapping();

    }

    private void initMapping() {
        if (this.mItems != null) {
            int size = this.mItems.size();
            if (size > 0) {
                int extra = (int) (size * fraction);
                this.mapping = createMapping(size, extra);
                Log.i(Config.APP_NAME, show());
            }
        }
    }

    private String show() {
        if (this.mapping == null) {
            return "mapping is NULL";
        }
        String str = "mapping: ";
        for (int i = 0; i < this.mapping.length; i++) {
            str += String.valueOf(this.mapping[i]) + ", ";
        }
        return str;
    }

    /**
     * Creates an array from consecutive n integer numbers from 0 to n-1 in which number -1 gets
     * inserted m-many times randomly.
     *
     * @param n must be positive
     * @param m
     * @return array of size n+m
     */
    private int[] createMapping(int n, int m) {
        // list of positions of the insertions of -1's
        List<Integer> seeds = new ArrayList<>();
        Random randomGenerator = new Random();
        int pos;
        // generate list in which there is no pair of equal elements
        for (int i = 0; i < m; i++) {
            do {
                pos = randomGenerator.nextInt(n + m);
            } while (seeds.contains(pos));
            seeds.add(pos);
        }
        int[] list = new int[n + m];
        pos = 0;
        for (int i = 0; i < n; i++) {
            list[pos] = i;
            if (seeds.contains(pos)) {
                pos++;
                list[pos] = -1;
            }
            pos++;
        }
        return list;
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
        int itemType = getItemViewType(position);

        if (itemType == TYPE_AD) {
            Log.i(Config.APP_NAME, "TYPE = AD");
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
            Log.i(Config.APP_NAME, "TYPE = Proverb ? " + (itemType == TYPE_PROVERB));
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.proverb_row, parent, false);
                ProverbHolder proverbHolder = new ProverbHolder();
                proverbHolder.text = (TextView) row.findViewById(R.id.layout_proverb_row_text);
                Log.i(Config.APP_NAME, "set tag of type ProverbHolder at position " + position);
                row.setTag(proverbHolder);
            }
            ProverbHolder holder = (ProverbHolder) row.getTag();
            holder.text.setText(this.getItem(this.mapping[position]).text);

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
        initMapping();
        Log.i(Config.APP_NAME, "loading items: " + proverbs.size());
    }

    private static class ProverbHolder {
        public TextView text;
    }

    private static class AdHolder {
        public TextView text;
    }
}

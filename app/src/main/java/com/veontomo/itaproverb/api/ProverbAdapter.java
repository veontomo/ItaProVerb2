package com.veontomo.itaproverb.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.veontomo.itaproverb.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mario Rossi on 09/09/2015 at 21:28.
 */
public class ProverbAdapter extends BaseAdapter {

    /**
     * Constant to distinguish a proverb (from an ad)
     */
    private final static int TYPE_PROVERB = 0;
    /**
     * Constant to distinguish an ad (from a proverb)
     */
    private final static int TYPE_AD = 1;
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

    public ProverbAdapter(Context context, List<Proverb> proverbs, float fraction) {
        this.mContext = context;
        this.mItems = proverbs;
        this.fraction = fraction;
        initMapping();

    }

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

    private void initMapping() {
        if (this.mItems != null) {
            int size = this.mItems.size();
            if (size > 0) {
                int extra = (int) (size * fraction);
                this.mapping = createMapping(size, extra);
                // show());
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
        int size = this.mItems == null ? 0 : this.mItems.size();
        if (size > 0 && position < size) {
            return this.mItems.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_AD:
                if (row == null) {
                    AdHolder adHolder = new AdHolder();
                    row = new AdView(mContext);
                    adHolder.text = (AdView) row;
                    row.setTag(adHolder);
                    adHolder.text.setAdUnitId(Config.AD_UNIT_ID);
                    adHolder.text.setAdSize(AdSize.BANNER);
                }
                AdHolder holder = (AdHolder) row.getTag();
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest request = builder.build();
                holder.text.loadAd(request);
                break;
            case TYPE_PROVERB:
                if (row == null) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(R.layout.proverb_row, parent, false);
                    ProverbHolder proverbHolder = new ProverbHolder();
                    proverbHolder.text = (TextView) row.findViewById(R.id.layout_proverb_row_text);
                    row.setTag(proverbHolder);
                }
                ProverbHolder holder2 = (ProverbHolder) row.getTag();
                holder2.text.setText(getItemText(position));
                break;
            default:
                Logger.i("unknown item type");

        }
        return row;
    }

    /**
     * Returns text of a proverb from given position, or null if there is no
     * proverb at that position.
     *
     * @param position
     * @return
     */
    private String getItemText(int position) {
        int index = getItemIndex(position, -1);
        if (index != -1) {
            Proverb p = this.getItem(index);
            if (p != null) {
                return p.text;
            }
        }
        return null;
    }

    /**
     * Loads proverbs.
     *
     * @param proverbs
     */
    public void load(List<Proverb> proverbs) {
        this.mItems = proverbs;
        initMapping();
        Logger.i("loading items: " + proverbs.size());
    }

    /**
     * Returns the original number of the item that the adapter now contains at position n.
     * (remember, that the adapter mixes original items with ads). If the adapter now at
     * given position contains not a proverb, but an ad, then the default value is returned.
     *
     * @param n
     * @param n0 default value
     * @return
     */
    public int getItemIndex(int n, int n0) {
        // returning default value
        if (this.mapping == null) {
            return n0;
        }
        int i = this.mapping.length;
        if (i == 0 || i <= n) {
            return n0;
        }
        // checking what the mapping contains at position n
        i = this.mapping[n];
        if (i == -1) {
            return n0;
        }
        return i;
    }

    private static class ProverbHolder {
        public TextView text;
    }

    private static class AdHolder {
        public AdView text;
    }
}

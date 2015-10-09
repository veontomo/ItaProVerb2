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

import java.util.List;

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
     * the number of items that this adapter visualizes on the screen (taking into account the
     * position of ad)
     */
    private int mSize = 0;
    /**
     * Position (zero-based) of the ad inside the list that this adapter should visualize.
     */
    private int adPos = -1;
    /**
     * List of proverbs
     */
    private List<Proverb> mItems;

    public ProverbAdapter(Context context, List<Proverb> proverbs) {
        this.mContext = context;
        this.mItems = proverbs;
        this.mSize = proverbs != null ? proverbs.size() : 0;
    }

    /**
     * Sets the position in the resulting list at which the ad should be displayed
     *
     * @param adPos
     */
    public void setAdPosition(int adPos) {
        this.adPos = adPos;
        if (adPos != -1 && this.mSize >= adPos) {
            this.mSize++;
        }
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == adPos ? TYPE_AD : TYPE_PROVERB;
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
        if (position == adPos){
            // there is an ad at this position!
            return null;
        }
        int index;
        if (adPos != -1 && position > adPos) {
            index = position - 1;
        } else {
            index = position;
        }
        Proverb p = this.getItem(index);
        return p.text;
    }

    /**
     * Returns index of a proverb from initial list {@link #mItems}  that now is situated at
     * given position (that might change due to the insertion of ad).
     *
     * @param position
     * @return
     */
    public int getItemIndex(int position, int defaultPos) {
        if (position == adPos){
            // there is an ad at this position!
            return defaultPos;
        }
        int index;
        if (adPos != -1 && position > adPos) {
            index = position - 1;
        } else {
            index = position;
        }
        return index;
    }


    /**
     * Loads proverbs and adjust the value of the list size
     *
     * @param proverbs
     */
    public void load(List<Proverb> proverbs) {
        this.mItems = proverbs;
        int size = proverbs != null ? proverbs.size() : 0;
        if (adPos != -1 && size >= adPos) {
            size++;
        }
        this.mSize = size;
    }


    private static class ProverbHolder {
        public TextView text;
    }

    private static class AdHolder {
        public AdView text;
    }
}

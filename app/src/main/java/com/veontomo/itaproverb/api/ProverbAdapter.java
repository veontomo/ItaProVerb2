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
        View row = convertView;

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.proverb_row, parent, false);
            Holder holder = new Holder();
            holder.text = (TextView) row.findViewById(R.id.layout_proverb_row_text);
            row.setTag(holder);
        }
        Holder holder = (Holder) row.getTag();
        holder.text.setText(this.getItem(position).text);
        return row;
    }

    /**
     * Loads proverbs.
     * @param proverbs
     */
    public void load(List<Proverb> proverbs) {
        this.mItems = proverbs;
    }

    private static class Holder {
        public TextView text;
    }
}

package com.veontomo.itaproverb.activities;

import android.view.View;
import android.view.ViewGroup;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.Storage;

import java.util.List;

/**
 * Activity that displays favorite proverbs
 * @see ActMultiBase
 */
public class ActFavoriteProverbs extends ActMultiBase {

    @Override
    public List<Proverb> getItems(Storage storage) {
        return storage.getFavorites();
    }

    /**
     * Disables search input field
     */
    @Override
    public void onEmptyInput() {
        super.onEmptyInput();
        View v = getLayoutInflater().inflate(R.layout.empty_input, null);
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.act_favorites_root);
        insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}

package com.veontomo.itaproverb.activities;

import android.content.Intent;
import android.util.Log;

import com.veontomo.itaproverb.api.Config;
import com.veontomo.itaproverb.api.Proverb;
import com.veontomo.itaproverb.api.Storage;

import java.util.List;

/**
 * Activity that displays favorite proverbs
 * @see ActMultiBase
 */
public class ActAllProverbs extends ActMultiBase  {
    @Override
    public List<Proverb> getItems(Storage storage) {
        return storage.getAllProverbs();
    }

}

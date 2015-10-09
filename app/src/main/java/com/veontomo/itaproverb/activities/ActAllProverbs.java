package com.veontomo.itaproverb.activities;

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
    public int getAdPosition(){
        return Config.AD_POSITION;
    }


    @Override
    public List<Proverb> getItems(Storage storage) {
        return storage.getAllProverbs();
    }

}

package com.veontomo.itaproverb.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.veontomo.itaproverb.R;
import com.veontomo.itaproverb.api.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads proverbs from given file into storage.
 */
public class ProverbLoaderTask extends AsyncTask<Void, Integer, Boolean> {
    private final String encoding;
    private final String mFileName;
    private final Context mContext;

    /**
     * Constructor
     *
     * @param fileName file to read from
     * @param encoding file encoding
     */
    public ProverbLoaderTask(final Context context, final String fileName, final String encoding) {
        this.mContext = context;
        this.encoding = encoding;
        this.mFileName = fileName;
    }

    /**
     * Retrieves the proverbs: if the Storage contains the table with proverbs, then load the
     * proverbs from that table. Otherwise read them from file and save them to the
     * proverb table.
     *
     * @param voids the method ignore the parameters
     * @return true if after execution the storage contains the proverbs, false otherwise
     */
    protected Boolean doInBackground(Void... voids) {
        Storage storage = new Storage(mContext);
        boolean tableExists = storage.tableProverbExists();
        if (!tableExists) {
            List<String> data = readFromFile();
            return storage.saveAsProverbs(data);
        }
        return true;
    }


    /**
     * Reads proverbs from the file located in the app assets folder
     *
     * @return list of strings
     * @since 0.1
     */
    private List<String> readFromFile() {
        InputStream stream = null;
        BufferedReader bufferedReader;
        List<String> block = new ArrayList<>();
        try {
            stream = this.mContext.getAssets().open(mFileName);
            bufferedReader = new BufferedReader(new InputStreamReader(stream, encoding));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                block.add(line);
            }
            stream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return block;

    }

    protected void onProgressUpdate(Integer... progress) {
    }


    @Override
    protected void onPostExecute(Boolean b) {
        String message = b ? mContext.getString(R.string.proverb_loading_success) : mContext.getString(R.string.proverb_loading_failure);
        Toast.makeText(this.mContext, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
    }


}

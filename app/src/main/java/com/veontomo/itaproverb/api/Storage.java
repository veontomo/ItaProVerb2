package com.veontomo.itaproverb.api;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs operations with saving proverbs in database.
 */
public class Storage extends SQLiteOpenHelper {
    /**
     * current version of the database
     */
    private static final int DATABASE_VERSION = 3;

    /**
     * Name of database that contains tables of the application
     */
    private static final String DATABASE_NAME = "ItaProVerbs";


    /**
     * Application context
     */
    private final Context mContext;

    /**
     * Constructor
     *
     * @param context application context
     * @since 0.1
     */
    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }


    /**
     * Returns true if table with proverbs exists and is not empty,
     * false otherwise.
     *
     * @return true if table exists, false otherwise.
     */
    public boolean tableProverbExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(ProverbQueries.NUMBER_OF_RECORDS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }


    /**
     * context getter.
     *
     * @return application context
     * @since 0.1
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Creates database.
     * <p>
     * This code is called if the database has not been created yet.
     * </p>
     *
     * @param db reference to database
     * @since 0.1
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // version number 1
        db.execSQL(ProverbQueries.CREATE_TABLE);
        // version number 2
        db.execSQL(FavoriteQueries.CREATE_TABLE);
        // version number 3
        db.execSQL(TodayProverbsQueries.CREATE_TABLE);
    }


    /**
     * Performs step-by-step migrations from older versions to newer.
     * <p>
     * This code is called if the database has already been created but its version (oldVersion)
     * is inferior to required one (newVersion).
     * </p><p>
     * It controls what is the previous version of the database (oldVersion) and
     * performs operations that are required to arrive to the latest one (newVersion).
     * </p>
     *
     * @param db         reference to database
     * @param oldVersion current version of the database
     * @param newVersion required version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            // empty task: nothing to do
        }

        if (oldVersion < 2) {
            db.execSQL(FavoriteQueries.CREATE_TABLE);
        }

        if (oldVersion < 3) {
            db.execSQL(TodayProverbsQueries.CREATE_TABLE);
        }


    }

    /**
     * Saves each string of the given list as a proverb content.
     *
     * @param data list of proverb contents
     * @return true if all strings have been saved successfully, false otherwise
     */
    public boolean saveAsProverbs(List<String> data) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        Boolean outcome = true;
        long id;
        int size = data.size();
        for (int i = 0; i < size; i++) {
            values = new ContentValues();
            values.put(ProverbEntry.COLUMN_TEXT, data.get(i));
            id = db.insert(ProverbEntry.TABLE_NAME, null, values);
            if (id == -1) {
                outcome = false;
            }
        }
        try {
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return outcome;

    }

    /**
     * Returns list of favorite proverbs
     * @return
     */
    public List<Proverb> getFavorites() {
        List<Proverb> proverbs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(FavoriteQueries.FAVORITE_PROVERBS, null);
        int textColId = cursor.getColumnIndex(ProverbEntry.COLUMN_TEXT);
        int idColId = cursor.getColumnIndex(ProverbEntry._ID);
        if (cursor.moveToFirst()) {
            Proverb proverb;
            int id;
            do {
                id = (int) cursor.getLong(idColId);
                proverb = new Proverb(id, cursor.getString(textColId), true);
                proverbs.add(proverb);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return proverbs;

    }



    /**
     * Returns list of favorite proverb ids.
     * <p/>
     * This list is used by a method that retrieves all proverbs and controls
     * whether a given proverb is favorite or not. In order to avoid multiple
     * requests to a database, the list of favorite ids is retrieved just ones
     * and then it is controlled whether the id of given proverb belongs to
     * this list or not.
     *
     * @return list of ids of favorite proverbs
     */
    private List<Integer> retrieveFavoriteIds(SQLiteDatabase db) {
        List<Integer> favorites = new ArrayList<>();
        Cursor cursor = db.rawQuery(FavoriteQueries.SELECT_PROVERB_IDS, null);
        int proverbIdColNum = cursor.getColumnIndex(FavoriteEntry.COLUMN_PROVERB_ID);
        if (cursor.moveToFirst()) {
            do {
                favorites.add((int) cursor.getLong(proverbIdColNum));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favorites;
    }

    /**
     * Returns list of all proverbs taking into account their status (favorite/non-favorite)
     * @return
     */
    public List<Proverb> getAllProverbs() {
        List<Proverb> proverbs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(ProverbQueries.SELECT_ALL, null);
        int textColId = cursor.getColumnIndex(ProverbEntry.COLUMN_TEXT);
        int idColId = cursor.getColumnIndex(ProverbEntry._ID);
        List<Integer> favoriteIds = retrieveFavoriteIds(db);

        if (cursor.moveToFirst()) {
            Proverb proverb;
            int id;
            do {
                id = (int) cursor.getLong(idColId);
                proverb = new Proverb(id, cursor.getString(textColId), favoriteIds.contains(id));
                proverbs.add(proverb);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return proverbs;

    }

    /**
     * Returns randomly chosen proverb.
     * @return proverb
     */
    public Proverb getRandomProverb() {
        SQLiteDatabase db = getReadableDatabase();
        List<Integer> favoriteIds = retrieveFavoriteIds(db);
        Cursor cursor = db.rawQuery(ProverbQueries.SELECT_RANDOM, null);
        int textColId = cursor.getColumnIndex(ProverbEntry.COLUMN_TEXT);
        int idColId = cursor.getColumnIndex(ProverbEntry._ID);
        Proverb proverb = null;
        int proverbId;
        if (cursor.moveToFirst()) {
            proverbId = cursor.getInt(idColId);
            proverb = new Proverb(proverbId, cursor.getString(textColId), favoriteIds.contains(proverbId));
        }
        cursor.close();
        db.close();
        return proverb;
    }

    /**
     * Removes proverb with given id.
     * <p>If necessary, remove the proverb from the favorites</p>
     * @param id
     * @return true if the proverb is deleted from the database, false otherwise
     */
    public boolean removeProverb(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(FavoriteEntry.TABLE_NAME, FavoriteEntry.COLUMN_PROVERB_ID + " = ?", new String[]{String.valueOf(id)});
        int numOfLines = db.delete(ProverbEntry.TABLE_NAME, ProverbEntry._ID + " = ?", new String[]{String.valueOf(id)});
        try {
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return numOfLines > 0;
    }

    /**
     * Updates the proverb with given id.
     * @param id proverb id
     * @param text new text of the proverb
     * @return true, if the update succeeds, false otherwise
     */
    public boolean updateProverb(int id, String text) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProverbEntry.COLUMN_TEXT, text);
        int numOfLines = db.update(ProverbEntry.TABLE_NAME, values, ProverbEntry._ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return numOfLines == 1;


    }

    /**
     * Removes proverb with given id from the favorites.
     * @param id
     * @return true if operation succeeds, false otherwise
     */
    public boolean removeFromFavorites(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int numOfRows = db.delete(FavoriteEntry.TABLE_NAME, FavoriteEntry.COLUMN_PROVERB_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return numOfRows > 0;
    }

    /**
     * Removes proverb with given id from the favorites
     * @param pid proverb id
     * @return true if operation succeeds, false otherwise
     */
    public boolean addToFavorites(int pid) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry.COLUMN_PROVERB_ID, pid);
        long id = db.insert(FavoriteEntry.TABLE_NAME, null, values);
        db.close();
        return id != -1;
    }


    /**
     * Scheme of a table that stores proverbs
     */
    public static abstract class ProverbEntry implements BaseColumns {
        public static final String TABLE_NAME = "Proverbs";
        public static final String COLUMN_TEXT = "text";
    }

    /**
     * Various Proverbs-table related queries
     */
    public static abstract class ProverbQueries {
        public static final String CREATE_TABLE = "CREATE TABLE " +
                ProverbEntry.TABLE_NAME + " (" +
                ProverbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProverbEntry.COLUMN_TEXT + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE)";
        public static final String NUMBER_OF_RECORDS = "SELECT COUNT(*) FROM " + ProverbEntry.TABLE_NAME + ";";
        public static final String SELECT_ALL = "SELECT * FROM " + ProverbEntry.TABLE_NAME + ";";
        public static final String SELECT_RANDOM = "SELECT * FROM " + ProverbEntry.TABLE_NAME +
                " ORDER BY RANDOM() LIMIT 1;";
    }

    /**
     * Scheme of a table that stores favorite proverbs
     */
    public static abstract class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "Favorites";
        public static final String COLUMN_PROVERB_ID = "proverb_id";
    }

    /**
     * Various Favorites-table related queries
     */
    public static abstract class FavoriteQueries {
        /**
         * Query to construct the table
         */
        public static final String CREATE_TABLE = "CREATE TABLE " +
                FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteEntry.COLUMN_PROVERB_ID + " INTEGER, " +
                "FOREIGN KEY(" + FavoriteEntry.COLUMN_PROVERB_ID + ") REFERENCES " + ProverbEntry.TABLE_NAME + "(" + ProverbEntry._ID + ")" +
                ")";
        /**
         * Query to retrieve ids and content of proverbs that are favorites
         */
        public static final String FAVORITE_PROVERBS = "SELECT * FROM " + ProverbEntry.TABLE_NAME +
                " WHERE " + ProverbEntry._ID + " IN (SELECT " + FavoriteEntry.COLUMN_PROVERB_ID + " FROM " + FavoriteEntry.TABLE_NAME + ");";
        /**
         * Query to retrieve ids of the favorite proverbs
         */
        public static final String SELECT_PROVERB_IDS = "SELECT " + FavoriteEntry.COLUMN_PROVERB_ID +
                " FROM " + FavoriteEntry.TABLE_NAME + ";";
    }

    public static abstract class TodayProverbsEntry implements BaseColumns {
        public static final String TABLE_NAME = "TodayProverbs";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PROVERB_ID = "proverb_id";
    }

    public static abstract class TodayProverbsQueries {
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TodayProverbsEntry.TABLE_NAME + " (" +
                TodayProverbsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TodayProverbsEntry.COLUMN_DATE + " DATETIME UNIQUE NOT NULL, " +
                TodayProverbsEntry.COLUMN_PROVERB_ID + " INTEGER, " +
                "FOREIGN KEY(" + TodayProverbsEntry.COLUMN_PROVERB_ID + ") REFERENCES " + ProverbEntry.TABLE_NAME + "(" + ProverbEntry._ID + ")" +
                ")";
    }


}

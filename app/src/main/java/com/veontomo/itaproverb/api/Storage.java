package com.veontomo.itaproverb.api;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

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
        SQLiteDatabase db = this.getWritableDatabase();
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
     * @param data list of proverb contents
     */
    public boolean saveAsProverbs(List<String> data) {
        Log.i(Config.APP_NAME, "data contains " + data.size() + " elements");
        return true;

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
        public static final String SELECT_ALL = "SELECT * FROM " + ProverbEntry.TABLE_NAME + ";";
        public static final String NUMBER_OF_RECORDS = "SELECT COUNT(*) FROM " + ProverbEntry.TABLE_NAME + ";";
        public static final String SELECT_FAVORITES = "SELECT * FROM " + ProverbEntry.TABLE_NAME + " WHERE " +
                ProverbEntry._ID + " IN (SELECT " + FavoriteEntry.COLUMN_PROVERB_ID + " FROM " +
                FavoriteEntry.TABLE_NAME + ");";

        public static final String SELECT_SIMILAR = "SELECT * FROM " + ProverbEntry.TABLE_NAME +
                " WHERE " + ProverbEntry.COLUMN_TEXT + " LIKE ?";
        public static final String SELECT_RANDOM = "SELECT * FROM " + ProverbEntry.TABLE_NAME +
                " ORDER BY RANDOM() LIMIT 1; ";
        public static final String SELECT_RANDOM_NON_REPEATING = "SELECT * FROM " + ProverbEntry.TABLE_NAME +
                " WHERE " + ProverbEntry._ID + " NOT IN (SELECT " +
                TodayProverbsEntry.COLUMN_PROVERB_ID + " FROM " +
                TodayProverbsEntry.TABLE_NAME + " ORDER BY " +
                TodayProverbsEntry.COLUMN_DATE + " DESC LIMIT ?) ORDER BY RANDOM () LIMIT 1";

        public static final String RECENT_PROVERBS = "SELECT " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry._ID + " AS " + TodayProverbsEntry._ID_ALTERNATIVE + ", " +
                ProverbEntry.TABLE_NAME + "." + ProverbEntry._ID + " AS " + ProverbEntry._ID + ", " +
                ProverbEntry.TABLE_NAME + "." + ProverbEntry.COLUMN_TEXT + " AS " + ProverbEntry.COLUMN_TEXT + ", " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_DATE + " AS " + TodayProverbsEntry.COLUMN_DATE +
                " FROM " +
                ProverbEntry.TABLE_NAME +
                ", " +
                TodayProverbsEntry.TABLE_NAME +
                " WHERE " + ProverbEntry.TABLE_NAME + "." + ProverbEntry._ID + " = "
                + TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_PROVERB_ID +
                " ORDER BY " + TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_DATE + " DESC" +
                " LIMIT ?;";
        public static final String OLDER_PROVERBS = "SELECT " +
                ProverbEntry.TABLE_NAME + "." + ProverbEntry._ID + " AS " + ProverbEntry._ID + ", " +
                ProverbEntry.TABLE_NAME + "." + ProverbEntry.COLUMN_TEXT + " AS " + ProverbEntry.COLUMN_TEXT + ", " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_DATE + " AS " + TodayProverbsEntry.COLUMN_DATE + ", " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry._ID + " AS " + TodayProverbsEntry._ID_ALTERNATIVE +
                " FROM " +
                ProverbEntry.TABLE_NAME +
                ", " +
                TodayProverbsEntry.TABLE_NAME +
                " WHERE " + ProverbEntry.TABLE_NAME + "." + ProverbEntry._ID + " = " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_PROVERB_ID +
                " AND " + TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry._ID + " < ? " +
                " ORDER BY " + TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_DATE + " DESC" +
                " LIMIT ?;";
        public static final String NEWER_PROVERBS = "SELECT " +
                ProverbEntry.TABLE_NAME + "." + ProverbEntry._ID + " AS " + ProverbEntry._ID + ", " +
                ProverbEntry.TABLE_NAME + "." + ProverbEntry.COLUMN_TEXT + " AS " + ProverbEntry.COLUMN_TEXT + ", " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_DATE + " AS " + TodayProverbsEntry.COLUMN_DATE + ", " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry._ID + " AS " + TodayProverbsEntry._ID_ALTERNATIVE +
                " FROM " +
                ProverbEntry.TABLE_NAME +
                ", " +
                TodayProverbsEntry.TABLE_NAME +
                " WHERE " + ProverbEntry.TABLE_NAME + "." + ProverbEntry._ID + " = " +
                TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_PROVERB_ID +
                " AND " + TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry._ID + " > ? " +
                " ORDER BY " + TodayProverbsEntry.TABLE_NAME + "." + TodayProverbsEntry.COLUMN_DATE + " DESC" +
                " LIMIT ?;";
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
        public static final String CREATE_TABLE = "CREATE TABLE " +
                FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteEntry.COLUMN_PROVERB_ID + " INTEGER, " +
                "FOREIGN KEY(" + FavoriteEntry.COLUMN_PROVERB_ID + ") REFERENCES " + ProverbEntry.TABLE_NAME + "(" + ProverbEntry._ID + ")" +
                ")";
        public static final String SELECT_ALL = "SELECT * FROM " + FavoriteEntry.TABLE_NAME + ";";
    }

    public static abstract class TodayProverbsEntry implements BaseColumns {
        public static final String TABLE_NAME = "TodayProverbs";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PROVERB_ID = "proverb_id";
        public static final String _ID_ALTERNATIVE = "_id_alt";
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

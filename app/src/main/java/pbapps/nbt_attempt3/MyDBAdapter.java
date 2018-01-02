package pbapps.nbt_attempt3;

/**
 * Created by Prakash on 11/30/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBAdapter {

    private static final String DB_NAME = "MY_DATABASE";
    private static final int DB_VERSION = 2;

    private static final String DB_ROUTE_TABLE = "route_data";
    public static final String COL_ROUTE_NAME = "route_name";
    public static final String COL_STOP_1_NAME = "stop_1_name";
    public static final String COL_STOP_1_X = "X_1";
    public static final String COL_STOP_1_Y = "Y_1";
    public static final String COL_STOP_2_NAME = "stop_2_name";
    public static final String COL_STOP_2_X = "X_2";
    public static final String COL_STOP_2_Y = "Y_2";
    public static final String COL_STOP_3_NAME = "stop_3_name";
    public static final String COL_STOP_3_X = "X_3";
    public static final String COL_STOP_3_Y = "Y_3";

    /*
    *
    * private static final String DB_SAMPLE_TABLE = "sample_route_data";
    * public static final String COL_ROUTE_NAME = "sample_route_name";
    * public static final String COL_STOP_NAME = "sample_stop_name";
    * public static final String COL_STOP_NUMBER = "sample_route_number";
    * public static final String COL_X = "sample_x_coordinate";
    * public static final String COL_Y = "sample_y_coordinate";
    *
    * */


    private static final String DB_LOCATION_TABLE = "location_data";
    private static final String COL_ROUTE_NM = "route_number";
    private static final String COL_CURRENT_X = "X";
    private static final String COL_CURRENT_Y = "Y";

    private static final String DB_ROUTE_CREATE = "create table " + DB_ROUTE_TABLE +
            " (id integer primary key autoincrement, " +
            COL_ROUTE_NAME + " text not null, " +
            COL_STOP_1_NAME + " text not null, " +
            COL_STOP_1_X + " text not null, " +
            COL_STOP_1_Y + " text not null, " +
            COL_STOP_2_NAME + " text not null, " +
            COL_STOP_2_X + " text not null, " +
            COL_STOP_2_Y + " text not null, " +
            COL_STOP_3_NAME + " text not null, " +
            COL_STOP_3_X + " text not null, " +
            COL_STOP_3_Y + " text not null);";

    /*
    *
    * private static final String DB_SAMPLE_ROUTE_CREATE = "create table " + DB_SAMPLE_ROUTE_DATA +
    *       " ( " +
    *       COL_SAMPLE_ROUTE_NAME + " text not null, " +
    *       COL_SAMPLE_STOP_NAME + " text not null, " +
    *       COL_SAMPLE_STOP_NUMBER + " text not null, " +
    *       COL_X + "text not null, " +
    *       COL_Y + "text not null, " +
    *       "primary key( " + COL_SAMPLE_ROUTE_NAME + ", " + COL_SAMPLE_STOP_NAME + ");";
    *
    * */

    private static final String DB_LOCATION_CREATE = "create table " + DB_LOCATION_TABLE +
            " (id integer primary key autoincrement, " +
            COL_ROUTE_NM + " integer not null, " +
            COL_CURRENT_X + " text not null, " +
            COL_CURRENT_Y + " text not null);";

    private SQLiteDatabase database;
    private MyDBHelper helper;

    public MyDBAdapter(Context context) {
        helper = new MyDBHelper(context, DB_NAME, null, DB_VERSION);
    }

    public static class MyDBHelper extends SQLiteOpenHelper {


        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
            super(context, name, cursorFactory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_ROUTE_CREATE);
            db.execSQL(DB_LOCATION_CREATE);
            //db.execSQL(DB_SAMPLE_ROUTE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_ROUTE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DB_LOCATION_TABLE);
            //db.execSQL("DROP TABLE IF EXISTS " + DB_SAMPLE_ROUTE_TABLE);
            onCreate(db);
        }
    }

    public MyDBAdapter open() {
        database = helper.getWritableDatabase();
        return this;
    }

    public Cursor getAllRouteEntries() {
        return database.query(DB_ROUTE_TABLE,
                new String[]{COL_ROUTE_NAME, COL_STOP_1_NAME, COL_STOP_1_X, COL_STOP_1_Y, COL_STOP_2_NAME, COL_STOP_2_X, COL_STOP_2_Y, COL_STOP_3_NAME, COL_STOP_3_X, COL_STOP_3_Y},
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getSpecificRouteEntry(String rtname) {
        return database.query(DB_ROUTE_TABLE,
                new String[]{COL_ROUTE_NAME, COL_STOP_1_NAME, COL_STOP_1_X, COL_STOP_1_Y, COL_STOP_2_NAME, COL_STOP_2_X, COL_STOP_2_Y, COL_STOP_3_NAME, COL_STOP_3_X, COL_STOP_3_Y},
                COL_ROUTE_NAME + "=?",
                new String[]{rtname},
                null,
                null,
                null);
    }

    /*
    public Cursor getSampleSpecificRouteEntry(String rtname)
    {
        return database.query(DB_SAMPLE_ROUTE_TABLE,
        new String[]{COL_ROUTE_NAME, COL_STOP_NAME, COL_STOP_NUMBER, COL_X, COL_Y},
        COL_ROUTE_NAME + "=?",
        new String[]{rtname},
        null,
        null,
        null);
    }
    */
    public Cursor getLocationEntry(String rtname){
        return database.query(DB_LOCATION_TABLE,
                new String[]{COL_ROUTE_NM, COL_CURRENT_X, COL_CURRENT_Y},
                COL_ROUTE_NM + "=?",
                new String[]{rtname},
                null,
                null,
                null);
    }

    public void close() {
        database.close();
    }

    public void insertEntry(String rtname, String nm1, String nm2, String nm3, String X1, String Y1, String X2, String Y2, String X3, String Y3) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ROUTE_NAME, rtname);
        contentValues.put(COL_STOP_1_NAME, nm1);
        contentValues.put(COL_STOP_1_X, X1);
        contentValues.put(COL_STOP_1_Y, Y1);
        contentValues.put(COL_STOP_2_NAME, nm2);
        contentValues.put(COL_STOP_2_X, X2);
        contentValues.put(COL_STOP_2_Y, Y2);
        contentValues.put(COL_STOP_3_NAME, nm3);
        contentValues.put(COL_STOP_3_X, X3);
        contentValues.put(COL_STOP_3_Y, Y3);

        database.insert(DB_ROUTE_TABLE, null, contentValues);
    }

    /*
    *
    * public void insertSampleEntry(String routeName, String stopName, String stopNumber, String myX, String myY)
    * {
    *   ContentValues contentValues = new ContentValues();
    *
    *   contentValues.put(COL_ROUTE_NAME, routeName);
    *   contentValues.put(COL_STOP_NAME, stopName);
    *   contentValues.put(COL_STOP_NUMBER, stopNumber);
    *   contentValues.put(COL_X, myX);
    *   contentValues.put(COL_Y, myY);
    *
    *   database.insert(DB_SAMPLE_ROUTE_TABLE, null, contentValues);
    * }
    *
    * */

    public void insertLocation(String rtname, String x, String y) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ROUTE_NM, rtname);
        contentValues.put(COL_CURRENT_X, x);
        contentValues.put(COL_CURRENT_Y, y);

        database.insert(DB_LOCATION_TABLE, null, contentValues);
    }

    public ArrayList<String> getAllRoutesForSpinner() {
        ArrayList<String> routes = new ArrayList<>();

        Cursor cursor = database.query(DB_ROUTE_TABLE,
                new String[]{COL_ROUTE_NAME, COL_STOP_1_NAME, COL_STOP_1_X, COL_STOP_1_Y, COL_STOP_2_NAME, COL_STOP_2_X, COL_STOP_2_Y, COL_STOP_3_NAME, COL_STOP_3_X, COL_STOP_3_Y},
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            routes.add(cursor.getString(cursor.getColumnIndex(COL_ROUTE_NAME)));

            while (cursor.moveToNext())
                routes.add(cursor.getString(cursor.getColumnIndex(COL_ROUTE_NAME)));
        }
        return routes;
    }

}

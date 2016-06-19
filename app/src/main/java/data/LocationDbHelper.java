//package data;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by Exin on 2016/5/30.
// */
//public class LocationDbHelper extends SQLiteOpenHelper {
//
//    static final String DATABASE_NAME = "location.db";
//
//    public LocationDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, DATABASE_NAME, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        final String SQL_CREATE_LOCATION_TABLE =
//                "CREATE TABLE " + LocationContract.LocationEntry.TABLE_NAME +
//                        " (" +
//                        LocationContract.LocationEntry._ID + " INTEGER PRIMARY KEY," +
//                        LocationContract.LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
//                        LocationContract.LocationEntry.COLUMN_CITY_NAME_PINYIN + " TEXT NOT NULL, " +
//                        LocationContract.LocationEntry.COLUMN_LAT + " REAL NOT NULL, " +
//                        LocationContract.LocationEntry.COLUMN_LONG + " REAL NOT NULL " +
//                        " );";
//
//        db.execSQL(SQL_CREATE_LOCATION_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + LocationContract.LocationEntry.TABLE_NAME);
//        onCreate(db);
//    }
//}

//package data;
//
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.net.Uri;
//import android.provider.BaseColumns;
//
///**
// * Created by Exin on 2016/5/30.
// */
//public class LocationContract {
//    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
//    public static final String PATH_LOCATION = "location";
//
//    public static final class LocationEntry implements BaseColumns {
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();
//
//        // ???
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
//
//
//        public static final String TABLE_NAME = "location";
//        public static final String COLUMN_CITY_NAME = "city_name";
//        public static final String COLUMN_CITY_NAME_PINYIN = "city_name_pinyin";
//        public static final String COLUMN_LAT = "lat";
//        public static final String COLUMN_LONG = "long";
//
//        public static Uri buildLocationUri(long id) {
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//    }
//}

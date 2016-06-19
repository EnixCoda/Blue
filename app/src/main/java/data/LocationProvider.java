//package data;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.net.Uri;
//import android.support.annotation.Nullable;
//
///**
// * Created by Exin on 2016/5/30.
// *
// */
//public class LocationProvider extends ContentProvider {
//    private static final UriMatcher sUriMatcher = buildUriMatcher();
//    private LocationDbHelper locationDbHelper;
//
//    static final int LOCATION = 100;
//
//    private static final String sLocationSettingSelection =
//            LocationContract.LocationEntry.TABLE_NAME +
//                    "." + LocationContract.LocationEntry.COLUMN_CITY_NAME + " = ? ";
//
//    private Cursor getLonLatByChinese(Uri uri, String[] projection, String sortOrder) {
//        String locationSetting = LocationContract.LocationEntry.getLocationSettingFromUri(uri);
//        long startDate = LocationContract.LocationEntry.getStartDateFromUri(uri);
//
//        String[] selectionArgs;
//        String selection;
//
//        if (startDate == 0) {
//            selection = sLocationSettingSelection;
//            selectionArgs = new String[]{locationSetting};
//        } else {
//            selectionArgs = new String[]{locationSetting, Long.toString(startDate)};
//            selection = sLocationSettingWithStartDateSelection;
//        }
//
//        return sWeatherByLocationSettingQueryBuilder.query(locationDbHelper.getReadableDatabase(),
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                sortOrder
//        );
//    }
//
//    @Nullable
//    @Override
//    public String getType(Uri uri) {
//        switch (sUriMatcher.match(uri)) {
//            case LOCATION:
//                return LocationContract.LocationEntry.COLUMN_CITY_NAME;
//            default:
//                throw new UnsupportedOperationException("Unknown URI: " + uri);
//        }
//    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        return 0;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        return null;
//    }
//
//    @Override
//    public boolean onCreate() {
//        return false;
//    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        return 0;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        return null;
//    }
//
//    static UriMatcher buildUriMatcher() {
//        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
//        final String authority = LocationContract.CONTENT_AUTHORITY;
//
//        matcher.addURI(authority, LocationContract.PATH_LOCATION, LOCATION);
//        matcher.addURI(authority, LocationContract.PATH_LOCATION + "/*", LOCATION_WITH_NAME);
//
//        return matcher;
//    }
//}

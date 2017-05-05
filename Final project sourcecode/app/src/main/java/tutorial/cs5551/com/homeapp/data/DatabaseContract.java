package tutorial.cs5551.com.homeapp.data;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    //Database schema information
    public static final String TABLE_ITEMS = "tasks";
    //Unique authority string for the content provider
    public static final String CONTENT_AUTHORITY = "tutorial.cs5551.com.homeapp";
   /* *//* Sort order constants *//*
    //Priority first,  the rest by date,Completed last
    public static final String DEFAULT_SORT = String.format("%s DESC, %s ASC, %s ASC",
            TaskColumns.IS_PRIORITY, TaskColumns.DUE_DATE, TaskColumns.IS_COMPLETE);
    //date, then by priority, followed by Completed last
    public static final String DATE_SORT = String.format("%s ASC, %s DESC, %s ASC",
            TaskColumns.DUE_DATE, TaskColumns.IS_PRIORITY, TaskColumns.IS_COMPLETE);*/
    //Base content Uri for accessing the provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_ITEMS)
            .build();

    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static final class TaskColumns implements BaseColumns {

        public static final String DUE_DATE = "due_date";
        public static final String ITEMNAME = "item_name";
        public static final String ITEMBRAND = "item_brand";
        public static final String BNAME = "b_name";
        public static final String BEMAIL = "b_email";
        public static final String IS_COMPLETED = "is_completed";
    }
}

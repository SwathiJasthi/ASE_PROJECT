package tutorial.cs5551.com.homeapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TASKS = String.format("create table %s"
            +" (%s integer primary key autoincrement, %s text, %s text, %s text, %s text, %s integer, %s integer)",
            DatabaseContract.TABLE_ITEMS,
            DatabaseContract.TaskColumns._ID,
            DatabaseContract. TaskColumns.ITEMNAME,
            DatabaseContract.TaskColumns.ITEMBRAND,
            DatabaseContract.TaskColumns.BNAME,
            DatabaseContract.TaskColumns.BEMAIL,
            DatabaseContract.TaskColumns.DUE_DATE,
            DatabaseContract.TaskColumns.IS_COMPLETED
    );

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DatabaseContract.TABLE_ITEMS);

        onCreate(db);
    }
}

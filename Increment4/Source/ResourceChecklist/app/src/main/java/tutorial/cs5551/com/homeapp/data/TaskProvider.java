package tutorial.cs5551.com.homeapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

public class TaskProvider extends ContentProvider {
    private static final String TAG = TaskProvider.class.getSimpleName();

    private static final int CLEANUP_JOB_ID = 43;

    private static final int TASKS = 100;
    private static final int TASKS_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.google.developer.taskmaker/tasks
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_ITEMS,
                TASKS);

        // content://com.google.developer.taskmaker/tasks/id
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_ITEMS + "/#",
                TASKS_WITH_ID);
    }

    private TaskDbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mDbHelper = new TaskDbHelper(getContext());
        mDatabase = mDbHelper.getWritableDatabase();
       // manageCleanupJob();
        if (mDatabase == null) {
            return false;
        } else {
            return true;
        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null; /* Not used */
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        //TODO: Implement task query
        //TODO: Expected "query all" Uri: content://com.google.developer.taskmaker/tasks
        //TODO: Expected "query one" Uri: content://com.google.developer.taskmaker/tasks/{id}

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseContract.TABLE_ITEMS);
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor results = null;
        int val = sUriMatcher.match(uri);
        switch (val) {
            case TASKS:
                results = queryBuilder.query(mDatabase, null, null, null, null, null, null);
                results.setNotificationUri(getContext().getContentResolver(), uri);
                /*if (sortOrder.equals("default")) {
                    results = queryBuilder.query(mDatabase, null, null, null, null, null, DatabaseContract.DEFAULT_SORT);
                    results.setNotificationUri(getContext().getContentResolver(), uri);
                } else if (sortOrder.equals("due")) {
                    results = queryBuilder.query(mDatabase, null, null, null, null, null, DatabaseContract.DATE_SORT);
                    results.setNotificationUri(getContext().getContentResolver(), uri);
                }*/
                break;
            case TASKS_WITH_ID:
                selection = DatabaseContract.TaskColumns._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                results = database.query(DatabaseContract.TABLE_ITEMS, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }


        return results;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //TODO: Implement new task insert
        //TODO: Expected Uri: content://com.google.developer.taskmaker/tasks
        // getContentResolver().insert(DatabaseContract.CONTENT_URI,values);
        long row = mDatabase.insert(DatabaseContract.TABLE_ITEMS, "", values);
        if (row > 0) {
            Uri newuri = ContentUris.withAppendedId(uri, row);
            getContext().getContentResolver().notifyChange(newuri, null);
            return newuri;
        } else
            return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //TODO: Implement existing task update
        //TODO: Expected Uri: content://com.google.developer.taskmaker/tasks/{id}
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        selection = DatabaseContract.TaskColumns._ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        int returnvalue = database.update(DatabaseContract.TABLE_ITEMS, values, selection, selectionArgs);
        return returnvalue;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch (sUriMatcher.match(uri)) {
            case TASKS:
                //Rows aren't counted with null selection
                selection = (selection == null) ? "1" : selection;
                break;
            case TASKS_WITH_ID:
                long id = ContentUris.parseId(uri);
                selection = String.format("%s = ?", DatabaseContract.TaskColumns._ID);
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Illegal delete URI");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int count = db.delete(DatabaseContract.TABLE_ITEMS, selection, selectionArgs);

        if (count > 0) {
            //Notify observers of the change
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    /* Initiate a periodic job to clear out completed items */
    /*private void manageCleanupJob() {
        Log.d(TAG, "Scheduling cleanup job");
        JobScheduler jobScheduler = (JobScheduler) getContext()
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        //Run the job approximately every hour
        long jobInterval = 3600000L;
        Log.v("tag", "job interval value::::" + String.valueOf(jobInterval));
        ComponentName jobService = new ComponentName(getContext(), CleanupJobService.class);
        JobInfo task = new JobInfo.Builder(CLEANUP_JOB_ID, jobService)
                .setPeriodic(jobInterval)
                .setPersisted(true)
                .build();

        if (jobScheduler.schedule(task) != JobScheduler.RESULT_SUCCESS) {
            Log.w(TAG, "Unable to schedule cleanup job");
        }
    }*/
}

package tutorial.cs5551.com.homeapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import tutorial.cs5551.com.homeapp.data.DatabaseContract;
import tutorial.cs5551.com.homeapp.data.MyAdapter;
import tutorial.cs5551.com.homeapp.data.Task;
import tutorial.cs5551.com.homeapp.data.TaskUpdateService;

public class ActivityHistory extends AppCompatActivity implements MyAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    //TaskAdapter mAdapter;
    ArrayList<Task> tasklist;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    Uri urioftask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyy);
        tasklist = new ArrayList<Task>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView srcview = (SearchView) MenuItemCompat.getActionView(searchItem);
        srcview.setQueryHint("Search using borrower name");
        srcview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
               /* Log.d("tag", "onQueryTextSubmit ");
                return false;*/
                // myAdapter.filter(s);
                /*ArrayList<Task> templist = new ArrayList<Task>();
                for (Task temp : tasklist) {
                    if (temp.bname.toLowerCase().contains(s.toLowerCase())) {
                        templist.add(temp);
                    }
                }
                myAdapter = new MyAdapter(templist);
                recyclerView.setAdapter(myAdapter);*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("tag", "onQueryTextChange ");
                ArrayList<Task> templist = new ArrayList<Task>();
                for (Task temp : tasklist) {
                    if (temp.bname.toLowerCase().contains(s.toLowerCase())) {
                        templist.add(temp);
                    }
                }
                myAdapter = new MyAdapter(templist);
                recyclerView.setAdapter(myAdapter);
                myAdapter.setOnItemClickListener(ActivityHistory.this);
             //   myAdapter.filter(s);
                return false;
            }

        });
        srcview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                myAdapter = new MyAdapter(tasklist);
                recyclerView.setAdapter(myAdapter);
                myAdapter.setOnItemClickListener(ActivityHistory.this);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (i == 1) {
            return new CursorLoader(this, DatabaseContract.CONTENT_URI, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Task task = new Task(cursor);
            taskArrayList.add(task);
            tasklist.add(task);
        }
        /*mAdapter = new TaskAdapter(cursor);
        recyclerView.setAdapter(mAdapter);*/
        myAdapter = new MyAdapter(taskArrayList);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getLoaderManager().restartLoader(1, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(1, null, this);
    }

    @Override
    public void onItemClick(View v, final int position) {

        final AlertDialog.Builder ad1 = new AlertDialog.Builder(ActivityHistory.this);
        ad1.setTitle("WARNING");
        ad1.setMessage("Do you want to delete?");
        ad1.setCancelable(false);
        ad1.setIcon(R.drawable.error);
        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ActivityHistory.this,
                        "Clicked NO!", Toast.LENGTH_SHORT).show();
            }
        });
        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Uri singleUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, mAdapter.getItem(position).id);
                urioftask = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, myAdapter.getItemId(position));
                deleteItem(urioftask);

            }
        });
        AlertDialog alert = ad1.create();
        alert.show();
    }

    @Override
    public void onItemToggled(boolean active, int position) {
        Uri updateUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, myAdapter.getItemId(position));
        ContentValues values = new ContentValues(6);
        values.put(DatabaseContract.TaskColumns.ITEMNAME, myAdapter.getItem(position).itemname);
        values.put(DatabaseContract.TaskColumns.ITEMBRAND, myAdapter.getItem(position).itembrand);
        values.put(DatabaseContract.TaskColumns.BNAME, myAdapter.getItem(position).bname);
        values.put(DatabaseContract.TaskColumns.BEMAIL, myAdapter.getItem(position).bemail);
        values.put(DatabaseContract.TaskColumns.DUE_DATE, myAdapter.getItem(position).duedate);
        values.put(DatabaseContract.TaskColumns.IS_COMPLETED, active);
        TaskUpdateService.updateTask(this, updateUri, values);
    }
    private void deleteItem(Uri uri) {
        TaskUpdateService.deleteTask(ActivityHistory.this, uri);
        Log.v("tag", "Uri OF TASK in  delete function:" + uri);
    }
}

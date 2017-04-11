package tutorial.cs5551.com.homeapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class List extends AppCompatActivity {
    private DBclass db;
    private ArrayList<String> collist_1;
    private ArrayList<String> collist_2;
    private ArrayList<String> collist_3;
    private SQLiteDatabase odb;
    ListView l;
    int contactIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        collist_1 = new ArrayList<String>();
        collist_2 = new ArrayList<String>();
        collist_3 = new ArrayList<String>();
        getData();
        printList();
        setDataIntoList();
    }
    private void printList() {
        for (int i = 0; i < collist_1.size(); i++) {
            Log.e("***************",
                    collist_1.get(i) + " --- " + collist_2.get(i) + " --- " + collist_3.get(i));
        }
    }

    private void setDataIntoList() {

        // create the list item mapping
        String[] from = new String[]{"col_1", "col_2", "col_3"};
        int[] to = new int[]{R.id.col1tv, R.id.col2tv, R.id.col3tv};

        // prepare the list of all records
        java.util.List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < collist_1.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            String contextInfo = "PRODUCT_NAME: " + collist_1.get(i);
            String latitude = "PERSON_NAME: " + collist_2.get(i);
            String longitude = collist_3.get(i);
            map.put("col_1", contextInfo);
            map.put("col_2", latitude);
            map.put("col_3", longitude);
            fillMaps.add(map);
        }

        // fill in the grid_item layout
        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps,
                R.layout.view, from, to);
        setContentView(R.layout.list_item);
        l=(ListView)findViewById(R.id.listView);
        l.setAdapter(adapter);
        registerForContextMenu(l);
        l.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        contactIndex = position;
                        Toast.makeText(List.this,"position"+contactIndex,Toast.LENGTH_LONG).show();
                        return false;
                    }
                });

    }
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);


        menu.setHeaderTitle("Contact ...");
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        String[] menuItems = getResources().getStringArray(R.array.menu);
        for(int i=0;i<menuItems.length;i++) {
            menu.add(Menu.NONE, i,i, menuItems[i]);
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        collist_1 = new ArrayList<String>();
        collist_2 = new ArrayList<String>();
        collist_3 = new ArrayList<String>();
        int menuItemindex = item.getItemId();
        TextView tv =(TextView)findViewById(R.id.col3tv);
        if(menuItemindex==0) {

            db = new DBclass(this);
            boolean num;
            try {
                db.open();
                db.getdb();

                //odb.execSQL("delete from contexttable1");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
            collist_1.clear();
            collist_2.clear();
            collist_3.clear();

            db = new DBclass(this);
            try {
                db.open();
                db.deleteContact(contactIndex);
                Cursor cur = db.getAllTitles();
                while (cur.moveToNext()) {
                    String valueofcol1 = cur.getString(1);
                    String valueofcol2 = cur.getString(2);
                    String valueofcol3 = cur.getString(3);
//              Log.e("---****---", "***********   col 1 = " + valueofcol1);
//              Log.e("---****---", "***********   col 2 = " + valueofcol2);

                    collist_1.add(valueofcol1);
                    collist_2.add(valueofcol2);
                    collist_3.add(valueofcol3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
            // create the list item mapping
            String[] from = new String[]{"col_1", "col_2", "col_3"};
            int[] to = new int[]{R.id.col1tv, R.id.col2tv, R.id.col3tv};

            // prepare the list of all records
            java.util.List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < collist_1.size(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                String contextInfo = "PRODUCT_NAME: " + collist_1.get(i);
                String latitude = "PERSON_NAME: " + collist_2.get(i);
                String longitude = collist_3.get(i);
                map.put("col_1", contextInfo);
                map.put("col_2", latitude);
                map.put("col_3", longitude);
                fillMaps.add(map);
            }

            // fill in the grid_item layout
            SimpleAdapter adapter = new SimpleAdapter(this, fillMaps,
                    R.layout.view, from, to);
            setContentView(R.layout.list_item);
            l=(ListView)findViewById(R.id.listView);
            l.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Intent startNewActivity = new Intent(this,HomeActivity.class);
            startActivity(startNewActivity);

        }
        else{
            Log.i("Send email","");
            //Toast.makeText(Main2Activity.this,"position"+contactIndex,Toast.LENGTH_LONG).show();
            String TO = tv.getText().toString();
            String[] CC
                    = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "REMAINDER");
            emailIntent.putExtra(Intent.EXTRA_TEXT, " Hi, The due date for the item you borrowed is approaching. Please do keep a note of it.");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                //Log.i("Finished sending email...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(List.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onContextItemSelected(item);
    }


    public void getData() {
        collist_1.clear();
        collist_2.clear();
        collist_3.clear();

        db = new DBclass(this);
        try {
            db.open();
            Cursor cur = db.getAllTitles();
            while (cur.moveToNext()) {
                String valueofcol1 = cur.getString(1);
                String valueofcol2 = cur.getString(2);
                String valueofcol3 = cur.getString(3);
//              Log.e("---****---", "***********   col 1 = " + valueofcol1);
//              Log.e("---****---", "***********   col 2 = " + valueofcol2);

                collist_1.add(valueofcol1);
                collist_2.add(valueofcol2);
                collist_3.add(valueofcol3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }
}

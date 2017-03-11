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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import tutorial.google.zxing.integration.android.IntentIntegrator;
import tutorial.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main22Activity extends AppCompatActivity implements OnClickListener {
        private Button scanBtn;
    private DBclass db;
    private ArrayList<String> collist_1;
    private ArrayList<String> collist_2;
    private ArrayList<String> collist_3;
    private SQLiteDatabase odb;
    ListView l;
    int contactIndex;
    Button b1,b2,b3;
    EditText et1,et2,et3;

        private TextView formatTxt, contentTxt;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main22);
            et1=(EditText)findViewById(R.id.ed1);
            et2=(EditText)findViewById(R.id.ed2);
            et3=(EditText)findViewById(R.id.ed3);
            l=(ListView)findViewById(R.id.listView);
            b1 = (Button)findViewById(R.id.submit_btn);
            b2 = (Button)findViewById(R.id.view_btn);
            b3 = (Button)findViewById(R.id.sen_btn);
            scanBtn = (Button)findViewById(R.id.button);
            formatTxt = (TextView)findViewById(R.id.textView);
            contentTxt = (TextView)findViewById(R.id.textView11);

            scanBtn.setOnClickListener(this);
            collist_1 = new ArrayList<String>();
            collist_2 = new ArrayList<String>();
            collist_3 = new ArrayList<String>();

            getData();
            b3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Send email", "");
                    String TO = et3.getText().toString();
                    String[] CC = {""};
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
                    }
                    catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Main22Activity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            b2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //getData();
                    printList();
                    setDataIntoList();
                }
            });

            b1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //setContentView(R.layout.list);
                    submitData();
                }
            });

        }
        public void onClick(View v){
            if(v.getId()==R.id.button){
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
            }
        }
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                formatTxt.setText( scanFormat);
                contentTxt.setText(scanContent);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    protected void submitData() {
        String a = et1.getText().toString();
        String b = et2.getText().toString();
        String c = et3.getText().toString();

        db = new DBclass(this);
        long num;
        try {
            db.open();
            num = db.insertmaster(a, b, c);
            db.close();
        } catch (SQLException e) {
            num = -5;
        } finally {
            getData();
        }
        if (num > 0)
            Toast.makeText(this, "Row number: " + num, Toast.LENGTH_LONG).show();
        else if (num == -1)
            Toast.makeText(this, "Error Duplicate value", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error while inserting", Toast.LENGTH_LONG).show();
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
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < collist_1.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            String contextInfo = "PRODUCT_NAME: " + collist_1.get(i);
            String latitude = "PERSON_NAME: " + collist_2.get(i);
            String longitude = "EMAIL_ID: " + collist_3.get(i);
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
                        Toast.makeText(Main22Activity.this,"position"+contactIndex,Toast.LENGTH_LONG).show();
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
            List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
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
            Intent startNewActivity = new Intent(this,Main22Activity.class);
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
                Toast.makeText(Main22Activity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onContextItemSelected(item);
    }

}


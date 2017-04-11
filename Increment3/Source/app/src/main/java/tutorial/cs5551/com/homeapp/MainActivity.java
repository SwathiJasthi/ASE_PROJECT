package tutorial.cs5551.com.homeapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tutorial.google.zxing.integration.android.IntentIntegrator;
import tutorial.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    Button scanbtn, historybtn;
    EditText nameET, brandET, borrowerET, bemailET, bnumberET;
    String BASE_URL = "https://api.upcitemdb.com/prod/trial/lookup?upc=";
    String FINAL_URL = "";
    String barcodenumber = "";
    Button setremainderbtn;
    String personname = "";
    String personemail = "";
    String personnumber = "";
    DatabaseHelper mydb;
    int pos;
    ArrayList<ProductDetails> productlist = new ArrayList<>();
    TextView duedatetv;
    String duedatestring = "";
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int selectedyear, selectedmonth, selectedday;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainnnnnn);
        scanbtn = (Button) findViewById(R.id.btnscan);
        // historybtn = (Button) findViewById(R.id.addbtn);
        nameET = (EditText) findViewById(R.id.nameET);
        brandET = (EditText) findViewById(R.id.brandET);
        borrowerET = (EditText) findViewById(R.id.borrowET);
        setremainderbtn = (Button) findViewById(R.id.setbtn);
        bemailET = (EditText) findViewById(R.id.bemailET);
        // bnumberET = (EditText) findViewById(R.id.bnumberET);
        duedatetv = (TextView) findViewById(R.id.duedateTV);


        duedatetv.setOnClickListener(this);
        setremainderbtn.setOnClickListener(this);
        scanbtn.setOnClickListener(this);
       // nameET.setOnTouchListener(this);
        //brandET.setOnTouchListener(this);
        //borrowerET.setOnTouchListener(this);
        //bemailET.setOnTouchListener(this);
        // bnumberET.setOnTouchListener(this);
        // historybtn.setOnClickListener(this);
        mydb = new DatabaseHelper(this);
        mydb.createAllItemsTable();

        /*scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnscan:
                com.google.zxing.integration.android.IntentIntegrator integrator = new com.google.zxing.integration.android.IntentIntegrator(this);
                integrator.setDesiredBarcodeFormats(com.google.zxing.integration.android.IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                break;
            case R.id.setbtn:

                if ((nameET.getText().toString().isEmpty())) {
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_LONG).show();
                    nameET.setError("Cant Leave Empty", getDrawable(R.mipmap.back1));
                } else if ((brandET.getText().toString().isEmpty())) {
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_LONG).show();
                    brandET.setError("Cant Leave Empty", getDrawable(R.mipmap.back1));
                } else if (borrowerET.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_LONG).show();
                    borrowerET.setError("Cant Leave Empty", getDrawable(R.mipmap.back1));
                } else if (bemailET.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_LONG).show();
                    bemailET.setError("Cant Leave Empty", getDrawable(R.mipmap.back1));
                } /*else if (bnumberET.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_LONG).show();
                    bnumberET.setError("Cant Leave Empty", getDrawable(R.mipmap.back1));
                }*/ else if (duedatetv.getText().toString().equalsIgnoreCase("Due Date")) {
                    Toast.makeText(MainActivity.this, "Empty Fieldssss", Toast.LENGTH_LONG).show();
                    duedatetv.setError("Select Due Date", getDrawable(R.mipmap.back1));
                } else {
                    String itemname = nameET.getText().toString();
                    String itembrand = brandET.getText().toString();
                    String bname = borrowerET.getText().toString();
                    //  personnumber = bnumberET.getText().toString();
                    personemail = bemailET.getText().toString();
                    Log.v("tag", "pos:" + pos);
                    Log.v("tag", "itemname:" + itemname);
                    Log.v("tag", "itembrand:" + itembrand);
                    Log.v("tag", "personname:" + bname);
                    Log.v("tag", "personemail:" + personemail);
                    //  Log.v("tag", "personnum:" + personnumber);
                    Log.v("tag", "duedate:" + duedatestring);
                    /*
                 */
                    mydb.insertDataintoAllItemsTable(itemname, itembrand, bname, personemail, duedatestring, false);
                    // mydb.insertData(itemname, itembrand, bname, personemail, personnumber, duedatestring);
                    Calendar c = Calendar.getInstance();
                    c.set(selectedyear, selectedmonth, selectedday);
                    c.set(Calendar.HOUR_OF_DAY, 21);
                    c.set(Calendar.MINUTE, 26);
                    c.set(Calendar.SECOND, 0);
                    // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
                    // scheduleClient.setAlarmForNotification(c);
                   // setEvent();

                    AlarmScheduler.scheduleAlarm(this,c.getTimeInMillis());
                    Toast.makeText(MainActivity.this, "Remainder Set Successfully!!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.duedateTV:
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                selectedyear = year;
                                selectedmonth = monthOfYear + 1;
                                selectedday = dayOfMonth;

                                duedatestring = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                duedatetv.setText(duedatestring);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
                break;

        }

    }

    public void onBackPressed() {
        AlertDialog.Builder ad1 = new AlertDialog.Builder(this);
        ad1.setTitle("WARNING");
        ad1.setMessage("Do you want to exit?");
        ad1.setCancelable(false);
        ad1.setIcon(R.mipmap.icon1);
        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),
                        "Clicked NO!", Toast.LENGTH_SHORT).show();
            }
        });
        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        AlertDialog alert = ad1.create();
        alert.show();
    }

    public void setEvent() {
        long calID = 3;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(selectedyear, selectedmonth - 1, selectedday);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(selectedyear, selectedmonth - 1, selectedday);
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.ALL_DAY, false);
        values.put(CalendarContract.Events.TITLE, "BORROW REMAINDER");
        values.put(CalendarContract.Events.DESCRIPTION, personname);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Chicago");
        //  values.put(CalendarContract.Events.);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
            long eventID = Long.parseLong(uri.getLastPathSegment());
//
// ... do something with event ID
            // reminder insert
            // Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(this) + "reminders");
            values = new ContentValues();
            values.put("event_id", eventID);
            values.put("method", 1);
            values.put("minutes", 10);
            cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
        }

    }

    /*@Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if (scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.v("Tag", "Cancelled");
                //  Toast.makeText(MainActivity.this, "Cancelled Scan", Toast.LENGTH_LONG).show();
            } else {
                Log.v("Tag", "Scanned");

                barcodenumber = result.getContents();
                // Toast.makeText(MainActivity.this, "Scan Success" + barcodenumber, Toast.LENGTH_LONG).show();
                FINAL_URL = BASE_URL + barcodenumber;
                new getAsyncData().execute(FINAL_URL);
                /*nameET.setText(result.getFormatName());
                brandET.setText(barcodenumber);*/
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    protected void onResume() {
        Log.v("tag", "onresumeeeeeeee");
        super.onResume();

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
     /*   final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        TextView tv = (TextView) view;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (tv.getRight() - tv.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                // your action here
                tv.setText("");

                return false;
            }
        };*/
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       /* if (id == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        }*/


        return super.onOptionsItemSelected(item);
    }


    public class getAsyncData extends AsyncTask<String, Void, ProductDetails> {

        //ArrayList<ProductDetails> resultdata = new ArrayList<>();

        @Override

        protected ProductDetails doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(FINAL_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                //  String finaljson = buffer.toString();
                try {
                    return ProductDetailsParser.ProductDataParser.getFeedArrayList(buffer.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ProductDetails result) {

            if (result == null) {
                // Toast.makeText(MainActivity.this, "No data.Please Enter manually", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("NO DATA FROM BARCODE");
                builder.setIcon(R.mipmap.icon1);
                builder.setMessage("Please Enter Details Manually");
                builder.setCancelable(false);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {

                nameET.setText(result.getName());
                brandET.setText(result.getBrandname());
                //   descriptionET.setText(result.getDescription());
                //addbtn.setEnabled(true);

            }
        }
    }

}

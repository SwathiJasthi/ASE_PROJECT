package tutorial.cs5551.com.homeapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

import tutorial.cs5551.com.homeapp.data.DatabaseContract;
import tutorial.cs5551.com.homeapp.data.TaskUpdateService;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener, View.OnTouchListener {
    Button scanbtn, pricebtn;
    EditText nameET, brandET, borrowerET, bemailET, bnumberET;
    String BASE_URL = "https://api.upcitemdb.com/prod/trial/lookup?upc=";
    String FINAL_URL = "";
    String barcodenumber = "";
    Button setremainderbtn;
    String personname = "";
    String personemail = "";
    ListView pricesLV;
    String personnumber = "";
    //DatabaseHelper mydb;
    int pos;
    ArrayList<ProductDetails> productlist = new ArrayList<>();
    TextView duedatetv;
    String duedatestring = "";
    PricesAdapter adapter;
    ArrayList<PriceDetails> newlist = new ArrayList<>();
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int selectedyear, selectedmonth, selectedday;
    private long mDueDate = Long.MAX_VALUE;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainnnnnn);
        scanbtn = (Button) findViewById(R.id.btnscan);
        pricebtn = (Button) findViewById(R.id.pricebtn);
        nameET = (EditText) findViewById(R.id.nameET);
        brandET = (EditText) findViewById(R.id.brandET);
        borrowerET = (EditText) findViewById(R.id.borrowET);
        setremainderbtn = (Button) findViewById(R.id.setbtn);
        bemailET = (EditText) findViewById(R.id.bemailET);
        // bnumberET = (EditText) findViewById(R.id.bnumberET);
        duedatetv = (TextView) findViewById(R.id.duedateTV);
        pricesLV = (ListView) findViewById(R.id.pricelistview);


        duedatetv.setOnClickListener(this);
        setremainderbtn.setOnClickListener(this);
        scanbtn.setOnClickListener(this);
        nameET.setOnTouchListener(this);
        brandET.setOnTouchListener(this);
        borrowerET.setOnTouchListener(this);
        bemailET.setOnTouchListener(this);
        // pricebtn.setOnClickListener(this);


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
                    personemail = bemailET.getText().toString();
                    Log.v("tag", "pos:" + pos);
                    Log.v("tag", "itemname:" + itemname);
                    Log.v("tag", "itembrand:" + itembrand);
                    Log.v("tag", "personname:" + bname);
                    Log.v("tag", "personemail:" + personemail);

                    ContentValues values = new ContentValues(6);
                    values.put(DatabaseContract.TaskColumns.ITEMNAME, itemname);
                    values.put(DatabaseContract.TaskColumns.ITEMBRAND, itembrand);
                    values.put(DatabaseContract.TaskColumns.BNAME, bname);
                    values.put(DatabaseContract.TaskColumns.BEMAIL, personname);
                    values.put(DatabaseContract.TaskColumns.DUE_DATE, getDateSelection());
                    values.put(DatabaseContract.TaskColumns.IS_COMPLETED, 0);

                    TaskUpdateService.insertNewTask(this, values);
                    AlarmScheduler.scheduleAlarm(this, getDateSelection());

                }
                break;
            case R.id.duedateTV:
                DatePickerFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.pricebtn:
                Gson gson1 = new GsonBuilder().create();
                String gsonStr = gson1.toJson(newlist);
                Intent intent = new Intent(MainActivity.this, PricesActivity.class);
                intent.putExtra("prices", gsonStr);
                startActivity(intent);
                break;
        }

    }

    public long getDateSelection() {
        return mDueDate;
    }

    /* Manage the selected date value */
    public void setDateSelection(long selectedTimestamp) {
        mDueDate = selectedTimestamp;
        updateDateDisplay();

    }

    /* Date set events from dialog */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Set to noon on the selected day
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 19);
        c.set(Calendar.MINUTE, 28);
        c.set(Calendar.SECOND, 0);

        setDateSelection(c.getTimeInMillis());
        //formatteddate = DateFormat.format("MM/dd/yyyy", c.getTimeInMillis());
    }

    private void updateDateDisplay() {
        if (getDateSelection() == Long.MAX_VALUE) {
            duedatetv.setText(R.string.app_name);
        } else {
            CharSequence formatted = DateFormat.format("MM/dd/yyyy", mDueDate);
            duedatetv.setText(formatted);
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
        final int DRAWABLE_LEFT = 0;
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
        }
        ;
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this, ActivityHistory.class);
            startActivity(intent);
        }


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
                Log.v("tag", "FINAL URL:" + FINAL_URL);
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
                String[] merchant = result.getMerchantnames();
                Double[] prices = result.getPrices();
                String[] urls = result.getWebsitelink();


                for (int i = 0; i < merchant.length; i++) {
                    PriceDetails pd = new PriceDetails();
                    pd.setMerchantnames(merchant[i]);
                    pd.setPrices(prices[i]);
                    pd.setWebsitelink(urls[i]);
                    newlist.add(pd);
                }

               // adapter = new PricesAdapter(MainActivity.this, R.layout.pricelistitem, newlist);
               // pricesLV.setAdapter(adapter);
                pricebtn.setEnabled(true);
                pricebtn.setOnClickListener(MainActivity.this);
                //   descriptionET.setText(result.getDescription());
                //addbtn.setEnabled(true);

            }
        }
    }

}

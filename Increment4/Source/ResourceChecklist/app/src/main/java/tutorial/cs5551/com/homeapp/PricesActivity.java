package tutorial.cs5551.com.homeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class PricesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView pricelv;
    String pricesjson;
    ArrayList<PriceDetails> priceslist = new ArrayList<>();
    PricesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        pricelv = (ListView) findViewById(R.id.pricelistviewww);
        pricesjson = getIntent().getExtras().getString("prices");
        Gson gson = new GsonBuilder().create();
        final ArrayList<PriceDetails> prices = gson.fromJson(pricesjson, new TypeToken<ArrayList<PriceDetails>>() {
        }.getType());
        priceslist.addAll(prices);
        adapter = new PricesAdapter(PricesActivity.this, R.layout.pricelistitem, priceslist);
        pricelv.setAdapter(adapter);
        pricelv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String website = adapter.getItem(position).getWebsitelink();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(website));
        //intent.setType("url");
        startActivity(intent);
    }
}

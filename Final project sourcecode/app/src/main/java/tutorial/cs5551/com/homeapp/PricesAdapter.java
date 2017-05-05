package tutorial.cs5551.com.homeapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ashweeza on 4/26/2017.
 */

public class PricesAdapter extends ArrayAdapter<PriceDetails> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<PriceDetails> placelist;
    private SparseBooleanArray mSelectedItemsIds;
    private DisplayMetrics metrics_;

    public PricesAdapter(Context context, int resourceId,
                         ArrayList<PriceDetails> worldpopulationlist) {
        super(context, resourceId, worldpopulationlist);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.placelist = worldpopulationlist;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.pricelistitem, null);
            // Locate the TextViews in listview_item.xml
            holder.merchantname = (TextView) convertView.findViewById(R.id.merchantnameTV);
            holder.prices = (TextView) convertView.findViewById(R.id.priceTV);
          /*  holder.rating = (TextView) convertView.findViewById(R.id.ratingTV);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            holder.opennow = (TextView) convertView.findViewById(R.id.opentv);*/
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       /* Animation animation = null;
        animation = AnimationUtils.loadAnimation(context, R.anim.pushleft_in);*/
        // Capture position and set to the TextViews
        TextView name = holder.merchantname;
        TextView price = holder.prices;
       /* TextView vicinity = holder.vicinity;
        ImageView icon = holder.icon;
        TextView open = holder.opennow;*/

        name.setText(placelist.get(position).getMerchantnames());
        price.setText("$"+String.valueOf(placelist.get(position).getPrices()));

      /*  else{
            open.setText("Closed");
            open.setBackgroundColor(Color.RED);
        }*/

        // Picasso.with(context).load(placelist.get(position).getIcon_url()).resize(100, 100).into(icon);

        return convertView;
    }

    @Override
    public void remove(PriceDetails object) {
        placelist.remove(object);
        notifyDataSetChanged();
    }

    public ArrayList<PriceDetails> getWorldPopulation() {
        return placelist;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public static class ViewHolder {
        TextView merchantname;
        //ImageView icon;
        TextView prices;
        /*TextView rating;
        TextView opennow;*/
    }
}

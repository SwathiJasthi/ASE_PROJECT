package tutorial.cs5551.com.homeapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ashweeza on 3/14/2017.
 */

public class ProductDetailsParser {
    static public class ProductDataParser {
        public static ProductDetails getFeedArrayList(String in) throws JSONException {
       /*     String IMAGE_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&maxheight=100&photoreference=";
            String PHOTO_REF_URL;
            String KEY_URL = "&key=AIzaSyCz19lSR3gBMyzIUmUg_kkSO_EU4LqgkdA";
            String FINAL_URL = "";
            ProductDetails feedArrayList = new ProductDetails();*/

            JSONObject parentObject = new JSONObject(in);
            JSONArray dataarray = parentObject.getJSONArray("items");
            JSONObject dataobject = dataarray.getJSONObject(0);
            ProductDetails productDetails = new ProductDetails();

            if (dataobject.has("title")) {
                String name = dataobject.getString("title");
                productDetails.setName(name);
            } else {
                productDetails.setName("N/A");
            }


            if (dataobject.has("brand")) {
                String brandname = dataobject.getString("brand");
                productDetails.setBrandname(brandname);
            } else {
                productDetails.setBrandname("N/A");
            }

            if (dataobject.has("description")) {
                String description = dataobject.getString("description");
                productDetails.setDescription(description);
            } else {
                productDetails.setDescription("N/A");
            }



          /*  JSONObject root = new JSONObject(in);
            JSONArray placedataArray = root.getJSONArray("results");
            for (int k = 0; k < placedataArray.length(); k++) {
                JSONObject hourlyForecastObject = placedataArray.getJSONObject(k);

                placedata.setName(hourlyForecastObject.getString("name"));
                //   placedata.setRating(hourlyForecastObject.getDouble("rating"));
                placedata.setVicinity(hourlyForecastObject.getString("vicinity"));

                // placedata.setIcon_url(hourlyForecastObject.getString("icon"));

                placedata.setLattitude(hourlyForecastObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                placedata.setLongitude(hourlyForecastObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));

                *//*
                placedata.setCivilTime(hourlyForecastObject.getJSONObject("FCTTIME").getString("civil"));
                placedata.setDefaultDate(hourlyForecastObject.getJSONObject("FCTTIME").getString("mon_padded")+"/"
                        +hourlyForecastObject.getJSONObject("FCTTIME").getString("mday_padded")+"/"
                        +hourlyForecastObject.getJSONObject("FCTTIME").getString("year"));*//*


                if (hourlyForecastObject.has("photos")) {
                    JSONArray ref_array = hourlyForecastObject.getJSONArray("photos");
                    JSONObject ref_object = ref_array.getJSONObject(0);
                    String ref_value = ref_object.getString("photo_reference");
                    // PHOTO_REF_URL = ref_value;
                    FINAL_URL = IMAGE_BASE_URL + ref_value + KEY_URL;
                    placedata.setIcon_url(FINAL_URL);
                } else {
                    placedata.setIcon_url("https://maps.googleapis.com/maps/api/place/photo?maxwidth=100" +
                            "&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4o" +
                            "Rz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_" +
                            "YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyCz19lSR3gBMyzIUmUg_kkSO_EU4LqgkdA");
                }
                //placedata.setIcon_url(hourlyForecastObject.getString("icon"));

                if (hourlyForecastObject.has("rating")) {
                    placedata.setRating(hourlyForecastObject.getDouble("rating"));
                } else {
                    placedata.setRating(0.0);
                }

                if (hourlyForecastObject.has("opening_hours")) {
                    JSONObject isopenobject = hourlyForecastObject.getJSONObject("opening_hours");
                    Boolean isopenvalue = isopenobject.getBoolean("open_now");
                    placedata.setOpennow(isopenvalue);
                } else {
                    placedata.setOpennow(Boolean.parseBoolean(null));
                }
                placedata.setPlaceid(hourlyForecastObject.getString("place_id"));

                if (hourlyForecastObject.has("price_level")) {
                    placedata.setPrice_level(hourlyForecastObject.getInt("price_level"));

                } else {
                    placedata.setPrice_level(0);
                }*/

            // feedArrayList.add(productDetails);
            return productDetails;
        }

    }
}




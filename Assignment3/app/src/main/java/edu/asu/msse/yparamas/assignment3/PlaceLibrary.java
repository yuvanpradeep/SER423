package edu.asu.msse.yparamas.assignment3;

import android.app.Activity;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;

public class PlaceLibrary implements Serializable {
    public Hashtable<String, PlaceDescription> places;

    public PlaceLibrary() {}

    public PlaceLibrary(Activity parent) {
        android.util.Log.d(this.getClass().getSimpleName(),
                "Creating a new place collection");
        places = new Hashtable<>();
        try {
            this.loadFromJsonFile(parent);
        } catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(),
                    "error Resetting from places json file" + ex.getMessage());
        }
    }

    public boolean loadFromJsonFile(Activity parent) {
        boolean result = true;
        try {
            places.clear();
            InputStream is = parent.getApplicationContext().getResources().openRawResource(R.raw.places);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            while (br.ready()) {
                sb.append(br.readLine());
            }
            String placesJsonStr = sb.toString();
            JSONObject placesJson = new JSONObject(new JSONTokener(placesJsonStr));
            Iterator<String> it = placesJson.keys();
            while (it.hasNext()) {
                String placeName = it.next();
                JSONObject placeObj = placesJson.optJSONObject(placeName);

                if (placeObj != null) {
                    PlaceDescription placeDescription = new PlaceDescription(placeObj.toString());
                    places.put(placeName, placeDescription);
                }
            }
        } catch (Exception ex) {
            android.util.Log.d(this.getClass().getSimpleName(),
                    "Exception reading json file: " + ex.getMessage());
            result = false;
        }
        return result;
    }

    public boolean addPlace(PlaceDescription placeDescription) {
        boolean result = true;

        try {
            places.put(placeDescription.name, placeDescription);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public boolean removePlace(String placeName) {
        return (places.remove(placeName) == null) ? false : true;
    }

    public String[] getNames() {
        String[] placeNames = {};
        if (places.size() > 0) {
            placeNames = (places.keySet()).toArray(new String[0]);
        }
        return placeNames;
    }

    public PlaceDescription get(String placeName) {
        PlaceDescription result = null;
        PlaceDescription placeDescription = places.get(placeName);
        if (placeDescription != null) {
            result = placeDescription;
        }
        return result;
    }
}

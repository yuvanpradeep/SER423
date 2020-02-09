/* PlaceLibrary.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: PlaceLibrary is the collection class for place and helps to deserialize the json file data
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 7, 2020
 */

package edu.asu.msse.yparamas.assignment3;

import android.app.Activity;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * PlaceLibrary class is used as collection of places and perform operations over it
 */
public class PlaceLibrary implements Serializable {
    public Hashtable<String, PlaceDescription> places;

    public PlaceLibrary() {}

    public PlaceLibrary(Activity parent) {
        Log.d(this.getClass().getSimpleName(),
                "Creating a new place collection");
        places = new Hashtable<>();
        try {
            this.loadFromJsonFile(parent);
        } catch (Exception ex) {
            Log.d(this.getClass().getSimpleName(),
                    "error loading from places json file" + ex.getMessage());
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
            Log.d(this.getClass().getSimpleName(),
                    "Exception reading the json file: " + ex.getMessage());
            result = false;
        }
        return result;
    }

    public void addPlace(PlaceDescription placeDescription) {
        try {
            places.put(placeDescription.name, placeDescription);
        } catch (Exception ex) {
            Log.d(this.getClass().getSimpleName(), "Unable to add a new place: " + ex.getMessage());
        }
    }

    public void removePlace(String placeName) {
        try {
            places.remove(placeName);
        } catch(Exception ex) {
            Log.d(this.getClass().getSimpleName(), "Unable to remove a place: " + ex.getMessage());
        }
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

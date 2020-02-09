/* PlaceDescription.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: PlaceDescription is the model class for place
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 7, 2020
 */

package edu.asu.msse.yparamas.assignment3;

import android.util.Log;

import org.json.JSONObject;
import java.io.Serializable;

/**
 * PlaceDescription class is the model for describing the place attributes and properties
 */
public class PlaceDescription implements Serializable {
    public String name;
    public String description;
    public String category;
    public String addressTitle;
    public String addressStreet;
    public Double elevation;
    public Double latitude;
    public Double longitude;


    public PlaceDescription(String name, String description, String category, String addressTitle,
                            String addressStreet, Double elevation, Double latitude, Double longitude) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.addressTitle = addressTitle;
        this.addressStreet = addressStreet;
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlaceDescription(String jsonStr) {
        try{
            JSONObject jobj = new JSONObject(jsonStr);
            name = jobj.getString("name");
            description = jobj.getString("description");
            category = jobj.getString("category");
            addressTitle = jobj.getString("address-title");
            addressStreet = jobj.getString("address-street");
            elevation = jobj.getDouble("elevation");
            latitude = jobj.getDouble("latitude");
            longitude = jobj.getDouble("longitude");

        }catch (Exception ex){
            Log.d(this.getClass().getSimpleName(),
                    "Error getting place description from json string"+ ex.getMessage());
        }
    }
}

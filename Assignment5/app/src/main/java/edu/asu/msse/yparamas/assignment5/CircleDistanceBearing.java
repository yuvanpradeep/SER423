/* CircleDistanceBearing.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: CircleDistanceBearing helps to calculate the circle distance and bearing and display it
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 23, 2020
 */

package edu.asu.msse.yparamas.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * CircleDistanceBearing class to calculate the great circle distance and initial bearing of the two
 * selected places
 */
public class CircleDistanceBearing extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener{

    TextView distanceTxt;
    TextView fromPlacTxt;
    TextView bearingTxt;
    Spinner spinnerPlace;
    Double fromLatitude = 0.0;
    Double fromLongitude = 0.0;
    Button goToPlaceLst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circledistance_bearing);

        fromPlacTxt = findViewById(R.id.fromPlacTxt);
        String fromPlaceName = String.valueOf(getIntent().getSerializableExtra("name"));
        fromPlacTxt.setText(fromPlaceName);

        getPlaceDetails(fromPlaceName, "fromPlace");
        getPlaceNames();

        goToPlaceLst = findViewById(R.id.goToPlaceLst);
        // Redirection to main view
        goToPlaceLst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent listView = new Intent(CircleDistanceBearing.this, MainActivity.class);
                getPlaceNames();
                startActivity(listView);
            }
        });

    }

    public void setFromAttributes(PlaceDescription placeDescription) {
        fromLatitude =  placeDescription.latitude;
        fromLongitude =  placeDescription.longitude;
    }

    /**
     * Function to get place name list
     */
    public void getPlaceNames() {
        try{
            MethodDetail methodDetail = new MethodDetail(this, getString(R.string.defaultURL),"getNames",
                    new Object[]{}, "");
            AsyncConnection asyncConnection = (AsyncConnection) new AsyncConnection().execute(methodDetail);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        distanceTxt = findViewById(R.id.distanceTxt);
        String selectedPlace = spinnerPlace.getSelectedItem().toString();
        getPlaceDetails(selectedPlace, "toPlace");
    }

    public void calculate(PlaceDescription toPlace) {
        distanceTxt.setText(String.valueOf(calculateGreatCircle(fromLatitude, toPlace.latitude,
        fromLongitude, toPlace.longitude)));

        bearingTxt = findViewById(R.id.bearingTxt);
        bearingTxt.setText(String.valueOf(calculateBearing(fromLatitude, toPlace.latitude,
                fromLongitude, toPlace.longitude)));
    }

    /**
     * Function to get all detail about a place
     * @param selectedPlace
     */
    public void getPlaceDetails(String selectedPlace, String fromFunction) {
        try{
            MethodDetail methodDetail = new MethodDetail(this, getString(R.string.defaultURL),"get",
                    new String[]{selectedPlace}, fromFunction);
            AsyncConnection asyncConnection = (AsyncConnection) new AsyncConnection().execute(methodDetail);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }

    public double calculateGreatCircle(double latitude1, double latitude2,
                                       double longitude1, double longitude2) {
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);
        longitude1 = Math.toRadians(longitude1);
        longitude2 = Math.toRadians(longitude2);

        double distanlon = longitude2 - longitude1;
        double distanlat = latitude2 - latitude1;
        double a = Math.pow(Math.sin(distanlat / 2), 2)
                + Math.cos(latitude1) * Math.cos(latitude2)
                * Math.pow(Math.sin(distanlon / 2),2);

        double calc = 2 * Math.asin(Math.sqrt(a));
        double earthRad = 3956.0; // 3956.0 for miles and 6371.0 for kilometres

        return(calc * earthRad);
    }

    public double calculateBearing(double latitude1, double latitude2,
                                   double longitude1, double longitude2) {
        Double longitude1Rads = Math.toRadians(latitude1);
        Double longitude2Rads = Math.toRadians(latitude2);
        Double distanceLon = Math.toRadians(longitude2 - longitude1);

        double y = Math.sin(distanceLon) * Math.cos(longitude2Rads);
        double x = Math.cos(longitude1Rads) * Math.sin(longitude2Rads) - Math.sin(longitude1Rads)
                * Math.cos(longitude2Rads) * Math.cos(distanceLon);
        return Math.toDegrees((Math.atan2(y, x)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}

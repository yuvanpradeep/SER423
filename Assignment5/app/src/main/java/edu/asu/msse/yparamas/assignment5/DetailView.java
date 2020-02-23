/* DetailView.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: DetailView helps to display the place description
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
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


/**
 * DetailView class to display the description of the selected place
 */
public class DetailView extends AppCompatActivity {

    Button removeBtn;
    Button circleDistBtn;

    private EditText nameEdit;
    private EditText descriptionEdit;
    private EditText categoryEdit;
    private EditText addressTitleEdit;
    private EditText addressStreetEdit;
    private EditText elevationEdit;
    private EditText latitudeEdit;
    private EditText longitudeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        removeBtn = findViewById(R.id.removeBtn);
        circleDistBtn = findViewById(R.id.circleDistBtn);

        nameEdit = findViewById(R.id.nameView);
        descriptionEdit = findViewById(R.id.descView);
        categoryEdit = findViewById(R.id.cateView);
        addressTitleEdit = findViewById(R.id.titleView);
        addressStreetEdit = findViewById(R.id.streetView);
        elevationEdit = findViewById(R.id.elevView);
        latitudeEdit = findViewById(R.id.latView);
        longitudeEdit = findViewById(R.id.longView);

        nameEdit.setEnabled(false);

        String placeName = String.valueOf(getIntent().getSerializableExtra("placeName"));
        loadPlaceDetails(placeName, "get");


        removeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadPlaceDetails(String.valueOf(nameEdit.getText()), "remove");
            }
        });

        circleDistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent distanceView = new Intent(DetailView.this, CircleDistanceBearing.class);
            distanceView.putExtra("name", String.valueOf(nameEdit.getText()).trim());
            distanceView.putExtra("latitude", String.valueOf(latitudeEdit.getText()).trim());
            distanceView.putExtra("longitude", String.valueOf(longitudeEdit.getText()).trim());
            startActivity(distanceView);
            }
        });
    }


    public void loadPlaceDetails(String placeName, String methodName){
        try{
            MethodDetail methodDetail = new MethodDetail(this, getString(R.string.defaultURL),methodName,
                    new String[]{placeName});
            AsyncConnection asyncConnection = (AsyncConnection) new AsyncConnection().execute(methodDetail);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception: "+
                    ex.getMessage());
        }
    }
    public void setValues(PlaceDescription placeDescription) {
        try{
            nameEdit.setText(placeDescription.name);
            descriptionEdit.setText(placeDescription.description);
            categoryEdit.setText(placeDescription.category);
            addressTitleEdit.setText(placeDescription.addressTitle);
            addressStreetEdit.setText(placeDescription.addressStreet);
            elevationEdit.setText(String.valueOf(placeDescription.elevation));
            latitudeEdit.setText(String.valueOf(placeDescription.latitude));
            longitudeEdit.setText(String.valueOf(placeDescription.longitude));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updatePlaceDescription(View view) {
        PlaceDescription placeDescription = new PlaceDescription(String.valueOf(nameEdit.getText()),
                String.valueOf(descriptionEdit.getText()), String.valueOf(categoryEdit.getText()),
                String.valueOf(addressTitleEdit.getText()),
                String.valueOf(addressStreetEdit.getText()),
                Double.valueOf(String.valueOf(elevationEdit.getText())),
                Double.valueOf(String.valueOf(latitudeEdit.getText())),
                Double.valueOf(String.valueOf(longitudeEdit.getText())));

        loadPlaceDetails(String.valueOf(nameEdit.getText()), "remove");
        addPlace(placeDescription);
    }

    public void addPlace(PlaceDescription placeDescription) {
        try{
            MethodDetail methodDetail = new MethodDetail(this, getString(R.string.defaultURL),"add",
                    new Object[]{placeDescription.toJson()});
            AsyncConnection asyncConnection = (AsyncConnection) new AsyncConnection().execute(methodDetail);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception: "+
                    ex.getMessage());
        }
    }

    public void loadMainActivity() {
        Intent activity = new Intent(this, MainActivity.class);
        startActivity(activity);
    }
}

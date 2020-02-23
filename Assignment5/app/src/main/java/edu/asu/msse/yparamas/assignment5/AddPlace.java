/* AddPlace.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: Add place class to add new place using server
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

import org.json.JSONObject;

/**
 * Add place class helps the user to add new place to the server
 */
public class AddPlace extends AppCompatActivity {

    private EditText nameEdit;
    private EditText descriptionEdit;
    private EditText categoryEdit;
    private EditText addressTitleEdit;
    private EditText addressStreetEdit;
    private EditText elevationEdit;
    private EditText latitudeEdit;
    private EditText longitudeEdit;

    Button addPlaceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place);
        addPlaceBtn = findViewById(R.id.addPlaceBtn);
        nameEdit = findViewById(R.id.nameView);
        descriptionEdit = findViewById(R.id.descView);
        categoryEdit = findViewById(R.id.cateView);
        addressTitleEdit = findViewById(R.id.titleView);
        addressStreetEdit = findViewById(R.id.streetView);
        elevationEdit = findViewById(R.id.elevView);
        latitudeEdit = findViewById(R.id.latView);
        longitudeEdit = findViewById(R.id.longView);

        addPlaceBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(!String.valueOf(nameEdit.getText()).trim().equals("")) {
                    PlaceDescription placeDescription = new PlaceDescription(String.valueOf(nameEdit.getText()),
                            String.valueOf(descriptionEdit.getText()), String.valueOf(categoryEdit.getText()),
                            String.valueOf(addressTitleEdit.getText()),
                            String.valueOf(addressStreetEdit.getText()),
                            Double.valueOf(String.valueOf(elevationEdit.getText())),
                            Double.valueOf(String.valueOf(latitudeEdit.getText())),
                            Double.valueOf(String.valueOf(longitudeEdit.getText())));

                    addPlaceDetails(placeDescription.toJson());
                } else {
                    android.util.Log.w(this.getClass().getSimpleName(),"Place name should  not be empty ");
                }
            }
        });

    }

    /**
     * Function to add new place to the server
     * @param newPlace
     */
    public void addPlaceDetails(JSONObject newPlace) {
        try{
            MethodDetail methodDetail = new MethodDetail(this, getString(R.string.defaultURL),"add",
                    new Object[]{newPlace}, "");
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

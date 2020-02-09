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
 * Purpose: DetailView helps to display the place description and also perform update operation
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 7, 2020
 */

package edu.asu.msse.yparamas.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private PlaceLibrary placeLibrary;


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

        placeLibrary =  (PlaceLibrary) getIntent().getSerializableExtra("placeLibrary");
        nameEdit.setText(String.valueOf(getIntent().getSerializableExtra("name")));
        descriptionEdit.setText(String.valueOf(getIntent().getSerializableExtra("description")));
        categoryEdit.setText(String.valueOf(getIntent().getSerializableExtra("category")));
        addressTitleEdit.setText(String.valueOf(getIntent().getSerializableExtra("addressTitle")));
        addressStreetEdit.setText(String.valueOf(getIntent().getSerializableExtra("addressStreet")));
        elevationEdit.setText(String.valueOf(getIntent().getSerializableExtra("elevation")));
        latitudeEdit.setText(String.valueOf(getIntent().getSerializableExtra("latitude")));
        longitudeEdit.setText(String.valueOf(getIntent().getSerializableExtra("longitude")));


        removeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                placeLibrary.removePlace(String.valueOf(nameEdit.getText()).trim());
                Intent listView = new Intent(DetailView.this, MainActivity.class);
                listView.putExtra("placeLibrary", placeLibrary);
                startActivity(listView);
            }
        });

        circleDistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distanceView = new Intent(DetailView.this, CircleDistanceBearing.class);
                distanceView.putExtra("placeLibrary", placeLibrary);
                distanceView.putExtra("name", String.valueOf(nameEdit.getText()).trim());
                distanceView.putExtra("latitude", getIntent().getSerializableExtra("latitude"));
                distanceView.putExtra("longitude", getIntent().getSerializableExtra("longitude"));
                startActivity(distanceView);
            }
        });
    }

    public void updatePlaceDescription(View view) {
        try{
            String name = String.valueOf(nameEdit.getText()).trim();
            String description = String.valueOf(descriptionEdit.getText()).trim();
            String category = String.valueOf(categoryEdit.getText()).trim();
            String addressTitle = String.valueOf(addressTitleEdit.getText()).trim();
            String addressStreet = String.valueOf(addressStreetEdit.getText()).trim();
            Double elevation =  Double.valueOf(String.valueOf(elevationEdit.getText()).trim());
            Double latitude = Double.valueOf(String.valueOf(latitudeEdit.getText()).trim());
            Double longitude = Double.valueOf(String.valueOf(longitudeEdit.getText()).trim());

            PlaceDescription placeDescription = new PlaceDescription(name, description, category,
                    addressTitle, addressStreet, elevation, latitude, longitude);

            placeLibrary.places.put(name, placeDescription);

            Intent listView = new Intent(DetailView.this, MainActivity.class);
            listView.putExtra("placeLibrary", placeLibrary);
            startActivity(listView);
        } catch(Exception ex) {
            Log.d(this.getClass().getSimpleName(),
                    "Unable to update the place description" + ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        Intent listView = new Intent(DetailView.this, MainActivity.class);
        listView.putExtra("placeLibrary", placeLibrary);
        startActivity(listView);
    }
}

/* PlaceNameDialog.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: PlaceNameDialog helps the add new place name
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * PlaceNameDialog class helps the user to add new place to the library
 */
public class PlaceNameDialog extends AppCompatActivity {

    Button createBtn;
    EditText placeNameText;
    PlaceLibrary placeLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.place_name);

        createBtn = findViewById(R.id.createBtn);
        placeNameText = findViewById(R.id.placeNameText);
        placeLibrary = (PlaceLibrary) getIntent().getSerializableExtra("placeLibrary");

        createBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!String.valueOf(placeNameText.getText()).trim().equals("")) {
                    Intent detailView = new Intent(PlaceNameDialog.this, DetailView.class);

                    PlaceDescription placeDescription = new PlaceDescription(String.valueOf(placeNameText.getText()).trim(),
                            "", "", "", "",
                            0.0, 0.0, 0.0);

                    placeLibrary.addPlace(placeDescription);

                    detailView.putExtra("name", String.valueOf(placeNameText.getText()).trim());
                    detailView.putExtra("placeLibrary", placeLibrary);
                    detailView.putExtra("description", "");
                    detailView.putExtra("category","");
                    detailView.putExtra("addressTitle", "");
                    detailView.putExtra("addressStreet", "");
                    detailView.putExtra("elevation", "0.0");
                    detailView.putExtra("latitude", "0.0");
                    detailView.putExtra("longitude", "0.0");
                    startActivity(detailView);
                } else {
                    Log.d(this.getClass().getSimpleName(),
                            "Please enter the valid place name to add");
                }
            }
        });
    }
}

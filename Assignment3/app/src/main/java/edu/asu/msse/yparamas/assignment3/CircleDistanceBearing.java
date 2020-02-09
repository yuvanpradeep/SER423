package edu.asu.msse.yparamas.assignment3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CircleDistanceBearing extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener{


    TextView distanceTxt;
    TextView fromPlacTxt;
    Spinner spinnerPlace;
    PlaceLibrary placeLibrary;
    Double fromLatitude = 0.0;
    Double fromLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circledistance_bearing);


        // Load spinner
        spinnerPlace = findViewById(R.id.spinnerPlace);
        placeLibrary = (PlaceLibrary) getIntent().getSerializableExtra("placeLibrary");
        String [] otherPlaces = placeLibrary.getNames();
        ArrayAdapter<String> placesDropdown = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, otherPlaces);
        spinnerPlace.setAdapter(placesDropdown);
        spinnerPlace.setOnItemSelectedListener(this);


        fromPlacTxt = findViewById(R.id.fromPlacTxt);
        String fromPlaceName = String.valueOf(getIntent().getSerializableExtra("name"));
        fromPlacTxt.setText(fromPlaceName);


        fromLatitude = (Double) getIntent().getSerializableExtra("latitude");
        fromLongitude = (Double) getIntent().getSerializableExtra("longitude");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        distanceTxt = findViewById(R.id.distanceTxt);
        String selectedPlace = spinnerPlace.getSelectedItem().toString();
        PlaceDescription toPlace = placeLibrary.get(selectedPlace);
        distanceTxt.setText(String.valueOf(calculateGreatCircle(fromLatitude, toPlace.latitude,
                fromLongitude, toPlace.longitude)));

    }

    public double calculateGreatCircle(double latitude1, double latitude2,
                                       double longitude1, double longitude2)
    {
        longitude1 = Math.toRadians(longitude1);
        longitude2 = Math.toRadians(longitude2);
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);


        double dlon = longitude2 - longitude1;
        double dlat = latitude2 - latitude1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(latitude1) * Math.cos(latitude2)
                * Math.pow(Math.sin(dlon / 2),2);

        double calc = 2 * Math.asin(Math.sqrt(a));
        double earthRad = 6371;

        return(calc * earthRad);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}

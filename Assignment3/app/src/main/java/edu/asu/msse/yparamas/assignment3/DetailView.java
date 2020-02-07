package edu.asu.msse.yparamas.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DetailView extends AppCompatActivity {

    Button removeBtn;

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
                placeLibrary.removePlace(String.valueOf(nameEdit.getText()));
                Intent listView = new Intent(DetailView.this, MainActivity.class);
                listView.putExtra("placeLibrary", placeLibrary);
                startActivity(listView);
            }
        });

    }

    public void updatePlaceDescription(View view) {
        String name = String.valueOf(nameEdit.getText());
        String description = String.valueOf(descriptionEdit.getText());
        String category = String.valueOf(categoryEdit.getText());
        String addressTitle = String.valueOf(addressTitleEdit.getText());
        String addressStreet = String.valueOf(addressStreetEdit.getText());
        Double elevation =  Double.valueOf(String.valueOf(elevationEdit.getText()));
        Double latitude = Double.valueOf(String.valueOf(latitudeEdit.getText()));
        Double longitude = Double.valueOf(String.valueOf(longitudeEdit.getText()));

        PlaceDescription placeDescription = new PlaceDescription(name, description, category,
                addressTitle, addressStreet, elevation, latitude, longitude);

        placeLibrary.places.put(name, placeDescription);

        Intent listView = new Intent(DetailView.this, MainActivity.class);
        listView.putExtra("placeLibrary", placeLibrary);
        startActivity(listView);

    }
}

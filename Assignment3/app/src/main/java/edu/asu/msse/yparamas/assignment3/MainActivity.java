package edu.asu.msse.yparamas.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    PlaceLibrary placeLibrary = new PlaceLibrary();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getSerializableExtra("placeLibrary") != null) {
            placeLibrary = (PlaceLibrary) getIntent().getSerializableExtra("placeLibrary");
        } else {
            placeLibrary = new PlaceLibrary(this);
            placeLibrary.loadFromJsonFile(this);
        }

        String[] placeNames = placeLibrary.getNames();
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.place_listview, placeNames);

        listView = findViewById(R.id.place_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = view.findViewById(R.id.place_name);
        String text = textView.getText().toString();

        PlaceDescription placeDescription = placeLibrary.get(text);

        try {
            Intent newIntent = new Intent(MainActivity.this, DetailView.class);
            newIntent.putExtra("placeLibrary", placeLibrary);
            newIntent.putExtra("name", placeDescription.name);
            newIntent.putExtra("description", placeDescription.description);
            newIntent.putExtra("category", placeDescription.category);
            newIntent.putExtra("addressTitle", placeDescription.addressTitle);
            newIntent.putExtra("addressStreet", placeDescription.addressStreet);
            newIntent.putExtra("elevation", placeDescription.elevation);
            newIntent.putExtra("latitude", placeDescription.latitude);
            newIntent.putExtra("longitude", placeDescription.longitude);

            startActivity(newIntent);
        }catch(Exception e) {
            System.out.println("Error binding!" + e);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addPlace) {
            Intent dialog = new Intent(this, PlaceNameDialog.class);
            startActivity(dialog);

        }
        return super.onOptionsItemSelected(item);
    }
}

/* MainActivity.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: Main activity which loads the list of places and displays the detail.
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 23, 2020
 */
package edu.asu.msse.yparamas.assignment5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * MainActivity class is used to display the list of place names which got extracted from json file
 */
public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    ListView listView;
    CustomListAdapter placeNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] placeNames = new String[]{"unknown"};
        placeNameAdapter = new CustomListAdapter(this, placeNames);
        listView = findViewById(R.id.place_list);
        listView.setAdapter(placeNameAdapter);
        listView.setOnItemClickListener(this);
        getPlaceNames();
    }

    /**
     * Function get place name list
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = view.findViewById(R.id.place_name);
        String placeName = textView.getText().toString().trim();
        Intent intent = new Intent(this, DetailView.class);
        intent.putExtra("placeName", placeName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addPlace) {
            Intent dialog = new Intent(this, AddPlace.class);
            startActivity(dialog);
        }
        return super.onOptionsItemSelected(item);
    }
}

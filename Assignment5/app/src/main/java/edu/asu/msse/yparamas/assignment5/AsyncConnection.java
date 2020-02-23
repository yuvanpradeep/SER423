/* AsyncConnection.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: Example Android application that uses an AsyncTask to accomplish the same effect
 * as using a Thread and android.os.Handler
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 23, 2020
 */

package edu.asu.msse.yparamas.assignment5;

import android.os.AsyncTask;
import android.os.Looper;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;

public class AsyncConnection extends AsyncTask<MethodDetail, Integer, MethodDetail> {
    @Override
    protected void onPreExecute(){
        android.util.Log.d(this.getClass().getSimpleName(),"in onPreExecute on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
    }

    @Override
    protected MethodDetail doInBackground(MethodDetail... asyncRequest) {
        android.util.Log.d(this.getClass().getSimpleName(),"in doInBackground on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
        try {
            JSONArray ja = new JSONArray(asyncRequest[0].params);
            android.util.Log.d(this.getClass().getSimpleName(),"params: "+ ja.toString());
            String requestData = "{ \"jsonrpc\":\"2.0\", \"method\":\""+ asyncRequest[0].method
                    + "\"," + " \"params\":"+ja.toString()+ ",\"id\":3}";
            android.util.Log.d(this.getClass().getSimpleName(),"requestData: "+requestData+" url: "+asyncRequest[0].url);
            JsonRPCRequestViaHttp conn = new JsonRPCRequestViaHttp((new URL(asyncRequest[0].url)), asyncRequest[0].activity);
            asyncRequest[0].resultAsJson = conn.call(requestData);
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"exception in remote call "+
                    ex.getMessage());
        }
        return asyncRequest[0];
    }


    @Override
    protected void onPostExecute(MethodDetail result){
        android.util.Log.d(this.getClass().getSimpleName(),"result: "+ result);
        try {
            if (result.method.equals("getNames")) {
                JSONObject jsonObject = new JSONObject(result.resultAsJson);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                ArrayList<String> al = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    al.add(jsonArray.getString(i));
                }
                String[] placeNames = al.toArray(new String[0]);

                if(result.activity.getComponentName().getClassName().contains("CircleDistanceBearing")) {
                    CircleDistanceBearing cdb = (CircleDistanceBearing) result.activity;

                    ArrayAdapter<String> placesDropdown = new ArrayAdapter<>(result.activity,
                            android.R.layout.simple_spinner_dropdown_item, placeNames);
                    cdb.spinnerPlace = result.activity.findViewById(R.id.spinnerPlace);
                    cdb.spinnerPlace.setAdapter(placesDropdown);
                    cdb.spinnerPlace.setOnItemSelectedListener(cdb);

                } else {
                    MainActivity mainActivity = (MainActivity) result.activity;
                    mainActivity.placeNameAdapter = new CustomListAdapter(result.activity, placeNames);
                    mainActivity.listView = result.activity.findViewById(R.id.place_list);
                    mainActivity.listView.setAdapter( ((MainActivity)result.activity).placeNameAdapter);
                    mainActivity.listView.setOnItemClickListener((mainActivity));
                }

            } else if (result.method.equals("get")) {
                JSONObject jsonObject = new JSONObject(result.resultAsJson);
                PlaceDescription placeDescription = new PlaceDescription(jsonObject.getJSONObject("result"));
                if(result.activity.getComponentName().getClassName().contains("CircleDistanceBearing")) {
                    CircleDistanceBearing cdb = (CircleDistanceBearing) result.activity;
                    cdb.calculate(placeDescription);
                } else {
                    DetailView detailView = (DetailView) result.activity;
                    detailView.setValues(placeDescription);
                }
            } else if (result.method.equals("add")) {
                if(result.activity.getComponentName().getClassName().contains("AddPlace")) {
                    JSONObject jsonObject = new JSONObject(result.resultAsJson);
                    if (jsonObject.get("result").equals(true)) {
                        AddPlace addPlace = (AddPlace) result.activity;
                        addPlace.loadMainActivity();
                    } else {
                        android.util.Log.d(this.getClass().getSimpleName(), "Add place got failed");
                    }
                } else {
                    goToMainPage(result);
                }
            } else if (result.method.equals("remove")) {
                goToMainPage(result);
            }
        }  catch (JSONException e) {
           e.printStackTrace();
        }
    }

    public void goToMainPage(MethodDetail result) {
        try {
            JSONObject jsonObject = new JSONObject(result.resultAsJson);
            if(jsonObject.get("result").equals(true)) {
                DetailView detailView = (DetailView) result.activity;
                detailView.loadMainActivity();
            } else {
                android.util.Log.d(this.getClass().getSimpleName(),"Unable to load main page");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

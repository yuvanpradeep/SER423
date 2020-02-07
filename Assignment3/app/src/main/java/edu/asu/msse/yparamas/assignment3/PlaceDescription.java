package edu.asu.msse.yparamas.assignment3;

import org.json.JSONObject;
import java.io.Serializable;

public class PlaceDescription implements Serializable {
    public String name;
    public String description;
    public String category;
    public String addressTitle;
    public String addressStreet;
    public Double elevation;
    public Double latitude;
    public Double longitude;


    public PlaceDescription(String name, String description, String category, String addressTitle,
                            String addressStreet, Double elevation, Double latitude, Double longitude) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.addressTitle = addressTitle;
        this.addressStreet = addressStreet;
        this.elevation = elevation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlaceDescription(String jsonStr) {
        try{
            JSONObject jobj = new JSONObject(jsonStr);
            name = jobj.getString("name");
            description = jobj.getString("description");
            category = jobj.getString("category");
            addressTitle = jobj.getString("address-title");
            addressStreet = jobj.getString("address-street");
            elevation = jobj.getDouble("elevation");
            latitude = jobj.getDouble("latitude");
            longitude = jobj.getDouble("longitude");

        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),
                    "error getting PlaceDescription description from json string");
        }
    }
}

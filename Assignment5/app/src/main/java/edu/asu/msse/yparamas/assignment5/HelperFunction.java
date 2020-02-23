package edu.asu.msse.yparamas.assignment5;

import android.app.Activity;

import org.json.JSONObject;

public class HelperFunction {

    public void callMethod(Activity activity,
                           String methodName,
                           PlaceDescription placeDescription,
                           JSONObject jsonObject) {

        try{
            MethodDetail methodDetail = new MethodDetail(activity, String.valueOf(R.string.defaultURL),"getNames",
                    new Object[]{});
            AsyncConnection asyncConnection = (AsyncConnection) new AsyncConnection().execute(methodDetail);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }
}

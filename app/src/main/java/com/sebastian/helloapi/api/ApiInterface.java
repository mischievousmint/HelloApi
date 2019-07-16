package com.sebastian.helloapi.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.sebastian.helloapi.model.Film;
import com.sebastian.helloapi.model.Films;
import com.sebastian.helloapi.model.Vehicle;
import com.sebastian.helloapi.model.Vehicles;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by terranology on 15/7/19.
 */

public class ApiInterface {

    public static Films films;
    private static ApiInterface mInstance = null;
    private static ApiListener mApiListener;
    private Context mContext;
    public static Vehicles vehicles;

    public static ApiInterface getInstance() {
        if (mInstance == null)
            mInstance = new ApiInterface();
        return mInstance;
    }

    public void setApiListener(ApiListener listener, Context context) {
        mApiListener = listener;
        mContext = context;
    }

   public void getFilms() {
        int tag = 1;
        String endpoint = "https://swapi.co/api/films/";
        callApiGet(endpoint, mApiListener, tag, false);
    }

    public void getVehicles() {
        int tag = 1;
        String endpoint = "https://swapi.co/api/vehicles/";
        callApiGet(endpoint, mApiListener, tag, false);
    }



    // **************   API CALLS   **************** //


    private void callApiGet(String url, final ApiListener listener, final int tag, final boolean withAuth) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject responseJson) {



                        //  Creo un Objeto de la clase Films
                        Gson gson = new Gson();
                        films = gson.fromJson(responseJson.toString(), Films.class);
                        //vehicles = gson.fromJson(responseJson.toString(), Vehicles.class);

                        Log.i("OK", films.toString());
                        //Log.i("OK", vehicles.toString());


                        listener.onResponse(films);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("KO", "Error" + error.getMessage());
                listener.onErrorResponse(tag, error.getMessage());
            }
        });

        // HACE LA PETICIÓN
        ApiController.getInstance().addToRequestQueue(jsonObjReq, tag);

    }

    public interface ApiListener {
        void onResponse(Films films);

        void onErrorResponse(Object tag, String error);
    }
}

package com.suvidha.rupeekhiring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private final String API_URL = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/placesofinterest39c1c48.json";
    private final Context context = this;
    private RecyclerView recyclerView_poi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView_poi = findViewById(R.id.home_rv_poi);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView_poi.setLayoutManager(gridLayoutManager);
        fetchData();
    }

    private void fetchData()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(API_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<POI> poiList = new ArrayList<>();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        poiList.add(new POI(
                                Integer.parseInt(jsonObject.get("id").toString()),
                                jsonObject.get("name").toString(),
                                jsonObject.get("image").toString(),
                                jsonObject.get("latitude").toString(),
                                jsonObject.get("longitude").toString(),
                                jsonObject.get("address").toString()
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView_poi.setAdapter(new RecyclerViewAdapterPOI(poiList,context));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
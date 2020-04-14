package com.mind.goodstracker.DiliveryPerson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mind.goodstracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeliveryPerson extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<ShowTrip> tripArrayList = new ArrayList<>();
    public String source;
    public String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_person);

        recyclerView = (RecyclerView)findViewById(R.id.tripRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(DeliveryPerson.this));

//        showTrip.setTitle("TRIP1");
//        showTrip.setSource("Source");
//        showTrip.setDest("Dest");

        //getting source and destination
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.234:3000/trip_info",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("source");
                            JSONArray jsonArray1 = jsonObject.getJSONArray("destination");

                            //Array for source
                            for(int i=0; i<jsonArray.length(); i++){
                                 JSONObject o = jsonArray.getJSONObject(i);
                                JSONObject o1 = jsonArray1.getJSONObject(i);
                                ShowTrip showTrip=new ShowTrip();
                                showTrip.setTitle("TRIP "+(i+1));
//                                source=o.getString("area")+","+o.getString("pincode")+","+o.getString("city")+","+o.getString("state")+","+o.getString("country");
//                                destination=o.getString("area")+","+o.getString("pincode")+","+o.getString("city")+","+o.getString("state")+","+o.getString("country");
                                showTrip.setSource(o.getString("area")+","+o.getString("city")+","+o.getString("state")+","+o.getString("country"));
                                showTrip.setDest(o1 .getString("area")+","+o1.getString("city")+","+o1.getString("state")+","+o1.getString("country"));
                                tripArrayList.add(showTrip);
                            }
                            //creating adapter object and setting it to recyclerview
                            TripAdapter adapter = new TripAdapter(DeliveryPerson.this, tripArrayList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error+"error123");

                    }
                })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put("id",Integer.parseInt(getIntent().getStringExtra("loginid"))+"");
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(DeliveryPerson.this);
        requestQueue.add(stringRequest);

    }
}

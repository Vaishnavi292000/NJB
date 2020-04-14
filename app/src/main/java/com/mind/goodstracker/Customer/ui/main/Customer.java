package com.mind.goodstracker.Customer.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class Customer extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<ShowOrder> orderArrayList = new ArrayList<>();
    int loginid;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_customer, container, false);

        recyclerView = (RecyclerView)root.findViewById(R.id.customerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        loginid=Integer.parseInt(getIntent().getStringExtra("loginid"));
//        showTrip.setTitle("TRIP1");
//        showTrip.setSource("Source");
//        showTrip.setDest("Dest");

        //getting source and destination
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.234:3000/order_info",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("product");

                            //Array for source
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                ShowOrder showOrder=new ShowOrder();
                                showOrder.setTitle("ORDER "+(i+1));
                                showOrder.setProductName(o.getString("name"));
                                showOrder.setId(o.getString("id"));
                                orderArrayList.add(showOrder);
                            }
                            //creating adapter object and setting it to recyclerview
                            OrderAdapter adapter = new OrderAdapter(getActivity(), orderArrayList);
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
                params.put("id",Integer.parseInt(getActivity().getIntent().getStringExtra("loginid"))+"");
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return root;
        }
}

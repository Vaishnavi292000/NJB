package com.mind.goodstracker.Admin.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class TripDescription extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    Button submit;
    Spinner source,dest,vehicleno;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList1 = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter,arrayAdapter1,arrayAdapter2;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.trip_description, container, false);
        submit=(Button)root.findViewById(R.id.submitTrip);

        source=(Spinner)root.findViewById(R.id.source);
        dest=(Spinner)root.findViewById(R.id.destination);
        vehicleno=(Spinner)root.findViewById(R.id.vehicalno);

        if (!arrayList.isEmpty() || !arrayList1.isEmpty()||!arrayList2.isEmpty())
        {
            arrayList.clear();
            arrayList1.clear();
            arrayList2.clear();
        }
        arrayList.add("SELECT SOURCE");
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source.setAdapter(arrayAdapter);
        source.setSelection(0);

        arrayList1.add("SELECT DESTINATION");
        arrayAdapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dest.setAdapter(arrayAdapter1);
        dest.setSelection(0);

        arrayList2.add("SELECT VEHICLE NUMBER");
        arrayAdapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleno.setAdapter(arrayAdapter2);
        vehicleno.setSelection(0);

        getSpinner();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"In trip details",Toast.LENGTH_LONG).show();
//                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.234:3000/trip_details",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                if(response.equals("1"))
//                                {
//                                    final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
//                                    builder.setMessage("Inserted Successfully");
//                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                            source.setSelection(0);
//                                            dest.setSelection(0);
//                                            vehicleno.setSelection(0);
////                                            startActivity(new Intent(ProductDescription.this,TripDescription .class));
//
//                                        }
//                                    });
//                                    builder.create()
//                                            .show();
//                                }
//                                else
//                                {
//                                    final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
//                                    builder.setMessage("Inserted Unsuccessfully");
//                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                    builder.create()
//                                            .show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                System.out.println(error+"error123");
//
//                            }
//                        })
//                {
//                    @Override
//                    protected Map<String,String> getParams() throws AuthFailureError
//                    {
//                        Map<String,String> params=new HashMap<>();
//                        params.put("source",source.getSelectedItem().toString());
//                        params.put("destination",dest.getSelectedItem().toString());
//                        params.put("vehicleno",vehicleno.getSelectedItem().toString());
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue= Volley.newRequestQueue(getContext());
//                requestQueue.add(stringRequest);
            }
        });
        return root;
    }

    public void getSpinner()
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.234:3000/trip_description",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("city");
                            JSONArray jsonArray1 = jsonObject.getJSONArray("state");
                            JSONArray jsonArray2 = jsonObject.getJSONArray("vehicle_no");

                            //Array for source
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                JSONObject o1 = jsonArray1.getJSONObject(i);
                                arrayList.add(o.getString("city")+","+o1.getString("state"));
                            }
                            arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            source.setAdapter(arrayAdapter);
                            source.setSelection(0);

                            //Array for destination
                            for(int i=0; i<jsonArray1.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                JSONObject o1 = jsonArray1.getJSONObject(i);
                                arrayList1.add(o.getString("city")+","+o1.getString("state"));
                            }
                            arrayAdapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList1);
                            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dest.setAdapter(arrayAdapter1);
                            dest.setSelection(0);

                            //Array for vehicle
                            for(int i=0; i<jsonArray2.length(); i++){
                                JSONObject o2 = jsonArray2.getJSONObject(i);
                                arrayList2.add(o2.getString("vehicle_no"));
                            }
                            arrayAdapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList2);
                            arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            vehicleno.setAdapter(arrayAdapter2);
                            vehicleno.setSelection(0);

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
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
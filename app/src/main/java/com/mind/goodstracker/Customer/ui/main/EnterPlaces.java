package com.mind.goodstracker.Customer.ui.main;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mind.goodstracker.Admin.ui.main.PageViewModel;
import com.mind.goodstracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnterPlaces extends Fragment {

    EditText area,city,state,country,pincode,placetype;
    Button submit;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_enter_places, container, false);

        area=(EditText)root.findViewById(R.id.editTextArea);
        city=(EditText)root.findViewById(R.id.editTextCity);
        state=(EditText)root.findViewById(R.id.editTextState);
        country=(EditText)root.findViewById(R.id.editTextCountry);
        pincode=(EditText)root.findViewById(R.id.editTextPincode);
        placetype=(EditText)root.findViewById(R.id.editTextPlacetype);
        submit=(Button)root.findViewById(R.id.submitplace);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.234:3000/enter_places",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1"))
                                {
                                    final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                    builder.setMessage("Inserted Successfully");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            area.setText("");
                                            city.setText("");
                                            state.setText("");
                                            country.setText("");
                                            pincode.setText("");
                                            placetype.setText("");
//                                            startActivity(new Intent(ProductDescription.this,TripDescription .class));

                                        }
                                    });
                                    builder.create()
                                            .show();
                                }
                                else
                                {
                                    final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                    builder.setMessage("Inserted Unsuccessfully");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.create()
                                            .show();
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
                        params.put("area",area.getText().toString());
                        params.put("city",city.getText().toString());
                        params.put("state",state.getText().toString());
                        params.put("country",country.getText().toString());
                        params.put("pincode",pincode.getText().toString());
                        params.put("place_type",placetype.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            }
        });

        return root;
    }
}
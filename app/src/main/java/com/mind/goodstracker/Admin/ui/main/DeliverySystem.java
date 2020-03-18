package com.mind.goodstracker.Admin.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mind.goodstracker.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */

public class DeliverySystem extends Fragment {

    EditText name,designation,vehical;
    // Spinner vehicalno;
    Button submit;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.delivery_system, container, false);
        name=(EditText)root.findViewById(R.id.editTextName);
        designation=(EditText)root.findViewById(R.id.editTextDesignation);
        vehical=(EditText)root.findViewById(R.id.editTextVehical);
        submit=(Button)root.findViewById(R.id.submitDelivery);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.234:3000/delivery_system",
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
                                            name.setText("");
                                            designation.setText("");
                                            vehical.setText("");
//                                            startActivity(new Intent(LoginActivity.this,ProductDescription.class));
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
                        params.put("name",name.getText().toString());
                        params.put("designation",designation.getText().toString());
                        params.put("vehicle",vehical.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });
        return root;
    }
}
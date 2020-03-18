package com.mind.goodstracker.Admin.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductDescription extends Fragment {

    EditText pname, mfdlocation;
    Spinner pstatus;
    Button submit;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_description, container, false);

        pname=(EditText)root.findViewById(R.id.editTextProductname);
        mfdlocation=(EditText)root.findViewById(R.id.editTextMfdLocation);
        submit=(Button)root.findViewById(R.id.submitProduct);

        pstatus=(Spinner)root.findViewById(R.id.ProductStatus);
        arrayList.add("SELECT THE PRODUCT STATUS");
        arrayList.add("In Production");
        arrayList.add("Manufactured");
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pstatus.setAdapter(arrayAdapter);
        pstatus.setSelection(0);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.234:3000/product_description",
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
                                            pname.setText("");
                                            mfdlocation.setText("");
                                            pstatus.setSelection(0);
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
                        params.put("pname",pname.getText().toString());
                        params.put("mfdlocation",mfdlocation.getText().toString());
                        params.put("pstatus",pstatus.getSelectedItem().toString());
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
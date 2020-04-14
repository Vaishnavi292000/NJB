package com.mind.goodstracker.LoginSystem;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.mind.goodstracker.Admin.Admin;
import com.mind.goodstracker.Admin.Admin;
import com.mind.goodstracker.Customer.Customer_Tabbed;
import com.mind.goodstracker.Customer.ui.main.Customer;
import com.mind.goodstracker.DiliveryPerson.DeliveryPerson;
import com.mind.goodstracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btRegister;
    private ImageView circle1;
    TextView tvLogin;
    EditText username,password;
    Button btlogin;
    String category;
    String loginid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);

        btlogin = findViewById(R.id.btLogin);
        btRegister  = findViewById(R.id.btRegister);
        tvLogin     = findViewById(R.id.tvLogin);
        circle1     = findViewById(R.id.circle1);

        btRegister.setOnClickListener(this);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,username.getText().toString(),Toast.LENGTH_LONG).show();
                StringRequest sr = new StringRequest(Request.Method.POST,"http://192.168.43.234:3000/auth", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response = "+response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("user");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                category =o.getString("category");
                                loginid=(o.getString("id"));
                            }
                            if(category.equals("admin"))
                            {
                                Toast.makeText(MainActivity.this, "admin", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Admin.class));
                            }
                            else if (category.equals("customer"))
                            {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Customer_Tabbed.class).putExtra("loginid",loginid));
                            }
                            else if (category.equals("driver")){
                                Toast.makeText(MainActivity.this, "driver", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, DeliveryPerson.class).putExtra("loginid",loginid));
                            }
                            else {
                                Toast.makeText(MainActivity.this, "INVALID USER", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
                {
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError
                    {
                        Map<String,String> params=new HashMap<>();
                        params.put("username",username.getText().toString());
                        params.put("password",password.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(sr);

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v==btRegister){
            Intent a = new Intent(MainActivity.this, RegisterActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View,String> (tvLogin,"login");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
            startActivity(a,activityOptions.toBundle());
        }
    }
}

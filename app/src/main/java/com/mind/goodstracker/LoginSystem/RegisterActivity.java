package com.mind.goodstracker.LoginSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    private Menu menu;
    EditText name,email,username,password,repassword,contact;
    Spinner cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        rlayout     = findViewById(R.id.rlayout);
        animation   = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        rlayout.setAnimation(animation);
        
        
        name=(EditText)findViewById(R.id.etName);
        email=(EditText)findViewById(R.id.etEmail);
        username=(EditText)findViewById(R.id.etUsername);
        password=(EditText)findViewById(R.id.etPassword);
        repassword=(EditText)findViewById(R.id.etRePassword);
        contact=(EditText)findViewById(R.id.etContact);
        
        if(password.getText().toString().equals(repassword.getText().toString()))
        {
            StringRequest sr = new StringRequest(Request.Method.POST,"http://192.168.43.234:3000/register", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("response = "+response);
                    if(response.equals("1"))
                    {
                        Toast.makeText(RegisterActivity.this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "REGISTERATION UNSUCCESSFULL!", Toast.LENGTH_SHORT).show();
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
                    params.put("name",name.getText().toString());
                    params.put("email",email.getText().toString());
                    params.put("contact",contact.getText().toString());
                    params.put("category",cat.getSelectedItem().toString());
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
            requestQueue.add(sr);
        }
        else
        {
            Toast.makeText(RegisterActivity.this,"Password Doesn't Match",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

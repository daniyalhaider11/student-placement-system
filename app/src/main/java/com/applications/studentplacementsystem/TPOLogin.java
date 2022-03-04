package com.applications.studentplacementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class TPOLogin extends AppCompatActivity {

    EditText tpousername, tpopassword;
    Button tpologin;

    final String tpologin_url = "#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_login);
        tpousername = findViewById(R.id.tpousername);
        tpopassword = findViewById(R.id.tpopassword);
        tpologin = findViewById(R.id.tpologinbutton);
        tpologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPOLogin(tpousername.getText().toString(), tpopassword.getText().toString());
            }
        });
    }

    public void TPOLogin(final String tpoemail, final String tpopass) {
        final String email = tpousername.getText().toString().trim();
        final String pass = tpopassword.getText().toString().trim();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("TPO Logging...");
        if (email.isEmpty()) {
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_LONG).show();
            return;
        } else if (pass.isEmpty()) {
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_LONG).show();
            return;
        } else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tpologin_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    if (response.trim().equals("TPO Login Successfully")) {
                        Intent intent = new Intent(TPOLogin.this, TPOArea.class);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> obj = new HashMap<>();
                    obj.put("temail", tpoemail);
                    obj.put("tpassword", tpopass);
                    return obj;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}
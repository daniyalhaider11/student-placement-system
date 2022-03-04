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

public class AddTPO extends AppCompatActivity {

    EditText tpo_name,tpo_email,tpo_password;
    Button add_tpo;
    String addtpo_url = "#";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tpo);
        tpo_name=findViewById(R.id.tpo_name);
        tpo_email=findViewById(R.id.tpo_email);
        tpo_password=findViewById(R.id.tpo_password);
        add_tpo=findViewById(R.id.add_tpo);
        add_tpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addtpo_credential(tpo_name.getText().toString(), tpo_email.getText().toString(),tpo_password.getText().toString());
            }
        });
    }

    public void Addtpo_credential(final String Tponame, final String Tpoemail, final String Tpopass) {
        final String tname = tpo_name.getText().toString().trim();
        final String temail = tpo_email.getText().toString().trim();
        final String tpass = tpo_password.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating TPO Account...");

        if (tname.isEmpty()) {
            Toast.makeText(this, "Enter TPO Name", Toast.LENGTH_LONG).show();
            return;
        } else if (temail.isEmpty()) {
            Toast.makeText(this, "Enter TPO Email", Toast.LENGTH_LONG).show();
            return;
        } else if (tpass.isEmpty()) {
            Toast.makeText(this, "Enter TPO Password", Toast.LENGTH_LONG).show();
            return;
        } else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, addtpo_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    if (response.trim().equals("Added TPO Successfully!!!")) {
                        Intent intent = new Intent(AddTPO.this, TPOLogin.class);
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
                    obj.put("tponame", Tponame);
                    obj.put("tpoemail", Tpoemail);
                    obj.put("tpopassword", Tpopass);
                    return obj;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }


    }
}
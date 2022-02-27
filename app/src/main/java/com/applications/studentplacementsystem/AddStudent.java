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

public class AddStudent extends AppCompatActivity {

    EditText std_name,std_branch,std_per,std_email,std_password;
    Button add_std;
    String addstu_url = "https://beltexco.000webhostapp.com/stdlogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        std_name = findViewById(R.id.std_name);
        std_branch = findViewById(R.id.std_branch);
        std_per = findViewById(R.id.std_per);
        std_email = findViewById(R.id.std_email);
        std_password = findViewById(R.id.std_password);
        add_std = findViewById(R.id.add_std);
        add_std.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addstu_credential(std_name.getText().toString(), std_branch.getText().toString(), std_per.getText().toString(), std_email.getText().toString(), std_password.getText().toString());
            }

        });
    }
    public void Addstu_credential(final String stuname,final String stubranch,final String stupercent,final String stuemail, final String stupass) {

        final String Stuname = std_name.getText().toString().trim();
        final String Stubranch = std_branch.getText().toString().trim();
        final String Stupercent = std_per.getText().toString().trim();
        final String Stuemail = std_email.getText().toString().trim();
        final String Stupass = std_password.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Student Account...");

        if (Stuname.isEmpty()) {
            Toast.makeText(this, "Enter Student Name", Toast.LENGTH_LONG).show();
            return;
        } else if (Stubranch.isEmpty()) {
            Toast.makeText(this, "Enter Student Branch", Toast.LENGTH_LONG).show();
            return;
        } else if (Stupercent.isEmpty()) {
            Toast.makeText(this, "Enter Student Percent", Toast.LENGTH_LONG).show();
            return;
        } else if (Stuemail.isEmpty()) {
            Toast.makeText(this, "Enter Student Email", Toast.LENGTH_LONG).show();
            return;
        } else if (Stupass.isEmpty()) {
            Toast.makeText(this, "Enter Student Password", Toast.LENGTH_LONG).show();
            return;
        } else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, addstu_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    if (response.trim().equals("Student Account Created Successfully!!")) {
                        Intent intent = new Intent(AddStudent.this, StudentLogin.class);
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

                    obj.put("std_name", Stuname);
                    obj.put("std_branch", Stubranch);
                    obj.put("std_per", Stupercent);
                    obj.put("std_email", Stuemail);
                    obj.put("std_password", Stupass);
                    return obj;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}
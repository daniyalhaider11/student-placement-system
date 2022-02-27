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

public class AddCompany extends AppCompatActivity {

    EditText Addcompname, Addcompdetails, Addcompjobdesc;
    Button add_company;
    final String addcompany_url = "https://spsys.000webhostapp.com/addcompany.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        Addcompname = (EditText)findViewById(R.id.addcomapanyname);
        Addcompdetails = (EditText)findViewById(R.id.addcompanydetails);
        Addcompjobdesc = (EditText)findViewById(R.id.addcompanyjobdesc);
        add_company = (Button) findViewById(R.id.add_company);
        add_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcompany_detail(Addcompname.getText().toString(),Addcompdetails.getText().toString(),Addcompjobdesc.getText().toString());
            }
        });
    }

    public void addcompany_detail(final String Compname, final String Compdetail, final String Compjobdetail) {
        final String compname = Addcompname.getText().toString().trim();
        final String compdesc = Addcompdetails.getText().toString().trim();
        final String compjobdesc = Addcompjobdesc.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Company Detail...");

        if (compname.isEmpty()) {
            Toast.makeText(this, "Enter Company Name", Toast.LENGTH_LONG).show();
            return;
        } else if (compdesc.isEmpty()) {
            Toast.makeText(this, "Enter Company Description", Toast.LENGTH_LONG).show();
            return;
        } else if (compjobdesc.isEmpty()) {
            Toast.makeText(this, "Enter Job Description", Toast.LENGTH_LONG).show();
            return;
        } else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, addcompany_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    if (response.trim().equals("Company Details Added Successfully!!")) {
                        Intent intent = new Intent(AddCompany.this, TPOArea.class);
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
                    obj.put("compname", Compname);
                    obj.put("compdesc", Compdetail);
                    obj.put("compjobdesc", Compjobdetail);
                    return obj;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}
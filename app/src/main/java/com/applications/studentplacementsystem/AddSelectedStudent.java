package com.applications.studentplacementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSelectedStudent extends AppCompatActivity {

    EditText stuskill, stunumber,stuaddress;
    Spinner fetchstudata;
    Button addstudecord;
    ArrayList<String> studlist =new ArrayList<>();
    ArrayAdapter<String> adapter;
    String fetch_url = "https://spsys.000webhostapp.com/fetchstudent.php";
    String insertstu_url = "https://spsys.000webhostapp.com/addselectedstudent.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_selected_student);
        stuskill = findViewById(R.id.studentskill);
        stunumber = findViewById(R.id.studentnumber);
        stuaddress = findViewById(R.id.studentaddress);
        fetchstudata = findViewById(R.id.fetchstudent);
        addstudecord = findViewById(R.id.btnaddselectstu);
        studlist.add("Select Student");
        populateSpinner();


        adapter =new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,studlist);
        fetchstudata.setAdapter(adapter);

        addstudecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  myval = String.valueOf(fetchstudata.getSelectedItem());
                addselected_student(stuskill.getText().toString(),stunumber.getText().toString(),
                        stuaddress.getText().toString(),myval);
            }
        });

    }
    public void addselected_student(final String Studentskill,final String Studentnumber,final String Studentaddress,
                                    final String g_id) {
        final String studentname = stuskill.getText().toString().trim();
        final String studentnumber = stunumber.getText().toString().trim();
        final String studentaddress = stuaddress.getText().toString().trim();


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding Student Detail...");

        if (studentname.isEmpty()) {
            Toast.makeText(this, "Enter Student Skill Name", Toast.LENGTH_LONG).show();
            return;
        } else if (studentnumber.isEmpty()) {
            Toast.makeText(this, "Enter Student Number", Toast.LENGTH_LONG).show();
            return;
        } else if (studentaddress.isEmpty()) {
            Toast.makeText(this, "Enter Student Address", Toast.LENGTH_LONG).show();
            return;
        } else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, insertstu_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //  progressDialog.hide();
                    if (response.trim().equals("Added Selected Detail Successfully")) {
                        Toast.makeText(getApplicationContext(), "Selected Student add Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddSelectedStudent.this, TPOArea.class);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    } else if (response.trim().equals("Selected Student Detail Not Added Try Again")) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("stuskill", Studentskill);
                    map.put("stunum", Studentnumber);
                    map.put("stuaddress", Studentaddress);
                    map.put("drop",g_id);
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
    private void populateSpinner()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, fetch_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<String>list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("studata");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        list.add(object.getString("stu_id"));
                        list.add(object.getString("stu_name"));

                    }
                    studlist.addAll(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
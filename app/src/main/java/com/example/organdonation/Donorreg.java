package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Donorreg extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
    String gender,fname,lname,age,email,phone,pin,uname,pass;
    Spinner s1;
    String[] group,gr_id,value;
    public static String group_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorreg);
        EditText e1 =(EditText) findViewById(R.id.donfname);
        EditText e2 =(EditText) findViewById(R.id.donlname);
        EditText e3 =(EditText) findViewById(R.id.donage);
        EditText e4 =(EditText) findViewById(R.id.donemail);
        EditText e5 =(EditText) findViewById(R.id.donphone);
        EditText e6 =(EditText) findViewById(R.id.donpin);
        EditText e7 =(EditText) findViewById(R.id.donuname);
        EditText e8 =(EditText) findViewById(R.id.donpas);

        RadioButton r1 =(RadioButton) findViewById(R.id.r1);
        RadioButton r2 =(RadioButton) findViewById(R.id.r2);

        s1 = (Spinner) findViewById(R.id.spinner);
        s1.setOnItemSelectedListener(this);

        Button b1=(Button) findViewById(R.id.btreg);


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Donorreg.this;
        String q = "/viewbloodtype";
        q = q.replace(" ", "%20");
        JR.execute(q);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (r1.isChecked()){
                    gender="male";

                }else {
                    gender="female";
                }
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                age=e3.getText().toString();
                email=e4.getText().toString();
                phone=e5.getText().toString();
                pin=e6.getText().toString();
                uname=e7.getText().toString();
                pass=e8.getText().toString();

                if (fname.equalsIgnoreCase("")){

                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);

                }else if(lname.equalsIgnoreCase("")){

                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                }
                else if(uname.equalsIgnoreCase("")){

                    e7.setError("Enter your Username");
                    e7.setFocusable(true);
                }
                else if(pass.equalsIgnoreCase("")){

                    e8.setError("Enter your Password");
                    e8.setFocusable(true);
                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Donorreg.this;
                    String q = "/donreg?fname=" + fname + "&lname=" + lname + "&age=" + age + "&email=" + email + "&gender=" + gender + "&phone=" + phone + "&pincode=" + pin + "&username=" + uname + "&password=" + pass+"&group_id="+group_id;

                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }


            }
        });


    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("donreg")) {


            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {



                Toast.makeText(getApplicationContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));


            }
            else if (status.equalsIgnoreCase("already")) {
                Toast.makeText(getApplicationContext(), "Username Or Password ALREADY EXIST", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Donorreg.class));

            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }



            }

            if (method.equalsIgnoreCase("viewbloodtype")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    group = new String[ja1.length()];

                    gr_id = new String[ja1.length()];


                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        group[i] = ja1.getJSONObject(i).getString("group_name");

                        gr_id[i] = ja1.getJSONObject(i).getString("group_id");

                        value[i] = group[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    s1.setAdapter(ar);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        group_id=gr_id[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
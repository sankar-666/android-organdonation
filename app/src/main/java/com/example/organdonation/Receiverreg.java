package com.example.organdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class Receiverreg extends AppCompatActivity implements JsonResponse {
    String state,fname,lname,hname,place,phone,pin,uname,pass,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiverreg);
        EditText e1 =(EditText) findViewById(R.id.rfname);
        EditText e2 =(EditText) findViewById(R.id.rlname);
        EditText e3 =(EditText) findViewById(R.id.rhname);
        EditText e4 =(EditText) findViewById(R.id.rplace);
        EditText e5 =(EditText) findViewById(R.id.rpin);
        EditText e6 =(EditText) findViewById(R.id.rstate);
        EditText e7 =(EditText) findViewById(R.id.rphone);
        EditText e8 =(EditText) findViewById(R.id.remail);
        EditText e9 =(EditText) findViewById(R.id.runame);
        EditText e10 =(EditText) findViewById(R.id.rpass);

        Button b1=(Button) findViewById(R.id.bfor);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname=e1.getText().toString();
                lname=e2.getText().toString();
                hname=e3.getText().toString();
                place=e4.getText().toString();
                pin=e5.getText().toString();
                state=e6.getText().toString();
                phone=e7.getText().toString();
                email=e8.getText().toString();
                uname=e9.getText().toString();
                pass=e10.getText().toString();

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
                    JR.json_response = (JsonResponse) Receiverreg.this;
                    String q = "/receiverreg?fname=" + fname + "&lname=" + lname + "&housename=" + hname + "&email=" + email + "&state=" + state + "&phone=" + phone + "&pincode=" + pin + "&username=" + uname + "&password=" + pass + "&place=" + place;

                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }

            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {



                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));


            }
            if (status.equalsIgnoreCase("already")) {
                Toast.makeText(getApplicationContext(), "Username Or Password ALREADY EXIST", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Receiverreg.class));

            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}